package com.stevehead.ksp.rocketbuilder.rocket;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class RocketTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBuilderOneStage() {
		double payloadMass = 1000;
		double fueltank1DryMass = 500;
		double fueltank1Mass = 5000;
		Engine engine1 = Engine.PreDefined.LV_T30_LIQUID_FUEL_ENGINE.toEngine();
		
		Rocket.Builder rocketBuilder = new Rocket.Builder(payloadMass);
		rocketBuilder.addStage(new FuelTank(fueltank1DryMass, fueltank1Mass), engine1);
		Rocket testRocket = rocketBuilder.build();
		
		double expectedDryMass = payloadMass + fueltank1DryMass + engine1.getDryMass();
		double expectedMass = payloadMass + fueltank1Mass + engine1.getMass();
		double expectedDeltaV = BaseThruster.KERBIN_GRAVITY * engine1.getIsp() * Math.log(expectedMass / expectedDryMass);
		
		assertEquals(expectedDeltaV, testRocket.getTotalDeltaV(), 1e-7);
	}
	
	@Test
	public void testBuilderOneStageManual() {
		double payloadMass = 1000;
		double fueltank1DryMass = 500;
		double fueltank1Mass = 5000;
		FuelTank fuelTank1 = new FuelTank(fueltank1DryMass, fueltank1Mass);
		Engine engine1 = Engine.PreDefined.LV_T30_LIQUID_FUEL_ENGINE.toEngine();
		Stack stack1 = new Stack(fuelTank1, engine1);
		Stage stage1 = new Stage(new Payload(payloadMass), stack1);
		
		Rocket.Builder rocketBuilder = new Rocket.Builder();
		
		rocketBuilder.addStage(stage1);
		Rocket testRocket = rocketBuilder.build();
		
		double expectedDryMass = payloadMass + fueltank1DryMass + engine1.getDryMass();
		double expectedMass = payloadMass + fueltank1Mass + engine1.getMass();
		double expectedDeltaV = BaseThruster.KERBIN_GRAVITY * engine1.getIsp() * Math.log(expectedMass / expectedDryMass);
		
		assertEquals(expectedDeltaV, testRocket.getTotalDeltaV(), 1e-7);
	}
	
	@Test
	public void testBuilderTwoStage() {
		double payloadMass = 1000;
		double fueltank2DryMass = 500;
		double fueltank2Mass = 5000;
		FuelTank fuelTank2 = new FuelTank(fueltank2DryMass, fueltank2Mass);
		Engine engine2 = Engine.PreDefined.LV_T30_LIQUID_FUEL_ENGINE.toEngine();
		Stack stack2 = new Stack(fuelTank2, engine2);
		Stage stage2 = new Stage(new Payload(payloadMass), stack2);
		
		double fueltank1DryMass = 1000;
		double fueltank1Mass = 10000;
		FuelTank fuelTank1 = new FuelTank(fueltank1DryMass, fueltank1Mass);
		Engine engine1 = Engine.PreDefined.LV_T30_LIQUID_FUEL_ENGINE.toEngine();
		Stack stack1 = new Stack(fuelTank1, engine1);
		Stage stage1 = new Stage(stage2, stack1);
		
		Rocket.Builder rocketBuilder = new Rocket.Builder();
		rocketBuilder.addStage(stage2);
		rocketBuilder.addStage(stage1);
		Rocket testRocket = rocketBuilder.build();
		
		double expectedDryMass2 = payloadMass + fueltank2DryMass + engine2.getDryMass();
		double expectedMass2 = payloadMass + fueltank2Mass + engine2.getMass();
		double expectedDeltaV2 = BaseThruster.KERBIN_GRAVITY * engine2.getIsp() * Math.log(expectedMass2 / expectedDryMass2);
		
		double expectedDryMass1 = expectedMass2 + fueltank1DryMass + engine1.getDryMass();
		double expectedMass1 = expectedMass2 + fueltank1Mass + engine1.getMass();
		double expectedDeltaV1 = BaseThruster.KERBIN_GRAVITY * engine1.getIsp() * Math.log(expectedMass1 / expectedDryMass1);
		double expectedDeltaV = expectedDeltaV1 + expectedDeltaV2;
		
		assertEquals(expectedDeltaV, testRocket.getTotalDeltaV(), 1e-7);
	}
	
	@Test
	public void testBuilderTwoStageManual() {
		double payloadMass = 1000;
		double fueltank1DryMass = 1000;
		double fueltank1Mass = 10000;
		Engine engine1 = Engine.PreDefined.LV_T30_LIQUID_FUEL_ENGINE.toEngine();
		double fueltank2DryMass = 500;
		double fueltank2Mass = 5000;
		Engine engine2 = Engine.PreDefined.LV_909_LIQUID_FUEL_ENGINE.toEngine();
		
		Rocket.Builder rocketBuilder = new Rocket.Builder(payloadMass);
		rocketBuilder.addStage(new FuelTank(fueltank2DryMass, fueltank2Mass), engine2);
		rocketBuilder.addStage(new FuelTank(fueltank1DryMass, fueltank1Mass), engine1);
		Rocket testRocket = rocketBuilder.build();
		
		double expectedDryMass2 = payloadMass + fueltank2DryMass + engine2.getDryMass();
		double expectedMass2 = payloadMass + fueltank2Mass + engine2.getMass();
		double expectedDeltaV2 = BaseThruster.KERBIN_GRAVITY * engine2.getIsp() * Math.log(expectedMass2 / expectedDryMass2);
		
		double expectedDryMass1 = expectedMass2 + fueltank1DryMass + engine1.getDryMass();
		double expectedMass1 = expectedMass2 + fueltank1Mass + engine1.getMass();
		double expectedDeltaV1 = BaseThruster.KERBIN_GRAVITY * engine1.getIsp() * Math.log(expectedMass1 / expectedDryMass1);
		double expectedDeltaV = expectedDeltaV1 + expectedDeltaV2;
		
		assertEquals(expectedDeltaV, testRocket.getTotalDeltaV(), 1e-7);
	}

}
