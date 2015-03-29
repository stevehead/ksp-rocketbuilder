package com.stevehead.ksp.rocketbuilder.interfaces;

/**
 * Expendable is a type of object that has mass, but can lose mass over time.
 * An example would be a fuel tank.
 * 
 * @author Steve Johnson
 */
public interface Expendable extends Massable {
	
	/**
	 * Returns the mass of the object after all expendable mass is depleted.
	 * 
	 * @return		the dry mass in kg
	 */
	double getDryMass();
	
	/**
	 * Returns the maximum mass the object can have.
	 * 
	 * @return		the total mass in kg
	 */
	double getTotalMass();
}
