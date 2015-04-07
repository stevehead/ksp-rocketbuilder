package com.stevehead.ksp.rocketbuilder.rocket;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProceduralTankTest {
	
	private static ProceduralTank liquidFuelOxidizerTank;
	private static ProceduralTank monopropellantTank;
	private static final double liquidFuelOxidizerTankMass = 1000;
	private static final double monopropellantTankMass = 345;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		liquidFuelOxidizerTank = new ProceduralTank(liquidFuelOxidizerTankMass);
		monopropellantTank = new ProceduralTank(monopropellantTankMass, Propellant.MONOPROPELLANT);
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLiquidFuelOxidizerTank() {
		double expectedDryMassValue = ProceduralTank.PROCEDURAL_TANK_LF_OX_MASS_RATIO * liquidFuelOxidizerTankMass;
		assertEquals(expectedDryMassValue, liquidFuelOxidizerTank.getDryMass(), 1e-7);
	}
	
	@Test
	public void testMonopropellantTank() {
		double expectedDryMassValue = ProceduralTank.PROCEDURAL_TANK_MP_MASS_RATIO * monopropellantTankMass;
		assertEquals(expectedDryMassValue, monopropellantTank.getDryMass(), 1e-7);
	}

}
