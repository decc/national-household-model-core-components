package uk.org.cse.nhm.energycalculator.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculationResult;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculator;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IEnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.IHeatingSchedule;
import uk.org.cse.nhm.energycalculator.api.IHeatingSystem;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISeasonalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.IVentilationSystem;
import uk.org.cse.nhm.energycalculator.api.impl.ClassEnergyState;
import uk.org.cse.nhm.energycalculator.api.impl.DefaultConstants;
import uk.org.cse.nhm.energycalculator.api.impl.GraphvizEnergyState;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.energycalculator.api.types.ZoneType;
import uk.org.cse.nhm.energycalculator.constants.EnergyCalculatorConstants;
import uk.org.cse.nhm.energycalculator.impl.appliances.Appliances09;
import uk.org.cse.nhm.energycalculator.impl.demands.HotWaterDemand09;
import uk.org.cse.nhm.energycalculator.impl.gains.EvaporativeGainsSource;
import uk.org.cse.nhm.energycalculator.impl.gains.GainsTransducer;
import uk.org.cse.nhm.energycalculator.impl.gains.MetabolicGainsSource;

/**
 * An implementation of an EnergyCalculator. The main
 * method is the {@link #evaluate(IEnergyCalculatorHouseCase, IEnergyCalculatorParameters)} method,
 * which has an extensive javadoc where the operation of the algorithm is
 * described.
 * 
 * @author hinton
 * 
 */
public class EnergyCalculatorCalculator implements IEnergyCalculator {
	private static final Logger log = LoggerFactory.getLogger(EnergyCalculatorCalculator.class);

	private static double div(final double num, final double den, final String die) {
		if (den == 0) {
			if (num == 0)
				return 0;
			else
				throw new RuntimeException("Illegal division for " + die);
		} else {
			return num / den;
		}
	}

	private static Comparator<IEnergyTransducer> phasingComparator = new Comparator<IEnergyTransducer>() {
		@Override
		public int compare(final IEnergyTransducer arg0, final IEnergyTransducer arg1) {
			final int p1 = arg0.getPhase().ordinal();
			final int p2 = arg1.getPhase().ordinal();
			final int c1 = c(p1, p2);
			if (c1 == 0) {
				return c(arg0.getPriority(), arg1.getPriority());
			} else {
				return c1;
			}
		}

		private final int c(final int a, final int b) {
			if (a == b)
				return 0;
			if (a > b)
				return 1;
			return -1;
		}
	};

	private final IConstants constants;

	/**
	 * Should you wish to inject some logic into the {@link IEnergyState} to
	 * keep track of calculations, for example to produce something like
	 * {@link GraphvizEnergyState}'s output, you can set an instance of this
	 * interface into the calculator to ensure the right kind of state is used.
	 * 
	 * @author hinton
	 *
	 */
	public interface IEnergyStateFactory {
		public IEnergyState createEnergyState();
	}

	/**
	 * The default state factory produces a {@link ClassEnergyState}, but you
	 * could put in another one with
	 * {@link #setStateFactory(IEnergyStateFactory)}
	 */
	private IEnergyStateFactory stateFactory = new IEnergyStateFactory() {
		@Override
		public IEnergyState createEnergyState() {
			return new ClassEnergyState();
		}
	};

	/**
	 * Defined in BREDEM 7.0 equations 1 and 2
	 */
	private final double VENTILATION_HEAT_LOSS_COEFFICIENT;
	private static final double MINUTES_PER_HOUR = 60.0;

	private final double TIME_CONSTANT_HEAT_LOSS_PARAMETER_MULTIPLIER;

	private final double OLD_THERMAL_BRIDGING_COEFFICIENT;
	private final double NEW_THERMAL_BRIDGING_COEFFICIENT;

	/**
	 * Contains a list of transducers which are present in every house. These
	 * are stateless, so it is safe to share them between different calculation
	 * threads.
	 */
	private final List<IEnergyTransducer> defaultTransducers = new ArrayList<IEnergyTransducer>();

	private final int THERMAL_BRIDGING_PARAMETER_IMPROVEMENT_YEAR;

	private final double SHELTERED_SIDES_EXPOSURE_FACTOR;

	private final double WIND_FACTOR_DIVISOR;

	private final double REFERENCE_HEAT_LOSS_PARAMETER;
	private final double REFERENCE_HEAT_LOSS_PARAMETER2;

	private final double MINIMUM_COOLING_TIME;

