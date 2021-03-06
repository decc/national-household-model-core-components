package uk.org.cse.nhm.simulator.state.dimensions.energy;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;
import javax.inject.Named;

import org.pojomatic.annotations.AutoProperty;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculationResult;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculator;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IHeatingSchedule;
import uk.org.cse.nhm.energycalculator.api.ISeasonalParameters;
import uk.org.cse.nhm.energycalculator.api.IWeather;
import uk.org.cse.nhm.energycalculator.api.impl.BredemExternalParameters;
import uk.org.cse.nhm.energycalculator.api.impl.DailyHeatingSchedule;
import uk.org.cse.nhm.energycalculator.api.impl.SAPExternalParameters;
import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.MonthType;
import uk.org.cse.nhm.energycalculator.api.types.RegionType.Country;
import uk.org.cse.nhm.energycalculator.impl.BredemSeasonalParameters;
import uk.org.cse.nhm.energycalculator.impl.SAPSeasonalParameters;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.energycalculator.api.types.SiteExposureType;
import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.util.EObjectWrapper;
import uk.org.cse.nhm.hom.people.People;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulator.state.dimensions.FuelServiceTable;
import uk.org.cse.nhm.simulator.state.dimensions.behaviour.IHeatingBehaviour;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;

/**
 * Glue that runs a energy calculator from within the simulator.
 *
 * @author hinton
 *
 */
public class EnergyCalculatorBridge implements IEnergyCalculatorBridge {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(EnergyCalculatorBridge.class);

	public static final String CACHE_SIZE = "CACHE_SIZE";

	//TODO get tariff type from somewhere
	private static final ElectricityTariffType tariffType = ElectricityTariffType.ECONOMY_7;

	private final LoadingCache<Wrapper, Result> cache;
	private long requests;

	private static class Result implements IPowerTable {
		private static final float WATT_DAYS_TO_KWH = 24f / 1000f;

        private final FuelServiceTable table;

        private final float specificHeatLoss, fabricHeatLoss, ventilationHeatLoss, thermalBridgingHeatLoss;
        private final float airChangeRate;
		private final float meanInternalTemperature;
        private final float[][] heatLoad = new float[MonthType.values().length][2];
        private final float hotWaterDemand;
        private final float primaryHeatDemand;
        private final float secondaryHeatDemand;

        Result(final IEnergyCalculationResult[] months) {
            float specificHeatLoss = 0;
            float fabricHeatLoss = 0;
            float ventilationHeatLoss = 0;
            float thermalBridgingHeatLoss = 0;

            float airChangeRate = 0;
            float meanInternalTemperature = 0;

            final FuelServiceTable.Builder builder = FuelServiceTable.builder();

            float hotWaterDemandAccum = 0;
            float primaryHeatDemandAccum = 0;
            float secondaryHeatDemandAccum = 0;

            for (final MonthType m : MonthType.values()) {
                final IEnergyCalculationResult result = months[m.ordinal()];
                final IEnergyState energyState = result.getEnergyState();

                final double wattsPerDayToKWHPerMonth = m.getStandardDays() * WATT_DAYS_TO_KWH;

                hotWaterDemandAccum += wattsPerDayToKWHPerMonth * energyState.getTotalSupply(
                		EnergyType.DemandsHOT_WATER,
                		ServiceType.WATER_HEATING);

                primaryHeatDemandAccum += wattsPerDayToKWHPerMonth * energyState.getTotalSupply(
                		EnergyType.DemandsHEAT,
                		ServiceType.PRIMARY_SPACE_HEATING);

                secondaryHeatDemandAccum += wattsPerDayToKWHPerMonth * energyState.getTotalSupply(
                		EnergyType.DemandsHEAT,
                		ServiceType.SECONDARY_SPACE_HEATING);

                for (final EnergyType et : EnergyType.allFuels) {
                    final FuelType ft = FuelType.of(et);
                    for (final ServiceType st : ServiceType.values()) {
                        final double kWhUsed = wattsPerDayToKWHPerMonth
							* (energyState.getTotalDemand(et, st) - energyState.getTotalSupply(et, st));

                        builder.add(ft, st, kWhUsed);

                        if (Double.isInfinite(kWhUsed) || Double.isNaN(kWhUsed)) {
                            log.error("Used {} kWh of {} for {} in {}", new Object[] {kWhUsed, ft, st, m});
                        }
                    }
                }

                {
                    final ISpecificHeatLosses losses = result.getHeatLosses();
                    specificHeatLoss        += losses.getSpecificHeatLoss()    * m.getStandardDays();
                    fabricHeatLoss          += losses.getFabricLoss()          * m.getStandardDays();
                    ventilationHeatLoss     += losses.getVentilationLoss()     * m.getStandardDays();
                    thermalBridgingHeatLoss += losses.getThermalBridgeEffect() * m.getStandardDays();
                }

                meanInternalTemperature +=
                    m.getStandardDays() *
                    result.getEnergyState().getTotalSupply(EnergyType.HackMEAN_INTERNAL_TEMPERATURE);

                airChangeRate += result.getHeatLosses().getAirChangeRate() * m.getStandardDays();

                final float convert = m.getStandardDays() * WATT_DAYS_TO_KWH;
                heatLoad[m.ordinal()][0] = (float) (convert * result.getEnergyState().getTotalDemand(EnergyType.DemandsHEAT));
                heatLoad[m.ordinal()][1] = (float) (convert * result.getEnergyState().getTotalDemand(EnergyType.DemandsHOT_WATER));
            }

            this.specificHeatLoss        = specificHeatLoss        / 365f;
            this.fabricHeatLoss          = fabricHeatLoss          / 365f;
            this.ventilationHeatLoss     = ventilationHeatLoss     / 365f;
            this.thermalBridgingHeatLoss = thermalBridgingHeatLoss / 365f;

            this.airChangeRate = airChangeRate;
            this.meanInternalTemperature = meanInternalTemperature;

            this.primaryHeatDemand = primaryHeatDemandAccum;
            this.secondaryHeatDemand = secondaryHeatDemandAccum;
            this.hotWaterDemand = hotWaterDemandAccum;

            this.table = builder.build();
        }

