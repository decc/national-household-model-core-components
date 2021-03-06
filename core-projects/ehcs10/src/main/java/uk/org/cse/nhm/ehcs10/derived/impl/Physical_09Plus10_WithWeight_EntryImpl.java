package uk.org.cse.nhm.ehcs10.derived.impl;

import uk.org.cse.nhm.ehcs10.derived.Physical_09Plus10_WithWeight_Entry;
import uk.org.cse.nhm.ehcs10.derived.types.Enum101;
import uk.org.cse.nhm.ehcs10.derived.types.Enum102;
import uk.org.cse.nhm.ehcs10.derived.types.Enum103;
import uk.org.cse.nhm.ehcs10.derived.types.Enum105;
import uk.org.cse.nhm.ehcs10.derived.types.Enum106;
import uk.org.cse.nhm.ehcs10.derived.types.Enum107;
import uk.org.cse.nhm.ehcs10.derived.types.Enum109;
import uk.org.cse.nhm.ehcs10.derived.types.Enum110;
import uk.org.cse.nhm.ehcs10.derived.types.Enum111;
import uk.org.cse.nhm.ehcs10.derived.types.Enum113;
import uk.org.cse.nhm.ehcs10.derived.types.Enum114;
import uk.org.cse.nhm.ehcs10.derived.types.Enum116;
import uk.org.cse.nhm.ehcs10.derived.types.Enum117;
import uk.org.cse.nhm.ehcs10.derived.types.Enum118;
import uk.org.cse.nhm.ehcs10.derived.types.Enum119;
import uk.org.cse.nhm.ehcs10.derived.types.Enum120;
import uk.org.cse.nhm.ehcs10.derived.types.Enum121;
import uk.org.cse.nhm.ehcs10.derived.types.Enum122;
import uk.org.cse.nhm.ehcs10.derived.types.Enum123;
import uk.org.cse.nhm.ehcs10.derived.types.Enum124;
import uk.org.cse.nhm.ehcs10.derived.types.Enum125;
import uk.org.cse.nhm.ehcs10.derived.types.Enum126;
import uk.org.cse.nhm.ehcs10.derived.types.Enum128;
import uk.org.cse.nhm.ehcs10.derived.types.Enum129;
import uk.org.cse.nhm.ehcs10.derived.types.Enum131;
import uk.org.cse.nhm.ehcs10.derived.types.Enum133;
import uk.org.cse.nhm.ehcs10.derived.types.Enum135;
import uk.org.cse.nhm.ehcs10.derived.types.Enum136;
import uk.org.cse.nhm.ehcs10.derived.types.Enum138;
import uk.org.cse.nhm.ehcs10.derived.types.Enum139;
import uk.org.cse.nhm.ehcs10.derived.types.Enum140;
import uk.org.cse.nhm.ehcs10.derived.types.Enum143;
import uk.org.cse.nhm.ehcs10.derived.types.Enum144;
import uk.org.cse.nhm.ehcs10.derived.types.Enum145;
import uk.org.cse.nhm.ehcs10.derived.types.Enum146;
import uk.org.cse.nhm.ehcs10.derived.types.Enum147;
import uk.org.cse.nhm.ehcs10.derived.types.Enum150;
import uk.org.cse.nhm.ehcs10.derived.types.Enum151;
import uk.org.cse.nhm.ehcs10.derived.types.Enum153;
import uk.org.cse.nhm.ehcs10.derived.types.Enum154;
import uk.org.cse.nhm.ehcs10.derived.types.Enum155;
import uk.org.cse.nhm.ehcs10.derived.types.Enum16;
import uk.org.cse.nhm.ehcs10.derived.types.Enum69;
import uk.org.cse.stockimport.spss.SurveyEntryImpl;

