package com.stevehead.ksp.rocketbuilder.rocket;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class BaseComponentTest {
	
	private static BaseComponent testComponent;
	private static final double mass = 100;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testComponent = new TestComponent(mass);
	}
	
	@Test
	public void testGetMass() {
		assertEquals("Mass should be " + mass, mass, testComponent.getMass(), 1e-15);
	}
	
	private static class TestComponent extends BaseComponent {
		public TestComponent(double mass) {
			super(mass);
		}
	}
}
