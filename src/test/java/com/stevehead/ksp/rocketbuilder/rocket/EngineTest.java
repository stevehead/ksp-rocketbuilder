package com.stevehead.ksp.rocketbuilder.rocket;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.stevehead.ksp.rocketbuilder.game.Mod;

public class EngineTest {
	
	private static Engine testEngine;
	private static Engine testTweakScaleEngine;
	private static final String name = "Test Engine";
	private static final Mod mod = Mod.CUSTOM;
	private static final double dryMass = 10000;
	private static final double mass = 40000;
	private static final double thrust = 2000000;
	private static final double isp = 340;
	private static final double size = 2.5;
	private static final double tweakScaleSize = 1.25;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testEngine = new Engine(name, mod, dryMass, mass, thrust, isp, size);
		testTweakScaleEngine = testEngine.tweakScale(tweakScaleSize);
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
		assertEquals(name, testEngine.getName());
	}
	
	@Test
	public void testGetMod() {
		assertEquals(mod, testEngine.getMod());
	}
	
	@Test
	public void testGetIspScaler() {
		assertEquals(1, Engine.getIspScaler(), 1e-7);
	}

	@Test
	public void testSetIspScaler() {
		double newIspScaler = 0.81;
		Engine.setIspScaler(newIspScaler);
		assertEquals(newIspScaler, Engine.getIspScaler(), 1e-7);
	}

	@Test
	public void testGetDeltaV() {
		double expectedDeltaV = Engine.KERBIN_GRAVITY * isp * Math.log(mass / dryMass);
		assertEquals(expectedDeltaV, testEngine.getDeltaV(), 1e-7);
	}
	
	@Test
	public void testGetDeltaVAfterScale() {
		double newIspScaler = 0.81;
		Engine.setIspScaler(newIspScaler);
		double expectedDeltaV = Engine.KERBIN_GRAVITY * isp * newIspScaler * Math.log(mass / dryMass);
		assertEquals(expectedDeltaV, testEngine.getDeltaV(), 1e-7);
	}
	
	@Test
	public void testTweakScaleDifferentScale() {
		assertNotSame(testEngine, testTweakScaleEngine);
		assertNotEquals(testEngine.getSize(), testTweakScaleEngine.getSize(), 1e-7);
	}
	
	@Test
	public void testTweakScaleSameScale() {
		Engine testSameScale = testEngine.tweakScale(size);
		assertSame(testEngine, testSameScale);
		assertEquals(testEngine.getSize(), testSameScale.getSize(), 1e-7);
	}
	
	@Test
	public void testTweakScaleDryMass() {
		double ratio = tweakScaleSize / size;
		double massScale = Math.pow(ratio, Engine.TWEAKSCALE_MASS_EXPONENT);
		
		assertEquals(dryMass * massScale, testTweakScaleEngine.getDryMass(), 1e-7);
	}
	
	@Test
	public void testTweakScaleMass() {
		double ratio = tweakScaleSize / size;
		double massScale = Math.pow(ratio, Engine.TWEAKSCALE_MASS_EXPONENT);
		
		assertEquals(mass * massScale, testTweakScaleEngine.getMass(), 1e-7);
	}
	
	@Test
	public void testTweakScaleThrust() {
		double ratio = tweakScaleSize / size;
		double thrustScale = Math.pow(ratio, Engine.TWEAKSCALE_THRUST_EXPONENT);
		
		assertEquals(thrust * thrustScale, testTweakScaleEngine.getThrust(), 1e-7);
	}
	
	@Test
	public void testTweakScaleDeltaV() {
		assertEquals(testEngine.getDeltaV(), testTweakScaleEngine.getDeltaV(), 1e-7);
	}
	
	@Test
	public void testTweakScaleTWR() {
		double ratio = tweakScaleSize / size;
		double massScale = Math.pow(ratio, Engine.TWEAKSCALE_MASS_EXPONENT);
		double thrustScale = Math.pow(ratio, Engine.TWEAKSCALE_THRUST_EXPONENT);
		double twrScale = thrustScale / massScale;
		
		assertEquals(testEngine.getMinTWR() * twrScale, testTweakScaleEngine.getMinTWR(), 1e-7);
		assertEquals(testEngine.getMaxTWR() * twrScale, testTweakScaleEngine.getMaxTWR(), 1e-7);
	}
}
