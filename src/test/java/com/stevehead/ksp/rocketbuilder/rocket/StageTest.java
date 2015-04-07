package com.stevehead.ksp.rocketbuilder.rocket;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.stevehead.ksp.rocketbuilder.interfaces.Thrustable;

public class StageTest {
	
	private static Stage testStage;
	private static final double payloadMass = 1000;
	private static final double tankDryMass = 2000;
	private static final double tankMass = 10000;
	private static final Thrustable testEngine = Engine.PreDefined.LV_T30_LIQUID_FUEL_ENGINE;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		FuelTank fuelTank = new FuelTank(tankDryMass, tankMass);
		Stack stack = new Stack(fuelTank, testEngine);
		testStage = new Stage(new Payload(payloadMass), stack);
	}

	@Test
	public void testGetMass() {
		double expectedMass = payloadMass + tankMass + testEngine.getMass();
		assertEquals(expectedMass, testStage.getMass(), 1e-7);
	}
	
	@Test
	public void testGetDryMass() {
		double expectedDryMass = payloadMass + tankDryMass + testEngine.getDryMass();
		assertEquals(expectedDryMass, testStage.getDryMass(), 1e-7);
	}
	
	@Test
	public void testGetDeltaV() {
		double expectedMass = payloadMass + tankMass + testEngine.getMass();
		double expectedDryMass = payloadMass + tankDryMass + testEngine.getDryMass();
		double expectedDeltaV = Stage.KERBIN_GRAVITY * testEngine.getIsp() * Math.log(expectedMass / expectedDryMass);
		assertEquals(expectedDeltaV, testStage.getDeltaV(), 1e-7);
	}

}
