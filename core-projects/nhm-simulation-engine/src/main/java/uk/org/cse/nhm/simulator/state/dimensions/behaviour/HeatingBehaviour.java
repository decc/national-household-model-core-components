package uk.org.cse.nhm.simulator.state.dimensions.behaviour;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.google.common.base.Optional;

import uk.org.cse.nhm.energycalculator.api.IHeatingSchedule;
import uk.org.cse.nhm.energycalculator.api.impl.DailyHeatingSchedule;
import uk.org.cse.nhm.energycalculator.api.impl.WeeklyHeatingSchedule;
import uk.org.cse.nhm.energycalculator.api.types.EnergyCalculatorType;

@AutoProperty
public class HeatingBehaviour implements IHeatingBehaviour {
	public static final IHeatingBehaviour DEFAULT_BEHAVIOUR =
			new HeatingBehaviour(
				new WeeklyHeatingSchedule(
						DailyHeatingSchedule.fromHours(7, 8, 18, 23),
						DailyHeatingSchedule.fromHours(7, 23)
				),
				19d,
				3d,
				true,
				EnergyCalculatorType.BREDEM2012
			);

	private IHeatingSchedule heatingSchedule;
	private double livingAreaDemandTemperature;
	private double secondAreaDemandTemperatureOrDifference;
	private boolean secondTemperatureIsDifference;
	private EnergyCalculatorType calculatorType;

	public HeatingBehaviour(final IHeatingSchedule heatingSchedule,
			final double livingAreaDemandTemperature,
			final double secondTemperature,
			final boolean secondTemperatureIsDifference,
			final EnergyCalculatorType calculatorType) {
		this.heatingSchedule = heatingSchedule;
		this.livingAreaDemandTemperature = livingAreaDemandTemperature;
		this.secondAreaDemandTemperatureOrDifference = secondTemperature;
		this.secondTemperatureIsDifference = secondTemperatureIsDifference;
		this.calculatorType = calculatorType;
	}

	@Override
	public IHeatingSchedule getHeatingSchedule() {
		return heatingSchedule;
	}

	@Override
	public void setHeatingSchedule(final IHeatingSchedule heatingSchedule) {
		this.heatingSchedule = heatingSchedule;
	}

	@Override
	public double getLivingAreaDemandTemperature() {
		return livingAreaDemandTemperature;
	}

	@Override
	public void setLivingAreaDemandTemperature(final double livingAreaDemandTemperature) {
		this.livingAreaDemandTemperature = livingAreaDemandTemperature;
	}

	@Override
	public Optional<Double> getTemperatureDifference() {
		if (secondTemperatureIsDifference) {
			return Optional.of(secondAreaDemandTemperatureOrDifference);
		} else {
			return Optional.absent();
		}
	}

	@Override
	public void setTemperatureDifference(final double temperatureDifference) {
		secondTemperatureIsDifference = true;
		this.secondAreaDemandTemperatureOrDifference = temperatureDifference;
	}

	@Override
	public Optional<Double> getSecondAreaDemandTemperature() {
		if (secondTemperatureIsDifference) {
			return Optional.absent();
		} else {
			return Optional.of(secondAreaDemandTemperatureOrDifference);
		}
	}

	@Override
	public void setSecondAreaDemandTemperature(final double temperature) {
		secondTemperatureIsDifference = false;
		this.secondAreaDemandTemperatureOrDifference = temperature;
	}

	@Override
	public IHeatingBehaviour withLivingAreaDemandTemperature(final double newLivingAreaTemp) {
		return new HeatingBehaviour(getHeatingSchedule(), newLivingAreaTemp,
				secondAreaDemandTemperatureOrDifference, secondTemperatureIsDifference, calculatorType);
	}

	@Override
	public EnergyCalculatorType getEnergyCalculatorType() {
		return calculatorType;
	}

	@Override
	public void setEnergyCalculatorType(final EnergyCalculatorType calculatorType) {
		this.calculatorType = calculatorType;
	}

	@Override
	public IHeatingBehaviour withEnergyCalculatorType(final EnergyCalculatorType newCalculatorType) {
		return new HeatingBehaviour(
				heatingSchedule,
				livingAreaDemandTemperature,
				secondAreaDemandTemperatureOrDifference,
				secondTemperatureIsDifference,
				newCalculatorType);
	}

	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((calculatorType == null) ? 0 : calculatorType.hashCode());
		long temp;
		result = prime * result + ((heatingSchedule == null) ? 0 : heatingSchedule.hashCode());
		temp = Double.doubleToLongBits(livingAreaDemandTemperature);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(secondAreaDemandTemperatureOrDifference);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (secondTemperatureIsDifference ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final HeatingBehaviour other = (HeatingBehaviour) obj;
		if (calculatorType != other.calculatorType)
			return false;
		if (heatingSchedule == null) {
			if (other.heatingSchedule != null)
				return false;
		} else if (!heatingSchedule.equals(other.heatingSchedule))
			return false;
		if (Double.doubleToLongBits(livingAreaDemandTemperature) != Double
				.doubleToLongBits(other.livingAreaDemandTemperature))
			return false;
		if (Double.doubleToLongBits(secondAreaDemandTemperatureOrDifference) != Double
				.doubleToLongBits(other.secondAreaDemandTemperatureOrDifference))
			return false;
		if (secondTemperatureIsDifference != other.secondTemperatureIsDifference)
			return false;
		return true;
	}

	@Override
	public IHeatingBehaviour copy() {
		return new HeatingBehaviour(heatingSchedule, livingAreaDemandTemperature, secondAreaDemandTemperatureOrDifference, secondTemperatureIsDifference, calculatorType);
	}
}
