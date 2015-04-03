package com.stevehead.ksp.rocketbuilder.rocket;

/**
 * Payload is anything that contributes mass to the rocket.
 * 
 * @author Steve Johnson
 */
public class Payload extends BaseComponent {
	/**
	 * @param mass		the mass in kg
	 */
	public Payload(double mass) {
		this.mass = mass;
	}
}
