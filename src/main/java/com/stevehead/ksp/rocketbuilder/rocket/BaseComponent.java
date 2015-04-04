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
	
	@Override
	public final double getMass() {
		return mass;
	}
}
