package com.stevehead.ksp.rocketbuilder.parts;

import java.util.ArrayList;

import com.stevehead.ksp.rocketbuilder.interfaces.Thrustable;
import com.stevehead.ksp.rocketbuilder.parts.Propellant;
import com.stevehead.ksp.rocketbuilder.parts.Tank;

/**
 * Engines provide thrust and may or may not have a fuel tank. If so, it is
 * likely a booster (tank and engine combo).
 * 
 * @author Steve Johnson
 */
public enum Engine implements Thrustable {
	ROCKOMAX_48_7S					("Rockomax 48-7S", Mod.STOCK, 100, 30000, 350, 0.625),
	LV_909_LIQUID_FUEL_ENGINE		("LV-909 Liquid Fuel Engine", Mod.STOCK, 500, 50000, 390, 1.25),
	LV_T30_LIQUID_FUEL_ENGINE		("LV-T30 Liquid Fuel Engine", Mod.STOCK, 1250, 215000, 370, 1.25),
	LV_T45_LIQUID_FUEL_ENGINE		("LV-T45 Liquid Fuel Engine", Mod.STOCK, 1500, 200000, 370, 1.25),
	RAPIER_ENGINE					("R.A.P.I.E.R. Engine", Mod.STOCK, 1200, 175000, 360, 1.25),
	TOROIDAL_AEROSPACE_ROCKET		("Toroidal Aerospike Rocket", Mod.STOCK, 1500, 175000, 390, 1.25),
	LV_N_ATOMIC_ROCKET_MOTOR		("LV-N Atomic Rocket Motor", Mod.STOCK, 2250, 60000, 800, 1.25),
	ROCKOMAX_POODLE_LIQUID_ENGINE	("Rockomax \"Poodle\" Liquid Engine", Mod.STOCK, 2000, 220000, 390, 2.5),
	ROCKOMAX_SKIPPER_LIQUID_ENGINE	("Rockomax \"Skipper\" Liquid Engine", Mod.STOCK, 3000, 650000, 370, 2.5),
	ROCKOMAX_MAINSAIL_LIQUID_ENGINE	("Rockomax \"Mainsail\" Liquid Engine", Mod.STOCK, 6000, 1500000, 360, 2.5),
	LFB_KR_1X2						("LFB KR-1x2", Mod.STOCK, 2000000, 380, 2.5, new Tank(10000, 42000, Propellant.LIQUID_FUEL, Propellant.OXIDIZER)),
	KERBODYNE_KR_2L_ADVANCED_ENGINE	("Kerbodyne KR-2L Advanced Engine", Mod.STOCK, 6500, 2500000, 380, 3.75),
	S3_KS_25X4_ENGINE_CLUSTER		("S3 KS-25x4 Engine Cluster", Mod.STOCK, 9750, 3200000, 360, 3.75);
	
	/**
	 * Mod describes the set of engines from a game mod.
	 * 
	 * @author Steve Johnson
	 */
	public enum Mod {
		STOCK, KW_ROCKETRY, AIES, NOVAPUNCH, KSPX
	}
	
	/**
	 * The scalar that is used to scale ISP. If the mod Kerbal Isp Difficulty
	 * Scaler (KIDS) is used in game, need to set this value to that.
	 */
	private static double ispScaler = 1;
	
	/**
	 * The proper display name of the engine.
	 */
	private final String name;
	
	/**
	 * The game mod the engine is found in.
	 */
	private final Mod mod;
	
	/**
	 * The dry mass in kg.
	 */
	private final double dryMass;
	
	/**
	 * The total mass in kg.
	 */
	private final double totalMass;
	
	/**
	 * The thrust in Newtons.
	 */
	private final double thrust;
	
	/**
	 * The specific impulse in seconds.
	 */
	private final double isp;
	
	/**
	 * The diameter of the engine in meters.
	 */
	private final double size;
	
	/**
	 * The fuel tank booster.
	 */
	private final Tank fuelTank;
	
	/**
	 * The curent mass.
	 */
	private double mass;
	
