package uk.org.cse.stockimport.hom.impl.steps;

import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.api.IEnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.IHeatingSystem;
import uk.org.cse.nhm.energycalculator.api.IVentilationSystem;
import uk.org.cse.nhm.energycalculator.api.ThermalMassLevel;
import uk.org.cse.nhm.energycalculator.api.types.AreaType;
import uk.org.cse.nhm.energycalculator.api.types.FloorConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.FloorType;
import uk.org.cse.nhm.energycalculator.api.types.FrameType;
import uk.org.cse.nhm.energycalculator.api.types.GlazingType;
import uk.org.cse.nhm.energycalculator.api.types.OvershadingType;
import uk.org.cse.nhm.energycalculator.api.types.RoofConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.RoofType;
import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.WindowInsulationType;
import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.hom.ISurveyCaseBuildStep;
import uk.org.cse.stockimport.hom.impl.steps.imputation.DoorWranglingStep;
import uk.org.cse.stockimport.hom.impl.steps.imputation.MainImputationStep;
import uk.org.cse.stockimport.repository.IHouseCaseSources;

/**
 * @since 1.0
 */
public class InternalWallBuildStep implements ISurveyCaseBuildStep {
	final String IDENTIFIER = InternalWallBuildStep.class.getCanonicalName();

	/**
	 * A visitor which collects up all the external areas on the structure model for us.
	 * @author hinton
	 *
	 */
	private static class AreaVisitor  implements IEnergyCalculatorVisitor {
		private double externalArea = 0;

		@Override
		public void visitHeatingSystem(final IHeatingSystem system, final double proportion) {
			// Noop
		}

		@Override
		public double heatSystemProportion(final IHeatingSystem system) {
			// Noop
			return 0.0;
		}

		@Override
		public void visitEnergyTransducer(final IEnergyTransducer transducer) {
			// Noop
		}

		@Override
		public void visitVentilationSystem(final IVentilationSystem ventilation) {
			// Noop
		}

		@Override
		public void addWallInfiltration(final double wallArea, final WallConstructionType wallConstructionType, final double airChangeRate) {
			// Noop
		}

		@Override
		public void addFanInfiltration(final int fans) {
			// Noop
		}

		@Override
		public void addGroundFloorInfiltration(final FloorConstructionType floorType) {
			// Noop
		}

		public double getExternalArea() {
			return externalArea;
		}

		@Override
		public void addVentInfiltration(final int vents) {
			// Noop
		}

		@Override
		public void addFlueInfiltration() {
			// Noop
		}

		@Override
		public void addChimneyInfiltration() {
			// Noop
        }

		@Override
		public void visitTransparentElement(final GlazingType glazingType, final WindowInsulationType insulationType,
				final double visibleLightTransmittivity, final double solarGainTransmissivity, final double area, final FrameType frameType,
				final double frameFactor, final double horizontalOrientation, final double verticalOrientation,
				final OvershadingType overshading) {
			// Noop

		}

		@Override
		public void visitWall(final WallConstructionType constructionType,
				final double externalOrExternalInsulationThickness, final boolean hasCavityInsulation, final double area, final double uValue, final double thickness,
				final Optional<ThermalMassLevel> thermalMassLevel) {
			if (constructionType.getWallType().isExternal() || constructionType.getWallType().getAreaType() == AreaType.PartyWall) {
				externalArea += area;
			}
		}

		@Override
		public void visitDoor(final double area, final double uValue) {
			externalArea += area;
		}

		@Override
		public void setRoofType(final RoofConstructionType constructionType, final double insulationThickness) {
			// Noop
		}

		@Override
		public void visitCeiling(final RoofType type, final double area, final double uValue) {
			// Noop
		}

		@Override
		public void visitWindow(final double area, final double uValue, final FrameType frameType, final GlazingType glazingType,
				final WindowInsulationType insulationType) {
			externalArea += area;
		}

		@Override
		public void setFloorType(final FloorConstructionType groundFloorConstructionType, final double insulationThickness) {
			// Noop
		}

		@Override
		public void visitFloor(final FloorType type, final boolean isGroundFloor, final double area, final double uValue, final double exposedPerimeter, final double wallThickness) {
			// Noop
		}
	}

	@Override
	public String getIdentifier() {
		return IDENTIFIER;
	}

	@Override
	public Set<String> getDependencies() {
		return ImmutableSet.of(StoreyBuildStep.IDENTIFIER, ElevationBuildStep.IDENTIFIER, MainImputationStep.IDENTIFIER, DoorWranglingStep.IDENTIFIER);
	}

	@Override
    /**
     * @assumption Based on CHM: internal wall area is the same as facade area.
     */
	public void build(final SurveyCase model, final IHouseCaseSources<IBasicDTO> dtoProvider) {
		final AreaVisitor av = new AreaVisitor();
		model.getStructure().accept(av);
		model.getStructure().setInternalWallArea(av.getExternalArea());
	}
}