		@Override
		public float getFuelUseByEnergyService(final ServiceType es, final FuelType ft) {
            return table.get(ft, es);
		}

		@Override
		public float getFuelUseByEnergyService(final List<ServiceType> es, final FuelType ft) {
			float accum = 0f;
			for (final ServiceType serviceType : es) {
				accum += getFuelUseByEnergyService(serviceType, ft);
			}
			return accum;
		}

		@Override
		public float getPowerByFuel(final FuelType ft) {
            return table.get(ft);
		}

		@Override
		public float getSpecificHeatLoss() {
            return specificHeatLoss;
		}

        @Override
        public float getFabricHeatLoss() {
            return fabricHeatLoss;
        }

        @Override
        public float getVentilationHeatLoss() {
            return ventilationHeatLoss;
        }

        @Override
        public float getThermalBridgingHeatLoss() {
            return thermalBridgingHeatLoss;
        }

		@Override
		public float getAirChangeRate() {
            return airChangeRate / 365f;
		}

        @Override
		public float getMeanInternalTemperature() {
			return meanInternalTemperature / 365f;
		}

        @Override
        public float getWeightedHeatLoad(final double[] weights, final boolean space, final boolean water) {
    	    float acc = 0;
    	    final double hw = space ? 1 : 0;
    	    final double ww = water ? 1 : 0;
            for (int i = 0; i<heatLoad.length && i < weights.length; i++) {
            	acc += weights[i] * (heatLoad[i][0] * hw + heatLoad[i][1] * ww);
            }
            return acc;
        }

		@Override
		public float getPrimaryHeatDemand() {
			return primaryHeatDemand;
		}

		@Override
		public float getSecondaryHeatDemand() {
			return secondaryHeatDemand;
		}

		@Override
		public float getHotWaterDemand() {
			return hotWaterDemand;
		}
	}

	@AutoProperty
	private static class Wrapper implements IEnergyCalculatorHouseCase {
		final StructureModel structure;
		final EObjectWrapper<ITechnologyModel> technology;
		public final IWeather weather;
		public final double people;
		private final int buildYear;
		private final double latitudeRadians;
		private final IHeatingBehaviour heatingBehaviour;
		private final SiteExposureType siteExposure;
		private final Country country;
		private final int hash;