	/**
	 * Internal-use only constructor.
	 * 
	 * @param name			the display name of the engine
	 * @param mod			the game mod the engine is in
	 * @param dryMass		the dry mass in kg
	 * @param totalMass		the total mass in kg
	 * @param thrust		the thrust in Newtons
	 * @param isp			the specific impulse in seconds
	 * @param size			the diameter of the engine in meters
	 * @param fuelTank		the fuel tank booster
	 */
	private Engine(String name, Mod mod, double dryMass, double totalMass, double thrust, double isp, double size, Tank fuelTank) {
		this.name = name;
		this.mod = mod;
		this.dryMass = dryMass;
		this.totalMass = totalMass;
		this.thrust = thrust;
		this.isp = isp;
		this.size = size;
		this.fuelTank = fuelTank;
		this.mass = totalMass;
	}
	
	/**
	 * @param name			the display name of the engine
	 * @param mod			the game mod the engine is in
	 * @param mass			the engine's mass in kg
	 * @param thrust		the thrust in Newtons
	 * @param isp			the specific impulse in seconds
	 * @param fuelTank		the fuel tank booster
	 */
	Engine(String name, Mod mod, double mass, double thrust, double isp, double size) {
		this(name, mod, mass, mass, thrust, isp, size, null);
	}
	
	/**
	 * @param name			the display name of the engine
	 * @param mod			the game mod the engine is in
	 * @param thrust		the thrust in Newtons
	 * @param isp			the specific impulse in seconds
	 * @param size			the diameter of the engine in meters
	 * @param fuelTank		the fuel tank booster
	 */
	Engine(String name, Mod mod, double thrust, double isp, double size, Tank fuelTank) {
		this(name, mod, fuelTank.getDryMass(), fuelTank.getTotalMass(), thrust, isp, size, fuelTank);
	}
	
	/**
	 * Determines if the tank has a fuel tank.
	 * 
	 * @return		<code>true</code> if the engine has a fuel tank;
	 * 				<code>false</code> otherwise.
	 */
	public boolean hasFuelTank() {
		if (fuelTank != null) return true;
		return false;
	}
	
	/**
	 * @return		the KSP game mod
	 */
	public Mod getMod() {
		return mod;
	}
	
	/**
	 * @return		the display name
	 */
	public String getName() {
		return name();
	}
	
	/**
	 * @return		the stack diameter
	 */
	public double getSize() {
		return size;
	}
	
	/**
	 * @return		the ISP scaler
	 */
	public static double getIspScaler() {
		return ispScaler;
	}
	
	/**
	 * @param ispScaler		the new ISP scaler
	 */
	public static void setIspScaler(double ispScaler) {
		Engine.ispScaler = ispScaler;
	}
	
	/**
	 * Searches and returns an array of engines from a particular mod.
	 * 
	 * @param mod		the mod in question
	 * @return			array of engines
	 */
	public static Engine[] getEngines(Mod mod) {
		ArrayList<Engine> engines = new ArrayList<Engine>();
		
		for (Engine engine : Engine.values()) {
			if (engine.getMod() == mod) {
				engines.add(engine);
			}
		}
		
		return (Engine[]) engines.toArray();
	}
	
	/**
	 * Searches and returns an array of engines with a particular diameter.
	 * 
	 * @param size		the diameter
	 * @return			array of engines
	 */
	public static Engine[] getEngines(double size) {
		ArrayList<Engine> engines = new ArrayList<Engine>();
		
		for (Engine engine : Engine.values()) {
			if (engine.getSize() == size) {
				engines.add(engine);
			}
		}
		
		return (Engine[]) engines.toArray();
	}
	
	public double getDeltaV() {
		return isp * Math.log(getTotalMass() / getDryMass());
	}
	
	public double getIsp() {
		return isp * ispScaler;
	}
	
	public void changeMass(double massChange) {
		double mass = this.mass + massChange;
		setMass(mass);
	}
	
	public double getMass() {
		return mass;
	}
	
	public void setMass(double mass) {
		mass = Math.max(mass, getDryMass());
		mass = Math.min(mass, getTotalMass());
		this.mass = mass;
	}
	
	public double getDryMass() {
		return dryMass;
	}
	
	public double getMaxTWR() {
		return KERBIN_GRAVITY * thrust / getDryMass();
	}
	
	public double getMinTWR() {
		return KERBIN_GRAVITY * thrust / getTotalMass();
	}
	
	public double getThrust() {
		return thrust;
	}
	
	public double getTotalMass() {
		return totalMass;
	}
	
	public String toString() {
		return name;
	}
}
