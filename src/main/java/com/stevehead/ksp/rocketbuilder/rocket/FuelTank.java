package com.stevehead.ksp.rocketbuilder.rocket;

public class FuelTank extends BaseTank {
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
		LIQUID_FUEL_AND_OXIDIZER, MONOPROPELLANT, NONE, AUTO
	}
	
	/**
	 * @param dryMass		dry mass in kg
	 * @param totalMass		total mass in kg
	 * @param type			type of tank (can be auto determined)
	 * @param propellants	propellants used
	 */
	protected FuelTank(double dryMass, double totalMass, Type type, Propellant... propellants) {
		super(dryMass, totalMass, propellants);
		if (type == Type.AUTO) {
			this.type = determineTankType(propellants);
		} else {
			this.type = type;
		}
	}
	
	/**
	 * @param dryMass		dry mass in kg
	 * @param totalMass		total mass in kg
	 * @param propellants	propellants used
	 */
	public FuelTank(double dryMass, double totalMass, Propellant... propellants) {
		this(dryMass, totalMass, Type.AUTO, propellants);
	}
	
	/**
	 * @param dryMass		dry mass in kg
	 * @param totalMass		total mass in kg
	 */
	public FuelTank(double dryMass, double totalMass) {
		this(dryMass, totalMass, Type.AUTO, DEFAULT_PROPELLANTS);
	}
	
	/**
	 * @return		the tank type
	 */
	public Type getType() {
		return type;
	}
	
	/**
	 * Helper method that determines the tank type from the propellants used.
	 * Full functionality may be added gradually.
	 * 
	 * @param propellants		the propellants used
	 * @return					the type of tank
	 */
	private static Type determineTankType(Propellant... propellants) {
		boolean usingLF = false;
		boolean usingOX = false;
		boolean usingMP = false;
		
		for (Propellant propellant : propellants) {
			switch (propellant) {
			case LIQUID_FUEL:
				usingLF = true;
				break;
			case OXIDIZER:
				usingOX = true;
				break;
			case MONOPROPELLANT:
				usingMP = true;
				break;
			default:
				throw new IllegalArgumentException("The provided propellant is currently not supported" + propellant + ".");
			}
		}
		
		// Monopropellant can only be used by itself.
		if (usingMP && (usingLF || usingOX)) {
			throw new IllegalArgumentException("Invalid propellant combination.");
		}
		
		// Liquid fuel requires oxidizer.
		if (usingLF && usingOX) {
			return Type.LIQUID_FUEL_AND_OXIDIZER;
		}
		
		if (usingMP) {
			return Type.MONOPROPELLANT;
		}
		
		throw new IllegalArgumentException("Invalid propellant combination.");
	}
}
