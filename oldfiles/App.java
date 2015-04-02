package com.stevehead.ksp.rocketbuilder;

import java.util.Arrays;

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
	private static boolean logOutput = true;
	
	public static void main(String[] args)
	{
		double targetDeltaV = 3800;
		double ispScale = 0.81;
		
		double engineSize = 1.25;
		double tweakScale = 1.25;
		int firstStageStacks = 3;
		
		double massIncrements;
		
		if (tweakScale == engineSize)
			massIncrements = Builder.DEFAULT_MASS_INCREMENTS * Math.pow(engineSize / 1.25, 2.6);
		else
			massIncrements = Builder.DEFAULT_MASS_INCREMENTS * Math.pow(tweakScale / 1.25, 2.6);
		
		Engine[] engines = Engine.getEngines(engineSize);
		
		for (Engine firstStageEngine : engines) {
			Thrustable firstStageEngineFinal;
			if (firstStageStacks > 1) {
				Thrustable[] firstStageClusterArray = new Thrustable[firstStageStacks];
				Arrays.fill(firstStageClusterArray, firstStageEngine.tweakscale(tweakScale));
				firstStageEngineFinal = new EngineCluster(firstStageClusterArray);
			}
			else {
				firstStageEngineFinal = firstStageEngine.tweakscale(tweakScale);
			}
			
			for (Engine secondStageEngine : engines) {
				
				if (secondStageEngine.getIsp() <= firstStageEngine.getIsp()) continue;
				
				System.out.println("*********************************");
				
				if (firstStageStacks > 1)
					System.out.println("First stage: " + firstStageEngine + " x" + firstStageStacks);
				else
					System.out.println("First stage: " + firstStageEngine);
				System.out.println("Second stage: " + secondStageEngine);
				System.out.println("*********************************");
				Builder builder = new Builder(targetDeltaV, firstStageEngineFinal, secondStageEngine.tweakscale(tweakScale));
				builder.setIspScaler(ispScale);
				builder.setMassIncrements(massIncrements);
				builder.setFirstStageStackSize(firstStageStacks);
				builder.run();
				System.out.println("*********************************\n\n\n\n\n");
			}
		}
	}
	
	public static class Builder {
		private static final double DEFAULT_MASS_INCREMENTS = 50;
		private static final double TARGET_STAGE_1_TWR = 1.2;
		private static final double TARGET_STAGE_2_TWR = 2.0;
		private static final double TARGET_STAGE_3_TWR = 1.0;
		
		private final double targetDeltaV;
		private final double maxPayloadMass;
		private final Thrustable[] engines;
		private double massIncrements = DEFAULT_MASS_INCREMENTS;
		private double ispScaler = Engine.getIspScaler();
		private int firstStageStackSize = 1;

		
		public Builder(double targetDeltaV, Thrustable... engines) {
			if (engines.length == 0) {
				throw new IllegalArgumentException("One engine is required at least.");
			} else if (engines.length > 3) {
				throw new IllegalArgumentException("Only up to three stages is supported currently.");
			}
			this.targetDeltaV = targetDeltaV;
			this.engines = engines;
			double maxPayloadMass = engines[0].getThrust() / Thrustable.KERBIN_GRAVITY;
			this.maxPayloadMass = Math.ceil(maxPayloadMass / massIncrements) * massIncrements;
		}
		
		public Builder setMassIncrements(double massIncrements) {
			this.massIncrements = massIncrements;
			return this;
		}
		
		public Builder setIspScaler(double ispScaler) {
			this.ispScaler = ispScaler;
			return this;
		}
		
		public Builder setFirstStageStackSize(int firstStageStackSize) {
			this.firstStageStackSize = firstStageStackSize;
			return this;
		}
		
		public Rocket run() {
			Rocket rocket = null;
			double originalIspScaler = Engine.getIspScaler();
			Engine.setIspScaler(ispScaler);
			
			switch (engines.length) {
			case 1:
				rocket = runOneStage();
				break;
			case 2:
				rocket = runTwoStage();
				break;
			case 3:
				rocket = runThreeStage();
				break;
			}
			
			Engine.setIspScaler(originalIspScaler);
			return rocket;
		}
		
		private Rocket runOneStage() {
			Rocket bestRocket = null;
			boolean foundBest = false;
			
			double payloadMass = maxPayloadMass;
			while (payloadMass > 0) {
				double bestPayloadFraction = 0;
				double stage1tankMass = massIncrements;
				while (true) {
					Tank tank1 = new Tank.ProceduralBuilder(stage1tankMass, engines[0].getPropellants()).build();
					RocketStage stage1 = new RocketStage(new Payload(payloadMass), tank1, engines[0]);
					
					if (stage1.getMinTWR() < TARGET_STAGE_1_TWR) break;
					
					Rocket.Builder rocketBuilder = new Rocket.Builder();
					rocketBuilder.addStage(stage1);
					Rocket rocket = rocketBuilder.build();
					
					if (rocket.getDeltaV() > targetDeltaV) {
						double payloadFraction = 100.0 * payloadMass / (rocket.getTotalMass());
						if (payloadFraction > bestPayloadFraction) {
							if (logOutput)
								System.out.println(payloadMass + ", " + stage1tankMass + ": " + rocket.getDeltaV() + ", " + payloadFraction + "%");
							bestRocket = rocket;
							bestPayloadFraction = payloadFraction;
							foundBest = true;
						}
					}
					
					stage1tankMass += massIncrements;
				}
				if (foundBest) break;
				payloadMass -= massIncrements;
			}
			
			return bestRocket;
		}
		
		private Rocket runTwoStage() {
			Rocket bestRocket = null;
			boolean foundBest = false;
			
			double payloadMass = maxPayloadMass;
			while (payloadMass > 0) {
				double bestPayloadFraction = 0;
				double stage2tankMass = massIncrements;
				while (true) {
					Tank tank2 = new Tank.ProceduralBuilder(stage2tankMass, engines[1].getPropellants()).build();
					RocketStage stage2 = new RocketStage(new Payload(payloadMass), tank2, engines[1]);
					
					if (stage2.getMinTWR() < TARGET_STAGE_2_TWR) break;
					
					double stage1tankMass = stage2tankMass + massIncrements;
					while (true) {
						Tank tank1 = new Tank.ProceduralBuilder(stage1tankMass, engines[0].getPropellants()).build();
						RocketStage stage1 = new RocketStage(stage2, tank1, engines[0]);
						
						if (stage1.getMinTWR() < TARGET_STAGE_1_TWR) break;
						
						double tankSizeRatio = tank2.getTotalMass() / (tank1.getTotalMass());
						
						if (tankSizeRatio > 0.3 && tankSizeRatio < 1) {
							Rocket.Builder rocketBuilder = new Rocket.Builder();
							rocketBuilder.addStage(stage2);
							rocketBuilder.addStage(stage1);
							Rocket rocket = rocketBuilder.build();
							
							if (rocket.getDeltaV() > targetDeltaV) {
								double payloadFraction = 100.0 * payloadMass / (rocket.getTotalMass());
								if (payloadFraction > bestPayloadFraction) {
									if (logOutput)
										System.out.println(payloadMass + ", " + stage1tankMass + ", " + stage2tankMass + ": " + rocket.getDeltaV() + ", " + payloadFraction + "%");
									bestRocket = rocket;
									bestPayloadFraction = payloadFraction;
									foundBest = true;
								}
							}
						}
						
						stage1tankMass += massIncrements;
					}
					stage2tankMass += massIncrements;
				}
				if (foundBest) break;
				payloadMass -= massIncrements;
			}
			
			return bestRocket;
		}
		
		private Rocket runThreeStage() {
			Rocket bestRocket = null;
			boolean foundBest = false;
			
			double payloadMass = maxPayloadMass;
			while (payloadMass > 0) {
				double bestPayloadFraction = 0;
				double stage3tankMass = massIncrements;
				
				while (true) {
					Tank tank3 = new Tank.ProceduralBuilder(stage3tankMass, engines[2].getPropellants()).build();
					RocketStage stage3 = new RocketStage(new Payload(payloadMass), tank3, engines[2]);
					
					if (stage3.getMinTWR() < TARGET_STAGE_3_TWR) break;
				
					double stage2tankMass = stage3tankMass + massIncrements;
					while (true) {
						Tank tank2 = new Tank.ProceduralBuilder(stage2tankMass, engines[1].getPropellants()).build();
						RocketStage stage2 = new RocketStage(stage3, tank2, engines[1]);
						
						if (stage2.getMinTWR() < TARGET_STAGE_2_TWR) break;
						if (stage2.getMinTWR() < stage3.getMinTWR()) break;
						
						double stage1tankMass = stage2tankMass + massIncrements;
						while (true) {
							Tank tank1 = new Tank.ProceduralBuilder(stage1tankMass, engines[0].getPropellants()).build();
							RocketStage stage1 = new RocketStage(stage2, tank1, engines[0]);
							
							if (stage1.getMinTWR() < TARGET_STAGE_1_TWR) break;
							
							Rocket.Builder rocketBuilder = new Rocket.Builder();
							rocketBuilder.addStage(stage2);
							rocketBuilder.addStage(stage1);
							Rocket rocket = rocketBuilder.build();
							
							if (rocket.getDeltaV() > targetDeltaV) {
								double payloadFraction = 100.0 * payloadMass / (rocket.getTotalMass());
								if (payloadFraction > bestPayloadFraction) {
									if (logOutput)
										System.out.println(payloadMass + ", " + stage1tankMass + ", " + stage2tankMass + ", " + stage3tankMass + ": " + rocket.getDeltaV() + ", " + payloadFraction + "%");
									bestRocket = rocket;
									bestPayloadFraction = payloadFraction;
									foundBest = true;
								}
							}
							stage1tankMass += massIncrements;
						}
						stage2tankMass += massIncrements;
					}
					stage3tankMass += massIncrements;
				}
				if (foundBest) break;
				payloadMass -= massIncrements;
			}
			
			return bestRocket;
		}
	}
}
