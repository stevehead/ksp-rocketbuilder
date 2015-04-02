package com.stevehead.ksp.rocketbuilder.rocket;

/**
 * Propellants are the fuels used in KSP.
 * 
 * @author Steve Johnson
 */
public enum Propellant {
	LIQUID_FUEL (0.005),
	OXIDIZER (0.005),
	SOLID_FUEL (0.0075),
	MONOPROPELLANT (0.004),
	XENON_GAS (0.0001);
	
	/**
	 * The ratio of liquid fuel to oxidizer required for combustion.
	 */
	public static final double LIQUID_FUEL_TO_OXIDIZER_RATIO = 9.0 / 11.0;
	
	/**
	 * The density of the propellant. Units are currently unknown.
	 */
	private final double density;
	
	/**
	 * @param density		the density
	 */
	Propellant(double density) {
		this.density = density;
	}
	
	/**
	 * @return		the density
	 */
	public double getDensity() {
		return density;
	}
}
