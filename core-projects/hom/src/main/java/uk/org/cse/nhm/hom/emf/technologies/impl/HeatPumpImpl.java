/**
 */
package uk.org.cse.nhm.hom.emf.technologies.impl;

import java.util.EnumSet;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.impl.ElectricHeatTransducer;
import uk.org.cse.nhm.energycalculator.api.impl.HeatTransducer;
import uk.org.cse.nhm.energycalculator.api.impl.HybridHeatpumpTransducer;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.hom.IHeatProportions;
import uk.org.cse.nhm.hom.constants.PumpAndFanConstants;
import uk.org.cse.nhm.hom.constants.SplitRateConstants;
import uk.org.cse.nhm.hom.constants.adjustments.EfficiencyAdjustments;
import uk.org.cse.nhm.hom.constants.adjustments.TemperatureAdjustments;
import uk.org.cse.nhm.hom.emf.technologies.EmitterType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.HeatPumpSourceType;
import uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType;
import uk.org.cse.nhm.hom.emf.technologies.IHeatPump;
import uk.org.cse.nhm.hom.emf.technologies.IHybridHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;
import uk.org.cse.nhm.hom.emf.technologies.IWaterTank;
import uk.org.cse.nhm.hom.emf.technologies.boilers.FlueType;
import uk.org.cse.nhm.hom.emf.technologies.boilers.impl.util.FlueVentilationHelper;
import uk.org.cse.nhm.hom.emf.technologies.impl.util.HotWaterUtilities;
import uk.org.cse.nhm.hom.emf.technologies.impl.util.Pump;
import uk.org.cse.nhm.hom.emf.util.Efficiency;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Heat Pump</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.HeatPumpImpl#getSourceType <em>Source Type</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.HeatPumpImpl#getCoefficientOfPerformance <em>Coefficient Of Performance</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.HeatPumpImpl#isWeatherCompensated <em>Weather Compensated</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.HeatPumpImpl#isAuxiliaryPresent <em>Auxiliary Present</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.HeatPumpImpl#getHybrid <em>Hybrid</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class HeatPumpImpl extends HeatSourceImpl implements IHeatPump {
	/**
	 * The default value of the '{@link #getSourceType() <em>Source Type</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getSourceType()
	 * @generated
	 * @ordered
	 */
	protected static final HeatPumpSourceType SOURCE_TYPE_EDEFAULT = HeatPumpSourceType.GROUND;
	/**
	 * The offset of the flags representing the value of the '{@link #getSourceType() <em>Source Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected static final int SOURCE_TYPE_EFLAG_OFFSET = 9;
	/**
	 * The flags representing the default value of the '{@link #getSourceType() <em>Source Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected static final int SOURCE_TYPE_EFLAG_DEFAULT = SOURCE_TYPE_EDEFAULT.ordinal() << SOURCE_TYPE_EFLAG_OFFSET;
	/**
	 * The array of enumeration values for '{@link HeatPumpSourceType Heat Pump Source Type}'
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	private static final HeatPumpSourceType[] SOURCE_TYPE_EFLAG_VALUES = HeatPumpSourceType.values();
	/**
	 * The flag representing the value of the '{@link #getSourceType() <em>Source Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceType()
	 * @generated
	 * @ordered
	 */
	protected static final int SOURCE_TYPE_EFLAG = 1 << SOURCE_TYPE_EFLAG_OFFSET;
	/**
	 * The default value of the '{@link #getCoefficientOfPerformance() <em>Coefficient Of Performance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCoefficientOfPerformance()
	 * @generated
	 * @ordered
	 */
	protected static final Efficiency COEFFICIENT_OF_PERFORMANCE_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getCoefficientOfPerformance() <em>Coefficient Of Performance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCoefficientOfPerformance()
	 * @generated
	 * @ordered
	 */
	protected Efficiency coefficientOfPerformance = COEFFICIENT_OF_PERFORMANCE_EDEFAULT;

	/**
	 * The default value of the '{@link #isWeatherCompensated() <em>Weather Compensated</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
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
	 * The default value of the '{@link #isAuxiliaryPresent() <em>Auxiliary Present</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #isAuxiliaryPresent()
	 * @generated
	 * @ordered
	 */
	protected static final boolean AUXILIARY_PRESENT_EDEFAULT = false;
	/**
	 * The flag representing the value of the '{@link #isAuxiliaryPresent() <em>Auxiliary Present</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAuxiliaryPresent()
	 * @generated
	 * @ordered
	 */
	protected static final int AUXILIARY_PRESENT_EFLAG = 1 << 11;
	/**
	 * The cached value of the '{@link #getHybrid() <em>Hybrid</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHybrid()
	 * @generated
	 * @ordered
	 */
	protected IHybridHeater hybrid;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected HeatPumpImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ITechnologiesPackage.Literals.HEAT_PUMP;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public HeatPumpSourceType getSourceType() {
		return SOURCE_TYPE_EFLAG_VALUES[(flags & SOURCE_TYPE_EFLAG) >>> SOURCE_TYPE_EFLAG_OFFSET];
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSourceType(HeatPumpSourceType newSourceType) {
		HeatPumpSourceType oldSourceType = SOURCE_TYPE_EFLAG_VALUES[(flags & SOURCE_TYPE_EFLAG) >>> SOURCE_TYPE_EFLAG_OFFSET];
		if (newSourceType == null) newSourceType = SOURCE_TYPE_EDEFAULT;
		flags = flags & ~SOURCE_TYPE_EFLAG | newSourceType.ordinal() << SOURCE_TYPE_EFLAG_OFFSET;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.HEAT_PUMP__SOURCE_TYPE, oldSourceType, newSourceType));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Efficiency getCoefficientOfPerformance() {
		return coefficientOfPerformance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCoefficientOfPerformance(Efficiency newCoefficientOfPerformance) {
		Efficiency oldCoefficientOfPerformance = coefficientOfPerformance;
		coefficientOfPerformance = newCoefficientOfPerformance;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.HEAT_PUMP__COEFFICIENT_OF_PERFORMANCE, oldCoefficientOfPerformance, coefficientOfPerformance));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isWeatherCompensated() {
		return (flags & WEATHER_COMPENSATED_EFLAG) != 0;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setWeatherCompensated(boolean newWeatherCompensated) {
		boolean oldWeatherCompensated = (flags & WEATHER_COMPENSATED_EFLAG) != 0;
		if (newWeatherCompensated) flags |= WEATHER_COMPENSATED_EFLAG; else flags &= ~WEATHER_COMPENSATED_EFLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.HEAT_PUMP__WEATHER_COMPENSATED, oldWeatherCompensated, newWeatherCompensated));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isAuxiliaryPresent() {
		return (flags & AUXILIARY_PRESENT_EFLAG) != 0;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAuxiliaryPresent(boolean newAuxiliaryPresent) {
		boolean oldAuxiliaryPresent = (flags & AUXILIARY_PRESENT_EFLAG) != 0;
		if (newAuxiliaryPresent) flags |= AUXILIARY_PRESENT_EFLAG; else flags &= ~AUXILIARY_PRESENT_EFLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.HEAT_PUMP__AUXILIARY_PRESENT, oldAuxiliaryPresent, newAuxiliaryPresent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IHybridHeater getHybrid() {
		return hybrid;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetHybrid(IHybridHeater newHybrid, NotificationChain msgs) {
		IHybridHeater oldHybrid = hybrid;
		hybrid = newHybrid;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.HEAT_PUMP__HYBRID, oldHybrid, newHybrid);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setHybrid(IHybridHeater newHybrid) {
		if (newHybrid != hybrid) {
			NotificationChain msgs = null;
			if (hybrid != null)
				msgs = ((InternalEObject)hybrid).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ITechnologiesPackage.HEAT_PUMP__HYBRID, null, msgs);
			if (newHybrid != null)
				msgs = ((InternalEObject)newHybrid).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ITechnologiesPackage.HEAT_PUMP__HYBRID, null, msgs);
			msgs = basicSetHybrid(newHybrid, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.HEAT_PUMP__HYBRID, newHybrid, newHybrid));
	}

	/**
	 * <!-- begin-user-doc --> Puts the flue fan into the system <!--
	 * end-user-doc -->
	 * 
	 * @generated no
	 */
	@Override
	public void accept(final IConstants constants, final IEnergyCalculatorParameters parameters, final IEnergyCalculatorVisitor visitor, final AtomicInteger heatingSystemCounter, final IHeatProportions heatProportions) {
		if (getFlueType() == FlueType.OPEN_FLUE) {
			FlueVentilationHelper.addInfiltration(visitor, getFlueType(), constants);
		}

		if (getFuel().isGas() && getFlueType() == FlueType.FAN_ASSISTED_BALANCED_FLUE) {
			visitor.visitEnergyTransducer(new Pump("Heat Pump Flue", ServiceType.PRIMARY_SPACE_HEATING, constants.get(
					PumpAndFanConstants.GAS_BOILER_FLUE_FAN_WATTAGE), 0));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ITechnologiesPackage.HEAT_PUMP__HYBRID:
				return basicSetHybrid(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ITechnologiesPackage.HEAT_PUMP__SOURCE_TYPE:
				return getSourceType();
			case ITechnologiesPackage.HEAT_PUMP__COEFFICIENT_OF_PERFORMANCE:
				return getCoefficientOfPerformance();
			case ITechnologiesPackage.HEAT_PUMP__WEATHER_COMPENSATED:
				return isWeatherCompensated();
			case ITechnologiesPackage.HEAT_PUMP__AUXILIARY_PRESENT:
				return isAuxiliaryPresent();
			case ITechnologiesPackage.HEAT_PUMP__HYBRID:
				return getHybrid();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ITechnologiesPackage.HEAT_PUMP__SOURCE_TYPE:
				setSourceType((HeatPumpSourceType)newValue);
				return;
			case ITechnologiesPackage.HEAT_PUMP__COEFFICIENT_OF_PERFORMANCE:
				setCoefficientOfPerformance((Efficiency)newValue);
				return;
			case ITechnologiesPackage.HEAT_PUMP__WEATHER_COMPENSATED:
				setWeatherCompensated((Boolean)newValue);
				return;
			case ITechnologiesPackage.HEAT_PUMP__AUXILIARY_PRESENT:
				setAuxiliaryPresent((Boolean)newValue);
				return;
			case ITechnologiesPackage.HEAT_PUMP__HYBRID:
				setHybrid((IHybridHeater)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ITechnologiesPackage.HEAT_PUMP__SOURCE_TYPE:
				setSourceType(SOURCE_TYPE_EDEFAULT);
				return;
			case ITechnologiesPackage.HEAT_PUMP__COEFFICIENT_OF_PERFORMANCE:
				setCoefficientOfPerformance(COEFFICIENT_OF_PERFORMANCE_EDEFAULT);
				return;
			case ITechnologiesPackage.HEAT_PUMP__WEATHER_COMPENSATED:
				setWeatherCompensated(WEATHER_COMPENSATED_EDEFAULT);
				return;
			case ITechnologiesPackage.HEAT_PUMP__AUXILIARY_PRESENT:
				setAuxiliaryPresent(AUXILIARY_PRESENT_EDEFAULT);
				return;
			case ITechnologiesPackage.HEAT_PUMP__HYBRID:
				setHybrid((IHybridHeater)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ITechnologiesPackage.HEAT_PUMP__SOURCE_TYPE:
				return (flags & SOURCE_TYPE_EFLAG) != SOURCE_TYPE_EFLAG_DEFAULT;
			case ITechnologiesPackage.HEAT_PUMP__COEFFICIENT_OF_PERFORMANCE:
				return COEFFICIENT_OF_PERFORMANCE_EDEFAULT == null ? coefficientOfPerformance != null : !COEFFICIENT_OF_PERFORMANCE_EDEFAULT.equals(coefficientOfPerformance);
			case ITechnologiesPackage.HEAT_PUMP__WEATHER_COMPENSATED:
				return ((flags & WEATHER_COMPENSATED_EFLAG) != 0) != WEATHER_COMPENSATED_EDEFAULT;
			case ITechnologiesPackage.HEAT_PUMP__AUXILIARY_PRESENT:
				return ((flags & AUXILIARY_PRESENT_EFLAG) != 0) != AUXILIARY_PRESENT_EDEFAULT;
			case ITechnologiesPackage.HEAT_PUMP__HYBRID:
				return hybrid != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated no
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		return String.format("%s %s-source HP %s%%", getFuel() == null ? "" : getFuel().getName(), getSourceType() == null ? "" : getSourceType().getName(),
				coefficientOfPerformance.value * 100);
	}

	@Override
	public void acceptFromHeating(final IConstants constants, final IEnergyCalculatorParameters parameters, final IEnergyCalculatorVisitor visitor, final double proportion, final int priority) {
		// heat transducer is all we need
		final double adjustedSpaceHeatingEfficiency;
		switch (getSpaceHeater().getEmitterType()) {
		case WARM_AIR_FAN_COIL:
			adjustedSpaceHeatingEfficiency = getCoefficientOfPerformance().value * constants.get(EfficiencyAdjustments.HEAT_PUMP_WITH_FAN_COILS);
			break;
		case RADIATORS:
			if (isWeatherCompensated()) {
				adjustedSpaceHeatingEfficiency = getCoefficientOfPerformance().value
						* constants.get(EfficiencyAdjustments.HEAT_PUMP_WITH_RADIATORS_AND_COMPENSATOR);
			} else {
				adjustedSpaceHeatingEfficiency = getCoefficientOfPerformance().value * constants.get(EfficiencyAdjustments.HEAT_PUMP_WITH_RADIATORS);
			}
			break;
		default:
			adjustedSpaceHeatingEfficiency = getCoefficientOfPerformance().value;
			break;
		}

		final double highRateFraction;
		if (getSourceType() == HeatPumpSourceType.AIR) {
			highRateFraction = constants.get(SplitRateConstants.AIR_SOURCE_SPACE_HEAT, parameters.getTarrifType());
		} else {
			if (isAuxiliaryPresent()) {
				highRateFraction = constants
						.get(SplitRateConstants.GROUND_SOURCE_SPACE_HEAT_WITH_ON_PEAK_AUXILIARY, parameters.getTarrifType());
			} else {
				highRateFraction = constants.get(SplitRateConstants.GROUND_SOURCE_SPACE_HEAT_NO_AUXILIARY, parameters.getTarrifType());
			}
		}

		final IHybridHeater hybrid = getHybrid();
		if (hybrid == null) {
			if (getFuel() == FuelType.ELECTRICITY) {
				visitor.visitEnergyTransducer(new ElectricHeatTransducer(proportion, priority) {
					@Override
					protected double getHighRateFraction(final IEnergyCalculatorHouseCase house, final IInternalParameters parameters, final ISpecificHeatLosses losses, final IEnergyState state) {
						return highRateFraction;
					}

					@Override
					protected double getEfficiency() {
						return adjustedSpaceHeatingEfficiency;
					}
				});
			} else {
				visitor.visitEnergyTransducer(
						new HeatTransducer(getFuel().getEnergyType(), adjustedSpaceHeatingEfficiency, proportion, true, priority, ServiceType.PRIMARY_SPACE_HEATING)
						);
			}
			
		} else {
			final double[] months = new double[12];
			for (int i = 0; i<12 && i<hybrid.getFraction().size(); i++) {
				months[i] = hybrid.getFraction().get(i);
			}
			if (getFuel() == FuelType.ELECTRICITY) {
				visitor.visitEnergyTransducer(
						new HybridHeatpumpTransducer(priority, 
								highRateFraction, hybrid.getFuel().getEnergyType(), 
								adjustedSpaceHeatingEfficiency, 
								hybrid.getEfficiency().value, proportion, 
								months
								));
			} else {
				visitor.visitEnergyTransducer(
						new HybridHeatpumpTransducer(priority, 
								getFuel().getEnergyType(), 
								hybrid.getFuel().getEnergyType(), 
								adjustedSpaceHeatingEfficiency, 
								hybrid.getEfficiency().value, proportion, 
								months
								));
			}
		}
	}
	
	@Override
	public double getDemandTemperatureAdjustment(final IInternalParameters parameters, final EList<HeatingSystemControlType> controlTypes) {
		if (getSpaceHeater() != null && getSpaceHeater().isThermostaticallyControlled() == false) {
			return parameters.getConstants().get(TemperatureAdjustments.HEAT_PUMP_NO_THERMOSTAT);
		} else {
			return 0;
		}
	}

	@Override
	public double generateHotWaterAndPrimaryGains(final IInternalParameters parameters, final IEnergyState state, final IWaterTank store, final boolean storeIsPrimary,
			final double primaryCorrectionFactor, final double distributionLossFactor, final double proportion) {
		
		final double adjustedEfficiency;
		final FuelType fuel;
		if (getHybrid() == null) {
			if (proportion == 1) {
				// heat pump is supplying all DHW, efficiency *= 0.7 (Table 4c)
				adjustedEfficiency = getCoefficientOfPerformance().value * parameters.getConstants().get(EfficiencyAdjustments.HEAT_PUMP_SUPPLYING_ALL_DHW);
			} else {
				adjustedEfficiency = getCoefficientOfPerformance().value;
			}
			fuel = getFuel();
		} else {
			final IHybridHeater hybrid = getHybrid();
			adjustedEfficiency = hybrid.getEfficiency().value;
			fuel = hybrid.getFuel();
		}

		final double hotWaterToGenerate = state.getBoundedTotalDemand(EnergyType.DemandsHOT_WATER, proportion);
		
		// primary pipework losses
		final double ppLosses = HotWaterUtilities.getPrimaryPipeworkLosses(parameters, getWaterHeater().getSystem().isPrimaryPipeworkInsulated(),
				(store != null) && store.isThermostatFitted(), primaryCorrectionFactor);

		final double totalToGenerate = hotWaterToGenerate + ppLosses;

		state.increaseSupply(EnergyType.DemandsHOT_WATER, hotWaterToGenerate);
		state.increaseSupply(EnergyType.GainsHOT_WATER_SYSTEM_GAINS, ppLosses);

		final double sourceEnergy = totalToGenerate / adjustedEfficiency;

		if (fuel == FuelType.ELECTRICITY) {
			// high rate depends on presence of immersion heater, which is a bit
			// of a nuisance
			// presume immersion heater always present? do some instanceof
			// horrors?
			state.increaseElectricityDemand(getDHWHighRateFraction(parameters), sourceEnergy);
		} else {
			state.increaseDemand(fuel.getEnergyType(), sourceEnergy);
		}
		
		return hotWaterToGenerate;
	}

	private double getDHWHighRateFraction(final IInternalParameters parameters) {
		return parameters.getConstants().get(
				getWaterHeater().getSystem().hasImmersionHeater() ? SplitRateConstants.HEAT_PUMP_DHW_WITH_IMMERSION_HEATER
						: SplitRateConstants.HEAT_PUMP_DHW_WITHOUT_IMMERSION_HEATER, parameters.getTarrifType());
	}

	@Override
	public void generateHotWaterSystemGains(final IInternalParameters parameters, final IEnergyState state, final IWaterTank store, final boolean storeIsPrimary, final double systemLosses) {
		final double adjustedEfficiency;
		final FuelType fuel;
		
		if (getHybrid() == null) {
			// heat pump is supplying all DHW, efficiency *= 0.7 (Table 4c)
			adjustedEfficiency = getCoefficientOfPerformance().value * parameters.getConstants().get(EfficiencyAdjustments.HEAT_PUMP_SUPPLYING_ALL_DHW);
			fuel = getFuel();
		} else {
			final IHybridHeater hybrid = getHybrid();
			adjustedEfficiency = hybrid.getEfficiency().value;
			fuel = hybrid.getFuel();
		}

		state.increaseSupply(EnergyType.GainsHOT_WATER_SYSTEM_GAINS, systemLosses);

		if (fuel == FuelType.ELECTRICITY) {
			// high rate depends on presence of immersion heater, which is a bit
			// of a nuisance
			// presume immersion heater always present? do some instanceof
			// horrors?
			state.increaseElectricityDemand(getDHWHighRateFraction(parameters), systemLosses / adjustedEfficiency);
		} else {
			state.increaseDemand(fuel.getEnergyType(), systemLosses / adjustedEfficiency);
		}
	}

	/**
	 * @assumption SAP specifies a zone two control parameter for
	 *             "Programmer and at least two room stats", but we lack
	 *             information about the number of room stats, and merely know
	 *             that there is at least one or none. Consequently we assume that
         *             there is programmer and room thermostat, corresponding to
         *             the fourth row in Group 2 of SAP table 4e.
	 */
	@Override
	public double getZoneTwoControlParameter(final IInternalParameters parameters, final EList<HeatingSystemControlType> controls, final EmitterType emitterType) {
		if (controls.contains(HeatingSystemControlType.TIME_TEMPERATURE_ZONE_CONTROL)
				|| controls.containsAll(EnumSet.of(HeatingSystemControlType.PROGRAMMER, HeatingSystemControlType.THERMOSTATIC_RADIATOR_VALVE,
						HeatingSystemControlType.BYPASS))) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	protected double getResponsivenessImpl(final IConstants parameters,
			final EList<HeatingSystemControlType> controls, final EmitterType emitterType) {
		return getSAPTable4dResponsiveness(parameters, controls, emitterType);
	}
} // HeatPumpImpl