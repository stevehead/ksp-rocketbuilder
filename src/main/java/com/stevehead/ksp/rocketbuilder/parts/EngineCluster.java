package com.stevehead.ksp.rocketbuilder.parts;

import com.stevehead.ksp.rocketbuilder.interfaces.Thrustable;

public class EngineCluster implements Thrustable {
	
	/**
	 * Engines in cluster.
	 */
	private final Engine[] engines;
	
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
	 * The curent mass.
	 */
	private double mass;
	
	/**
	 * @param engines		the engines in the cluster
	 */
	public EngineCluster(Engine... engines) {
		double dryMass, totalMass, thrust, ispDenominator;
		this.engines = engines;
		
		dryMass = 0;
		totalMass = 0;
		thrust = 0;
		ispDenominator = 0;
		
		for (Engine engine : engines) {
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
	
	/**
	 * @return		the engines in the cluster
	 */
	public Engine[] getEngines() {
		return engines;
	}
	
	public double getDeltaV() {
		return getIsp() * Math.log(getTotalMass() / getDryMass()) * KERBIN_GRAVITY;
	}
	
	public double getDryMass() {
		return dryMass;
	}
	
	public double getIsp() {
		return isp;
	}
	
	public void changeMass(double massChange) {
		double mass = getMass() + massChange;
		setMass(mass);
	}
	
	public double getMass() {
		return mass;
	}
	
	public void setMass(double mass) {
		mass = Math.max(mass, getDryMass());
		mass = Math.min(mass, getTotalMass());
		this.mass = mass;
	}
	
	public double getMaxTWR() {
		return getThrust() / (getDryMass() * KERBIN_GRAVITY);
	}
	
	public double getMinTWR() {
		return getThrust() / (getTotalMass() * KERBIN_GRAVITY);
	}
	
	public double getThrust() {
		return thrust;
	}
	
	public double getTotalMass() {
		return totalMass;
	}
}