public class Physical_09Plus10_WithWeight_EntryImpl extends SurveyEntryImpl implements Physical_09Plus10_WithWeight_Entry {
	private Integer DwellWeight_PairedCases2009_10And2010_11;
	private Integer LoftInsulationThickness;
	private Integer NumberOfFloorsAboveGround;
	private Integer NumberOfLiveabilityProblemsPresent;
	private Double BasicRepairCosts_Actual_;
	private Double ComprehensiveRepairCosts_Actual_;
	private Double BasicRepairCosts_PerSquareMetre_;
	private Double UseableFloorArea_Sqm_;
	private Double UrgentRepairCosts_PerSquareMetre_;
	private Double ComprehensiveRepairCosts_PerSquareMetre_;
	private Double UrgentRepairCosts_Actual_;
	private Double EnvironmentalImpactRating_EhsSAP2009_;
	private Double HhldWeight_PairedCases2009_10And2010_11;
	private Double CostToMakeDecent_Hhsrs15Model_;
	private Double EnergyEfficiency_SAP09_Rating;
	private Enum101 DwellingType;
	private Enum102 AppearanceOfArea;
	private Enum103 PredominantTypeOfWindow;
	private Enum69 PoorQualityEnvironment;
	private Enum105 DwellingAge;
	private Enum106 LoftInsulationThickness_LOFTINS6;
	private Enum107 DecentHomesCriteriaNotMet_Hhsrs15Model_;
	private Enum16 EnergyEfficiencyRatingBand_EhsSAP2009_;
	private Enum109 DecentHomes_NumberOfCriteriaFailed_Hhsrs26Model_;
	private Enum110 PredominantTypeOfRoofCovering;
	private Enum111 ReasonForFailingDecentHomesOnThermalComfort_HhsrsModel__;
	private Enum107 DecentHomesCriteriaNotMet_Hhsrs26Model_;
	private Enum113 SecureWindowsAndDoors;
	private Enum114 UseableFloorArea;
	private Enum69 BasementPresentInDwelling;
	private Enum116 MainHeatingSystem;
	private Enum117 EnergyEfficiency_SAP09_Rating_SAP409;
	private Enum118 MainFuelType;
	private Enum119 PredominantTypeOfWallFinish;
	private Enum120 DecentHomes_HHSRS26Model;
	private Enum121 ParkingProvisionOfSurveyDwelling;
	private Enum122 TypeOfWallAndInsulation;
	private Enum123 DecentHomesRepairCriterion;
	private Enum124 ExtentOfDoubleGlazing;
	private Enum125 DwellingAgeAndType;
	private Enum126 LoftInsulationThickness_LOFTINS4;
	private Enum120 DecentHomes_HHSRS15Model;
	private Enum128 DwellingAge_DWAGE5X;
	private Enum129 DwellingType_DWTYPENX;
	private Enum16 EnvironmentalImpactRatingBand_EhsSAP2009_;
	private Enum131 PredominantTypeOfRoofStucture;
	private Enum123 DecentHomesHhsrs_15Hazards;
	private Enum133 DwellingType_DWTYPE7X;
	private Enum69 AtticPresentInDwelling;
	private Enum135 ExtentOfDoubleGlazing_DBLGLAZ2;
	private Enum136 DwellingType_DWTYPE8X;
	private Enum69 PoorQualityEnvironment_TrafficProblems;
	private Enum138 TypeOfWall;
	private Enum139 DwellingAge_DWAGE6X;
	private Enum140 TypeOfArea;
	private Enum123 DecentHomesThermalComfortCriterion;
	private Enum69 PoorQualityEnvironment_UtilisationProblems;
	private Enum143 MainHeatingSystem_HEAT4X;
	private Enum144 DwellingAge_DWAGE9X;
	private Enum145 AgeOfHeatingSystem;
	private Enum146 PredominantTypeOfWallStucture;
	private Enum147 RequirementToPassDecentHomesThermalComfortCriterion_HhsrsModel_;
	private Enum69 PoorQualityEnvironment_UpkeepProblems;
	private Enum123 DecentHomesModernFacilitiesCriterion;
	private Enum150 NatureOfArea;
	private Enum151 MainHeatingFuel;
	private Enum109 DecentHomes_NumberOfCriteriaFailed_Hhsrs15Model_;
	private Enum153 WaterHeatingSystem;
	private Enum154 TypeOfBoiler;
	private Enum155 DwellingType_HOUSEX;
	private Enum123 DecentHomesHhsrs_26Hazards;
	public Integer getDwellWeight_PairedCases2009_10And2010_11() {
		return DwellWeight_PairedCases2009_10And2010_11;	}

