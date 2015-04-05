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
		double expectedDeltaV = TestThruster.KERBIN_GRAVITY * isp * Math.log(mass / dryMass);
		assertEquals(expectedDeltaV, testThruster.getDeltaV(), 1e-7);
	}
	
	@Test
	public void testGetIsp() {
		assertEquals(isp, testThruster.getIsp(), 1e-15);
	}
	
	@Test
	public void testGetThrust() {
		assertEquals(thrust, testThruster.getThrust(), 1e-15);
	}
	
	@Test
	public void testGetMinTWR() {
		double expectedMinTWR = thrust / (mass * TestThruster.KERBIN_GRAVITY);
		assertEquals(expectedMinTWR, testThruster.getMinTWR(), 1e-7);
	}
	
	@Test
	public void testGetMaxTWR() {
		double expectedMinTWR = thrust / (dryMass * TestThruster.KERBIN_GRAVITY);
		assertEquals(expectedMinTWR, testThruster.getMaxTWR(), 1e-7);
	}
	
	@Test
	public void testCalculateThrust() {
		double testThrust = 500000;
		double expectedThrust = thrust + testThrust;
		BaseThruster testThruster2 = new TestThruster(300, 600, testThrust, 260);
		
		assertEquals(expectedThrust, TestThruster.calculateThrust(testThruster, testThruster2), 1e-7);
	}
	
	@Test
	public void testCalculateIspSame() {
		BaseThruster testThruster2 = new TestThruster(300, 600, 500000, isp);
		
		assertEquals(isp, TestThruster.calculateIsp(testThruster, testThruster2), 1e-7);
	}
	
	@Test
	public void testCalculateIspDifferent() {
		double testThrust = 500000;
		double testIsp = isp - 20.5;
		double expectedIsp = (thrust + testThrust) / (thrust / isp + testThrust / testIsp);
		BaseThruster testThruster2 = new TestThruster(300, 600, testThrust, testIsp);
		
		assertEquals(expectedIsp, TestThruster.calculateIsp(testThruster, testThruster2), 1e-7);
	}
	
	private static class TestThruster extends BaseThruster {
		public TestThruster(double dryMass, double mass, double thrust, double isp, Propellant... propellants) {
			super(dryMass, mass, thrust, isp, propellants);
		}
	}
}
