package com.stevehead.ksp.rocketbuilder.parts;

import com.stevehead.ksp.rocketbuilder.interfaces.Thrustable;

public class RocketStage extends Payload implements Thrustable {
	
	/**
	 * The dry mass in kg.
	 */
	private final double dryMass;
	
	/**
	 * The total mass in kg.
	 */
	private final double totalMass;
	
	/**
	 * The thrust in Newtons.
	 */
	private final double thrust;
	
	/**
	 * The specific impulse in seconds.
	 */
	private final double isp;
	
	/**
	 * The current mass.
	 */
	private double mass;
	
	public RocketStage(Payload payload, Tank fuelTank, Thrustable... engines) {
		double dryMass, totalMass, thrust, ispDenominator;
		
		dryMass = payload.getMass() + fuelTank.getDryMass();
		totalMass = payload.getMass() + fuelTank.getTotalMass();
		thrust = 0;
		ispDenominator = 0;
		
		for (Thrustable engine : engines) {
			dryMass += engine.getDryMass();
			totalMass += engine.getTotalMass();
			thrust += engine.getThrust();
			ispDenominator += engine.getThrust() / engine.getIsp();
		}
		
		this.dryMass = dryMass;
		this.totalMass = totalMass;
		this.mass = totalMass;
		this.thrust = thrust;
		this.isp = thrust / ispDenominator;
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
	public void setMass(double mass) {
		mass = Math.max(mass, getDryMass());
		mass = Math.min(mass, getTotalMass());
		this.mass = mass;
	}

	@Override
	public void changeMass(double massChange) {
		double mass = getMass() + massChange;
		setMass(mass);
	}
	
	@Override
	public double getMass() {
		return mass;
	}

	@Override
	public double getDeltaV() {
		return getIsp() * Math.log(getTotalMass() / getDryMass());
	}

	@Override
	public double getIsp() {
		return isp * Engine.getIspScaler();
	}

	@Override
	public double getMinTWR() {
		return KERBIN_GRAVITY * getThrust() / getTotalMass();
	}

	@Override
	public double getMaxTWR() {
		return KERBIN_GRAVITY * getThrust() / getDryMass();
	}

	@Override
	public double getThrust() {
		return thrust;
	}
}
