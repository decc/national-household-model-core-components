package uk.org.cse.nhm.hom.emf.technologies.impl.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.impl.EnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.energycalculator.api.types.TransducerPhaseType;
import uk.org.cse.nhm.hom.constants.HeatingSystemConstants;
import uk.org.cse.nhm.hom.constants.SplitRateConstants;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.impl.PointOfUseWaterHeaterImpl;

/**
 * A utility class associated with {@link PointOfUseWaterHeaterImpl} which is supplied into the energy calculation
 * to satisfy some hot water demand in the manner of a point of use water heater.
 * 
 * @author hinton
 *
 */
public class PointOfUseWaterHeaterTransducer extends EnergyTransducer {
	private static final Logger log = LoggerFactory.getLogger(PointOfUseWaterHeaterTransducer.class);
	private final double proportion;
	private final boolean multipoint;
	private final double efficiency;
	private final FuelType fuelType;

	public PointOfUseWaterHeaterTransducer(final int priority, final FuelType fuelType, final double proportion, final double efficiency, final boolean multipoint) {
		super(ServiceType.WATER_HEATING, priority);
		this.proportion = proportion;
		this.efficiency = efficiency;
		this.fuelType = fuelType;
		this.multipoint = multipoint;
		if (this.fuelType == FuelType.ELECTRICITY && this.efficiency != 1) {
			log.warn("Constructed with fuel type {} and efficiency {}, which should really be 1", fuelType, efficiency);
		}
	}

	@Override
	public void generate(final IEnergyCalculatorHouseCase house,
			final IInternalParameters parameters, final ISpecificHeatLosses losses,
			final IEnergyState state) {
		final double toSatisfy = state.getBoundedTotalDemand(EnergyType.DemandsHOT_WATER, proportion);
		log.debug("satisfying demand for {} of hot water", toSatisfy);
		state.increaseSupply(EnergyType.DemandsHOT_WATER, toSatisfy);
		state.increaseSupply(EnergyType.GainsHOT_WATER_USAGE_GAINS, toSatisfy); // this gets multiplied down in the gains transducer
		final IConstants constants = parameters.getConstants();
		
		/*
		BEISDOC
		NAME: Point-of-use Distribution Losses
		DESCRIPTION: Point of use distribution losses are applied it if it multipoint.
		TYPE: formula
		UNIT: W
		SAP: (46)
		BREDEM: 2.2A
		DEPS: distribution-loss-factor,usage-adjusted-water-volume,is-main-weater-heating
		ID: point-of-use-distribution-loss
		CODSIEB
		*/
		final double distributionLossFactor = 
				multipoint ? constants.get(HeatingSystemConstants.CENTRAL_HEATING_DISTRIBUTION_LOSSES) : 0.0;
		
		final double distributionLosses = toSatisfy * distributionLossFactor;
		
		final double requiredEnergy = toSatisfy + distributionLosses;
		
		state.increaseSupply(EnergyType.GainsHOT_WATER_SYSTEM_GAINS, distributionLosses);
		
		/*
		BEISDOC
		NAME: Point-of-use Fuel Energy Demand 
		DESCRIPTION: The amount of fuel consumed by a point-of-use water heater. 
		TYPE: formula
		UNIT: W
		SAP: (64,217,219)
		BREDEM: 2.2A-2.2D
		DEPS: is-main-weater-heating,usage-adjusted-water-volume,point-of-use-distribution-losses
		ID: point-of-use-fuel-energy-demand
		CODSIEB
		*/
		if (fuelType == FuelType.ELECTRICITY) {
			state.increaseElectricityDemand(constants.get(SplitRateConstants.DEFAULT_FRACTIONS, parameters.getTarrifType()), 
					requiredEnergy);
		} else {
			state.increaseDemand(fuelType.getEnergyType(), requiredEnergy/efficiency);
		}
	}
	
	@Override
	public TransducerPhaseType getPhase() {
		return TransducerPhaseType.HotWater;
	}
}