	public void setDwellWeight_PairedCases2009_10And2010_11(final Integer DwellWeight_PairedCases2009_10And2010_11) {
		this.DwellWeight_PairedCases2009_10And2010_11 = DwellWeight_PairedCases2009_10And2010_11;	}

	public Integer getLoftInsulationThickness() {
		return LoftInsulationThickness;	}

	public void setLoftInsulationThickness(final Integer LoftInsulationThickness) {
		this.LoftInsulationThickness = LoftInsulationThickness;	}

	public Integer getNumberOfFloorsAboveGround() {
		return NumberOfFloorsAboveGround;	}

	public void setNumberOfFloorsAboveGround(final Integer NumberOfFloorsAboveGround) {
		this.NumberOfFloorsAboveGround = NumberOfFloorsAboveGround;	}

	public Integer getNumberOfLiveabilityProblemsPresent() {
		return NumberOfLiveabilityProblemsPresent;	}

	public void setNumberOfLiveabilityProblemsPresent(final Integer NumberOfLiveabilityProblemsPresent) {
		this.NumberOfLiveabilityProblemsPresent = NumberOfLiveabilityProblemsPresent;	}

	public Double getBasicRepairCosts_Actual_() {
		return BasicRepairCosts_Actual_;	}

	public void setBasicRepairCosts_Actual_(final Double BasicRepairCosts_Actual_) {
		this.BasicRepairCosts_Actual_ = BasicRepairCosts_Actual_;	}

	public Double getComprehensiveRepairCosts_Actual_() {
		return ComprehensiveRepairCosts_Actual_;	}

	public void setComprehensiveRepairCosts_Actual_(final Double ComprehensiveRepairCosts_Actual_) {
		this.ComprehensiveRepairCosts_Actual_ = ComprehensiveRepairCosts_Actual_;	}

	public Double getBasicRepairCosts_PerSquareMetre_() {
		return BasicRepairCosts_PerSquareMetre_;	}

	public void setBasicRepairCosts_PerSquareMetre_(final Double BasicRepairCosts_PerSquareMetre_) {
		this.BasicRepairCosts_PerSquareMetre_ = BasicRepairCosts_PerSquareMetre_;	}

	public Double getUseableFloorArea_Sqm_() {
		return UseableFloorArea_Sqm_;	}

	public void setUseableFloorArea_Sqm_(final Double UseableFloorArea_Sqm_) {
		this.UseableFloorArea_Sqm_ = UseableFloorArea_Sqm_;	}

	public Double getUrgentRepairCosts_PerSquareMetre_() {
		return UrgentRepairCosts_PerSquareMetre_;	}

	public void setUrgentRepairCosts_PerSquareMetre_(final Double UrgentRepairCosts_PerSquareMetre_) {
		this.UrgentRepairCosts_PerSquareMetre_ = UrgentRepairCosts_PerSquareMetre_;	}

	public Double getComprehensiveRepairCosts_PerSquareMetre_() {
		return ComprehensiveRepairCosts_PerSquareMetre_;	}

	public void setComprehensiveRepairCosts_PerSquareMetre_(final Double ComprehensiveRepairCosts_PerSquareMetre_) {
		this.ComprehensiveRepairCosts_PerSquareMetre_ = ComprehensiveRepairCosts_PerSquareMetre_;	}

	public Double getUrgentRepairCosts_Actual_() {
		return UrgentRepairCosts_Actual_;	}

	public void setUrgentRepairCosts_Actual_(final Double UrgentRepairCosts_Actual_) {
		this.UrgentRepairCosts_Actual_ = UrgentRepairCosts_Actual_;	}

