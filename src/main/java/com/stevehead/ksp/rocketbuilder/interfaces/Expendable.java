package com.stevehead.ksp.rocketbuilder.interfaces;

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
}