	private final double COOLING_TIME_CONSTANT_MULTIPLIER;

	private final double UTILISATION_FACTOR_TIME_CONSTANT_DIVISOR;

	public EnergyCalculatorCalculator() {
		this(DefaultConstants.INSTANCE);
	}

	@Inject
	public EnergyCalculatorCalculator(final IConstants constants) {
		log.debug("Constructing EnergyCalculator with {}", constants);

		this.constants = constants;
		VENTILATION_HEAT_LOSS_COEFFICIENT = constants.get(EnergyCalculatorConstants.VENTILATION_HEAT_LOSS_COEFFICIENT);
		OLD_THERMAL_BRIDGING_COEFFICIENT = constants.get(EnergyCalculatorConstants.OLD_THERMAL_BRIDGING_COEFFICIENT);
		NEW_THERMAL_BRIDGING_COEFFICIENT = constants.get(EnergyCalculatorConstants.NEW_THERMAL_BRIDGING_COEFFICIENT);
		THERMAL_BRIDGING_PARAMETER_IMPROVEMENT_YEAR = (int) constants
				.get(EnergyCalculatorConstants.THERMAL_BRIDING_COEFFICIENT_IMPROVEMENT_YEAR);
		TIME_CONSTANT_HEAT_LOSS_PARAMETER_MULTIPLIER = constants
				.get(EnergyCalculatorConstants.TIME_CONSTANT_HEAT_LOSS_PARAMETER_MULTIPLIER);
		SHELTERED_SIDES_EXPOSURE_FACTOR = constants.get(EnergyCalculatorConstants.SHELTERED_SIDES_EXPOSURE_FACTOR);
		WIND_FACTOR_DIVISOR = constants.get(EnergyCalculatorConstants.WIND_FACTOR_DIVISOR);
		REFERENCE_HEAT_LOSS_PARAMETER = constants.get(EnergyCalculatorConstants.REFERENCE_HEAT_LOSS_PARAMETER);
		REFERENCE_HEAT_LOSS_PARAMETER2 = Math.pow(REFERENCE_HEAT_LOSS_PARAMETER, 2);
		MINIMUM_COOLING_TIME = constants.get(EnergyCalculatorConstants.MINIMUM_COOLING_TIME);
		COOLING_TIME_CONSTANT_MULTIPLIER = constants.get(EnergyCalculatorConstants.COOLING_TIME_TIME_CONSTANT_MULTIPLIER);
		UTILISATION_FACTOR_TIME_CONSTANT_DIVISOR = constants
				.get(EnergyCalculatorConstants.UTILISATION_FACTOR_TIME_CONSTANT_DIVISOR);

		defaultTransducers.add(new HotWaterDemand09(constants));
		defaultTransducers.add(new Appliances09(constants));
		defaultTransducers.add(new MetabolicGainsSource(constants));
		defaultTransducers.add(new EvaporativeGainsSource(constants));
		defaultTransducers.add(new GainsTransducer(constants));
	}

