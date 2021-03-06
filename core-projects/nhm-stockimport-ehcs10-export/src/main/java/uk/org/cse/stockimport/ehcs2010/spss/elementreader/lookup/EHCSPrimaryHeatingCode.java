package uk.org.cse.stockimport.ehcs2010.spss.elementreader.lookup;

import static uk.org.cse.nhm.hom.emf.technologies.FuelType.ELECTRICITY;
import static uk.org.cse.nhm.hom.emf.technologies.FuelType.HOUSE_COAL;
import static uk.org.cse.nhm.hom.emf.technologies.FuelType.MAINS_GAS;
import static uk.org.cse.nhm.hom.emf.technologies.boilers.FlueType.BALANCED_FLUE;
import static uk.org.cse.nhm.hom.emf.technologies.boilers.FlueType.FAN_ASSISTED_BALANCED_FLUE;
import static uk.org.cse.nhm.hom.emf.technologies.boilers.FlueType.OPEN_FLUE;
import static uk.org.cse.stockimport.domain.services.SpaceHeatingSystemType.AIR_SOURCE_HEAT_PUMP;
import static uk.org.cse.stockimport.domain.services.SpaceHeatingSystemType.BACK_BOILER;
import static uk.org.cse.stockimport.domain.services.SpaceHeatingSystemType.BACK_BOILER_NO_CENTRAL_HEATING;
import static uk.org.cse.stockimport.domain.services.SpaceHeatingSystemType.COMMUNITY_HEATING_WITHOUT_CHP;
import static uk.org.cse.stockimport.domain.services.SpaceHeatingSystemType.COMMUNITY_HEATING_WITH_CHP;
import static uk.org.cse.stockimport.domain.services.SpaceHeatingSystemType.GROUND_SOURCE_HEAT_PUMP;
import static uk.org.cse.stockimport.domain.services.SpaceHeatingSystemType.ROOM_HEATER;
import static uk.org.cse.stockimport.domain.services.SpaceHeatingSystemType.STANDARD;
import static uk.org.cse.stockimport.domain.services.SpaceHeatingSystemType.STORAGE_HEATER;
import static uk.org.cse.stockimport.domain.services.SpaceHeatingSystemType.WARM_AIR;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.boilers.FlueType;
import uk.org.cse.stockimport.domain.services.SpaceHeatingSystemType;

/**
 * Table 4 of the Cambridge Architectural Research EHS conversion document.
 * 
 * @assumption The CHM has no concept of solid fuel back boilers, and so has
 *             categorised these as standard boilers. We do have this concept,
 *             so we have categorised them as back boilers. Affects EHCS codes 116,
 *             117 and 118.
 * 
 * @assumption The CHM has no concept of gas, oil, or coal room heaters, and so
 *             has categorised them as standard boilers. We have categorised
 *             them as room heaters. Affects EHCS codes 601 to 610, 615, 616, 618 and
 *             620.
 * 
 * @assumption We have introduced a new type BACK_BOILER_NO_CENTRAL_HEATING for
 *             back boilers which are not connected to radiators (used for room
 *             heating as room heaters. Affects EHCS codes 617 and 619.
 * 
 * @assumption The CHM conversion document has no concept of an oil warm air
 *             heater, and always assumes that those described as 'gas/oil' are
 *             mains gas. It also assumes that a number of unknown categories
 *             are mains gas, and makes no allowances for bottled or bulk LPG.
 *             We have added alternative fuel types to some EHCS codes to allow us
 *             to support these alternative fuel types if the survey data
 *             indicates they are present.
 * 
 * @author glenns
 * @since 1.0
 */