	public Double getEnvironmentalImpactRating_EhsSAP2009_() {
		return EnvironmentalImpactRating_EhsSAP2009_;	}

	public void setEnvironmentalImpactRating_EhsSAP2009_(final Double EnvironmentalImpactRating_EhsSAP2009_) {
		this.EnvironmentalImpactRating_EhsSAP2009_ = EnvironmentalImpactRating_EhsSAP2009_;	}

	public Double getHhldWeight_PairedCases2009_10And2010_11() {
		return HhldWeight_PairedCases2009_10And2010_11;	}

	public void setHhldWeight_PairedCases2009_10And2010_11(final Double HhldWeight_PairedCases2009_10And2010_11) {
		this.HhldWeight_PairedCases2009_10And2010_11 = HhldWeight_PairedCases2009_10And2010_11;	}

	public Double getCostToMakeDecent_Hhsrs15Model_() {
		return CostToMakeDecent_Hhsrs15Model_;	}

	public void setCostToMakeDecent_Hhsrs15Model_(final Double CostToMakeDecent_Hhsrs15Model_) {
		this.CostToMakeDecent_Hhsrs15Model_ = CostToMakeDecent_Hhsrs15Model_;	}

	public Double getEnergyEfficiency_SAP09_Rating() {
		return EnergyEfficiency_SAP09_Rating;	}

	public void setEnergyEfficiency_SAP09_Rating(final Double EnergyEfficiency_SAP09_Rating) {
		this.EnergyEfficiency_SAP09_Rating = EnergyEfficiency_SAP09_Rating;	}

	public Enum101 getDwellingType() {
		return DwellingType;	}

	public void setDwellingType(final Enum101 DwellingType) {
		this.DwellingType = DwellingType;	}

	public Enum102 getAppearanceOfArea() {
		return AppearanceOfArea;	}

	public void setAppearanceOfArea(final Enum102 AppearanceOfArea) {
		this.AppearanceOfArea = AppearanceOfArea;	}

	public Enum103 getPredominantTypeOfWindow() {
		return PredominantTypeOfWindow;	}

	public void setPredominantTypeOfWindow(final Enum103 PredominantTypeOfWindow) {
		this.PredominantTypeOfWindow = PredominantTypeOfWindow;	}

	public Enum69 getPoorQualityEnvironment() {
		return PoorQualityEnvironment;	}

	public void setPoorQualityEnvironment(final Enum69 PoorQualityEnvironment) {
		this.PoorQualityEnvironment = PoorQualityEnvironment;	}

	public Enum105 getDwellingAge() {
		return DwellingAge;	}

	public void setDwellingAge(final Enum105 DwellingAge) {
		this.DwellingAge = DwellingAge;	}

	public Enum106 getLoftInsulationThickness_LOFTINS6() {
		return LoftInsulationThickness_LOFTINS6;	}

	public void setLoftInsulationThickness_LOFTINS6(final Enum106 LoftInsulationThickness_LOFTINS6) {
		this.LoftInsulationThickness_LOFTINS6 = LoftInsulationThickness_LOFTINS6;	}

	public Enum107 getDecentHomesCriteriaNotMet_Hhsrs15Model_() {
		return DecentHomesCriteriaNotMet_Hhsrs15Model_;	}

	public void setDecentHomesCriteriaNotMet_Hhsrs15Model_(final Enum107 DecentHomesCriteriaNotMet_Hhsrs15Model_) {
		this.DecentHomesCriteriaNotMet_Hhsrs15Model_ = DecentHomesCriteriaNotMet_Hhsrs15Model_;	}

	public Enum16 getEnergyEfficiencyRatingBand_EhsSAP2009_() {
		return EnergyEfficiencyRatingBand_EhsSAP2009_;	}

	public void setEnergyEfficiencyRatingBand_EhsSAP2009_(final Enum16 EnergyEfficiencyRatingBand_EhsSAP2009_) {
		this.EnergyEfficiencyRatingBand_EhsSAP2009_ = EnergyEfficiencyRatingBand_EhsSAP2009_;	}

