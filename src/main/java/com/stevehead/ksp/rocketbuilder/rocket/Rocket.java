package com.stevehead.ksp.rocketbuilder.rocket;

import java.util.ArrayList;
import java.util.Collections;

import com.stevehead.ksp.rocketbuilder.interfaces.Massive;
import com.stevehead.ksp.rocketbuilder.interfaces.Thrustable;

public class Rocket {
	
	private final Stage[] stages;
	
	public Rocket(Stage... stages) {
		if (stages.length < 1) {
			throw new IllegalArgumentException("At least one stage is required.");
		}
		this.stages = stages;
	}
	
	public Stage[] getStages() {
		return stages;
	}
	
	public Stage getStage(int i) {
		return stages[i];
	}
	
	public Stage getFinalStage() {
		return getStage(stages.length - 1);
	}
	
	public double getTotalMass() {
		return getStage(0).getMass();
	}
	
	public double getTotalDeltaV() {
		double deltaV = 0;
		for (Stage stage : stages) {
			deltaV += stage.getDeltaV();
		}
		return deltaV;
	}
	
	public double getStartingTWR() {
		return getStage(0).getMinTWR();
	}
	
	public static class Builder {
		private final Massive payload;
		private ArrayList<Stage> stages = new ArrayList<Stage>();
		
		public Builder(Massive payload) {
			this.payload = payload;
		}
		
		public Builder(double payloadMass) {
			this(new Payload(payloadMass));
		}
		
		public Builder() {
			this(0);
		}
		
		public Builder addStage(FuelTank fuelTank, Thrustable engine, int stackNumber) {
			Massive payload;
			if (stages.isEmpty()) {
				payload = this.payload;
			} else {
				payload = stages.get(stages.size() - 1);
			}
			Stack stack = new Stack(fuelTank, engine);
			Stage stage = new Stage(payload, stack, stackNumber);
			stages.add(stage);
			return this;
		}
		
		public Builder addStage(FuelTank fuelTank, Thrustable engine) {
			return addStage(fuelTank, engine, 1);
		}
		
		public Rocket build() {
			Collections.reverse(stages);
			Stage[] stageArray = new Stage[stages.size()];
			return new Rocket(stages.toArray(stageArray));
		}
	}
}