		public Wrapper(final StructureModel structure, final ITechnologyModel technology, final BasicCaseAttributes attributes, final IWeather weather, final double npeople, final IHeatingBehaviour behaviour) {
			this.structure = structure;
			this.technology = new EObjectWrapper<ITechnologyModel>(technology);
			this.weather = weather;
			this.buildYear = attributes.getBuildYear();
			this.latitudeRadians = attributes.getRegionType().getLatitudeRadians();
			this.siteExposure = attributes.getSiteExposure();
			this.heatingBehaviour = behaviour;
			this.country = attributes.getRegionType().getCountry();

			switch(behaviour.getEnergyCalculatorType()) {
			case SAP2012:
				/*
				 * This field is derived from floor area in SAP 2012 mode, so we set it to a dummy value.
				 * See {@link SAPExternalParameters}
				 */
				this.people = 0;
				break;
			case BREDEM2012:
				this.people = npeople;
				break;
			default:
				throw new UnsupportedOperationException("Unknown energy calculator type when constructing EnergyCalcujlatorBridge.Wrapper " + behaviour.getEnergyCalculatorType());
			}

			this.hash = _hashCode();
		}

		@Override
		public void accept(final IConstants constants, final IEnergyCalculatorParameters parameters, final IEnergyCalculatorVisitor visitor) {
			structure.accept(visitor);
			technology.unwrap().accept(constants, parameters, visitor, new AtomicInteger(), null);
		}

		@Override
		public double getFloorArea() {
			return structure.getFloorArea();
		}

		@Override
		public double getLivingAreaProportionOfFloorArea() {
			return structure.getLivingAreaProportionOfFloorArea();
		}

		@Override
		public double getInterzoneSpecificHeatLoss() {
			return structure.getInterzoneSpecificHeatLoss();
		}

		@Override
		public double getHouseVolume() {
			return structure.getVolume();
		}

		@Override
		public int getNumberOfStoreys() {
			return structure.getNumberOfStoreys();
		}

		@Override
		public boolean hasDraughtLobby() {
			return structure.hasDraughtLobby();
		}

		@Override
		public double getZoneTwoHeatedProportion() {
			/*
			BEISDOC
			NAME: Zone 2 Heated Proportion
			DESCRIPTION: The fraction of Zone 2 which is heated. This should be a number between 0 and 1. A dwelling with central heating usually has the value 1.
			TYPE: value
			UNIT: Dimensionless
			BREDEM: Section 7 fz2htd Input
			NOTES: Always 100% in SAP 2012 mode.
			ID: zone-2-heated-proportion
			CODSIEB
			*/
			switch(heatingBehaviour.getEnergyCalculatorType()) {
			case SAP2012:
				return 1d;
			case BREDEM2012:
				return structure.getZoneTwoHeatedProportion();
			default:
				throw new RuntimeException("Unknown energy calculator type while working out zone 2 heated proportion " + heatingBehaviour.getEnergyCalculatorType());
			}
		}

		@Override
		public int getBuildYear() {
			return buildYear;
		}

		@Override
		public double getDraughtStrippedProportion() {
			return structure.getDraughtStrippedProportion();
		}

		@Override
		public int getNumberOfShelteredSides() {
			return structure.getNumberOfShelteredSides();
		}

		@Override
		public SiteExposureType getSiteExposure() {
			return siteExposure;
		}

		public double getLatitudeRadians() {
			return latitudeRadians;
		}

		@Override
		public double getThermalBridgingCoefficient() {
			return structure.getThermalBridgingCoefficient();
		}

		@Override
		public boolean hasReducedInternalGains() {
			switch (heatingBehaviour.getEnergyCalculatorType()) {
			case SAP2012:
				return false;
			case BREDEM2012:
				return structure.hasReducedInternalGains();
			default:
				throw new UnsupportedOperationException("Unknown energy calculator type when trying to determine if we should use reduced internal gains " + heatingBehaviour.getEnergyCalculatorType());
			}
		}

		@Override
		public Country getCountry() {
			return country;
		}

		@Override
		public int hashCode() {
			return this.hash;
		}

		protected int _hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + buildYear;
			result = prime * result + ((country == null) ? 0 : country.hashCode());
			result = prime * result + ((heatingBehaviour == null) ? 0 : heatingBehaviour.hashCode());
			long temp;
			temp = Double.doubleToLongBits(latitudeRadians);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			temp = Double.doubleToLongBits(people);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			result = prime * result + ((siteExposure == null) ? 0 : siteExposure.hashCode());
			result = prime * result + ((structure == null) ? 0 : structure.hashCode());
			result = prime * result + ((technology == null) ? 0 : technology.hashCode());
			result = prime * result + ((weather == null) ? 0 : weather.hashCode());
			return result;
		}

