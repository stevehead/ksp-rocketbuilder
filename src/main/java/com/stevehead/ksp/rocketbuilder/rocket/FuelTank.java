package com.stevehead.ksp.rocketbuilder.rocket;

public class FuelTank extends BaseTank {
	/**
	 * @param dryMass		dry mass in kg
	 * @param totalMass		total mass in kg
	 * @param type			type of tank (can be auto determined)
	 * @param propellants	propellants used
	 */
	protected FuelTank(double dryMass, double totalMass, Type type, Propellant... propellants) {
		super(dryMass, totalMass, type, propellants);
	}
	
	/**
	 * @param dryMass		dry mass in kg
	 * @param totalMass		total mass in kg
	 * @param propellants	propellants used
	 */
	public FuelTank(double dryMass, double totalMass, Propellant... propellants) {
		this(dryMass, totalMass, Type.AUTO, propellants);
	}
	
	/**
	 * @param dryMass		dry mass in kg
	 * @param totalMass		total mass in kg
	 */
	public FuelTank(double dryMass, double totalMass) {
		this(dryMass, totalMass, Type.AUTO);
	}
}
