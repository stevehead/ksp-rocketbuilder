package com.stevehead.ksp.rocketbuilder.rocket;

import static org.junit.Assert.*;

import org.junit.Test;

public class EngineTest {
	
	@Test
	public void testGetIspScaler() {
		assertEquals("Initial ISP Scaler should be 1", 1, Engine.getIspScaler(), 1e-15);
	}

	@Test
	public void testSetIspScaler() {
		double newIspScaler = 0.81;
		double originalIspScaler = Engine.getIspScaler();
		Engine.setIspScaler(newIspScaler);
		assertEquals("New ISP Scaler should be 0.81", newIspScaler, Engine.getIspScaler(), 1e-15);
		Engine.setIspScaler(originalIspScaler);
	}

	@Test
	public void testGetDeltaV() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetIsp() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMinTWR() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMaxTWR() {
		fail("Not yet implemented");
	}

}
