package com.stevehead.ksp.rocketbuilder.rocket;

import com.stevehead.ksp.rocketbuilder.interfaces.Massive;

public class Stage extends BaseThruster {
	
	/**
	 * @param payload		the payload
	 * @param stacks		the stacks
	 */
	public Stage(Massive payload, Stack... stacks) {
		super(
				payload.getMass() + calculateDryMass(stacks),
				payload.getMass() + calculateMass(stacks),
				calculateThrust(stacks),
				calculateThrust(stacks),
				combinePropellants(stacks)
				);
	}
	
	/**
	 * @param payload		the payload
	 * @param stack			the stack
	 * @param stackNumber	the number of times the stack is repeated
	 */
	public Stage(Massive payload, Stack stack, int stackNumber) {
		this(payload, stack.duplicate(stackNumber));
	}
}