		/**
		 * equals generated using Eclipse source menu.
		 * Includes all fields except "hash".
		 */
		@Override
		public boolean equals(final Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final Wrapper other = (Wrapper) obj;
			if (buildYear != other.buildYear)
				return false;
			if (country != other.country)
				return false;
			if (heatingBehaviour == null) {
				if (other.heatingBehaviour != null)
					return false;
			} else if (!heatingBehaviour.equals(other.heatingBehaviour))
				return false;
			if (Double.doubleToLongBits(latitudeRadians) != Double.doubleToLongBits(other.latitudeRadians))
				return false;
			if (Double.doubleToLongBits(people) != Double.doubleToLongBits(other.people))
				return false;
			if (siteExposure != other.siteExposure)
				return false;
			if (structure == null) {
				if (other.structure != null)
					return false;
			} else if (!structure.equals(other.structure))
				return false;
			if (technology == null) {
				if (other.technology != null)
					return false;
			} else if (!technology.equals(other.technology))
				return false;
			if (weather == null) {
				if (other.weather != null)
					return false;
			} else if (!weather.equals(other.weather))
				return false;
			return true;
		}
	}

	@Inject
	public EnergyCalculatorBridge(final IEnergyCalculator calculator, @Named(CACHE_SIZE) final int cacheSize) {
		this.cache = CacheBuilder.newBuilder().
				softValues().
				recordStats().
				maximumSize(cacheSize).
				expireAfterAccess(20, TimeUnit.MINUTES).build(
					new CacheLoader<Wrapper, Result>() {
						@Override
						public Result load(final Wrapper key) throws Exception {

							final IEnergyCalculatorParameters parameters = createParameters(key.heatingBehaviour, key.structure.getFloorArea(), key.people);

							final ISeasonalParameters[] climate = new ISeasonalParameters[MonthType.values().length];

							for (final MonthType m : MonthType.values()) {
								switch (key.heatingBehaviour.getEnergyCalculatorType()) {
								case SAP2012:
									climate[m.ordinal()] = new SAPSeasonalParameters(m);
									break;
								case BREDEM2012:
									final double externalTemperature = key.weather.getExternalTemperature(m);

									climate[m.ordinal()] = new BredemSeasonalParameters(
											m,
											externalTemperature,
											key.weather.getWindSpeed(m),
											key.weather.getHorizontalSolarFlux(m),
											key.getLatitudeRadians(),

											key.heatingBehaviour.getHeatingMonths().contains(m) ?
													key.heatingBehaviour.getHeatingSchedule() :
														DailyHeatingSchedule.OFF,

											Optional.<IHeatingSchedule>absent()
											);
								break;
								default:
									throw new UnsupportedOperationException("Unknown energy calculator type when preparing seasonal parameters " + key.heatingBehaviour.getEnergyCalculatorType());
								};
							}

                            return new Result(calculator.evaluate(key, parameters, climate));
						}

						private IEnergyCalculatorParameters createParameters(final IHeatingBehaviour heatingBehaviour, final double floorArea, final double occupancy) {
							switch (heatingBehaviour.getEnergyCalculatorType()) {
							case BREDEM2012:
								return new BredemExternalParameters(
										tariffType,
										heatingBehaviour.getLivingAreaDemandTemperature(),
										heatingBehaviour.getSecondAreaDemandTemperature(),
										heatingBehaviour.getTemperatureDifference(),
										occupancy
									);
							case SAP2012:
								return new SAPExternalParameters(tariffType, floorArea);
							default:
								throw new IllegalArgumentException("Unknown energy calculator type when creating energy calculator parameters " + heatingBehaviour.getEnergyCalculatorType());
							}
						}
					}
				);
	}

	@Override
	public IPowerTable evaluate(
			final IWeather weather,
			final StructureModel structure,
			final ITechnologyModel technology,
			final BasicCaseAttributes attributes,
			final People people,
			final IHeatingBehaviour behaviour) {
		Preconditions.checkNotNull(weather, "Weather was null");
		Preconditions.checkNotNull(structure, "Structure was null");
		Preconditions.checkNotNull(technology, "Technology was null");
		Preconditions.checkNotNull(attributes, "Basic attributes were null");
		Preconditions.checkNotNull(people, "People was null");
		Preconditions.checkNotNull(behaviour, "Heating behaviour was null");

		requests++;
		final Wrapper w = new Wrapper(structure, technology, attributes, weather, people.getOccupancy(), behaviour);
		try {
			return cache.get(w);
		} catch (final Exception e) {
			throw new RuntimeException("Exception in energy calculator: " + e.getMessage() + " for "
					+attributes.getAacode()
					, e);
		} finally {
			if (requests % 10000 == 0) {
				log.debug("Cache details: {}", cache.stats());
			}
		}
	}
}
