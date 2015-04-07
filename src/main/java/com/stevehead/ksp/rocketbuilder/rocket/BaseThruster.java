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
	 * Calculates the combined thrust of the engines.
	 * 
	 * @param engines		engines to be combined
	 * @return				the total thrust of the engines
	 */
	protected static double calculateThrust(Thrustable... engines) {
		double thrust = 0;
		for (Thrustable engine : engines) {
			thrust += engine.getThrust();
		}
		return thrust;
	}
	
	/**
	 * Calculates the combined specific impulse of the engines.
	 * 
	 * @param engines		engines to be combined
	 * @return				the combined Isp of the engines
	 */
	protected static double calculateIsp(Thrustable... engines) {
		double numerator = 0;
		double denominator = 0;
		for (Thrustable engine : engines) {
			numerator += engine.getThrust();
			denominator += engine.getThrust() / engine.getIsp();
		}
		return numerator / denominator;
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
	public double getIsp() {
		return isp;
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
