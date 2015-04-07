package com.stevehead.ksp.rocketbuilder.rocket;

public class ProceduralTank extends FuelTank {
	/**
	 * The dry-to-wet mass ratio of a LX/OX procedural tank.
	 */
	public static final double PROCEDURAL_TANK_LF_OX_MASS_RATIO = 1.0 / 9.0;
	
	/**
	 * The dry-to-wet mass ratio of a monopropellant procedural tank.
	 */
	public static final double PROCEDURAL_TANK_MP_MASS_RATIO = 1.0 / 5.0;
	
	/**
	 * @param totalMass		total mass in kg
	 * @param propellants	propellants used
	 */
	public ProceduralTank(double totalMass, Propellant... propellants) {
		super(determineDryMass(totalMass, propellants), totalMass, propellants);
	}
	
	/**
	 * Calculates the dry mass based on the tank type.
	 * 
	 * @param totalMass		total mass in kg
	 * @param type			type of tank
	 * @return				dry mass in kg
	 */
	private static double determineDryMass(double totalMass, Propellant... propellants) {
		Type type = determineTankType(determinePropellants(propellants));
		switch (type) {
		case LIQUID_FUEL_AND_OXIDIZER:
			return PROCEDURAL_TANK_LF_OX_MASS_RATIO * totalMass;
		case MONOPROPELLANT:
			return PROCEDURAL_TANK_MP_MASS_RATIO * totalMass;
		default:
			throw new IllegalArgumentException("The provided tank type is currently not supported: " + type + ".");
		}
	}
}