public enum EHCSPrimaryHeatingCode {
	GAS_FAN_ASSISTED_ELECTRIC_IGNITION_LOW_THERMAL_CAPACITY(101,STANDARD,MAINS_GAS,FAN_ASSISTED_BALANCED_FLUE,84, FuelType.BOTTLED_LPG, FuelType.BULK_LPG),
	GAS_FAN_ASSISTED_ELECTRIC_IGNITION_HIGH_THERMAL_CAPACITY(102,STANDARD,MAINS_GAS,FAN_ASSISTED_BALANCED_FLUE,84, FuelType.BOTTLED_LPG, FuelType.BULK_LPG),
	GAS_FAN_ASSISTED_PERM_PILOT_LIGHT_OR_UNKNOWN_LOW_THERMAL_CAP(103,STANDARD,MAINS_GAS,FAN_ASSISTED_BALANCED_FLUE,80, FuelType.BOTTLED_LPG, FuelType.BULK_LPG),
	GAS_FAN_ASSISTED_PERM_PILOT_LIGHT_OR_UNKNOWN_HIGH_THERMAL_CAP(104,STANDARD,MAINS_GAS,FAN_ASSISTED_BALANCED_FLUE,80, FuelType.BOTTLED_LPG, FuelType.BULK_LPG),
	GAS_WALL_MOUNTED_OPEN_OR_BALANCED_FLUE(105,STANDARD,MAINS_GAS,BALANCED_FLUE,66, FuelType.BOTTLED_LPG, FuelType.BULK_LPG),
	GAS_FLOOR_MOUNTED_OR_BACK_BOILER_OPEN_OR_BALANCED_FLUE(106,BACK_BOILER,MAINS_GAS,BALANCED_FLUE,66, FuelType.BOTTLED_LPG, FuelType.BULK_LPG),
	GAS_UNKNOWN_OPEN_OR_BALANCED_FLUE(107,STANDARD,MAINS_GAS,BALANCED_FLUE,66, FuelType.BOTTLED_LPG, FuelType.BULK_LPG),
	GAS_UNKNOWN_FLUE_TYPE(108,STANDARD,MAINS_GAS,BALANCED_FLUE,75, FuelType.BOTTLED_LPG, FuelType.BULK_LPG),
	OIL(109,STANDARD,FuelType.OIL,BALANCED_FLUE,71),
	SOLID_MANUAL_FEED_IN_HEATED_SPACE(110,STANDARD,HOUSE_COAL,BALANCED_FLUE,65),
	SOLID_MANUAL_FEED_IN_UNHEATED_SPACE(111,STANDARD,HOUSE_COAL,BALANCED_FLUE,60),
	SOLID_MANUAL_FEED_UNKNOWN(112,STANDARD,HOUSE_COAL,BALANCED_FLUE,60),
	SOLID_AUTO_FEED_IN_HEATED_SPACE(113,STANDARD,HOUSE_COAL,BALANCED_FLUE,70),
	SOLID_AUTO_FEED_IN_UNHEATED_SPACE(114,STANDARD,HOUSE_COAL,BALANCED_FLUE,65),
	SOLID_AUTO_FEED_UNKNOWN(115,STANDARD,HOUSE_COAL,BALANCED_FLUE,65),
	SOLID_BACK_BOILER_OPEN_FIRE(116, BACK_BOILER, HOUSE_COAL, BALANCED_FLUE, 63), 
	SOLID_BACK_BOILER_CLOSED_FIRE(117, BACK_BOILER, HOUSE_COAL, BALANCED_FLUE, 67), 
	SOLID_BACK_BOILER_UNKNOWN(118, BACK_BOILER, HOUSE_COAL,	BALANCED_FLUE, 67),
	SOLID_UNKNOWN(119,STANDARD,HOUSE_COAL,BALANCED_FLUE,65),
	ELECTRIC_IN_HEATED_SPACE(120,STANDARD,ELECTRICITY,BALANCED_FLUE,100),
	ELECTRIC_IN_UNHEATED_SPACE(121,STANDARD,ELECTRICITY,BALANCED_FLUE,85),
	ELECTRIC_UNKNOWN(122,STANDARD,ELECTRICITY,BALANCED_FLUE,85),
	HEAT_PUMP_GROUND_SOURCE(123,GROUND_SOURCE_HEAT_PUMP,ELECTRICITY,BALANCED_FLUE,320),
	HEAT_PUMP_WATER_SOURCE(124,null,ELECTRICITY,BALANCED_FLUE,300),
	HEAT_PUMP_AIR_SOURCE(125,AIR_SOURCE_HEAT_PUMP,ELECTRICITY,BALANCED_FLUE,250),
	UNKNOWN(129,STANDARD,MAINS_GAS,BALANCED_FLUE,75, FuelType.ELECTRICITY),
	ELECTRIC_STORAGE_OLD_LARGE_VOLUME(201,STORAGE_HEATER,ELECTRICITY,BALANCED_FLUE,100),
	ELECTRIC_STORAGE_MODERN_SLIMLINE_CONVECTOR(202,STORAGE_HEATER,ELECTRICITY,BALANCED_FLUE,100),
	ELECTRIC_STORAGE_MODERN_SLIMLINE_WITH_FAN(203,STORAGE_HEATER,ELECTRICITY,FAN_ASSISTED_BALANCED_FLUE,100),
	ELECTRIC_STORAGE_HEATER_UNKNOWN(204,STORAGE_HEATER,ELECTRICITY,BALANCED_FLUE,100),
	WARM_AIR_GAS_OR_OIL_WITH_FAN_DUCTED(301,WARM_AIR,MAINS_GAS,FAN_ASSISTED_BALANCED_FLUE,70, FuelType.OIL),
	WARM_AIR_GAS_OR_OIL_WITH_FAN_ROOM_HEATER(302,WARM_AIR,MAINS_GAS,FAN_ASSISTED_BALANCED_FLUE,69, FuelType.OIL),
	WARM_AIR_GAS_OR_OIL_WITH_FAN_UNKNOWN(303,WARM_AIR,MAINS_GAS,FAN_ASSISTED_BALANCED_FLUE,70, FuelType.OIL),
	WARM_AIR_GAS_OR_OIL_WITH_BALANCED_OR_OPEN_FLUE_DUCTED_ON_OFF_CONTROL(304,WARM_AIR,MAINS_GAS,BALANCED_FLUE,70, FuelType.OIL),
	WARM_AIR_GAS_OR_OIL_WITH_BALANCED_OR_OPEN_FLUE_DUCTED_MODULAR_CONTROL(305,WARM_AIR,MAINS_GAS,BALANCED_FLUE,72, FuelType.OIL),
	WARM_AIR_GAS_OR_OIL_WITH_BALANCED_OR_OPEN_FLUE_DUCTED_HEAT_RECOVERY(306,WARM_AIR,MAINS_GAS,BALANCED_FLUE,85, FuelType.OIL),
	WARM_AIR_GAS_OR_OIL_WITH_BALANCED_OR_OPEN_FLUE_DUCTED_UNKNOWN(307,WARM_AIR,MAINS_GAS,BALANCED_FLUE,76, FuelType.OIL),
	WARM_AIR_GAS_OR_OIL_WITH_BALANCED_OR_OPEN_FLUE_STUB_DUCT_NO_FLUE_RECOV(308,WARM_AIR,MAINS_GAS,BALANCED_FLUE,74, FuelType.OIL),
	WARM_AIR_GAS_OR_OIL_WITH_BALANCED_OR_OPEN_FLUE_STUB_DUCT_FLUE_RECOVERY(309,WARM_AIR,MAINS_GAS,BALANCED_FLUE,85, FuelType.OIL),
	WARM_AIR_GAS_OR_OIL_WITH_BALANCED_OR_OPEN_FLUE_STUB_DUCTED_UNKNOWN(310,WARM_AIR,MAINS_GAS,BALANCED_FLUE,80, FuelType.OIL),
	WARM_AIR_GAS_OR_OIL_WITH_BALANCED_OR_OPEN_FLUE_CONDENSING(311,WARM_AIR,MAINS_GAS,BALANCED_FLUE,81, FuelType.OIL),
	WARM_AIR_GAS_OR_OIL_WITH_BALANCED_OR_OPEN_FLUE_UNKNOWN(312,WARM_AIR,MAINS_GAS,BALANCED_FLUE,78, FuelType.OIL),
	WARM_AIR_ELECTRICAIRE(313,WARM_AIR,ELECTRICITY,BALANCED_FLUE,100),
	WARM_AIR_GROUND_SOURCE_HEAT_PUMP(314,GROUND_SOURCE_HEAT_PUMP,ELECTRICITY,FAN_ASSISTED_BALANCED_FLUE,320),
	WARM_AIR_WATER_SOURCE_HEAT_PUMP(315,null,ELECTRICITY,FAN_ASSISTED_BALANCED_FLUE,300),
	WARM_AIR_AIR_SOURCE_HEAT_PUMP(316,AIR_SOURCE_HEAT_PUMP,ELECTRICITY,FAN_ASSISTED_BALANCED_FLUE,250),
	WARM_AIR_UNKNOWN(319,WARM_AIR,MAINS_GAS,FAN_ASSISTED_BALANCED_FLUE,76, FuelType.OIL, FuelType.ELECTRICITY),
	COMMUNAL_DEDICATED_BOILER(401,COMMUNITY_HEATING_WITHOUT_CHP,MAINS_GAS,BALANCED_FLUE,75),
	COMMUNAL_WASTE_HEAT_FROM_POWER_STATION(402,COMMUNITY_HEATING_WITHOUT_CHP,MAINS_GAS,BALANCED_FLUE,75),
	COMMUNAL_UNKNOWN(403,COMMUNITY_HEATING_WITHOUT_CHP,MAINS_GAS,BALANCED_FLUE,75),
	CHP(404,COMMUNITY_HEATING_WITH_CHP,MAINS_GAS,BALANCED_FLUE,75),
	COMMUNAL_OR_CHP_UNKNOWN(405,COMMUNITY_HEATING_WITHOUT_CHP,MAINS_GAS,BALANCED_FLUE,75),
	CHP_MICRO_DOMESTIC_WARM_AIR(406,COMMUNITY_HEATING_WITH_CHP,MAINS_GAS,BALANCED_FLUE,75),
	CHP_MICRO_DOMESTIC_RADIATOR(407,COMMUNITY_HEATING_WITH_CHP,MAINS_GAS,BALANCED_FLUE,75),
	ELECTRIC_CEILING_HEATING(501,ROOM_HEATER,ELECTRICITY,FAN_ASSISTED_BALANCED_FLUE,100),
	ELECTRIC_UNDERFLOOR_HEATING(502,ROOM_HEATER,ELECTRICITY,FAN_ASSISTED_BALANCED_FLUE,100),
	ELECTRIC_UNKNOWN2(503,ROOM_HEATER,ELECTRICITY,FAN_ASSISTED_BALANCED_FLUE,100),
	ROOM_HEATER_GAS_OPEN_FLUE(601,ROOM_HEATER,MAINS_GAS,OPEN_FLUE,63),
	ROOM_HEATER_GAS_BALANCED_FLUE(602,ROOM_HEATER,MAINS_GAS,BALANCED_FLUE,58),
	ROOM_HEATER_GAS_FAN_ASSISTED_FLUE(603,ROOM_HEATER,MAINS_GAS,FAN_ASSISTED_BALANCED_FLUE,72),
	ROOM_HEATER_GAS_CONDENSING(604,ROOM_HEATER,MAINS_GAS,BALANCED_FLUE,85),
	ROOM_HEATER_GAS_LIVE_EFFECT_SEALED_TO_CHIMNEY(605,ROOM_HEATER,MAINS_GAS,BALANCED_FLUE,40),
	ROOM_HEATER_GAS_LIVE_EFFECT_FAN_ASSISTED_FLUE(606,ROOM_HEATER,MAINS_GAS,FAN_ASSISTED_BALANCED_FLUE,45),
	ROOM_HEATER_GAS_DECORATIVE_FUEL_EFFECT_GAS_FIRE_OPEN_TO_CHIMNEY(607,ROOM_HEATER,MAINS_GAS,OPEN_FLUE,20),
	ROOM_HEATER_GAS_FLUELESS_GAS_FIRE(608,ROOM_HEATER,MAINS_GAS,BALANCED_FLUE,90),
	ROOM_HEATER_GAS_UNKNOWN(609,ROOM_HEATER,MAINS_GAS,BALANCED_FLUE,59),
	ROOM_HEATER_OIL_FIXED_HEATERS(610,ROOM_HEATER,FuelType.OIL,OPEN_FLUE,55),
	ROOM_HEATER_ELECTRIC_PANEL_CONVECTOR_OR_RADIANT_HEATERS(611,ROOM_HEATER,ELECTRICITY,FAN_ASSISTED_BALANCED_FLUE,100),
	ROOM_HEATER_ELECTRIC_UNKNOWN(614,ROOM_HEATER,ELECTRICITY,FAN_ASSISTED_BALANCED_FLUE,100),
	ROOM_HEATER_SOLID_OPEN_FIRE_IN_GRATE(615,ROOM_HEATER,HOUSE_COAL,OPEN_FLUE,37),
	ROOM_HEATER_SOLID_OPEN_FIRE_IN_GRATE_WITH_THROAT_RESTRICTOR(616,ROOM_HEATER,HOUSE_COAL,OPEN_FLUE,37),
	ROOM_HEATER_SOLID_OPEN_FIRE_IN_GRATE_WITH_BACK_BOILER_NO_RADS(617,BACK_BOILER_NO_CENTRAL_HEATING,HOUSE_COAL,OPEN_FLUE,50),
	ROOM_HEATER_SOLID_CLOSED_ROOM_HEATER_ONLY(618,ROOM_HEATER,HOUSE_COAL,BALANCED_FLUE,65),
	ROOM_HEATER_SOLID_CLOSED_ROOM_HEATER_BACK_BOILER_NO_RADS(619,BACK_BOILER_NO_CENTRAL_HEATING,HOUSE_COAL,BALANCED_FLUE,67),
	ROOM_HEATER_SOLID_UNKNOWN(620,ROOM_HEATER,HOUSE_COAL,OPEN_FLUE,51),
	ROOM_HEATER_UNKNOWN(621,ROOM_HEATER,ELECTRICITY,FAN_ASSISTED_BALANCED_FLUE,63),
	OPTICAL_READER_ERROR(7777,STANDARD,MAINS_GAS,BALANCED_FLUE,75),
	QUESTION_NOT_APPLICABLE_NO_PRIMARY_HEATING(8888,STANDARD,MAINS_GAS,BALANCED_FLUE,75),
	UNKNOWN2(9999,STANDARD,MAINS_GAS,BALANCED_FLUE,75);

