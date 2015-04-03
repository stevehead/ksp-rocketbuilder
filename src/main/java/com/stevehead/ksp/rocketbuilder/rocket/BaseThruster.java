package com.stevehead.ksp.rocketbuilder.rocket;

import com.stevehead.ksp.rocketbuilder.interfaces.Thrustable;

/**
 * BaseThruster is any object with mass that can thrust.
 * 
 * @author Steve Johnson
 *
 */
public abstract class BaseThruster extends BaseComponent implements Thrustable {
	/**
	 * Default propellants when no propellants are given in constructor.
	 */
	protected static final Propellant[] DEFAULT_PROPELLANTS = {Propellant.LIQUID_FUEL, Propellant.OXIDIZER};
	
	/**
	 * The mass after all propellant is used.
	 */
	protected double dryMass;
	
	/**
	 * The thrust in Newtons.
	 */
	protected double thrust;
	
	/**
	 * The specific impulse in seconds.
	 */
	protected double isp;
	
	/**
	 * The minumum thrust-to-weight ratio.
	 */
	protected double minTWR; 
	
	/**
	 * The maximum thrust-to-weight ratio.
	 */
	protected double maxTWR;
	
	/**
	 * The propellants this engine uses.
	 */
	protected Propellant[] propellants;
	
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
	
	/**
	 * Returns default propellents if no propellants are provided.
	 * 
	 * @param propellants	input propellants
	 * @return				the determined propellants
	 */
	protected Propellant[] determinePropellants(Propellant... propellants) {
		if (propellants.length > 0) {
			return propellants;
		} else {
			return DEFAULT_PROPELLANTS;
		}
	}
	
	@Override
	public final double getDryMass() {
		return dryMass;
	}
	
	@Override
	public final Propellant[] getPropellants() {
		return propellants;
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
