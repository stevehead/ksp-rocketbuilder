package com.stevehead.ksp.rocketbuilder.parts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.ListUtils;

import com.stevehead.ksp.rocketbuilder.interfaces.Thrustable;

public class Rocket extends Payload implements Thrustable {
	
	private final double dryMass;
	private final double totalMass;
	private final RocketStage[] stages;
	private final Propellant[] propellants;
	private int currentStage = 0;
	
	private Rocket(RocketStage... stages) {
		double dryMass = 0;
		double totalMass = 0;
		List<Propellant> propellants = new ArrayList<Propellant>();
		
		if (stages.length < 1) {
			throw new IllegalArgumentException("At least one stage is required.");
		}
		this.stages = stages;
		
		for (RocketStage stage : stages) {
			dryMass += stage.getDryMass();
			totalMass += stage.getTotalMass();
			List<Propellant> thisPropellants = Arrays.asList(stage.getPropellants());
			propellants = ListUtils.union(propellants, thisPropellants);
		}
		
		this.dryMass = dryMass;
		this.totalMass = totalMass;
		this.propellants = propellants.toArray(new Propellant[propellants.size()]);
	}
	
	public void stage() {
		currentStage++;
		currentStage = Math.min(currentStage, stages.length);
	}
	
	public RocketStage[] getStages() {
		return stages;
	}
	
	public RocketStage getStage() {
		return getStage(currentStage);
	}
	
	public RocketStage getStage(int i) {
		return stages[i];
	}

	@Override
	public double getDryMass() {
		return dryMass;
	}

	@Override
	public double getTotalMass() {
		return totalMass;
	}
	
	@Override
	public double getMass() {
		double mass = 0;
		for (RocketStage stage : stages) {
			mass += stage.getMass();
		}
		return mass;
	}

	@Override
	public void setMass(double mass) {
		stages[currentStage].setMass(mass);
	}

	@Override
	public void changeMass(double massChange) {
		stages[currentStage].changeMass(massChange);
	}

	@Override
	public double getDeltaV() {
		double deltaV = 0;
		for (RocketStage stage : stages) {
			deltaV += stage.getDeltaV();
		}
		return deltaV;
	}

	@Override
	public double getIsp() {
		return stages[currentStage].getIsp();
	}

	@Override
	public double getMinTWR() {
		return stages[currentStage].getMinTWR();
	}

	@Override
	public double getMaxTWR() {
		return stages[currentStage].getMaxTWR();
	}
	
	@Override
	public Propellant[] getPropellants() {
		return propellants;
	}

	@Override
	public double getThrust() {
		return stages[currentStage].getThrust();
	}
	
	public static class Builder {
		private final Payload payload;
		private ArrayList<RocketStage> stages = new ArrayList<RocketStage>();
		
		public Builder() {
			this(0);
		}
		
		public Builder(Payload payload) {
			this.payload = payload;
		}
		
		public Builder(double payloadMass) {
			this(new Payload(payloadMass));
		}
		
		public Builder addStage(RocketStage stage) {
			stages.add(stage);
			return this;
		}
		
		public Builder addStage(Payload payload, Tank fuelTank, Thrustable... engines) {
			RocketStage stage = new RocketStage(payload, fuelTank, engines);
			return addStage(stage);
		}
		
		public Builder addStage(Tank fuelTank, Thrustable... engines) {
			Payload payload;
			if (stages.isEmpty()) {
				payload = this.payload;
			} else {
				payload = stages.get(stages.size() - 1);
			}
			return addStage(payload, fuelTank, engines);
		}
		
		public Rocket build() {
			Collections.reverse(stages);
			RocketStage[] stageArray = new RocketStage[stages.size()];
			return new Rocket(stages.toArray(stageArray));
		}
	}
}