	/**
	 * Compute the specific heat losses for a house case, given the summed
	 * values collected from visiting a house; this method combines the fabric
	 * losses together with thermal bridging losses and ventilation losses.
	 * 
	 * @param houseCase
	 * @param parameters
	 * @param totalSpecificHeatLoss
	 * @param totalThermalMass
	 * @param totalExternalArea
	 * @param infiltration
	 * @param ventilationSystems
	 * @return
	 * 
	 * @assumption In the CHM, the thermal bridging parameter is switched on
	 *             houses that are built before 2003, but in BREDEM8 a thermal
	 *             bridging value is computed with reference to the layout of
	 *             the floorplan. Here we are using the CHM's method.
	 */
	public SpecificHeatLosses calculateSpecificHeatLosses(final IEnergyCalculatorHouseCase houseCase,
            final IInternalParameters parameters, final double fabricLosses, final double totalThermalMass,
			final double totalExternalArea, final IStructuralInfiltrationAccumulator infiltration,
			final List<IVentilationSystem> ventilationSystems) {

		/**
		 * Interzone specific heat loss
		 */
		double H3 = 0;

		log.debug("Total thermal mass: {}", totalThermalMass);

		final double structuralAirChangeRate = infiltration.getAirChangeRate(houseCase, parameters);

		final double exposure = 1 - (houseCase.getNumberOfShelteredSides() * SHELTERED_SIDES_EXPOSURE_FACTOR);

		final double exposureFactor = (parameters.getClimate().getSiteWindSpeed() / WIND_FACTOR_DIVISOR) * exposure;

		final double climateAdjustedAirChangeRate = structuralAirChangeRate * exposureFactor;

		double houseAirChangeRate = climateAdjustedAirChangeRate;

		// If no mechanical ventilation rate then assume human will ventilate
		if (ventilationSystems.isEmpty()) {
			ventilationSystems.add(new HumanVentilationSystem(constants));
		}

		for (final IVentilationSystem system : ventilationSystems) {
			houseAirChangeRate = system.getAirChangeRate(houseAirChangeRate);
		}

		log.debug("air change rates: {} {} {}", structuralAirChangeRate, climateAdjustedAirChangeRate,
				houseAirChangeRate);

		// CHM handles thermal bridging using a simple model, which we just add
		// in here; the simple model states
		// that the linear thermal bridge effect is 0.15 * total external area
		// if the house is built before 2003,
		// or 1/4 that if it is built after 2003.

		final double thermalBridgeEffect;
		if (houseCase.getBuildYear() < THERMAL_BRIDGING_PARAMETER_IMPROVEMENT_YEAR) {
			thermalBridgeEffect = OLD_THERMAL_BRIDGING_COEFFICIENT * totalExternalArea;
		} else {
			thermalBridgeEffect = NEW_THERMAL_BRIDGING_COEFFICIENT * totalExternalArea;
		}

		final double ventilationLosses = VENTILATION_HEAT_LOSS_COEFFICIENT * houseAirChangeRate
				* houseCase.getHouseVolume();

        H3 = houseCase.getInterzoneSpecificHeatLoss();

        return new SpecificHeatLosses(fabricLosses,
                                      H3,
                                      totalThermalMass,
                                      houseCase.getFloorArea(),
                                      ventilationLosses,
                                      thermalBridgeEffect,
                                      climateAdjustedAirChangeRate);
	}

	/**
	 * Compute the gains utilisation factor as defined in BREDEM 2009 ch. 7.
	 * 
	 * @param Tin
	 * @param Text
	 * @param H
	 * @param totalGains
	 * @param utilisationFactorExponent
	 * @return
	 * 
	 * @assumption In BREDEM, the gains utilisation factor is defined using the
	 *             gain to loss ratio. The BREDEM 2009 definition contains a
	 *             singular point at Tin = TextAs the specific heat loss tends
	 *             to zero. In this case, the gains utilisation factor should
	 *             also tend to zero, by the application of L'Hopital's rule for
	 *             limits. However, BREDEM does not specify this, so it has been
	 *             assumed here so we mark it up here as an assumption.
	 */
	protected static double calculateGainsUtilisationFactor(final double Tin, final double Text, final double H,
			final double totalGains, final double utilisationFactorExponent) {
		final double heatLossRate = H * (Tin - Text);

		// from l'hopital's rule.
		if (heatLossRate == 0)
			return 0;

		final double gainToLossRatio = totalGains / heatLossRate;

		final double gainsUtilisationFactor;
		if (gainToLossRatio < 0) {
			gainsUtilisationFactor = 1;
		} else if (gainToLossRatio == 1f) {
			gainsUtilisationFactor = div(utilisationFactorExponent, (utilisationFactorExponent + 1),
					"gains utilisation factor (glr = 1)");
		} else {
			gainsUtilisationFactor = div((1 - Math.pow(gainToLossRatio, utilisationFactorExponent)),
					(1 - Math.pow(gainToLossRatio, utilisationFactorExponent + 1)), "gains utilisation factor");
		}

		log.debug("GU: gamma = {}/{} = {}, a = {}, factor = {}", totalGains, heatLossRate, gainToLossRatio,
				utilisationFactorExponent, gainsUtilisationFactor);

		return gainsUtilisationFactor;
	}

