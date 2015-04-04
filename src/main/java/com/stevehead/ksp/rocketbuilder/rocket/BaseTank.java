package com.stevehead.ksp.rocketbuilder.rocket;

import com.stevehead.ksp.rocketbuilder.interfaces.Expendable;
import com.stevehead.ksp.rocketbuilder.interfaces.PropellantRequired;

/**
 * Any object with expendable propellants.
 * 
 * @author Steve Johnson
 *
 */
public abstract class BaseTank extends BaseComponent implements Expendable, PropellantRequired {
	/**
	 * Default propellants when no propellants are given in constructor.
	 */
	protected static final Propellant[] DEFAULT_PROPELLANTS = {Propellant.LIQUID_FUEL, Propellant.OXIDIZER};
	
	/**
	 * The mass after all propellant is used.
	 */
	protected final double dryMass;
	
	/**
	 * The propellants used.
	 */
	protected final Propellant[] propellants;
	
	/**
	 * @param dryMass			the dry mass in kg
	 * @param mass				the total mass in kg
	 * @param propellants		the propellants used
	 */
	protected BaseTank(double dryMass, double mass, Propellant... propellants) {
		super(mass);
		this.dryMass = dryMass;
		this.propellants = determinePropellants(propellants);
	}
	
	/**
	 * @param mass				the mass in kg
	 * @param propellants		the propellants used
	 */
	protected BaseTank(double mass, Propellant... propellants) {
		this(mass, mass, propellants);
	}
	
	/**
	 * Returns default propellents if no propellants are provided.
	 * 
	 * @param propellants	input propellants
	 * @return				the determined propellants
	 */
	protected static Propellant[] determinePropellants(Propellant... propellants) {
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
	public Propellant[] getPropellants() {
		return propellants;
	}
}
