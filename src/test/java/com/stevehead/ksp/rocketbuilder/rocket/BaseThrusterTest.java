package com.stevehead.ksp.rocketbuilder.rocket;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class BaseThrusterTest {
	
	private static BaseThruster testThruster;
	private static final double dryMass = 10000;
	private static final double mass = 40000;
	private static final double thrust = 2000000;
	private static final double isp = 340;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Engine.setIspScaler(1);
		testThruster = new TestThruster(dryMass, mass, thrust, isp);
	}
	
	@Test
	public void testGetDeltaV() {
		double actualDeltaV = TestThruster.KERBIN_GRAVITY * isp * Math.log(mass / dryMass);
		assertEquals("Delta-V should be ~" + Math.round(actualDeltaV), actualDeltaV, testThruster.getDeltaV(), 1e-7);
	}
	
	@Test
	public void testGetIsp() {
		assertEquals("Specific impulse should be " + isp, isp, testThruster.getIsp(), 1e-15);
	}
	
	@Test
	public void testGetThrust() {
		assertEquals("Thrust should be " + thrust, thrust, testThruster.getThrust(), 1e-15);
	}
	
	@Test
	public void testGetMinTWR() {
		double actualMinTWR = thrust / (mass * TestThruster.KERBIN_GRAVITY);
		assertEquals("Min TWR should be " + actualMinTWR, actualMinTWR, testThruster.getMinTWR(), 1e-7);
	}
	
	@Test
	public void testGetMaxTWR() {
		double actualMinTWR = thrust / (dryMass * TestThruster.KERBIN_GRAVITY);
		assertEquals("Max TWR should be " + actualMinTWR, actualMinTWR, testThruster.getMaxTWR(), 1e-7);
	}
	
	private static class TestThruster extends BaseThruster {
		public TestThruster(double dryMass, double mass, double thrust, double isp, Propellant... propellants) {
			super(dryMass, mass, thrust, isp, propellants);
		}
	}
}
