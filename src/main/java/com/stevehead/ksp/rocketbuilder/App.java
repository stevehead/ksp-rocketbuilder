package com.stevehead.ksp.rocketbuilder;

import com.stevehead.ksp.rocketbuilder.interfaces.Thrustable;
import com.stevehead.ksp.rocketbuilder.parts.Engine;
import com.stevehead.ksp.rocketbuilder.parts.EngineCluster;
import com.stevehead.ksp.rocketbuilder.parts.Payload;
import com.stevehead.ksp.rocketbuilder.parts.Propellant;
import com.stevehead.ksp.rocketbuilder.parts.Rocket;
import com.stevehead.ksp.rocketbuilder.parts.RocketStage;
import com.stevehead.ksp.rocketbuilder.parts.Tank;

public class App 
{
	public static void main(String[] args)
	{
		Engine.setIspScaler(0.81);
		Thrustable mainEngine = new EngineCluster(Engine.ROCKOMAX_MAINSAIL_LIQUID_ENGINE);
		double massIncrements = 50;
		double targetDeltaV = 3800;
		boolean foundBest = false;
		double maxPayloadMass = mainEngine.getThrust() / Thrustable.KERBIN_GRAVITY;
		maxPayloadMass = Math.ceil(maxPayloadMass / massIncrements) * massIncrements;
		
		double payloadMass = maxPayloadMass;
		System.out.println("Max Payload:" + maxPayloadMass);
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
			//
		}
		while (payloadMass > 0) {
			double bestDeltaVDiff = -1;
			double stage2tankMass = massIncrements;
			while (true) {
				Tank tank2 = new Tank.ProceduralBuilder(stage2tankMass, Propellant.LIQUID_FUEL, Propellant.OXIDIZER).build();
				RocketStage stage2 = new RocketStage(new Payload(payloadMass), tank2, Engine.ROCKOMAX_SKIPPER_LIQUID_ENGINE);
				
				if (stage2.getMinTWR() < 1) break;
				
				double stage1tankMass = stage2tankMass + massIncrements;
				while (true) {
					Tank tank1 = new Tank.ProceduralBuilder(stage1tankMass, Propellant.LIQUID_FUEL, Propellant.OXIDIZER).build();
					RocketStage stage1 = new RocketStage(stage2, tank1, mainEngine);
					
					if (stage1.getMinTWR() < 1.2) break;
					if (stage1.getMinTWR() < stage2.getMinTWR()) break;
					
					Rocket.Builder rocketBuilder = new Rocket.Builder();
					rocketBuilder.addStage(stage2);
					rocketBuilder.addStage(stage1);
					Rocket rocket = rocketBuilder.build();
					
					if (rocket.getDeltaV() > targetDeltaV) {
						double deltaVDiff = Math.abs(rocket.getDeltaV() - targetDeltaV);
						if (bestDeltaVDiff < 0 || deltaVDiff < bestDeltaVDiff) {
							System.out.println(payloadMass + ", " + stage1tankMass + ", " + stage2tankMass + ": " + rocket.getDeltaV());
							bestDeltaVDiff = deltaVDiff;
							foundBest = true;
						}
					}
					
					stage1tankMass += massIncrements;
				}
				stage2tankMass += massIncrements;
			}
			if (foundBest) break;
			payloadMass -= massIncrements;
		}
		System.out.println("done");
	}
}
