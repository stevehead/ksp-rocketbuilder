package com.stevehead.ksp.rocketbuilder.interfaces;

/**
 * Tweakscalable is any object that can be resized by the TweakScale mod.
 * 
 * 
 * @author Steve Johnson
 */
public interface Tweakscalable {
	/**
	 * Exponents used by Tweakscale.
	 */
	public static final double MASS_EXPONENT = 2.5;
	public static final double THRUST_EXPONENT = 2.6;
	
	/**
	 * Resizes the object.
	 * 
	 * @param scale		the new scale
	 * @return			the TweakScaled object
	 */
	Object tweakScale(double scale);
}
