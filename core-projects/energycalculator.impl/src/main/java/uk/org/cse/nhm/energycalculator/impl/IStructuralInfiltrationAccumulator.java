package uk.org.cse.nhm.energycalculator.impl;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;

public interface IStructuralInfiltrationAccumulator {

	public abstract double getAirChangeRate(IEnergyCalculatorHouseCase house, IEnergyCalculatorParameters parameters);

	/**
	 * In this implementation, to match the SAP worksheet, the wall infiltration is taken to the maximum
	 * infiltration from amongst the maximally sized walls.
	 */
	public abstract void addWallInfiltration(double wallArea, double airChangeRate);

	/**
	 * Ventilation infiltration rates are just added up
	 */
	public abstract void addVentInfiltration(int vents);
	
	/**
	 * Infiltration caused by a single open flue.
	 */
	public abstract void addFlueInfiltration();
	
	/**
	 * Infiltration caused by a single chimney;
	 */
	public abstract void addChimneyInfiltration();

	/**
	 * Floor infiltration rates are just added up
	 */
	public abstract void addFloorInfiltration(double airChangeRate);

	/**
	 * Fan infiltration rates are summed as well
	 */
	public abstract void addFanInfiltration(int fans);

}