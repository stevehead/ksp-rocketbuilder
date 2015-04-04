package com.stevehead.ksp.rocketbuilder.rocket;

import com.stevehead.ksp.rocketbuilder.interfaces.Thrustable;

/**
 * BaseThruster is any object with mass that can thrust.
 * 
 * @author Steve Johnson
 *
 */
public abstract class BaseThruster extends BaseTank implements Thrustable {
	/**
	 * The thrust in Newtons.
	 */
	protected final double thrust;
	
	/**
	 * The specific impulse in seconds.
	 */
	protected final double isp;
	
	/**
	 * The minumum thrust-to-weight ratio.
	 */
	protected final double minTWR; 
	
	/**
	 * The maximum thrust-to-weight ratio.
	 */
	protected final double maxTWR;
	
	/**
	 * @param dryMass		the dry mass in kg
	 * @param mass			the total mass in kg
	 * @param thrust		the thrust in N
	 * @param isp			the specific impulse in seconds
	 * @param propellants	the propellants used
	 */
	protected BaseThruster(double dryMass, double mass, double thrust, double isp, Propellant... propellants) {
		super(dryMass, mass, propellants);
		this.thrust = thrust;
		this.isp = isp;
		this.minTWR = calculateTWR(getMass(), getThrust());
		this.maxTWR = calculateTWR(getDryMass(), getThrust());
	}
	
	/**
	 * @param mass			the mass in kg
	 * @param thrust		the thrust in N
	 * @param isp			the specific impulse in seconds
	 * @param propellants	the propellants used
	 */
	protected BaseThruster(double mass, double thrust, double isp, Propellant... propellants) {
		this(mass, mass, thrust, isp , propellants);
	}
	
	/**
	 * Calculates the thrust-to-weight ratio.
	 * 
	 * @param mass			the mass in kg
	 * @param thrust		the thrust in N
	 * @return				the thrust-to-weight ratio
	 */
	protected static double calculateTWR(double mass, double thrust) {
		return thrust / (KERBIN_GRAVITY * mass);
	}
	
	@Override
	public final double getDeltaV() {
		return getIsp() * Math.log(getMass() / getDryMass()) * KERBIN_GRAVITY;
	}
	
	@Override
	public final double getIsp() {
		return Engine.getIspScaler() * isp;
	}
	
	@Override
	public final double getMinTWR() {
		return minTWR;
	}
	
	@Override
	public final double getMaxTWR() {
		return maxTWR;
	}
	
	@Override
	public final double getThrust() {
		return thrust;
	}
}
