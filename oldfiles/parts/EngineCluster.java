package com.stevehead.ksp.rocketbuilder.parts;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import org.apache.commons.collections4.ListUtils;

import com.stevehead.ksp.rocketbuilder.interfaces.Thrustable;

public class EngineCluster implements Thrustable {
	
	/**
	 * Engines in cluster.
	 */
	private final Thrustable[] engines;
	
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
	
	private final Propellant[] propellants;
	
	/**
	 * The curent mass.
	 */
	private double mass;
	
	/**
	 * @param engines		the engines in the cluster
	 */
	public EngineCluster(Thrustable... engines) {
		double dryMass, totalMass, thrust, ispDenominator;
		List<Propellant> propellants = new ArrayList<Propellant>();
		this.engines = engines;
		
		dryMass = 0;
		totalMass = 0;
		thrust = 0;
		ispDenominator = 0;
		
		for (Thrustable engine : engines) {
			dryMass += engine.getDryMass();
			totalMass += engine.getTotalMass();
			thrust += engine.getThrust();
			ispDenominator += engine.getThrust() / engine.getIsp();
			List<Propellant> thisPropellants = Arrays.asList(engine.getPropellants());
			propellants = ListUtils.union(propellants, thisPropellants);
		}
		
		this.dryMass = dryMass;
		this.totalMass = totalMass;
		this.mass = totalMass;
		this.thrust = thrust;
		this.isp = thrust / ispDenominator;
		this.propellants = propellants.toArray(new Propellant[propellants.size()]);
	}
	
	/**
	 * @return		the engines in the cluster
	 */
	public Thrustable[] getEngines() {
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
	
	public Propellant[] getPropellants() {
		return propellants;
	}
	
	public double getThrust() {
		return thrust;
	}
	
	public double getTotalMass() {
		return totalMass;
	}
}
