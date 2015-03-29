package com.stevehead.ksp.rocketbuilder.parts;

import com.stevehead.ksp.rocketbuilder.parts.Propellant;
import com.stevehead.ksp.rocketbuilder.interfaces.Expendable;

public class Tank implements Expendable {
	
	/**
	 * Current mass in kg.
	 */
	private double mass;
	
	/**
	 * Dry mass in kg.
	 */
	private final double dryMass;
	
	/**
	 * Total mass with propellant in kg.
	 */
	private final double totalMass;
	
	/**
	 * Propellants used in the tank.
	 */
	private final Propellant[] propellants;
	
	/**
	 * Type of tank.
	 */
	private final Type type;
	
	/**
	 * Types of tanks.
	 * 
	 * @author Steve Johnson
	 */
	private enum Type {
		LIQUID_FUEL_AND_OXIDIZER, MONOPROPELLANT, NONE, AUTO
	}
	
	/**
	 * Internal-use only constructor.
	 * 
	 * No-argument constructor creates a tank of nothing, with zero mass.
	 */
	protected Tank() {
		this(0, 0, Type.NONE);
	}
	
	/**
	 * Primary constructor used publicly. It is preferable to have the class
	 * auto determine the type from propellants.
	 * 
	 * @param dryMass		dry mass in kg
	 * @param totalMass		total mass in kg
	 * @param propellants	propellants used
	 */
	public Tank(double dryMass, double totalMass, Propellant... propellants) {
		this(dryMass, totalMass, Type.AUTO);
	}
	
	/**
	 * Internal-use only constructor.
	 * 
	 * @param dryMass		dry mass in kg
	 * @param totalMass		total mass in kg
	 * @param type			type of tank (can be auto determined)
	 * @param propellants	propellants used
	 */
	protected Tank(double dryMass, double totalMass, Type type, Propellant... propellants) {
		this.dryMass = dryMass;
		this.totalMass = totalMass;
		this.mass = totalMass;
		this.propellants = propellants;
		
		if (type == Type.AUTO) {
			this.type = determineTankType(propellants);
		} else {
			this.type = type;
		}
	}
	
	/**
	 * @return		dry mass in kg
	 */
	public double getDryMass() {
		return dryMass;
	}
	
	/**
	 * @return		total mass in kg
	 */
	public double getTotalMass() {
		return totalMass;
	}
	
	/**
	 * @return		current mass in kg
	 */
	public double getMass() {
		return mass;
	}
	
	/**
	 * @param mass		the new current mass in kg
	 */
	public void setMass(double mass) {
		mass = Math.max(mass, getDryMass());
		mass = Math.min(mass, getTotalMass());
		this.mass = mass;
	}
	
	/**
	 * @param mass		the change in mass in kg
	 */
	public void changeMass(double massChange) {
		double mass = this.mass + massChange;
		setMass(mass);
	}
	
	/**
	 * Helper method that determines the tank type from the propellants used.
	 * Full functionality may be added gradually.
	 * 
	 * @param propellants		the propellants used
	 * @return					the type of tank
	 */
	private static Type determineTankType(Propellant[] propellants) {
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
	
	/**
	 * Builder class that creates a procedural tank.
	 * 
	 * @author Steve Johnson
	 */
	public static class ProceduralBuilder {
		/**
		 * The dry-to-wet mass ratio of a LX/OX procedural tank.
		 */
		public static final double LF_OX_MASS_RATIO = 1.0 / 9.0;
		
		/**
		 * The dry-to-wet mass ratio of a monopropellant procedural tank.
		 */
		public static final double MP_MASS_RATIO = 1.0 / 5.0;
		
		/**
		 * Dry mass in kg.
		 */
		private final double dryMass;
		
		/**
		 * Total mass in kg.
		 */
		private final double totalMass;
		
		/**
		 * Propellants used.
		 */
		private final Propellant[] propellants;
		
		/**
		 * Tank type.
		 */
		private final Type type;
		
		/**
		 * @param totalMass		total mass in kg
		 * @param propellants	propellants used
		 */
		public ProceduralBuilder(double totalMass, Propellant... propellants) {
			this(totalMass, Type.AUTO, propellants);
		}
		
		/**
		 * For internal-use only.
		 * 
		 * @param totalMass		total mass in kg
		 * @param type			type of tank
		 * @param propellants	propellants used
		 */
		private ProceduralBuilder(double totalMass, Type type, Propellant... propellants) {
			this.totalMass = totalMass;
			this.propellants = propellants;
			
			if (type == Type.AUTO) {
				this.type = determineTankType(propellants);
			} else {
				this.type = type;
			}
			
			this.dryMass = calculateDryMass(this.totalMass, this.type);
		}
		
		/**
		 * Creates the proper tank object.
		 * 
		 * @return			the tank from builder
		 */
		public Tank build() {
			return new Tank(dryMass, totalMass, type, propellants);
		}
		
		/**
		 * Calculates the dry mass based on the tank type.
		 * 
		 * @param totalMass		total mass in kg
		 * @param type			type of tank
		 * @return				dry mass in kg
		 */
		private static double calculateDryMass(double totalMass, Type type) {
			switch (type) {
			case LIQUID_FUEL_AND_OXIDIZER:
				return LF_OX_MASS_RATIO * totalMass;
			case MONOPROPELLANT:
				return MP_MASS_RATIO * totalMass;
			default:
				throw new IllegalArgumentException("The provided tankt type is currently not supported" + type + ".");
			}
		}
	}
}
