package uk.org.cse.nhm.hom.constants;

import uk.org.cse.nhm.energycalculator.api.ConstantDescription;
import uk.org.cse.nhm.energycalculator.api.IConstant;

@ConstantDescription("Miscellaneous constants to do with various heating systems. Some of these could do with tidying up into other places.")
public enum HeatingSystemConstants implements IConstant {
	@ConstantDescription("The temperature adjustment for a direct electric non-thermostatic heater")
	DIRECT_ELECTRIC_NONTHERMOSTATIC_T_ADJ(0.5),
	@ConstantDescription("The worst-case primary pipework losses for a heating system")
	BAD_PRIMARY_PIPEWORK_LOSSES(139.1),
	@ConstantDescription("The medium-case primary pipework losses for a heating system")
	MED_PRIMARY_PIPEWORK_LOSSES(69.58),
	@ConstantDescription("The best-case primary pipework losses for a heating system")
	GOOD_PRIMARY_PIPEWORK_LOSSES(41.0),
	/**
	 * Additional primary pipework losses to do with heep hot facility, in this order:
	 * No KHF, Uncontrolled KHF, Controlled KHF
	 */
	@ConstantDescription("Additional losses caused by the presence of a keep hot facility on a combi boiler")
	KEEP_HOT_FACILITY_PIPEWORK_LOSSES(25.0, 95.0, 25.0),
	
	@ConstantDescription("Distribution losses from a centrally heated system, as a proportion of hot water energy")
	CENTRAL_HEATING_DISTRIBUTION_LOSSES(0.15 / 0.85),
	
	@ConstantDescription("Solar primary pipework correction factor (by month)")
	CENTRAL_HEATING_SOLAR_PPCF(1, 1, 0.94, 0.70, 0.45, 0.44, 0.44, 0.48, 0.76, 0.94, 1, 1),
	
	@ConstantDescription("The proportion of hot water energy that is used showering (for electric showers)")
	SHOWER_DEMAND_PROPORTION(0.348),
	
	@ConstantDescription("The proportion of hot water volume that is consumed by electric showers")
	SHOWER_VOLUME_PROPORTION(0.25),
	
	@ConstantDescription("Electric CPSU low-rate heat constant")
	ELECTRIC_CPSU_LOW_RATE_HEAT_CONSTANT(0.1456), 
	
	@ConstantDescription("Electric CPSU temperature offset (SAP appendix F)")
	ELECTRIC_CPSU_WINTER_TEMPERATURE_OFFSET(48),
	
	@ConstantDescription("Combi additional loss factor daily hot water usage limit (SAP 2009 table 3a)")
	COMBI_HOT_WATER_USAGE_LIMIT(100),
	
	@ConstantDescription("Instantaneous combi without KHF loss factor (SAP 2009 table 3a, row 1, converted from kWh/year to watts)")
	INSTANTANEOUS_COMBI_FACTOR(68.4477), 
	
	@ConstantDescription("The threshold volume above which a storage combi boiler has no additional losses (SAP 2009 table 3a)")
	STORAGE_COMBI_VOLUME_THRESHOLD(55), 
	
	@ConstantDescription("The additional wattage of combi losses when using a combi with a keep hot facility with a timeclock (SAP 2009 table 3a, converted to watts)")
	INSTANTANEOUS_COMBI_FACTOR_KHF_WITH_TIMECLOCK(68.4477),
	
	@ConstantDescription("The additional wattage of combi losses when using a combi with a keep hot facility without a timeclock (SAP 2009 table 3a, converted to watts)")
	INSTANTANEOUS_COMBI_FACTOR_KHF_WITHOUT_TIMECLOCK(102.671), 
	
	@ConstantDescription("The terms from the additional wattage equation for storage combis with store volume under 55l in SAP 2009 table 3a (in SAP this is fu * (600 - (volume - 15) * 15) - these values are in the same order here)")
	STORAGE_COMBI_LOSS_TERMS(68.4477, 15, 1.711)
	;
	
	
	
	private final double[] values;
	
	HeatingSystemConstants(final double... values) {
		this.values = values;
	}
	
	@Override
	public <T> T getValue(Class<T> clazz) {
		if (clazz.isAssignableFrom(double[].class)) {
			if (values == null) throw new RuntimeException(this + " cannot be read as a double[]");
			return clazz.cast(values);
		} else if (clazz.isAssignableFrom(Double.class)) {
			if (values.length != 1) throw new RuntimeException(this + " cannot be read as a double");
			return clazz.cast(values[0]);
		} else {
			throw new RuntimeException(this + " cannot be read as a " + clazz.getSimpleName());
		}
	}
}