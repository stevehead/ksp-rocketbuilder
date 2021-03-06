package com.stevehead.ksp.rocketbuilder.interfaces;

/**
 * Thrustable is a type of object that can generate thrust. Such examples would
 * be rocket engines and solid rocket boosters. Atmospheric calculations are too
 * complex and varied, so everything is assumed to be in a vacuum.
 * 
 * @author Steve Johnson
 */
public interface Thrustable extends Expendable {
	/**
	 * The ASL surface gravity of the planet Kerbin, in m/s^2
	 */
	public static final double KERBIN_GRAVITY = 9.80977664671;
	
	/**
	 * The change in velocity that can occur.
	 * 
	 * @return		the delta-V in m/s
	 */
	double getDeltaV();
	
	/**
	 * The specific impulse of the engine(s).
	 * 
	 * @return		Isp in seconds
	 */
	double getIsp();
	
	/**
	 * The initial thrust to weight ratio.
	 * 
	 * @return		minimum TWR
	 */
	double getMinTWR();
	
	/**
	 * The thrust to weight ratio after all propellant is depleted.
	 * 
	 * @return		maximum TWR
	 */
	double getMaxTWR();
	
	/**
	 * The total thrust produced in Newtons.
	 * 
	 * @return		total thrust in N
	 */
	double getThrust();
}
