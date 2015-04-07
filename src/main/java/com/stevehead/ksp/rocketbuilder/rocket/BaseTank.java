package com.stevehead.ksp.rocketbuilder.rocket;

import java.util.ArrayList;
import java.util.List;

import com.stevehead.ksp.rocketbuilder.interfaces.Expendable;

/**
 * Any object with expendable propellants.
 * 
 * @author Steve Johnson
 *
 */
public abstract class BaseTank extends BaseComponent implements Expendable {
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
	 * Type of tank.
	 */
	protected final Type type;
	
	/**
	 * Types of fuel tanks.
	 * 
	 * @author Steve Johnson
	 */
	protected enum Type {
		LIQUID_FUEL_AND_OXIDIZER,
		MONOPROPELLANT,
		SOLID_FUEL,
		LIQUID_FUEL,
		XENON_GAS,
		NONE,
		UNKNOWN
	}
	
	/**
	 * @param dryMass			the dry mass in kg
	 * @param mass				the total mass in kg
	 * @param propellants		the propellants used
	 */
	protected BaseTank(double dryMass, double mass, Propellant... propellants) {
		super(mass);
		this.dryMass = dryMass;
		this.propellants = determinePropellants(propellants);
		this.type = determineTankType(this.propellants);
	}
	
	/**
	 * @param mass				the mass in kg
	 * @param propellants		the propellants used
	 */
	protected BaseTank(double mass, Propellant... propellants) {
		this(mass, mass, propellants);
	}
	
	/**
	 * @return		the tank type
	 */
	public Type getType() {
		return type;
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
	
	/**
	 * Returns the combined dry mass of the expendable objects.
	 * 
	 * @param expendables		the objects to combine their dry mass
	 * @return					the combined dry mass in kg
	 */
	protected static double calculateDryMass(Expendable... expendables) {
		double dryMass = 0;
		for (Expendable expendableObject : expendables) {
			dryMass += expendableObject.getDryMass();
		}
		return dryMass;
	}
	
	/**
	 * Returns the combined types of propellants used.
	 * 
	 * @param expendables		the objects to combine their dry mass
	 * @return					the combined dry mass in kg
	 */
	protected static Propellant[] combinePropellants(Expendable... expendables) {
		List<Propellant> propellants = new ArrayList<Propellant>();
		for (Expendable expendableObject : expendables) {
			for (Propellant propellant : expendableObject.getPropellants()) {
				if (!propellants.contains(propellant)) {
					propellants.add(propellant);
				}
			}
		}
		return propellants.toArray(new Propellant[propellants.size()]);
	}
	
	/**
	 * Helper method that determines the tank type from the propellants used.
	 * Full functionality may be added gradually.
	 * 
	 * @param propellants		the propellants used
	 * @return					the type of tank
	 */
	protected static Type determineTankType(Propellant... propellants) {
		if (propellants.length == 0) return Type.NONE;
		if (propellants.length == 1) {
			switch (propellants[0]) {
			case LIQUID_FUEL:
				return Type.LIQUID_FUEL;
			case MONOPROPELLANT:
				return Type.MONOPROPELLANT;
			case XENON_GAS:
				return Type.XENON_GAS;
			case SOLID_FUEL:
				return Type.SOLID_FUEL;
			default:
				return Type.UNKNOWN;
			}
		}
		if (propellants.length > 2) return Type.UNKNOWN;
		
		boolean usingLF = false;
		boolean usingOX = false;
		
		for (Propellant propellant : propellants) {
			switch (propellant) {
			case LIQUID_FUEL:
				usingLF = true;
				break;
			case OXIDIZER:
				usingOX = true;
				break;
			default:
				break;
			}
		}
		
		if (usingLF && usingOX) {
			return Type.LIQUID_FUEL_AND_OXIDIZER;
		}
		
		return Type.UNKNOWN;
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
