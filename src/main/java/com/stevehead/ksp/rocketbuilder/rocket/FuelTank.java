package com.stevehead.ksp.rocketbuilder.rocket;

public class FuelTank extends BaseTank {
	/**
	 * @param dryMass		dry mass in kg
	 * @param totalMass		total mass in kg
	 * @param propellants	propellants used
	 */
	protected FuelTank(double dryMass, double totalMass, Propellant... propellants) {
		super(dryMass, totalMass, propellants);
	}
}
