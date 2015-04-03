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
	protected double mass;

	@Override
	public final double getMass() {
		return mass;
	}
}
