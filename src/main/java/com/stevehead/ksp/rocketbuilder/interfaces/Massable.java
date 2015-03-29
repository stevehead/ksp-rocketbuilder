package com.stevehead.ksp.rocketbuilder.interfaces;

/**
 * Massable is an interface that requires the object to have a retrievable mass.
 * Basically, this can represent any type of physical object in the universe.
 * To note, all masses should be in kilograms, as it is the SI standard unit.
 * 
 * @author Steve Johnson
 */
public interface Massable {
	
	/**
	 * Returns the mass of the object.
	 * 
	 * @return		the mass in kg
	 */
	double getMass();
}
