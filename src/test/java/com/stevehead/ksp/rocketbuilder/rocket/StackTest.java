package com.stevehead.ksp.rocketbuilder.rocket;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.stevehead.ksp.rocketbuilder.interfaces.Thrustable;

public class StackTest {
	
	private static Stack testStack;
	private static final double fuelTankMass = 1000;
	private static final Thrustable testEngine = Engine.PreDefined.LV_T30_LIQUID_FUEL_ENGINE;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ProceduralTank tank = new ProceduralTank(1000);
		testStack = new Stack(tank, testEngine);
	}
	
	@Test
	public void testGetDryMass() {
		double expectedDryMass = fuelTankMass * ProceduralTank.PROCEDURAL_TANK_LF_OX_MASS_RATIO;
		expectedDryMass += testEngine.getDryMass();
		assertEquals(expectedDryMass, testStack.getDryMass(), 1e-7);
	}
	
	@Test
	public void testGetMass() {
		double expectedDryMass = fuelTankMass + testEngine.getMass();
		assertEquals(expectedDryMass, testStack.getMass(), 1e-7);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidTypeCombination() {
		ProceduralTank monopropellantTank = new ProceduralTank(1000, Propellant.MONOPROPELLANT);
		Stack invalidStack = new Stack(monopropellantTank, testEngine);
		invalidStack.getClass();
	}

}
