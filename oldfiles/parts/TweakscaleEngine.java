package com.stevehead.ksp.rocketbuilder.parts;

import com.stevehead.ksp.rocketbuilder.interfaces.Thrustable;
import com.stevehead.ksp.rocketbuilder.parts.Engine.Mod;

public class TweakscaleEngine implements Thrustable {
	/**
	 * The proper display name of the engine.
	 */
	protected final String name;
	
	/**
	 * The game mod the engine is found in.
	 */
	protected final Mod mod;
	
	/**
	 * The dry mass in kg.
	 */
	protected final double dryMass;
	
	/**
	 * The total mass in kg.
	 */
	protected final double totalMass;
	
	/**
	 * The thrust in Newtons.
	 */
	protected final double thrust;
	
	/**
	 * The specific impulse in seconds.
	 */
	protected final double isp;
	
	/**
	 * The diameter of the engine in meters.
	 */
	protected final double size;
	
	protected final Propellant[] propellants;
	
	/**
	 * The curent mass.
	 */
	protected double mass;
	
	
	public TweakscaleEngine(String name, Mod mod, double dryMass, double totalMass, double thrust, double isp, double size, Propellant... propellants) {
		this.name = name;
		this.mod = mod;
		this.dryMass = dryMass;
		this.totalMass = totalMass;
		this.thrust = thrust;
		this.isp = isp;
		this.size = size;
		this.mass = totalMass;
		this.propellants = propellants;
	}
	
	/**
	 * @return		the KSP game mod
	 */
	public Mod getMod() {
		return mod;
	}
	
	/**
	 * @return		the display name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return		the stack diameter
	 */
	public double getSize() {
		return size;
	}
	
	public double getDeltaV() {
		return getIsp() * Math.log(getTotalMass() / getDryMass()) * KERBIN_GRAVITY;
	}
	
	public double getDryMass() {
		return dryMass;
	}
	
	public double getIsp() {
		return isp * Engine.getIspScaler();
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
		return getThrust() / (KERBIN_GRAVITY * getDryMass());
	}
	
	public double getMinTWR() {
		return getThrust() / (KERBIN_GRAVITY * getTotalMass());
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
	
	public String toString() {
		return name;
	}
}
