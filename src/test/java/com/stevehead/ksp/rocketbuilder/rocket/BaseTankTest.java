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
		assertEquals(dryMass, testTank.getDryMass(), 1e-7);
	}
	
	@Test
	public void testGetPropellantsWithEmpty() {
		assertArrayEquals(TestTank.DEFAULT_PROPELLANTS, testTank.getPropellants());
	}
	
	@Test
	public void testGetPropellantsWithoutEmpty() {
		Propellant[] newTankPropellants = new Propellant[]{Propellant.MONOPROPELLANT};
		BaseTank newTestTank = new TestTank(dryMass, mass, newTankPropellants);
		assertArrayEquals(newTankPropellants, newTestTank.getPropellants());
	}

	@Test
	public void testDeterminePropellantsWithEmpty() {
		Propellant[] blankPropellants = new Propellant[0];
		assertArrayEquals(TestTank.DEFAULT_PROPELLANTS, TestTank.determinePropellants(blankPropellants));
	}
	
	@Test
	public void testDeterminePropellantsWithoutEmpty() {
		Propellant[] nonBlankPropellants = new Propellant[]{Propellant.MONOPROPELLANT};
		assertArrayEquals(nonBlankPropellants, TestTank.determinePropellants(nonBlankPropellants));
	}
	
	@Test
	public void testCalculateDryMass() {
		double testDryMass = 5000;
		double expectedDryMass = dryMass + testDryMass;
		BaseTank testTank2 = new TestTank(testDryMass, mass);
		
		assertEquals(expectedDryMass, TestTank.calculateDryMass(testTank, testTank2), 1e-7);
	}
	
	@Test
	public void testCombinePropellants() {
		Propellant[] testPropellants = new Propellant[]{Propellant.XENON_GAS, Propellant.OXIDIZER};
		int expectedArrayLength = 3;
		BaseTank testTank2 = new TestTank(100, 200, testPropellants);
		
		assertEquals(expectedArrayLength, TestTank.combinePropellants(testTank, testTank2).length);
	}
	
	@Test
	public void testDetermineTankType() {
		assertEquals(TestTank.Type.LIQUID_FUEL_AND_OXIDIZER, TestTank.determineTankType(Propellant.LIQUID_FUEL, Propellant.OXIDIZER));
		assertEquals(TestTank.Type.MONOPROPELLANT, TestTank.determineTankType(Propellant.MONOPROPELLANT));
		assertEquals(TestTank.Type.LIQUID_FUEL, TestTank.determineTankType(Propellant.LIQUID_FUEL));
		assertEquals(TestTank.Type.XENON_GAS, TestTank.determineTankType(Propellant.XENON_GAS));
		assertEquals(TestTank.Type.SOLID_FUEL, TestTank.determineTankType(Propellant.SOLID_FUEL));
		assertEquals(TestTank.Type.NONE, TestTank.determineTankType());
		assertEquals(TestTank.Type.UNKNOWN, TestTank.determineTankType(Propellant.LIQUID_FUEL, Propellant.XENON_GAS));
	}
	
	@Test
	public void testNoProvidedPropellantsTank() {
		assertEquals(TestTank.Type.LIQUID_FUEL_AND_OXIDIZER, testTank.getType());
	}
	
	private static class TestTank extends BaseTank {
		public TestTank(double dryMass, double mass, Propellant... propellants) {
			super(dryMass, mass, propellants);
		}
	}
}