	public Enum109 getDecentHomes_NumberOfCriteriaFailed_Hhsrs26Model_() {
		return DecentHomes_NumberOfCriteriaFailed_Hhsrs26Model_;	}

	public void setDecentHomes_NumberOfCriteriaFailed_Hhsrs26Model_(final Enum109 DecentHomes_NumberOfCriteriaFailed_Hhsrs26Model_) {
		this.DecentHomes_NumberOfCriteriaFailed_Hhsrs26Model_ = DecentHomes_NumberOfCriteriaFailed_Hhsrs26Model_;	}

	public Enum110 getPredominantTypeOfRoofCovering() {
		return PredominantTypeOfRoofCovering;	}

	public void setPredominantTypeOfRoofCovering(final Enum110 PredominantTypeOfRoofCovering) {
		this.PredominantTypeOfRoofCovering = PredominantTypeOfRoofCovering;	}

	public Enum111 getReasonForFailingDecentHomesOnThermalComfort_HhsrsModel__() {
		return ReasonForFailingDecentHomesOnThermalComfort_HhsrsModel__;	}

	public void setReasonForFailingDecentHomesOnThermalComfort_HhsrsModel__(final Enum111 ReasonForFailingDecentHomesOnThermalComfort_HhsrsModel__) {
		this.ReasonForFailingDecentHomesOnThermalComfort_HhsrsModel__ = ReasonForFailingDecentHomesOnThermalComfort_HhsrsModel__;	}

	public Enum107 getDecentHomesCriteriaNotMet_Hhsrs26Model_() {
		return DecentHomesCriteriaNotMet_Hhsrs26Model_;	}

	public void setDecentHomesCriteriaNotMet_Hhsrs26Model_(final Enum107 DecentHomesCriteriaNotMet_Hhsrs26Model_) {
		this.DecentHomesCriteriaNotMet_Hhsrs26Model_ = DecentHomesCriteriaNotMet_Hhsrs26Model_;	}

	public Enum113 getSecureWindowsAndDoors() {
		return SecureWindowsAndDoors;	}

	public void setSecureWindowsAndDoors(final Enum113 SecureWindowsAndDoors) {
		this.SecureWindowsAndDoors = SecureWindowsAndDoors;	}

	public Enum114 getUseableFloorArea() {
		return UseableFloorArea;	}

	public void setUseableFloorArea(final Enum114 UseableFloorArea) {
		this.UseableFloorArea = UseableFloorArea;	}

	public Enum69 getBasementPresentInDwelling() {
		return BasementPresentInDwelling;	}

	public void setBasementPresentInDwelling(final Enum69 BasementPresentInDwelling) {
		this.BasementPresentInDwelling = BasementPresentInDwelling;	}

	public Enum116 getMainHeatingSystem() {
		return MainHeatingSystem;	}

	public void setMainHeatingSystem(final Enum116 MainHeatingSystem) {
		this.MainHeatingSystem = MainHeatingSystem;	}

	public Enum117 getEnergyEfficiency_SAP09_Rating_SAP409() {
		return EnergyEfficiency_SAP09_Rating_SAP409;	}

	public void setEnergyEfficiency_SAP09_Rating_SAP409(final Enum117 EnergyEfficiency_SAP09_Rating_SAP409) {
		this.EnergyEfficiency_SAP09_Rating_SAP409 = EnergyEfficiency_SAP09_Rating_SAP409;	}

	public Enum118 getMainFuelType() {
		return MainFuelType;	}

	public void setMainFuelType(final Enum118 MainFuelType) {
		this.MainFuelType = MainFuelType;	}

	public Enum119 getPredominantTypeOfWallFinish() {
		return PredominantTypeOfWallFinish;	}

	public void setPredominantTypeOfWallFinish(final Enum119 PredominantTypeOfWallFinish) {
		this.PredominantTypeOfWallFinish = PredominantTypeOfWallFinish;	}

	public Enum120 getDecentHomes_HHSRS26Model() {
		return DecentHomes_HHSRS26Model;	}