	private int ehsCode;
	private SpaceHeatingSystemType system;
	private FuelType fuelType;
	private FlueType flueType;
	private int efficiency;
	private Set<FuelType> alternativeFuels;

	private EHCSPrimaryHeatingCode(int ehsCode, SpaceHeatingSystemType system, FuelType fuelType, FlueType flueType, int efficiency, FuelType... alternativeFuels) {
		this.ehsCode = ehsCode;
		this.system = system;
		this.fuelType = fuelType;
		this.flueType = flueType;
		this.efficiency = efficiency;
		this.alternativeFuels = ImmutableSet.copyOf(alternativeFuels);
	}
	
	/**
     * @since 1.0
     */
    public int getEhsCode() {
		return ehsCode;
	}

    /**
     * @since 1.0
     */
    public SpaceHeatingSystemType getSystem() {
		return system;
	}

    /**
     * @since 1.0
     */
    public FuelType getFuelType() {
		return fuelType;
	}

    /**
     * @since 1.0
     */
    public void setFuelType(FuelType fuelType) {
		this.fuelType = fuelType;
	}

    /**
     * @since 1.0
     */
    public FlueType getFlueType() {
		return flueType;
	}

    /**
     * @since 1.0
     */
    public double getEfficiency() {
		return efficiency / 100.0;
	}
	
    /**
     * @since 1.0
     */
    public Set<FuelType> getAlternativeFuels() {
		return alternativeFuels;
	}

    /**
     * @since 1.0
     */
    public FuelType tryUseAlternativeFuel(FuelType option) {
		if (getAlternativeFuels().contains(option)) {
			return option;
		} else {
			return getFuelType();
		}
	}

    /**
     * @since 1.0
     */
    public static EHCSPrimaryHeatingCode lookupByEHSCode(Integer ehsCode) {
        if (ehsCode == null) return null;
		for(EHCSPrimaryHeatingCode code : EHCSPrimaryHeatingCode.values()) {
			if(code.ehsCode == ehsCode) {
				return code;
			}
		}
		return null;
	}
}
