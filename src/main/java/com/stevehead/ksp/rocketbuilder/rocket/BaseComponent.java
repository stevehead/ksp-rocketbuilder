package com.stevehead.ksp.rocketbuilder.rocket;

import com.stevehead.ksp.rocketbuilder.interfaces.Massive;

/**
 * Any object that has mass.
 * 
 * @author Steve Johnson
 */
public abstract class BaseComponent implements Massive {
	/**
	 * The mass in kg.
	 */
	protected final double mass;
	
	/**
	 * @param mass		the mass in kg
	 */
	protected BaseComponent(double mass) {
		this.mass = mass;
	}
	
	/**
	 * Returns the combined mass of the objects.
	 * 
	 * @param expendables		the objects to combine their mass
	 * @return					the combined mass in kg
	 */
	protected static double calculateMass(Massive... massables) {
		double mass = 0;
		for (Massive massiveObject : massables) {
			mass += massiveObject.getMass();
		}
		return mass;
	}
	
	@Override
	public final double getMass() {
		return mass;
	}
}