	public void setDecentHomes_HHSRS26Model(final Enum120 DecentHomes_HHSRS26Model) {
		this.DecentHomes_HHSRS26Model = DecentHomes_HHSRS26Model;	}

	public Enum121 getParkingProvisionOfSurveyDwelling() {
		return ParkingProvisionOfSurveyDwelling;	}

	public void setParkingProvisionOfSurveyDwelling(final Enum121 ParkingProvisionOfSurveyDwelling) {
		this.ParkingProvisionOfSurveyDwelling = ParkingProvisionOfSurveyDwelling;	}

	public Enum122 getTypeOfWallAndInsulation() {
		return TypeOfWallAndInsulation;	}

	public void setTypeOfWallAndInsulation(final Enum122 TypeOfWallAndInsulation) {
		this.TypeOfWallAndInsulation = TypeOfWallAndInsulation;	}

	public Enum123 getDecentHomesRepairCriterion() {
		return DecentHomesRepairCriterion;	}

	public void setDecentHomesRepairCriterion(final Enum123 DecentHomesRepairCriterion) {
		this.DecentHomesRepairCriterion = DecentHomesRepairCriterion;	}

	public Enum124 getExtentOfDoubleGlazing() {
		return ExtentOfDoubleGlazing;	}

	public void setExtentOfDoubleGlazing(final Enum124 ExtentOfDoubleGlazing) {
		this.ExtentOfDoubleGlazing = ExtentOfDoubleGlazing;	}

	public Enum125 getDwellingAgeAndType() {
		return DwellingAgeAndType;	}

	public void setDwellingAgeAndType(final Enum125 DwellingAgeAndType) {
		this.DwellingAgeAndType = DwellingAgeAndType;	}

	public Enum126 getLoftInsulationThickness_LOFTINS4() {
		return LoftInsulationThickness_LOFTINS4;	}

	public void setLoftInsulationThickness_LOFTINS4(final Enum126 LoftInsulationThickness_LOFTINS4) {
		this.LoftInsulationThickness_LOFTINS4 = LoftInsulationThickness_LOFTINS4;	}

	public Enum120 getDecentHomes_HHSRS15Model() {
		return DecentHomes_HHSRS15Model;	}

	public void setDecentHomes_HHSRS15Model(final Enum120 DecentHomes_HHSRS15Model) {
		this.DecentHomes_HHSRS15Model = DecentHomes_HHSRS15Model;	}

	public Enum128 getDwellingAge_DWAGE5X() {
		return DwellingAge_DWAGE5X;	}

	public void setDwellingAge_DWAGE5X(final Enum128 DwellingAge_DWAGE5X) {
		this.DwellingAge_DWAGE5X = DwellingAge_DWAGE5X;	}

	public Enum129 getDwellingType_DWTYPENX() {
		return DwellingType_DWTYPENX;	}

	public void setDwellingType_DWTYPENX(final Enum129 DwellingType_DWTYPENX) {
		this.DwellingType_DWTYPENX = DwellingType_DWTYPENX;	}

	public Enum16 getEnvironmentalImpactRatingBand_EhsSAP2009_() {
		return EnvironmentalImpactRatingBand_EhsSAP2009_;	}

	public void setEnvironmentalImpactRatingBand_EhsSAP2009_(final Enum16 EnvironmentalImpactRatingBand_EhsSAP2009_) {
		this.EnvironmentalImpactRatingBand_EhsSAP2009_ = EnvironmentalImpactRatingBand_EhsSAP2009_;	}

	public Enum131 getPredominantTypeOfRoofStucture() {
		return PredominantTypeOfRoofStucture;	}

	public void setPredominantTypeOfRoofStucture(final Enum131 PredominantTypeOfRoofStucture) {
		this.PredominantTypeOfRoofStucture = PredominantTypeOfRoofStucture;	}

	public Enum123 getDecentHomesHhsrs_15Hazards() {
		return DecentHomesHhsrs_15Hazards;	}

