package com.stevehead.ksp.rocketbuilder.parts;

import com.stevehead.ksp.rocketbuilder.interfaces.Massable;

/**
 * Payload is a static mass object that is placed on top of a rocket.
 * 
 * @author Steve Johnson
 */
public class Payload implements Massable {
	/**
	 * The mass of the payload in kg.
	 */
	private final double mass;
	
	/**
	 * @param mass 		mass in kg
	 */
	public Payload(double mass) {
		this.mass = mass;
	}
	
	public double getMass() {
		return mass;
	}
}