	/**
	 * This calculates the background temperature (responsive / unresponsive) as
	 * specified in BREDEM 2009. The CHM does something different.
	 * 
	 * @param parameters
	 * @param heatLosses
	 * @param zoneTwoheatedProportion
	 * @param totalGains
	 * @param utilisationFactorExponent
	 * @return
	 * @assumption BREDEM 2009 has a bug in the equation for computing the
	 *             unheated zone two temperature, which we have corrected here.
	 *             The BREDEM document is dimensionally inconsistent, containing
	 *             the sum of the heat loss parameter with the interzone heat
	 *             loss in the denominator. This should be the sum of the
	 *             specific heat loss and the interzone heat loss.
	 */
	protected static double[][] calculateBackgroundTemperatures(final IInternalParameters parameters,
			final SpecificHeatLosses heatLosses, final double zoneTwoheatedProportion, final double totalGains,
			final double utilisationFactorExponent) {
		final double Td1 = parameters.getZoneOneDemandTemperature();
		final double Td2 = parameters.getZoneTwoDemandTemperature();
		final double Text = parameters.getClimate().getExternalTemperature();

        final double specificHeatLoss = heatLosses.getSpecificHeatLoss();
		final double H = specificHeatLoss;
		final double H3 = heatLosses.interzoneHeatLoss;

		// Equation corrected from BREDEM 2009
		final double unheatedZoneTwoTemperature = Math.min(Td1, (Td1 * H3 + Text * H + totalGains) / (H + H3));

		log.debug("Unheated zone 2 temperature (with gains): {}", unheatedZoneTwoTemperature);

		// interpolate zone two demand temperature
		final double Td2_2 = Td2 * zoneTwoheatedProportion + (1 - zoneTwoheatedProportion) * unheatedZoneTwoTemperature;

		final double utilisationFactorInZone1 = calculateGainsUtilisationFactor(Td1, Text, specificHeatLoss, totalGains,
				utilisationFactorExponent);
		final double utilisationFactorInZone2 = calculateGainsUtilisationFactor(Td2_2, Text, specificHeatLoss,
				totalGains, utilisationFactorExponent);

		final double deltaT = parameters.getConstants().get(EnergyCalculatorConstants.UNRESPONSIVE_HEATING_SYSTEM_DELTA_T);

		final double[] unresponsiveTemperatures = new double[] { Math.max(Text, Td1 - deltaT),
				Math.max(Text, Td2_2 - deltaT) };

		final double[] responsiveTemperatures = new double[] {
				Text + div(utilisationFactorInZone1 * totalGains, specificHeatLoss, "repsonsive temperature 0"),
				Text + div(utilisationFactorInZone2 * totalGains, specificHeatLoss, "responsive temperature 1") };

		log.debug("Utilisation factors: {}, {}", utilisationFactorInZone1, utilisationFactorInZone2);

		// OK, so this gives us responsive/unresponsive for zone1/zone2
		return new double[][] { responsiveTemperatures, unresponsiveTemperatures };
	}

	public IEnergyStateFactory getStateFactory() {
		return stateFactory;
	}

	public void setStateFactory(final IEnergyStateFactory stateFactory) {
		this.stateFactory = stateFactory;
	}

