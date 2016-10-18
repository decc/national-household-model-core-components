/**
 */
package uk.org.cse.nhm.hom.emf.technologies.boilers.impl;

import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IEnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.impl.ElectricHeatTransducer;
import uk.org.cse.nhm.energycalculator.api.impl.EnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.impl.HeatTransducer;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.energycalculator.api.types.TransducerPhaseType;
import uk.org.cse.nhm.hom.IHeatProportions;
import uk.org.cse.nhm.hom.constants.PumpAndFanConstants;
import uk.org.cse.nhm.hom.constants.SplitRateConstants;
import uk.org.cse.nhm.hom.constants.adjustments.EfficiencyAdjustments;
import uk.org.cse.nhm.hom.constants.adjustments.TemperatureAdjustments;
import uk.org.cse.nhm.hom.emf.technologies.EmitterType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType;
import uk.org.cse.nhm.hom.emf.technologies.IWaterTank;
import uk.org.cse.nhm.hom.emf.technologies.boilers.FlueType;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersPackage;
import uk.org.cse.nhm.hom.emf.technologies.boilers.impl.util.FlueVentilationHelper;
import uk.org.cse.nhm.hom.emf.technologies.impl.HeatSourceImpl;
import uk.org.cse.nhm.hom.emf.technologies.impl.util.HotWaterUtilities;
import uk.org.cse.nhm.hom.emf.technologies.impl.util.Pump;
import uk.org.cse.nhm.hom.emf.util.Efficiency;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Boiler</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.boilers.impl.BoilerImpl#getSummerEfficiency <em>Summer Efficiency</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.boilers.impl.BoilerImpl#getWinterEfficiency <em>Winter Efficiency</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.boilers.impl.BoilerImpl#isCondensing <em>Condensing</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.boilers.impl.BoilerImpl#isWeatherCompensated <em>Weather Compensated</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.boilers.impl.BoilerImpl#getBasicResponsiveness <em>Basic Responsiveness</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.boilers.impl.BoilerImpl#isPumpInHeatedSpace <em>Pump In Heated Space</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BoilerImpl extends HeatSourceImpl implements IBoiler {
	private static final Logger log = LoggerFactory.getLogger(BoilerImpl.class);
	
	/**
	 * The default value of the '{@link #getSummerEfficiency() <em>Summer Efficiency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSummerEfficiency()
	 * @generated
	 * @ordered
	 */
	protected static final Efficiency SUMMER_EFFICIENCY_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getSummerEfficiency() <em>Summer Efficiency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSummerEfficiency()
	 * @generated
	 * @ordered
	 */
	protected Efficiency summerEfficiency = SUMMER_EFFICIENCY_EDEFAULT;
	/**
	 * The default value of the '{@link #getWinterEfficiency() <em>Winter Efficiency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWinterEfficiency()
	 * @generated
	 * @ordered
	 */
	protected static final Efficiency WINTER_EFFICIENCY_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getWinterEfficiency() <em>Winter Efficiency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWinterEfficiency()
	 * @generated
	 * @ordered
	 */
	protected Efficiency winterEfficiency = WINTER_EFFICIENCY_EDEFAULT;

	/**
	 * The default value of the '{@link #isCondensing() <em>Condensing</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCondensing()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CONDENSING_EDEFAULT = false;
	/**
	 * The flag representing the value of the '{@link #isCondensing() <em>Condensing</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCondensing()
	 * @generated
	 * @ordered
	 */
	protected static final int CONDENSING_EFLAG = 1 << 9;

	/**
	 * The default value of the '{@link #isWeatherCompensated() <em>Weather Compensated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isWeatherCompensated()
	 * @generated
	 * @ordered
	 */
	protected static final boolean WEATHER_COMPENSATED_EDEFAULT = false;
	/**
	 * The flag representing the value of the '{@link #isWeatherCompensated() <em>Weather Compensated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isWeatherCompensated()
	 * @generated
	 * @ordered
	 */
	protected static final int WEATHER_COMPENSATED_EFLAG = 1 << 10;

	/**
	 * The default value of the '{@link #getBasicResponsiveness() <em>Basic Responsiveness</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBasicResponsiveness()
	 * @generated
	 * @ordered
	 */
	protected static final double BASIC_RESPONSIVENESS_EDEFAULT = 1.0;
	/**
	 * The cached value of the '{@link #getBasicResponsiveness() <em>Basic Responsiveness</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBasicResponsiveness()
	 * @generated
	 * @ordered
	 */
	protected double basicResponsiveness = BASIC_RESPONSIVENESS_EDEFAULT;

	/**
	 * The default value of the '{@link #isPumpInHeatedSpace() <em>Pump In Heated Space</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPumpInHeatedSpace()
	 * @generated
	 * @ordered
	 */
	protected static final boolean PUMP_IN_HEATED_SPACE_EDEFAULT = false;
	/**
	 * The flag representing the value of the '{@link #isPumpInHeatedSpace() <em>Pump In Heated Space</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPumpInHeatedSpace()
	 * @generated
	 * @ordered
	 */
	protected static final int PUMP_IN_HEATED_SPACE_EFLAG = 1 << 11;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BoilerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBoilersPackage.Literals.BOILER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Efficiency getSummerEfficiency() {
		return summerEfficiency;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSummerEfficiency(Efficiency newSummerEfficiency) {
		Efficiency oldSummerEfficiency = summerEfficiency;
		summerEfficiency = newSummerEfficiency;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBoilersPackage.BOILER__SUMMER_EFFICIENCY, oldSummerEfficiency, summerEfficiency));
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Efficiency getWinterEfficiency() {
		return winterEfficiency;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWinterEfficiency(Efficiency newWinterEfficiency) {
		Efficiency oldWinterEfficiency = winterEfficiency;
		winterEfficiency = newWinterEfficiency;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBoilersPackage.BOILER__WINTER_EFFICIENCY, oldWinterEfficiency, winterEfficiency));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isCondensing() {
		return (flags & CONDENSING_EFLAG) != 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCondensing(boolean newCondensing) {
		boolean oldCondensing = (flags & CONDENSING_EFLAG) != 0;
		if (newCondensing) flags |= CONDENSING_EFLAG; else flags &= ~CONDENSING_EFLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBoilersPackage.BOILER__CONDENSING, oldCondensing, newCondensing));
	}

	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isWeatherCompensated() {
		return (flags & WEATHER_COMPENSATED_EFLAG) != 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setWeatherCompensated(boolean newWeatherCompensated) {
		boolean oldWeatherCompensated = (flags & WEATHER_COMPENSATED_EFLAG) != 0;
		if (newWeatherCompensated) flags |= WEATHER_COMPENSATED_EFLAG; else flags &= ~WEATHER_COMPENSATED_EFLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBoilersPackage.BOILER__WEATHER_COMPENSATED, oldWeatherCompensated, newWeatherCompensated));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getBasicResponsiveness() {
		return basicResponsiveness;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBasicResponsiveness(double newBasicResponsiveness) {
		double oldBasicResponsiveness = basicResponsiveness;
		basicResponsiveness = newBasicResponsiveness;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBoilersPackage.BOILER__BASIC_RESPONSIVENESS, oldBasicResponsiveness, basicResponsiveness));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isPumpInHeatedSpace() {
		return (flags & PUMP_IN_HEATED_SPACE_EFLAG) != 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPumpInHeatedSpace(boolean newPumpInHeatedSpace) {
		boolean oldPumpInHeatedSpace = (flags & PUMP_IN_HEATED_SPACE_EFLAG) != 0;
		if (newPumpInHeatedSpace) flags |= PUMP_IN_HEATED_SPACE_EFLAG; else flags &= ~PUMP_IN_HEATED_SPACE_EFLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBoilersPackage.BOILER__PUMP_IN_HEATED_SPACE, oldPumpInHeatedSpace, newPumpInHeatedSpace));
	}

	/**
	 * This is the water heating efficiency (the weighted average of winter and summer), 
	 * with adjustments from SAP 2009 Table 4c applied.
	 * 
	 * This is overridden in CPSUs and combis, because their water efficiency is not affected
	 * by the thermostatic control of space heating.
	 * 
	 * @param qWater
	 * @param qSpace
	 * @return
	 */
	protected double getWaterHeatingEfficiency(final IConstants constants, final double qWater, final double qSpace) {
		final double seasonalEfficiency = getSeasonalEfficiency(qWater, qSpace);
		
		// apply adjustments due to control systems
		if (getSpaceHeater() != null && getSpaceHeater().isThermostaticallyControlled()) {
			return seasonalEfficiency;
		} else {
			log.debug("Adjusting water heating efficiency due to lack of thermostatic control");
			return seasonalEfficiency + constants.get(EfficiencyAdjustments.BOILER_WITHOUT_INTERLOCK);
		}
	}
	
	protected double getSeasonalEfficiency(final double qWater, final double qSpace) {
		if (0 == (qSpace + qWater)) {
			return 1;
		} else {
			return (qSpace + qWater)  / (qSpace / getWinterEfficiency().value + qWater / getSummerEfficiency().value);
		}
	}
	
	/**
	 * This is the space heating efficiency, with adjustments from SAP 2009 table 4c included.
	 * 
	 * @param qWater
	 * @param qSpace
	 * @return
	 */
	protected double getSpaceHeatingEfficiency(final IConstants constants, final boolean underfloorHeating, final double qWater, final double qSpace) {
		double efficiency = getWinterEfficiency().value;
	
		// apply adjustments for condensing boilers
		if (isCondensing()) {
			// efficiency adjustments resulting from reduced circulating temperature
			int gasOrOil;
			switch (getFuel()) {
			case MAINS_GAS:
				gasOrOil = 0;
				break;
			case BULK_LPG:
			case BOTTLED_LPG:
			case OIL:
				gasOrOil = 1;
				break;
			default:
				log.error("Boiler with fuel type {} should not be condensing", getFuel());
				gasOrOil = -1;
			}
			
			if (gasOrOil >= 0) {
				if (underfloorHeating) {
					efficiency += 
							constants.get(EfficiencyAdjustments.CONDENSING_UNDERFLOOR_HEATING,
									gasOrOil);
				} else if (isWeatherCompensated()) {
					efficiency += constants.get(EfficiencyAdjustments.CONDENSING_ADVANCED_COMPENSATOR,
							gasOrOil);
				}
			}
		}
		
		// apply adjustments due to control systems
		if (!getSpaceHeater().isThermostaticallyControlled()) {
			log.debug("Reducing space heating efficiency due to lack of thermostatic control");
			efficiency += constants.get(EfficiencyAdjustments.BOILER_WITHOUT_INTERLOCK);
		}
		
		return efficiency;
	}
	
	/**
	 * Electric boilers should override this method to determine the <em>high rate fraction</em> for a given space & water heat usage.
	 * @param parameters
	 * @param losses
	 * @param state
	 * @param qWater
	 * @param qHeat
	 * @return
	 */
	protected double getHighRateFraction(final IInternalParameters parameters, final ISpecificHeatLosses losses, final IEnergyState state, final double qWater, final double qHeat) {
		if (getFuel() != FuelType.ELECTRICITY) throw new RuntimeException("Non-electric boilers do not have a high-rate fraction!");
		return parameters.getConstants().get(SplitRateConstants.ELECTRIC_BOILER_FRACTIONS, parameters.getTarrifType());
	}
	
	/**
	 * This is a helper class which produces the internal power type that this boiler uses (see {@link BoilerImpl#power})
	 * @author hinton
	 *
	 */
	protected static abstract class PowerTransducer extends EnergyTransducer {
		private final EnergyType power;

		public PowerTransducer(
				final EnergyType power, 
				final ServiceType serviceType, final int priority) {
			super(serviceType, priority);
			this.power = power;
		}
		
		@Override
		public void generate(final IEnergyCalculatorHouseCase house,
				final IInternalParameters parameters,
				final ISpecificHeatLosses losses, final IEnergyState state) {
			
			final double qHeat = state.getTotalDemand(power,ServiceType.PRIMARY_SPACE_HEATING);
			final double qWater = state.getTotalDemand(power,ServiceType.WATER_HEATING);
		
			state.increaseSupply(power, generate(house, parameters, losses, state, qWater, qHeat));
		}

		protected abstract double generate(final IEnergyCalculatorHouseCase house,
				final IInternalParameters parameters, final ISpecificHeatLosses losses,
				final IEnergyState state, final double qWater, final double qHeat) ;
		
		@Override
		public TransducerPhaseType getPhase() {
			return TransducerPhaseType.AfterEverything;
		}
	}
	
	/**
	 * @return true if the central space heater to which this is attached has underfloor emitters.
	 */
	protected boolean isSystemHeatingUnderfloor() {
		final boolean underfloorHeating;
		switch (getSpaceHeater().getEmitterType()) {
		case UNDERFLOOR_CONCRETE:
		case UNDERFLOOR_SCREED:
		case UNDERFLOOR_TIMBER:
			underfloorHeating = true;
			break;
		default:
			underfloorHeating = false;
		}
		return underfloorHeating;
	}
	
	/**
	 * Creates an energy transducer which outputs {@link #power} in the production of {@link ServiceType#WATER_HEATING}.
	 * 
	 * If the fuel for this boiler is electric, a transducer is created with an efficiency of 1 that delegates to the
	 * {@link BoilerImpl#getHighRateFraction(IInternalParameters, ISpecificHeatLosses, IEnergyState, double, double)}
	 * method to determine the split between cheap and expensive fuel.
	 * 
	 * If the boiler is non-electric, fuel is consumed with the efficency calculated by 
	 * {@link BoilerImpl#getWaterHeatingEfficiency(double, double)}.
	 * @param power TODO
	 * @param parameters
	 * 
	 * @return an energy transducer for hot water.
	 */
	protected IEnergyTransducer createWaterTransducer(final EnergyType power) {
		if (getFuel() == FuelType.ELECTRICITY) {
			// electricity gets special treatment - efficiency is always 1,
			// but we have to compute the high rate fraction.
			return new PowerTransducer(power, ServiceType.WATER_HEATING, Integer.MAX_VALUE) {
				@Override
				protected double generate(final IEnergyCalculatorHouseCase house,
						final IInternalParameters parameters, final ISpecificHeatLosses losses,
						final IEnergyState state, final double qWater, final double qHeat) {
					final double highRateFraction = getHighRateFraction(parameters, losses, state, qWater, qHeat);
					
					state.increaseElectricityDemand(highRateFraction, qWater);
					log.debug("Transduced {} of electricity at high rate {} for water",qWater, highRateFraction);
					return qWater;
				}
				
				@Override
				public String toString() {
					return "Water Circuit";
				}
			};
		} else {
			return new PowerTransducer(power, ServiceType.WATER_HEATING, Integer.MAX_VALUE) {
				@Override
				protected double generate(final IEnergyCalculatorHouseCase house,
						final IInternalParameters parameters, final ISpecificHeatLosses losses,
						final IEnergyState state, final double qWater, final double qHeat) {
					
					final double efficiency = getWaterHeatingEfficiency(parameters.getConstants(), qWater, qHeat);
					
					state.increaseDemand(getFuel().getEnergyType(), qWater / efficiency);
					
					log.debug("Transduced {} of power at efficiency {} for water", qWater, efficiency);
					
					return qWater;
				}
				
				@Override
				public String toString() {
					return "Water Circuit";
				}
			};
		}
	}
	
	/**
	 * This is analogous to {@link #createWaterTransducer(IInternalParameters)}, but for space heating.
	 * @param power TODO
	 * @param parameters
	 * @return
	 */
	protected IEnergyTransducer createHeatTransducer(final EnergyType power) {
		if (getFuel() == FuelType.ELECTRICITY) {
			// electricity gets special treatment - efficiency is always 1,
			// but we have to compute the high rate fraction.
			return new PowerTransducer(power, ServiceType.PRIMARY_SPACE_HEATING, Integer.MAX_VALUE) {
				@Override
				protected double generate(final IEnergyCalculatorHouseCase house,
						final IInternalParameters parameters, final ISpecificHeatLosses losses,
						final IEnergyState state, final double qWater, final double qHeat) {
					final double highRateFraction = getHighRateFraction(parameters, losses, state, qWater, qHeat);
					
					state.increaseElectricityDemand(highRateFraction, qHeat);
					
					log.debug("Transduced {} of electricity at high rate {} for space", qHeat, highRateFraction);
					
					return qHeat;
				}
				
				@Override
				public String toString() {
					return "Heat Circuit";
				}
			};
		} else {
			return new PowerTransducer(power, ServiceType.PRIMARY_SPACE_HEATING, Integer.MAX_VALUE) {
				@Override
				protected double generate(final IEnergyCalculatorHouseCase house,
						final IInternalParameters parameters, final ISpecificHeatLosses losses,
						final IEnergyState state, final double qWater, final double qHeat) {
					
					final double efficiency = getSpaceHeatingEfficiency(parameters.getConstants(), isSystemHeatingUnderfloor(), qWater, qHeat);
					state.increaseDemand(getFuel().getEnergyType(), qHeat / efficiency);
					
					log.debug("Transduced {} of power at efficiency {} for space", qHeat, efficiency);
					
					return qHeat;
				}
				
				@Override
				public String toString() {
					return "Heat Circuit";
				}
			};
		}
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * The main thing this does is introduce the transducers which generate this boiler's energy type,
	 * and compute the high rate fraction and/or seasonal efficiency for primary fuel use
	 * <!-- end-user-doc -->
	 * @generated NO
	 */
	@Override
	public void accept(final IConstants constants, final IEnergyCalculatorParameters parameters, final IEnergyCalculatorVisitor visitor, final AtomicInteger counter, IHeatProportions heatProportions) {
		if (isIntermediatePowerRequired()) {
			final EnergyType power = parameters.getInternalEnergyType(this);
			if (getWaterHeater() != null) visitor.visitEnergyTransducer(createWaterTransducer(power));
			if (getSpaceHeater() != null) visitor.visitEnergyTransducer(createHeatTransducer(power));
		}

		if (getFuel() != FuelType.ELECTRICITY && getFlueType() == FlueType.OPEN_FLUE) {
			FlueVentilationHelper.addInfiltration(visitor, getFlueType(), constants);
		}
		
		if (getFuel() == FuelType.OIL && getSpaceHeater() != null) {
			
			visitor.visitEnergyTransducer(
					new Pump("Oil Fuel", ServiceType.PRIMARY_SPACE_HEATING, 
							constants.get(PumpAndFanConstants.OIL_FUEL_PUMP_WATTAGE) *
							(getSpaceHeater().getControls().contains(HeatingSystemControlType.ROOM_THERMOSTAT) ?
									1 : constants.get(PumpAndFanConstants.NO_ROOM_THERMOSTAT_MULTIPLIER))
							, 
							(isPumpInHeatedSpace() ?
							constants.get(PumpAndFanConstants.OIL_FUEL_PUMP_GAINS) : 0)));
			
		} else if (getFuel().isGas() && getFlueType() == FlueType.FAN_ASSISTED_BALANCED_FLUE) {
			visitor.visitEnergyTransducer(
					new Pump("Gas Flue", ServiceType.PRIMARY_SPACE_HEATING,
							constants.get(PumpAndFanConstants.GAS_BOILER_FLUE_FAN_WATTAGE), 0));
		}
	}
	
	/**
	 * @return True iff the {@link #power} energy type is required, for example to compute an efficiency or split-rate
	 * charge at the end of the computation.
	 */
	protected boolean isIntermediatePowerRequired() {
		return (getFuel() != FuelType.ELECTRICITY) && (getSummerEfficiency() != getWinterEfficiency());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IBoilersPackage.BOILER__SUMMER_EFFICIENCY:
				return getSummerEfficiency();
			case IBoilersPackage.BOILER__WINTER_EFFICIENCY:
				return getWinterEfficiency();
			case IBoilersPackage.BOILER__CONDENSING:
				return isCondensing();
			case IBoilersPackage.BOILER__WEATHER_COMPENSATED:
				return isWeatherCompensated();
			case IBoilersPackage.BOILER__BASIC_RESPONSIVENESS:
				return getBasicResponsiveness();
			case IBoilersPackage.BOILER__PUMP_IN_HEATED_SPACE:
				return isPumpInHeatedSpace();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case IBoilersPackage.BOILER__SUMMER_EFFICIENCY:
				setSummerEfficiency((Efficiency)newValue);
				return;
			case IBoilersPackage.BOILER__WINTER_EFFICIENCY:
				setWinterEfficiency((Efficiency)newValue);
				return;
			case IBoilersPackage.BOILER__CONDENSING:
				setCondensing((Boolean)newValue);
				return;
			case IBoilersPackage.BOILER__WEATHER_COMPENSATED:
				setWeatherCompensated((Boolean)newValue);
				return;
			case IBoilersPackage.BOILER__BASIC_RESPONSIVENESS:
				setBasicResponsiveness((Double)newValue);
				return;
			case IBoilersPackage.BOILER__PUMP_IN_HEATED_SPACE:
				setPumpInHeatedSpace((Boolean)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case IBoilersPackage.BOILER__SUMMER_EFFICIENCY:
				setSummerEfficiency(SUMMER_EFFICIENCY_EDEFAULT);
				return;
			case IBoilersPackage.BOILER__WINTER_EFFICIENCY:
				setWinterEfficiency(WINTER_EFFICIENCY_EDEFAULT);
				return;
			case IBoilersPackage.BOILER__CONDENSING:
				setCondensing(CONDENSING_EDEFAULT);
				return;
			case IBoilersPackage.BOILER__WEATHER_COMPENSATED:
				setWeatherCompensated(WEATHER_COMPENSATED_EDEFAULT);
				return;
			case IBoilersPackage.BOILER__BASIC_RESPONSIVENESS:
				setBasicResponsiveness(BASIC_RESPONSIVENESS_EDEFAULT);
				return;
			case IBoilersPackage.BOILER__PUMP_IN_HEATED_SPACE:
				setPumpInHeatedSpace(PUMP_IN_HEATED_SPACE_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case IBoilersPackage.BOILER__SUMMER_EFFICIENCY:
				return SUMMER_EFFICIENCY_EDEFAULT == null ? summerEfficiency != null : !SUMMER_EFFICIENCY_EDEFAULT.equals(summerEfficiency);
			case IBoilersPackage.BOILER__WINTER_EFFICIENCY:
				return WINTER_EFFICIENCY_EDEFAULT == null ? winterEfficiency != null : !WINTER_EFFICIENCY_EDEFAULT.equals(winterEfficiency);
			case IBoilersPackage.BOILER__CONDENSING:
				return ((flags & CONDENSING_EFLAG) != 0) != CONDENSING_EDEFAULT;
			case IBoilersPackage.BOILER__WEATHER_COMPENSATED:
				return ((flags & WEATHER_COMPENSATED_EFLAG) != 0) != WEATHER_COMPENSATED_EDEFAULT;
			case IBoilersPackage.BOILER__BASIC_RESPONSIVENESS:
				return basicResponsiveness != BASIC_RESPONSIVENESS_EDEFAULT;
			case IBoilersPackage.BOILER__PUMP_IN_HEATED_SPACE:
				return ((flags & PUMP_IN_HEATED_SPACE_EFLAG) != 0) != PUMP_IN_HEATED_SPACE_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated no
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();
		final StringBuffer result = new StringBuffer();
		result.append(getFuel() == null ? "null" : getFuel().getName());
		result.append(" ");
		result.append(eClass().getName() + " ");
		result.append(summerEfficiency.value * 100);
		result.append("% vs ");
		result.append(winterEfficiency.value * 100);
		result.append("%");
		return result.toString();
	}

	/**
	 * This is overridden, because the boiler knows about its power source.
	 */
	@Override
	public void acceptFromHeating(final IConstants constants, final IEnergyCalculatorParameters parameters, final IEnergyCalculatorVisitor visitor, final double proportion, final int priority) {
		if (isIntermediatePowerRequired()) {
			visitor.visitEnergyTransducer(new HeatTransducer(parameters.getInternalEnergyType(this), 
					1, proportion, true, priority, ServiceType.PRIMARY_SPACE_HEATING));
		} else {
			if (getFuel() == FuelType.ELECTRICITY) {
				visitor.visitEnergyTransducer(new ElectricHeatTransducer(proportion, priority) {
					@Override
					protected double getHighRateFraction(
							final IEnergyCalculatorHouseCase house,
							final IInternalParameters parameters,
							final ISpecificHeatLosses losses, final IEnergyState state) {
						return BoilerImpl.this.getHighRateFraction(parameters, losses, state, 0d, 0d);
					}
					@Override
					public String toString() {
						return "Electric Boiler (space heat)";
					}
				});
			} else {				
				visitor.visitEnergyTransducer(
						new HeatTransducer(getFuel().getEnergyType(),
						 getSpaceHeatingEfficiency(constants, isSystemHeatingUnderfloor(), 0, 0),
						 proportion,
						 true,
						 priority,
						 ServiceType.PRIMARY_SPACE_HEATING)
						);
			}
		}
	}

	/**
	 * SAP 2009 Table 4d explains how emitter type affects responsiveness for most wet heating systems, but not all.
	 */
	
	
	@Override
	public double getResponsivenessImpl(final IConstants parameters,final EList<HeatingSystemControlType> controls, final EmitterType emitter) {
		
		switch (getFuel()) {
		case ELECTRICITY:
			// hold on, what about electric dry core storage boiler?
		case BOTTLED_LPG:
		case BULK_LPG:
		case MAINS_GAS:
		case OIL:
			// a lookup from a table.
			return super.getSAPTable4dResponsiveness(parameters, controls, emitter);
		default:
			// this applies for things like solid fuel boilers and so on.
			return getBasicResponsiveness();
		}
	}

	/**
	 * As SAP 2009 explains in Table 4e (group 1), boilers have some demand temperature adjustments
	 * associated with the heating system controls. The adjustments defined are:
	 * 
	 * +0.6 if (a) no time or thermostatic controls or (b) programmer but no thermostat
	 * -0.15 if there is a delayed start thermostat
	 * -0.1 for CPSUs (handled in {@link CPSUImpl}, naturally.
	 */
	@Override
	public double getDemandTemperatureAdjustment(final IInternalParameters parameters, final EList<HeatingSystemControlType> controls) {
		// no idea if this is worth doing, actually.
		
//		final EnumSet<HeatingSystemControlType> controls2 = EnumSet.copyOf(controls);
		final IConstants constants = parameters.getConstants();
		double adjustment = 0;
		
		if (!(controls.contains(HeatingSystemControlType.ROOM_THERMOSTAT) ||
			controls.contains(HeatingSystemControlType.THERMOSTATIC_RADIATOR_VALVE) ||
			controls.contains(HeatingSystemControlType.TIME_TEMPERATURE_ZONE_CONTROL))) {
			adjustment += constants.get(TemperatureAdjustments.BOILER_NO_THERMOSTAT);
		} else if (controls.contains(HeatingSystemControlType.DELAYED_START_THERMOSTAT)) {
			adjustment += constants.get(TemperatureAdjustments.BOILER_DELAYED_START_THERMOSTAT);
		}
		
		return adjustment;
	}

	@Override
	public double generateHotWaterAndPrimaryGains(final IInternalParameters parameters,
			final IEnergyState state, final IWaterTank store, final boolean storeIsPrimary,
			final double primaryCorrectionFactor, final double distributionLossFactor, final double proportion) {
		/**
		 * The amount of hot water power required overall
		 */
		final double totalDemand = state.getTotalDemand(EnergyType.DemandsHOT_WATER);
		/**
		 * The amount of hot water demand left to satisfy
		 */
		final double remainingDemand = state.getUnsatisfiedDemand(EnergyType.DemandsHOT_WATER);
		
		/**
		 * The amount this will satisfy
		 */
		final double demandThisBoilerWillSatisfy = Math.min(remainingDemand, totalDemand * proportion);
		
		final double primaryCircuitLosses = getPrimaryPipeworkLosses(parameters, 
				!(store == null) && store.isThermostatFitted()
				, primaryCorrectionFactor);
		
		final double additionalUsageLosses = getAdditionalUsageLosses(parameters, state);
		final double powerOutOfBoiler =  demandThisBoilerWillSatisfy + primaryCircuitLosses + additionalUsageLosses;

		final double gainsOutOfBoiler =  primaryCircuitLosses;
		
		
		log.debug("Primary pipework losses: {}", primaryCircuitLosses);
		log.debug("Additional usage losses: {}", additionalUsageLosses);
		log.debug("Output power: {}", powerOutOfBoiler);
		
		
		if (isIntermediatePowerRequired()) {			
			state.increaseDemand(parameters.getInternalEnergyType(BoilerImpl.this), powerOutOfBoiler);
		} else {
			if (getFuel() == FuelType.ELECTRICITY) {
				state.increaseElectricityDemand(getHighRateFraction(parameters, null, state, 0, 0), powerOutOfBoiler);
			} else {
				state.increaseDemand(getFuel().getEnergyType(), powerOutOfBoiler / getWinterEfficiency().value);
			}
		}
		
		state.increaseSupply(EnergyType.DemandsHOT_WATER, demandThisBoilerWillSatisfy);
		state.increaseSupply(EnergyType.GainsHOT_WATER_USAGE_GAINS, additionalUsageLosses);
		state.increaseSupply(EnergyType.GainsHOT_WATER_SYSTEM_GAINS, gainsOutOfBoiler);

		return demandThisBoilerWillSatisfy;
	}

	/**
	 * This is for additional hot water usage gains/losses (for example ECom, from combi boilers)
	 * 
	 * @param parameters
	 * @return
	 */
	protected double getAdditionalUsageLosses(final IInternalParameters parameters, final IEnergyState state) {
		return 0;
	}

	protected double getPrimaryPipeworkLosses(final IInternalParameters parameters,
			final boolean tankPresentAndThermostatic, final double primaryCorrectionFactor) {
		final boolean primaryPipeworkIsInsulated = getWaterHeater().getSystem().isPrimaryPipeworkInsulated();
		return HotWaterUtilities.getPrimaryPipeworkLosses(parameters, primaryPipeworkIsInsulated, tankPresentAndThermostatic, primaryCorrectionFactor);
	}

	@Override
	public void generateHotWaterSystemGains(final IInternalParameters parameters,
			final IEnergyState state, final IWaterTank store, final boolean storeIsPrimary,
			final double systemLosses) {

		if (isIntermediatePowerRequired()) {			
			state.increaseDemand(parameters.getInternalEnergyType(this), systemLosses);
		} else {
			if (getFuel() == FuelType.ELECTRICITY) {
				state.increaseElectricityDemand(getHighRateFraction(parameters, null, state, 0, 0), systemLosses);
			} else {
				state.increaseDemand(getFuel().getEnergyType(), systemLosses / getWinterEfficiency().value);
			}
		}
		
		state.increaseSupply(EnergyType.GainsHOT_WATER_SYSTEM_GAINS, systemLosses);
	}
	
	/**
	 * This is the control column of SAP 2009 table 4e (effectively)
	 */
	@Override
	public double getZoneTwoControlParameter(final IInternalParameters parameters, final EList<HeatingSystemControlType> controls, final EmitterType emitterType) {
		if (controls.contains(HeatingSystemControlType.ROOM_THERMOSTAT)
				|| controls.contains(HeatingSystemControlType.THERMOSTATIC_RADIATOR_VALVE)
				|| controls.contains(HeatingSystemControlType.TIME_TEMPERATURE_ZONE_CONTROL)) {
			return 1;
		} else {
			return 0;
		}
	}
	
	
} //BoilerImpl