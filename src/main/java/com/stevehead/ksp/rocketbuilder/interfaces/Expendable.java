package com.stevehead.ksp.rocketbuilder.interfaces;

import com.stevehead.ksp.rocketbuilder.rocket.Propellant;

/**
 * Expendable is a type of object that has mass, but can lose mass over time.
 * An example would be a fuel tank.
 * 
 * @author Steve Johnson
 */
public interface Expendable extends Massive {
	/**
	 * Returns the mass after all expendable mass is depleted.
	 * 
	 * @return		the dry mass in kg
	 */
	double getDryMass();
	
	/**
	 * Types of fuel tanks.
	 * 
	 * @author Steve Johnson
	 */
	public enum Type {
		LIQUID_FUEL_AND_OXIDIZER,
		MONOPROPELLANT,
		SOLID_FUEL,
		LIQUID_FUEL,
		XENON_GAS,
		NONE,
		UNKNOWN
	}
	
	/**
	 * The propellants required.
	 * 
	 * @return		propellants
	 */
	Propellant[] getPropellants();
	
	/**
	 * The type of tank/engine.
	 * 
	 * @return		the tank/engine type
	 */
	Type getType();
}
