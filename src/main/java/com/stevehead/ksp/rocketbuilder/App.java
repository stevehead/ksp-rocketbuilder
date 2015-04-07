package com.stevehead.ksp.rocketbuilder;

import com.stevehead.ksp.rocketbuilder.interfaces.*;
import com.stevehead.ksp.rocketbuilder.rocket.*;

public class App 
{
	private static boolean logOutput = true;
	
	public static void main(String[] args)
	{
		double targetDeltaV = 3800;
		double ispScale = 0.81;
		
		double engineSize = 1.25;
		double tweakScale = 1.25;
		int firstStageStacks = 1;
		
		double massIncrements;
		
		if (tweakScale == engineSize)
			massIncrements = Builder.DEFAULT_MASS_INCREMENTS * Math.pow(engineSize / 1.25, Tweakscalable.TWEAKSCALE_MASS_EXPONENT);
		else
			massIncrements = Builder.DEFAULT_MASS_INCREMENTS * Math.pow(tweakScale / 1.25, Tweakscalable.TWEAKSCALE_MASS_EXPONENT);
		
		Engine[] engines = Engine.getEngines(engineSize);
		
		for (Engine firstStageEngine : engines) {
			for (Engine secondStageEngine : engines) {
				
				if (secondStageEngine.getIsp() <= firstStageEngine.getIsp()) continue;
				
				System.out.println("*********************************");
				
				if (firstStageStacks > 1)
					System.out.println("First stage: " + firstStageEngine + " x" + firstStageStacks);
				else
					System.out.println("First stage: " + firstStageEngine);
				System.out.println("Second stage: " + secondStageEngine);
				System.out.println("*********************************");
				Builder builder = new Builder(targetDeltaV, firstStageEngine.tweakScale(tweakScale), secondStageEngine.tweakScale(tweakScale));
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
					ProceduralTank tank1 = new ProceduralTank(stage1tankMass, engines[0].getPropellants());
					Stack stack1 = new Stack(tank1, engines[0]);
					Stage stage1 = new Stage(new Payload(payloadMass), stack1, firstStageStackSize);
					
					if (stage1.getMinTWR() < TARGET_STAGE_1_TWR) break;
					
					Rocket.Builder rocketBuilder = new Rocket.Builder(stage1tankMass);
					rocketBuilder.addStage(stage1);
					Rocket rocket = rocketBuilder.build();
					
					if (rocket.getStartingTWR() < TARGET_STAGE_1_TWR) break;
					
					if (rocket.getTotalDeltaV() > targetDeltaV) {
						double payloadFraction = 100.0 * payloadMass / (rocket.getTotalMass());
						if (payloadFraction > bestPayloadFraction) {
							if (logOutput)
								System.out.println(payloadMass + ", " + stage1tankMass + ": " + rocket.getTotalDeltaV() + ", " + payloadFraction + "%");
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
					ProceduralTank tank2 = new ProceduralTank(stage2tankMass, engines[1].getPropellants());
					Stack stack2 = new Stack(tank2, engines[1]);
					Stage stage2 = new Stage(new Payload(payloadMass), stack2);
					
					if (stage2.getMinTWR() < TARGET_STAGE_2_TWR) break;
					
					double stage1tankMass = stage2tankMass + massIncrements;
					while (true) {
						ProceduralTank tank1 = new ProceduralTank(stage1tankMass, engines[0].getPropellants());
						Stack stack1 = new Stack(tank1, engines[0]);
						Stage stage1 = new Stage(stage2, stack1, firstStageStackSize);
						
						if (stage1.getMinTWR() < TARGET_STAGE_1_TWR) break;
						
						double tankSizeRatio = tank2.getMass() / (tank1.getMass() / firstStageStackSize);
						
						if (tankSizeRatio > 0.3 && tankSizeRatio < 1) {
							Rocket.Builder rocketBuilder = new Rocket.Builder();
							rocketBuilder.addStage(stage2);
							rocketBuilder.addStage(stage1);
							Rocket rocket = rocketBuilder.build();
							
							if (rocket.getTotalDeltaV() > targetDeltaV) {
								double payloadFraction = 100.0 * payloadMass / (rocket.getTotalMass());
								if (payloadFraction > bestPayloadFraction) {
									if (logOutput)
										System.out.println(payloadMass + ", " + stage1tankMass + ", " + stage2tankMass + ": " + rocket.getTotalDeltaV() + ", " + payloadFraction + "%");
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
					ProceduralTank tank3 = new ProceduralTank(stage3tankMass, engines[2].getPropellants());
					Stack stack3 = new Stack(tank3, engines[2]);
					Stage stage3 = new Stage(new Payload(payloadMass), stack3);
					
					if (stage3.getMinTWR() < TARGET_STAGE_3_TWR) break;
				
					double stage2tankMass = stage3tankMass + massIncrements;
					while (true) {
						ProceduralTank tank2 = new ProceduralTank(stage2tankMass, engines[1].getPropellants());
						Stack stack2 = new Stack(tank2, engines[1]);
						Stage stage2 = new Stage(stage3, stack2);
						
						if (stage2.getMinTWR() < TARGET_STAGE_2_TWR) break;
						if (stage2.getMinTWR() < stage3.getMinTWR()) break;
						
						double stage1tankMass = stage2tankMass + massIncrements;
						while (true) {
							ProceduralTank tank1 = new ProceduralTank(stage1tankMass, engines[0].getPropellants());
							Stack stack1 = new Stack(tank1, engines[0]);
							Stage stage1 = new Stage(stage2, stack1, firstStageStackSize);
							
							if (stage1.getMinTWR() < TARGET_STAGE_1_TWR) break;
							
							Rocket.Builder rocketBuilder = new Rocket.Builder();
							rocketBuilder.addStage(stage2);
							rocketBuilder.addStage(stage1);
							Rocket rocket = rocketBuilder.build();
							
							if (rocket.getTotalDeltaV() > targetDeltaV) {
								double payloadFraction = 100.0 * payloadMass / (rocket.getTotalMass());
								if (payloadFraction > bestPayloadFraction) {
									if (logOutput)
										System.out.println(payloadMass + ", " + stage1tankMass + ", " + stage2tankMass + ", " + stage3tankMass + ": " + rocket.getTotalDeltaV() + ", " + payloadFraction + "%");
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