	public void setDecentHomesHhsrs_15Hazards(final Enum123 DecentHomesHhsrs_15Hazards) {
		this.DecentHomesHhsrs_15Hazards = DecentHomesHhsrs_15Hazards;	}

	public Enum133 getDwellingType_DWTYPE7X() {
		return DwellingType_DWTYPE7X;	}

	public void setDwellingType_DWTYPE7X(final Enum133 DwellingType_DWTYPE7X) {
		this.DwellingType_DWTYPE7X = DwellingType_DWTYPE7X;	}

	public Enum69 getAtticPresentInDwelling() {
		return AtticPresentInDwelling;	}

	public void setAtticPresentInDwelling(final Enum69 AtticPresentInDwelling) {
		this.AtticPresentInDwelling = AtticPresentInDwelling;	}

	public Enum135 getExtentOfDoubleGlazing_DBLGLAZ2() {
		return ExtentOfDoubleGlazing_DBLGLAZ2;	}

	public void setExtentOfDoubleGlazing_DBLGLAZ2(final Enum135 ExtentOfDoubleGlazing_DBLGLAZ2) {
		this.ExtentOfDoubleGlazing_DBLGLAZ2 = ExtentOfDoubleGlazing_DBLGLAZ2;	}

	public Enum136 getDwellingType_DWTYPE8X() {
		return DwellingType_DWTYPE8X;	}

	public void setDwellingType_DWTYPE8X(final Enum136 DwellingType_DWTYPE8X) {
		this.DwellingType_DWTYPE8X = DwellingType_DWTYPE8X;	}

	public Enum69 getPoorQualityEnvironment_TrafficProblems() {
		return PoorQualityEnvironment_TrafficProblems;	}

	public void setPoorQualityEnvironment_TrafficProblems(final Enum69 PoorQualityEnvironment_TrafficProblems) {
		this.PoorQualityEnvironment_TrafficProblems = PoorQualityEnvironment_TrafficProblems;	}

	public Enum138 getTypeOfWall() {
		return TypeOfWall;	}

	public void setTypeOfWall(final Enum138 TypeOfWall) {
		this.TypeOfWall = TypeOfWall;	}

	public Enum139 getDwellingAge_DWAGE6X() {
		return DwellingAge_DWAGE6X;	}

	public void setDwellingAge_DWAGE6X(final Enum139 DwellingAge_DWAGE6X) {
		this.DwellingAge_DWAGE6X = DwellingAge_DWAGE6X;	}

	public Enum140 getTypeOfArea() {
		return TypeOfArea;	}

	public void setTypeOfArea(final Enum140 TypeOfArea) {
		this.TypeOfArea = TypeOfArea;	}

	public Enum123 getDecentHomesThermalComfortCriterion() {
		return DecentHomesThermalComfortCriterion;	}

	public void setDecentHomesThermalComfortCriterion(final Enum123 DecentHomesThermalComfortCriterion) {
		this.DecentHomesThermalComfortCriterion = DecentHomesThermalComfortCriterion;	}

	public Enum69 getPoorQualityEnvironment_UtilisationProblems() {
		return PoorQualityEnvironment_UtilisationProblems;	}

	public void setPoorQualityEnvironment_UtilisationProblems(final Enum69 PoorQualityEnvironment_UtilisationProblems) {
		this.PoorQualityEnvironment_UtilisationProblems = PoorQualityEnvironment_UtilisationProblems;	}

	public Enum143 getMainHeatingSystem_HEAT4X() {
		return MainHeatingSystem_HEAT4X;	}

	public void setMainHeatingSystem_HEAT4X(final Enum143 MainHeatingSystem_HEAT4X) {
		this.MainHeatingSystem_HEAT4X = MainHeatingSystem_HEAT4X;	}

	public Enum144 getDwellingAge_DWAGE9X() {
		return DwellingAge_DWAGE9X;	}

	public void setDwellingAge_DWAGE9X(final Enum144 DwellingAge_DWAGE9X) {
		this.DwellingAge_DWAGE9X = DwellingAge_DWAGE9X;	}

	public Enum145 getAgeOfHeatingSystem() {
		return AgeOfHeatingSystem;	}