	/**
	 * Run the energy calculation. There are inline comments in the source which
	 * may be more helpful, but this documentation gives a summary. The
	 * calculation takes the following steps
	 * <ol>
	 * <li>Passes a {@link Visitor} to the houseCase, to collect its contents;
	 * see the {@link IEnergyCalculatorVisitor} interface for more on this; this will
	 * total up relevant details of the house</li>
	 * <li>Invokes
	 * {@link #calculateSpecificHeatLosses(IEnergyCalculatorHouseCase, IEnergyCalculatorParameters, double, double, double, IStructuralInfiltrationAccumulator, List)}
	 * , to combine the fabric and infiltration losses and thermal bridging
	 * together and produce an {@link ISpecificHeatLosses} for the rest of the
	 * calculation.</li>
	 * <li>Sorts the energy transducers into order, using
	 * {@link #sortTransducers(List)}; this ensures that no transducer will be
	 * run before any that depend on it</li>
	 * <li>Runs all of the energy transducers
	 * {@link IEnergyTransducer#generate(IEnergyCalculatorHouseCase, IInternalParameters, ISpecificHeatLosses, IEnergyState)}
	 * methods up to the Gain/load ratio adjuster, by calling
	 * {@link #runToNonHeatTransducers(IEnergyCalculatorHouseCase, List, IEnergyTransducer, ISpecificHeatLosses, IInternalParameters, IEnergyState)}
	 * . These will include the standard transducers present in every house:
	 * <ol>
	 * <li>{@link HotWaterDemand09}</li>
	 * <li>{@link Appliances09}</li>
	 * <li>{@link MetabolicGainsSource}</li>
	 * <li>{@link EvaporativeGainsSource}</li>
	 * <li>{@link GainsTransducer}</li>
	 * </ol>
	 * 
	 * As these are passed into the visitor's constructor via the
	 * {@link #defaultTransducers} list.</li>
	 * <li>Computes the background temperature for each zone, by computing the
	 * background temperatures for fully responsive and unresponsive systems
	 * with
	 * {@link #calculateBackgroundTemperatures(IInternalParameters, ISpecificHeatLosses, double, double, double)}
	 * , and then using
	 * {@link #getBackgroundTemperatureFromHeatingSystems(List, ISpecificHeatLosses, IInternalParameters, IEnergyState, double[], double[], double[])}
	 * to determine where between those two extremes the house's background
	 * temperature</li>
	 * <li>Uses the {@link IHeatingSchedule} in the parameters to compute the
	 * <em>mean</em> temperature for each zone</li>
	 * <li>Invokes
	 * {@link #getAreaWeightedMeanTemperature(IEnergyCalculatorHouseCase, double[])} to
	 * convert the two temperatures into the single mean background temperature
	 * </li>
	 * <li>Invokes
	 * {@link #configureGainLossAdjuster(ISpecificHeatLosses, IInternalParameters, double[], double, double, double, GainLoadRatioAdjuster)}
	 * to tell the gain/load ratio calculation about the temperatures (see
	 * {@link GainLoadRatioAdjuster} for why this is required)</li>
	 * <li>Finally, runs the remaining heat-specific transducers using
	 * {@link #runHeatTransducers(IEnergyCalculatorHouseCase, List, ISpecificHeatLosses, IInternalParameters, IEnergyState, int)}
	 * </li>
	 * </ol>
	 * 
	 * @param houseCase
	 * @param parameters
	 * @return
	 */
	@Override
	public IEnergyCalculationResult[] evaluate(final IEnergyCalculatorHouseCase houseCase, final IEnergyCalculatorParameters eparameters,
			final ISeasonalParameters[] climate) {
		final Visitor v = new Visitor(constants, eparameters, defaultTransducers);

		houseCase.accept(constants, eparameters, v);
		log.debug("visitor: {}", v);

		sortTransducers(v.transducers);
		log.debug("New sort: {}", v.transducers);

		final IEnergyCalculationResult[] results = new IEnergyCalculationResult[climate.length];
		int i = 0;
		for (final ISeasonalParameters c : climate) {
			final IInternalParameters iparameters = new InternalParameters(eparameters, constants, c);
			results[i++] = evaluate(houseCase, iparameters, v);
		}

		return results;
	}

	private IEnergyCalculationResult evaluate(final IEnergyCalculatorHouseCase houseCase, final IInternalParameters parameters,
			final Visitor v) {
		final SpecificHeatLosses heatLosses = calculateSpecificHeatLosses(houseCase, parameters,
				v.totalSpecificHeatLoss, v.totalThermalMass, v.totalExternalArea, v.infiltration, v.ventilationSystems);

		// apply demand temperature adjustment, and compute zone 2 temp etc.
		final IInternalParameters adjustedParameters = adjustParameters(parameters, heatLosses, v.heatingSystems);

		final IEnergyState state = stateFactory.createEnergyState();
		// run transducer up to heat to compute background gains etc.
		final int indexOfFirstHeatingSystem = runToNonHeatTransducers(houseCase, v.transducers, v.glrAdjuster,
				heatLosses, adjustedParameters, state);

		final double[] demandTemperature = new double[] { adjustedParameters.getZoneOneDemandTemperature(),
				adjustedParameters.getZoneTwoDemandTemperature() };
		final double timeConstant = getTimeConstant(heatLosses);
		final double coolingTime = getCoolingTime(timeConstant);
		final double utilisationFactorExponent = getUtilisationFactorExponent(timeConstant);

		final double totalGains = state.getExcessSupply(EnergyType.GainsUSEFUL_GAINS);
		final double[][] backgroundTemperatures = calculateBackgroundTemperatures(adjustedParameters, heatLosses,
				houseCase.getZoneTwoHeatedProportion(), totalGains, utilisationFactorExponent);
		final double[] idealBackgroundTemperature = backgroundTemperatures[0];
		final double[] worstCaseBackgroundTemperature = backgroundTemperatures[1];

		if (log.isDebugEnabled()) {
			log.debug("Demand temperature: {}", demandTemperature);
			log.debug("Fully responsive background temperature: {}", idealBackgroundTemperature);
			log.debug("Fully unresponsive background temperature: {}", worstCaseBackgroundTemperature);
		}

		final double[] backgroundTemperature = getBackgroundTemperatureFromHeatingSystems(v.heatingSystems,
				v.proportions, heatLosses, adjustedParameters, state, demandTemperature, idealBackgroundTemperature,
				worstCaseBackgroundTemperature);

		if (log.isDebugEnabled())
			log.debug("Average background temperature for all systems = {}", backgroundTemperature);

		// find mean temperature from profile
		final double[] meanTemperature = new double[ZoneType.values().length];
		for (final ZoneType zt : ZoneType.values()) {
			final int zi = zt.ordinal();
			meanTemperature[zi] = adjustedParameters.getClimate().getHeatingSchedule(zt).getMeanTemperature(
					demandTemperature[zi], backgroundTemperature[zi], coolingTime * MINUTES_PER_HOUR);
		}

		double areaWeightedMeanTemperature = getAreaWeightedMeanTemperature(houseCase, meanTemperature);
		state.increaseSupply(EnergyType.HackUNADJUSTED_TEMPERATURE, areaWeightedMeanTemperature);

		areaWeightedMeanTemperature += adjustedParameters.getTemperatureAdjustment();

		if (log.isDebugEnabled())
			log.debug("Mean temps = {}, Area-weighted = {}", meanTemperature, areaWeightedMeanTemperature);

		configureGainLossAdjuster(heatLosses, adjustedParameters, demandTemperature, totalGains,
				utilisationFactorExponent, areaWeightedMeanTemperature, v.glrAdjuster);

		runHeatTransducers(houseCase, v.transducers, heatLosses, adjustedParameters, state, indexOfFirstHeatingSystem);

		return new EnergyCalculationResult(state, heatLosses, v.areasByType[0], v.areasByType[1]);
	}

