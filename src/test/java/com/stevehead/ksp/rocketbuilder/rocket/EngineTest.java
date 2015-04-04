package com.stevehead.ksp.rocketbuilder.rocket;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class EngineTest {
	
	private static Engine testEngine;
	private static final String name = "Test Engine";
	private static final Engine.Mod mod = Engine.Mod.CUSTOM;
	private static final double dryMass = 10000;
	private static final double mass = 40000;
	private static final double thrust = 2000000;
	private static final double isp = 340;
	private static final double size = 2.5;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testEngine = new Engine(name, mod, dryMass, mass, thrust, isp, size);
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
	public void testGetName() {
		assertEquals("Engine name should be " + name, name, testEngine.getName());
	}
	
	@Test
	public void testMod() {
		assertEquals("Engine mod should be " + mod, mod, testEngine.getMod());
	}
	
	@Test
	public void testGetIspScaler() {
		assertEquals("Initial ISP Scaler should be 1", 1, Engine.getIspScaler(), 1e-7);
	}

	@Test
	public void testSetIspScaler() {
		double newIspScaler = 0.81;
		Engine.setIspScaler(newIspScaler);
		assertEquals("New ISP Scaler should be " + newIspScaler, newIspScaler, Engine.getIspScaler(), 1e-7);
	}

	@Test
	public void testGetDeltaV() {
		double actualDeltaV = Engine.KERBIN_GRAVITY * isp * Math.log(mass / dryMass);
		assertEquals("Test engine dV should be " + actualDeltaV, actualDeltaV, testEngine.getDeltaV(), 1e-7);
	}
	
	@Test
	public void testGetDeltaVAfterScale() {
		double newIspScaler = 0.81;
		Engine.setIspScaler(newIspScaler);
		double actualDeltaV = Engine.KERBIN_GRAVITY * isp * newIspScaler * Math.log(mass / dryMass);
		assertEquals("Test engine dV should be " + actualDeltaV, actualDeltaV, testEngine.getDeltaV(), 1e-7);
	}
}
