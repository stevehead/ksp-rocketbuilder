package com.stevehead.ksp.rocketbuilder.rocket;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.stevehead.ksp.rocketbuilder.interfaces.Thrustable;

public class EngineClusterTest {
	
	private static EngineCluster testEngineCluster;
	private static final Thrustable testEngine1 = Engine.PreDefined.LV_T30_LIQUID_FUEL_ENGINE;
	private static final Thrustable testEngine2 = Engine.PreDefined.LV_909_LIQUID_FUEL_ENGINE;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testEngineCluster = new EngineCluster(testEngine1, testEngine2);
	}

	@Before
	public void setUp() throws Exception {
		Engine.setIspScaler(1);
	}

	@After
	public void tearDown() throws Exception {
		Engine.setIspScaler(1);
	}

	@Test
	public void testGetMass() {
		double expectedValue = testEngine1.getMass() + testEngine2.getMass();
		assertEquals(expectedValue, testEngineCluster.getMass(), 1e-7);
	}
	
	@Test
	public void testGetDryMass() {
		double expectedValue = testEngine1.getDryMass() + testEngine2.getDryMass();
		assertEquals(expectedValue, testEngineCluster.getDryMass(), 1e-7);
	}
	
	@Test
	public void testGetThrust() {
		double expectedValue = testEngine1.getThrust() + testEngine2.getThrust();
		assertEquals(expectedValue, testEngineCluster.getThrust(), 1e-7);
	}
	
	@Test
	public void testGetIsp() {
		double expectedValue = (testEngine1.getThrust() + testEngine2.getThrust()) / (testEngine1.getThrust() / testEngine1.getIsp() + testEngine2.getThrust() / testEngine2.getIsp());
		assertEquals(expectedValue, testEngineCluster.getIsp(), 1e-7);
	}
	
	@Test
	public void testGetIspAfterScaleChange() {
		Engine.setIspScaler(0.81);
		double expectedValue = (testEngine1.getThrust() + testEngine2.getThrust()) / (testEngine1.getThrust() / testEngine1.getIsp() + testEngine2.getThrust() / testEngine2.getIsp());
		assertEquals(expectedValue, testEngineCluster.getIsp(), 1e-7);
	}

}