	/**
	 * Run the remaining transducers, once the gain-load ratio adjustment has
	 * happened (call to
	 * {@link #configureGainLossAdjuster(ISpecificHeatLosses, IInternalParameters, double[], double, double, double, GainLoadRatioAdjuster)}
	 * )
	 * 
	 * @param houseCase
	 * @param transducers
	 * @param heatLosses
	 * @param adjustedParameters
	 * @param state
	 * @param indexOfFirstHeatingSystem
	 *            the index of the gain load ratio adjuster
	 */
	private void runHeatTransducers(final IEnergyCalculatorHouseCase houseCase, final List<IEnergyTransducer> transducers,
			final ISpecificHeatLosses heatLosses, final IInternalParameters adjustedParameters,
			final IEnergyState state, final int indexOfFirstHeatingSystem) {
		final boolean heatingOn = adjustedParameters.getClimate().isHeatingOn();
		for (int i = indexOfFirstHeatingSystem; i < transducers.size(); i++) {
			final IEnergyTransducer transducer = transducers.get(i);
			if (log.isDebugEnabled())
				log.debug("Running transducer {}", transducer);
			state.setCurrentServiceType(transducer.getServiceType(), transducer.toString());
			if (heatingOn || (transducer.getServiceType() != ServiceType.PRIMARY_SPACE_HEATING && transducer.getServiceType() != ServiceType.SECONDARY_SPACE_HEATING)) {
				transducer.generate(houseCase, adjustedParameters, heatLosses, state);
			}
		}
	}

	/**
	 * Set the various temperature related values into the GLR adjuster; this
	 * involves using
	 * {@link #calculateGainsUtilisationFactor(double, double, double, double, double)}
	 * to determine the final, true gain utilisation factor after doing the
	 * zone-specific versions.
	 * 
	 * @param heatLosses
	 * @param adjustedParameters
	 * @param demandTemperature
	 * @param totalGains
	 * @param utilisationFactorExponent
	 * @param areaWeightedMeanTemperature
	 * @param glrAdjuster
	 */
	protected static void configureGainLossAdjuster(final ISpecificHeatLosses heatLosses,
			final IInternalParameters adjustedParameters, final double[] demandTemperature, final double totalGains,
			final double utilisationFactorExponent, final double areaWeightedMeanTemperature,
			final GainLoadRatioAdjuster glrAdjuster) {
		final double revisedGUF = calculateGainsUtilisationFactor(areaWeightedMeanTemperature,
				adjustedParameters.getClimate().getExternalTemperature(), heatLosses.getSpecificHeatLoss(), totalGains,
				utilisationFactorExponent);

		glrAdjuster.setAreaWeightedMeanTemperature(areaWeightedMeanTemperature);
		glrAdjuster.setDemandTemperature(demandTemperature);
		glrAdjuster.setRevisedGUF(revisedGUF);
		glrAdjuster.setExternalTemperature(adjustedParameters.getClimate().getExternalTemperature());
	}

