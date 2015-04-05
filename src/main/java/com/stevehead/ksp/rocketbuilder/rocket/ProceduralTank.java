package com.stevehead.ksp.rocketbuilder.rocket;

public class ProceduralTank extends FuelTank {
	/**
	 * The dry-to-wet mass ratio of a LX/OX procedural tank.
	 */
	public static final double LF_OX_MASS_RATIO = 1.0 / 9.0;
	
	/**
	 * The dry-to-wet mass ratio of a monopropellant procedural tank.
	 */
	public static final double MP_MASS_RATIO = 1.0 / 5.0;
	
	/**
	 * @param totalMass		total mass in kg
	 * @param type			type of tank
	 * @param propellants	propellants used
	 */
	private ProceduralTank(double totalMass, Type type, Propellant... propellants) {
		super(determineDryMass(totalMass, type), totalMass, type, propellants);
	}
	
	/**
	 * @param totalMass		total mass in kg
	 * @param propellants	propellants used
	 */
	public ProceduralTank(double totalMass, Propellant... propellants) {
		this(totalMass, Type.AUTO, propellants);
	}
	
	/**
	 * @param totalMass		total mass in kg
	 */
	public ProceduralTank(double totalMass) {
		this(totalMass, Type.AUTO, DEFAULT_PROPELLANTS);
	}
	
	/**
	 * Calculates the dry mass based on the tank type.
	 * 
	 * @param totalMass		total mass in kg
	 * @param type			type of tank
	 * @return				dry mass in kg
	 */
	private static double determineDryMass(double totalMass, Type type) {
		switch (type) {
		case LIQUID_FUEL_AND_OXIDIZER:
			return LF_OX_MASS_RATIO * totalMass;
		case MONOPROPELLANT:
			return MP_MASS_RATIO * totalMass;
		default:
			throw new IllegalArgumentException("The provided tank type is currently not supported" + type + ".");
		}
	}
}
