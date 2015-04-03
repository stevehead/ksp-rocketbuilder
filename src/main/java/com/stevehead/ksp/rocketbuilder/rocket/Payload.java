package com.stevehead.ksp.rocketbuilder.rocket;

import com.stevehead.ksp.rocketbuilder.interfaces.Massive;

/**
 * Payload is anything that contributes mass to the rocket.
 * 
 * @author Steve Johnson
 */
public class Payload implements Massive {
	/**
	 * The mass in kg.
	 */
	protected final double mass;
	
	/**
	 * Zero argument constructor sets a mass of zero.
	 */
	public Payload() {
		this(0);
	}
	
	/**
	 * @param mass		the mass in kg
	 */
	public Payload(double mass) {
		this.mass = mass;
	}
	
	@Override
	public final double getMass() {
		return mass;
	}
}