	/**
	 * Compute the area weighted mean temperature for the given zonal means &
	 * house case
	 * 
	 * @param houseCase
	 * @param meanTemperature
	 * @return
	 */
	protected static double getAreaWeightedMeanTemperature(final IEnergyCalculatorHouseCase houseCase,
			final double[] meanTemperature) {
		return meanTemperature[0] * houseCase.getLivingAreaProportionOfFloorArea()
				+ meanTemperature[1] * (1 - houseCase.getLivingAreaProportionOfFloorArea());
	}

	/**
	 * 
	 * @param v
	 * @param heatLosses
	 * @param adjustedParameters
	 * @param state
	 * @param demandTemperature
	 * @param idealBackgroundTemperature
	 * @param worstCaseBackgroundTemperature
	 * @param remainingContribution
	 * @return
	 * @assumption If there is no heating system in the house, the background
	 *             temperature is taken to be the ideal background temperature
	 *             for a fully responsive system; this is used to compute energy
	 *             demand, which will then be unsatisfied as there's no heating
	 *             system. BREDEM does not specify a rule for this.
	 */
	protected static double[] getBackgroundTemperatureFromHeatingSystems(final List<IHeatingSystem> heatingSystems,
			final Map<IHeatingSystem, Double> proportions, final ISpecificHeatLosses heatLosses,
			final IInternalParameters adjustedParameters, final IEnergyState state, final double[] demandTemperature,
			final double[] idealBackgroundTemperature, final double[] worstCaseBackgroundTemperature) {
		final double[] backgroundTemperature = new double[2];
		
		double cumulativeProportions = 0d;
		
		for (final IHeatingSystem system : heatingSystems) {
			final double[] systemBackgroundTemperature = system.getBackgroundTemperatures(demandTemperature,
					idealBackgroundTemperature, worstCaseBackgroundTemperature, adjustedParameters, state, heatLosses);

			if (log.isDebugEnabled())
				log.debug("Background temperature from {} = {}", system, systemBackgroundTemperature);

			final double systemContribution = proportions.get(system);
			cumulativeProportions += systemContribution;

			for (int i = 0; i < systemBackgroundTemperature.length; i++) {
				backgroundTemperature[i] += systemBackgroundTemperature[i] * systemContribution;
			}
		}
		
		if (cumulativeProportions != 1d) {
			throw new RuntimeException("Heating proportions should sum to 1 when calculating the background temperature. Was " + cumulativeProportions);
		}

		// here is where the assumption above is applied

		if (heatingSystems.isEmpty()) {
			if (log.isDebugEnabled())
				log.debug("There are no heating systems - using ideal background temperature");
			for (final ZoneType zt : ZoneType.values()) {
				backgroundTemperature[zt.ordinal()] = idealBackgroundTemperature[zt.ordinal()];
			}
		}

		return backgroundTemperature;
	}

	private final double getUtilisationFactorExponent(final double timeConstant) {
		return 1 + (timeConstant / UTILISATION_FACTOR_TIME_CONSTANT_DIVISOR);
	}

	private final double getCoolingTime(final double timeConstant) {
		return MINIMUM_COOLING_TIME + COOLING_TIME_CONSTANT_MULTIPLIER * timeConstant;
	}

	private final double getTimeConstant(final SpecificHeatLosses heatLosses) {
        return div(heatLosses.getThermalMassParameter(),
                   (TIME_CONSTANT_HEAT_LOSS_PARAMETER_MULTIPLIER * heatLosses.getHeatLossParameter()), "time constant");
	}

	/**
	 * Run all the transducers until we get to the gain to loss ratio adjuster
	 * passed in, and then return the index we stopped at
	 * 
	 * @param houseCase
	 * @param v
	 * @param heatLosses
	 * @param adjustedParameters
	 * @param state
	 * @return
	 */
	protected static int runToNonHeatTransducers(final IEnergyCalculatorHouseCase houseCase,
			final List<IEnergyTransducer> transducers, final IEnergyTransducer stop,
			final ISpecificHeatLosses heatLosses, final IInternalParameters adjustedParameters,
			final IEnergyState state) {
		int indexOfFirstHeatingSystem = 0;
		final boolean heatingOn = adjustedParameters.getClimate().isHeatingOn();
		for (final IEnergyTransducer transducer : transducers) {
			if (transducer == stop)
				break;
			indexOfFirstHeatingSystem++;
			if (heatingOn || (transducer.getServiceType() != ServiceType.PRIMARY_SPACE_HEATING && transducer.getServiceType() != ServiceType.SECONDARY_SPACE_HEATING)) {
				if (log.isDebugEnabled())
					log.debug("Running {}", transducer);
				state.setCurrentServiceType(transducer.getServiceType(), transducer.toString());
				transducer.generate(houseCase, adjustedParameters, heatLosses, state);
			}
		}
		return indexOfFirstHeatingSystem;
	}