	public void setAgeOfHeatingSystem(final Enum145 AgeOfHeatingSystem) {
		this.AgeOfHeatingSystem = AgeOfHeatingSystem;	}

	public Enum146 getPredominantTypeOfWallStucture() {
		return PredominantTypeOfWallStucture;	}

	public void setPredominantTypeOfWallStucture(final Enum146 PredominantTypeOfWallStucture) {
		this.PredominantTypeOfWallStucture = PredominantTypeOfWallStucture;	}

	public Enum147 getRequirementToPassDecentHomesThermalComfortCriterion_HhsrsModel_() {
		return RequirementToPassDecentHomesThermalComfortCriterion_HhsrsModel_;	}

	public void setRequirementToPassDecentHomesThermalComfortCriterion_HhsrsModel_(final Enum147 RequirementToPassDecentHomesThermalComfortCriterion_HhsrsModel_) {
		this.RequirementToPassDecentHomesThermalComfortCriterion_HhsrsModel_ = RequirementToPassDecentHomesThermalComfortCriterion_HhsrsModel_;	}

	public Enum69 getPoorQualityEnvironment_UpkeepProblems() {
		return PoorQualityEnvironment_UpkeepProblems;	}

	public void setPoorQualityEnvironment_UpkeepProblems(final Enum69 PoorQualityEnvironment_UpkeepProblems) {
		this.PoorQualityEnvironment_UpkeepProblems = PoorQualityEnvironment_UpkeepProblems;	}

	public Enum123 getDecentHomesModernFacilitiesCriterion() {
		return DecentHomesModernFacilitiesCriterion;	}

	public void setDecentHomesModernFacilitiesCriterion(final Enum123 DecentHomesModernFacilitiesCriterion) {
		this.DecentHomesModernFacilitiesCriterion = DecentHomesModernFacilitiesCriterion;	}

	public Enum150 getNatureOfArea() {
		return NatureOfArea;	}

	public void setNatureOfArea(final Enum150 NatureOfArea) {
		this.NatureOfArea = NatureOfArea;	}

	public Enum151 getMainHeatingFuel() {
		return MainHeatingFuel;	}

	public void setMainHeatingFuel(final Enum151 MainHeatingFuel) {
		this.MainHeatingFuel = MainHeatingFuel;	}

	public Enum109 getDecentHomes_NumberOfCriteriaFailed_Hhsrs15Model_() {
		return DecentHomes_NumberOfCriteriaFailed_Hhsrs15Model_;	}

	public void setDecentHomes_NumberOfCriteriaFailed_Hhsrs15Model_(final Enum109 DecentHomes_NumberOfCriteriaFailed_Hhsrs15Model_) {
		this.DecentHomes_NumberOfCriteriaFailed_Hhsrs15Model_ = DecentHomes_NumberOfCriteriaFailed_Hhsrs15Model_;	}

	public Enum153 getWaterHeatingSystem() {
		return WaterHeatingSystem;	}

	public void setWaterHeatingSystem(final Enum153 WaterHeatingSystem) {
		this.WaterHeatingSystem = WaterHeatingSystem;	}

	public Enum154 getTypeOfBoiler() {
		return TypeOfBoiler;	}

	public void setTypeOfBoiler(final Enum154 TypeOfBoiler) {
		this.TypeOfBoiler = TypeOfBoiler;	}

	public Enum155 getDwellingType_HOUSEX() {
		return DwellingType_HOUSEX;	}

	public void setDwellingType_HOUSEX(final Enum155 DwellingType_HOUSEX) {
		this.DwellingType_HOUSEX = DwellingType_HOUSEX;	}

	public Enum123 getDecentHomesHhsrs_26Hazards() {
		return DecentHomesHhsrs_26Hazards;	}

	public void setDecentHomesHhsrs_26Hazards(final Enum123 DecentHomesHhsrs_26Hazards) {
		this.DecentHomesHhsrs_26Hazards = DecentHomesHhsrs_26Hazards;	}

}

