package com.stevehead.ksp.rocketbuilder.rocket;

import java.util.Arrays;

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
		super(
				calculateDryMass(fuelTank, engine),
				calculateMass(fuelTank, engine),
				engine.getThrust(),
				engine.getIsp(),
				combinePropellants(fuelTank, engine)
				);
		
		if (fuelTank.getType() != engine.getType()) {
			throw new IllegalArgumentException("Fuel tank and engines types must match.");
		}
	}
	
	/**
	 * Duplicates the stack into an array
	 * 
	 * @param stackNumber		the number of stacks
	 * @return					the array of stacks
	 */
	public Stack[] duplicate(int stackNumber) {
		Stack[] stacks = new Stack[stackNumber];
		Arrays.fill(stacks, this);
		return stacks;
	}
}
