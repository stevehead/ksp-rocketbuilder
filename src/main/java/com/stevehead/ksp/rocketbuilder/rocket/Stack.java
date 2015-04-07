package com.stevehead.ksp.rocketbuilder.rocket;

import com.stevehead.ksp.rocketbuilder.interfaces.Thrustable;

/**
 * Stack is a fuel tank and engine combo.
 * 
 * @author Steve Johnson
 *
 */
public class Stack extends BaseThruster {
	/**
	 * @param fuelTank		the stack's fuel tank
	 * @param engine		the stack's engine(s)
	 */
	public Stack(FuelTank fuelTank, Thrustable engine) {
		super(fuelTank.getDryMass() + engine.getDryMass(),
				fuelTank.getMass() + engine.getMass(), engine.getThrust(),
				engine.getIsp(), combinePropellants(fuelTank, engine));
		
		if (fuelTank.getType() != engine.getType()) {
			throw new IllegalArgumentException("Fuel tank and engines types must match.");
		}
	}
}
