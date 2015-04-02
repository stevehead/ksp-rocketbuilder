package com.stevehead.ksp.rocketbuilder.interfaces;

import com.stevehead.ksp.rocketbuilder.rocket.Propellant;

/**
 * PropellantRequired is an interface for objects that must contain propellants
 * as part of their mass.
 * 
 * @author Steve Johnson
 */
public interface PropellantRequired {
	/**
	 * The propellants required.
	 * 
	 * @return		propellants
	 */
	Propellant[] getPropellants();
}