	/**
	 * Adjust the demand temperature in the input parameters
	 * 
	 * In summary,
	 * 
	 * <ol>
	 * <li>Accumulate zone 1 temperature tweaks from heating system</li>
	 * <li>If zone 2 temperature is not set, calculate and override it</li>
	 * <li>Accumulate heating period time tweaks</li>
	 * <li>Create a new parameters which applies these tweaks</li>
	 * </ol>
	 * 
	 * @param parameters
	 * @param heatingSystems
	 * @return
	 * @assumption Secondary heating system control parameter (BREDEM 8 9.1
	 *             Tctl) is the maximum over all heating systems if there are
	 *             multiple heating systems (BREDEM does not specify)
	 * @assumption When adjusting the demand temperature, if there are multiple
	 *             adjustments due to multiple systems the adjustments are
	 *             summed (BREDEM does not specify)
	 * 
	 */
	protected IInternalParameters adjustParameters(final IInternalParameters parameters,
			final ISpecificHeatLosses heatLosses, final List<IHeatingSystem> heatingSystems) {
		double totalTemperatureAdjustment = 0;
		double heatingSystemZoneTwoControlParameter = 0;
		double heatingSystemZoneTwoTemperature;

		for (final IHeatingSystem system : heatingSystems) {
			totalTemperatureAdjustment += system.getDemandTemperatureAdjustment(parameters);
			heatingSystemZoneTwoControlParameter = Math.max(heatingSystemZoneTwoControlParameter,
					system.getZoneTwoControlParameter(parameters));
		}

		if (!parameters.isZoneTwoDemandTemperatureSpecified()) {
			heatingSystemZoneTwoTemperature = calculateZoneTwoDemandTemperature(
					parameters.getInterzoneTemperatureDifference(), heatLosses.getHeatLossParameter(),
					heatingSystemZoneTwoControlParameter, parameters.getZoneOneDemandTemperature());
		} else {
			heatingSystemZoneTwoTemperature = parameters.getZoneTwoDemandTemperature();
		}

		return new ParameterAdjustment(parameters, totalTemperatureAdjustment, heatingSystemZoneTwoTemperature);
	}

	protected double calculateZoneTwoDemandTemperature(final double interzoneTemperatureDifference,
			final double heatLossParameter, final double heatingSystemZoneTwoControlParameter,
			final double newZone1DemandTemperature) {
		final double uncontrolledZoneTwoDemandTemperature = newZone1DemandTemperature - interzoneTemperatureDifference
				* (Math.min(REFERENCE_HEAT_LOSS_PARAMETER, heatLossParameter) / REFERENCE_HEAT_LOSS_PARAMETER);

		final double controlledZoneTwoDemandTemperature = newZone1DemandTemperature + interzoneTemperatureDifference
				* (Math.pow(Math.min(heatLossParameter - REFERENCE_HEAT_LOSS_PARAMETER, 0), 2)
						/ REFERENCE_HEAT_LOSS_PARAMETER2 - 1);

		final double result = controlledZoneTwoDemandTemperature * heatingSystemZoneTwoControlParameter
				+ uncontrolledZoneTwoDemandTemperature * (1 - heatingSystemZoneTwoControlParameter);
		return result;
	}

	/**
	 * Place the given list of transducers into order satisfying the following
	 * rules:
	 * <ol>
	 * <li>if something is /input led/ that means that /everything/ which
	 * outputs a given input needs to happen before it</li>
	 * <li>if something is /output led/ that means that everything which inputs
	 * it /or/ lazily outputs it needs to happen before it</li>
	 * </ol>
	 * 
	 * @param transducers
	 */
	protected static void sortTransducers(final List<IEnergyTransducer> transducers) {
		Collections.sort(transducers, phasingComparator);
	}
}
