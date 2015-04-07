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
		double expectedDryMass = fuelTankMass * ProceduralTank.PROCEDURAL_TANK_LF_OX_MASS_RATIO + testEngine.getDryMass();
		assertEquals(expectedDryMass, testStack.getDryMass(), 1e-7);
	}
	
	@Test
	public void testGetMass() {
		double expectedMass = fuelTankMass + testEngine.getMass();
		assertEquals(expectedMass, testStack.getMass(), 1e-7);
	}
	
	@Test
	public void testGetDeltaV() {
		double expectedDryMass = fuelTankMass * ProceduralTank.PROCEDURAL_TANK_LF_OX_MASS_RATIO + testEngine.getDryMass();
		double expectedMass = fuelTankMass + testEngine.getMass();
		double expectedDeltaV = Stack.KERBIN_GRAVITY * testEngine.getIsp() * Math.log(expectedMass / expectedDryMass);
		assertEquals(expectedDeltaV, testStack.getDeltaV(), 1e-7);
	}
	
	@Test
	public void testDuplicate() {
		int testStackNumber = 3;
		Stack[] testStackArray = testStack.duplicate(testStackNumber);
		assertEquals(testStackNumber, testStackArray.length);
		for (Stack stack : testStackArray) {
			assertSame(testStack, stack);
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidTypeCombination() {
		ProceduralTank monopropellantTank = new ProceduralTank(1000, Propellant.MONOPROPELLANT);
		Stack invalidStack = new Stack(monopropellantTank, testEngine);
		invalidStack.getClass();
	}

}
