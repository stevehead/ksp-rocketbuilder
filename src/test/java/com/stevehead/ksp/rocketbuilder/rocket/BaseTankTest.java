package com.stevehead.ksp.rocketbuilder.rocket;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class BaseTankTest {
	
	private static BaseTank testTank;
	private static final double dryMass = 10000;
	private static final double mass = 40000;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testTank = new TestTank(dryMass, mass);
	}
	
	@Test
	public void testGetDryMass() {
		assertEquals("Dry mass should be " + dryMass, dryMass, testTank.getDryMass(), 1e-7);
	}
	
	@Test
	public void testGetPropellantsWithEmpty() {
		assertArrayEquals("Tank should have default propellants", TestTank.DEFAULT_PROPELLANTS, testTank.getPropellants());
	}
	
	@Test
	public void testGetPropellantsWithoutEmpty() {
		Propellant[] newTankPropellants = new Propellant[]{Propellant.MONOPROPELLANT};
		BaseTank newTestTank = new TestTank(dryMass, mass, newTankPropellants);
		assertArrayEquals("Tank should have monopropellant", newTankPropellants, newTestTank.getPropellants());
	}

	@Test
	public void testDeterminePropellantsWithEmpty() {
		Propellant[] blankPropellants = new Propellant[0];
		assertArrayEquals("No propellants should be converted to the default propellants.",
				TestTank.DEFAULT_PROPELLANTS, TestTank.determinePropellants(blankPropellants));
	}
	
	@Test
	public void testDeterminePropellantsWithoutEmpty() {
		Propellant[] nonBlankPropellants = new Propellant[]{Propellant.MONOPROPELLANT};
		assertArrayEquals("Default propellants should not be given if array is not empty.",
				nonBlankPropellants, TestTank.determinePropellants(nonBlankPropellants));
	}
	
	@Test
	public void testCalculateDryMass() {
		double testDryMass = 5000;
		double expectedValue = dryMass + testDryMass;
		BaseTank testTank2 = new TestTank(testDryMass, mass);
		
		assertEquals("Combined dry mass should be " + expectedValue, expectedValue,
				TestTank.calculateDryMass(testTank, testTank2), 1e-7);
	}
	
	@Test
	public void testCombinePropellants() {
		Propellant[] testPropellants = new Propellant[]{Propellant.XENON_GAS, Propellant.OXIDIZER};
		int expectedValue = 3;
		BaseTank testTank2 = new TestTank(100, 200, testPropellants);
		
		assertEquals("The combined propellants should have " + expectedValue,
				expectedValue, TestTank.combinePropellants(testTank, testTank2).length);
	}
	
	private static class TestTank extends BaseTank {
		public TestTank(double dryMass, double mass, Propellant... propellants) {
			super(dryMass, mass, propellants);
		}
	}
}
