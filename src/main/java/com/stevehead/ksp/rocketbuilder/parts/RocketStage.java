package com.stevehead.ksp.rocketbuilder.parts;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.collections4.ListUtils;

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
	
	private final Propellant[] propellants;
	
	/**
	 * The current mass.
	 */
	private double mass;
	
	public RocketStage(Payload payload, Tank fuelTank, Thrustable... engines) {
		double dryMass, totalMass, thrust, ispDenominator;
		ArrayList<Propellant> propellants = new ArrayList<Propellant>();
		
		dryMass = payload.getMass() + fuelTank.getDryMass();
		totalMass = payload.getMass() + fuelTank.getTotalMass();
		thrust = 0;
		ispDenominator = 0;
		
		for (Thrustable engine : engines) {
			dryMass += engine.getDryMass();
			totalMass += engine.getTotalMass();
			thrust += engine.getThrust();
			ispDenominator += engine.getThrust() / engine.getIsp();
			ListUtils.union(propellants, new ArrayList<Propellant>(Arrays.asList(engine.getPropellants())));
		}
		
		this.dryMass = dryMass;
		this.totalMass = totalMass;
		this.mass = totalMass;
		this.thrust = thrust;
		this.isp = thrust / ispDenominator;
		this.propellants = propellants.toArray(new Propellant[propellants.size()]);
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
		return getIsp() * Math.log(getTotalMass() / getDryMass()) * KERBIN_GRAVITY;
	}

	@Override
	public double getIsp() {
		return isp;
	}

	@Override
	public double getMinTWR() {
		return getThrust() / (KERBIN_GRAVITY * getTotalMass());
	}

	@Override
	public double getMaxTWR() {
		return getThrust() / ( KERBIN_GRAVITY * getDryMass());
	}
	
	@Override
	public Propellant[] getPropellants() {
		return propellants;
	}

	@Override
	public double getThrust() {
		return thrust;
	}
}
