package com.stevehead.ksp.rocketbuilder.parts;

import java.util.ArrayList;
import java.util.Arrays;

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
	// Stock Engines
	ROCKOMAX_48_7S								("Rockomax 48-7S", Mod.STOCK, 100, 30000, 350, 0.625),
	LV_909_LIQUID_FUEL_ENGINE					("LV-909 Liquid Fuel Engine", Mod.STOCK, 500, 50000, 390, 1.25),
	LV_T30_LIQUID_FUEL_ENGINE					("LV-T30 Liquid Fuel Engine", Mod.STOCK, 1250, 215000, 370, 1.25),
	LV_T45_LIQUID_FUEL_ENGINE					("LV-T45 Liquid Fuel Engine", Mod.STOCK, 1500, 200000, 370, 1.25),
	ROCKOMAX_POODLE_LIQUID_ENGINE				("Rockomax \"Poodle\" Liquid Engine", Mod.STOCK, 2000, 220000, 390, 2.5),
	ROCKOMAX_SKIPPER_LIQUID_ENGINE				("Rockomax \"Skipper\" Liquid Engine", Mod.STOCK, 3000, 650000, 370, 2.5),
	ROCKOMAX_MAINSAIL_LIQUID_ENGINE				("Rockomax \"Mainsail\" Liquid Engine", Mod.STOCK, 6000, 1500000, 360, 2.5),
	LFB_KR_1X2									("LFB KR-1x2", Mod.STOCK, 2000000, 380, 2500, new Tank(10000, 42000)),
	KERBODYNE_KR_2L_ADVANCED_ENGINE				("Kerbodyne KR-2L Advanced Engine", Mod.STOCK, 6500, 2500000, 380, 3.75),
	S3_KS_25X4_ENGINE_CLUSTER					("S3 KS-25x4 Engine Cluster", Mod.STOCK, 9750, 3200000, 360, 3.75),
	
	// NovaPunch Engines
	THE_LITTLE_MOTHER							("\"The Little Mother\"", Mod.NOVAPUNCH, 6000, 2000000, 350, 3.75),
	_4X_900_LIQUID_FUEL_ENGINE_CLUSTER			("4X-900 Liquid Fuel Engine Cluster", Mod.NOVAPUNCH, 6500, 1850000, 345, 2.5),
	ADVANCED_HEAVY_LIFTER_ENGINE				("Advanced Heavy Lifter Engine", Mod.NOVAPUNCH, 26000, 7500000, 320, 5.0),
	BASIC_BERTHA_MINI_QUAD						("Basic Bertha Mini Quad", Mod.NOVAPUNCH, 1250, 125000, 385, 1.25),
	BEARCAT_SERIES_TWO_5X_LAUNCH_ENGINE			("Bearcat Series Two 5x Launch Engine", Mod.NOVAPUNCH, 30000, 10000000, 315, 5.0),
	BEARCAT_SERIES_TWO_TRI_NOZZLE_ROCKET_ENGINE	("Bearcat Series Two Tri-nozzle Rocket Engine", Mod.NOVAPUNCH, 9250, 2400000, 330, 3.75),
	FREYJA_LIGHT_DUTY_ROCKET_MOTOR				("Freyja Light Duty Rocket Motor", Mod.NOVAPUNCH, 160, 37500, 345, 0.625),
	K2_X_LIQUID_FUEL_ROCKET_ENGINE_1_25			("K2-X Liquid Fuel Rocket Engine (1.25m)", Mod.NOVAPUNCH, 1200, 195000, 375, 1.25),
	K2_X_LIQUID_FUEL_ROCKET_ENGINE_3_75			("K2-X Liquid Fuel Rocket Engine (3.75m)", Mod.NOVAPUNCH, 7000, 1600000, 390, 3.75),
	K2_X_LIQUID_FUEL_ROCKET_ENGINE_5_0			("K2-X Liquid Fuel Rocket Engine (5.0m)", Mod.NOVAPUNCH, 18000, 5000000, 325, 5.0),
	M50_MAIN_ROCKET_ENGINE						("M50 Main Rocket Engine", Mod.NOVAPUNCH, 6900, 1800000, 350, 2.5),
	ORBITAL_BERTHA								("Orbital Bertha", Mod.NOVAPUNCH, 3800, 475000, 405, 2.5),
	RMA_3_ORBITAL_ACHIEVEMENT_DEVICE			("RMA-3 Orbital Achievement Device", Mod.NOVAPUNCH, 650, 75000, 410, 1.25),
	SLS_125_BEARCAT_SERIES_ONE_LIQUID_ENGINE	("SLS-125 Bearcat Series One Liquid Engine", Mod.NOVAPUNCH, 1500, 240000, 360, 1.25),
	SLS_250_BEARCAT_SERIES_TWO_LIQUID_ENGINE	("SLS-250 Bearcat Series Two Liquid Engine", Mod.NOVAPUNCH, 5250, 1400000, 360, 2.5),
	TD_180_BRONCO_QUAD							("TD-180 Bronco Quad", Mod.NOVAPUNCH, 10700, 3000000, 320, 3.75),
	THE_MATRIARCH								("The Matriarch", Mod.NOVAPUNCH, 13800, 4000000, 330, 5.0),
	THE_MICRO_MOTHER							("The Micro Mother", Mod.NOVAPUNCH, 4500, 1000000, 370, 2.5)
	;
	
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
	
	private final Propellant[] propellants;
	
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
	private Engine(String name, Mod mod, double dryMass, double totalMass, double thrust, double isp, double size, Propellant... propellants) {
		this.name = name;
		this.mod = mod;
		this.dryMass = dryMass;
		this.totalMass = totalMass;
		this.thrust = thrust;
		this.isp = isp;
		this.size = size;
		this.mass = totalMass;
		this.propellants = propellants;
	}
	
	/**
	 * @param name			the display name of the engine
	 * @param mod			the game mod the engine is in
	 * @param mass			the engine's mass in kg
	 * @param thrust		the thrust in Newtons
	 * @param isp			the specific impulse in seconds
	 */
	Engine(String name, Mod mod, double mass, double thrust, double isp, double size, Propellant... propellants) {
		this(name, mod, mass, mass, thrust, isp, size, propellants);
	}
	
	Engine(String name, Mod mod, double mass, double thrust, double isp, double size) {
		this(name, mod, mass, thrust, isp, size, Propellant.LIQUID_FUEL, Propellant.OXIDIZER);
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
		this(name, mod, fuelTank.getDryMass(), fuelTank.getTotalMass(), thrust, isp, size, fuelTank.getPropellants());
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
		return name;
	}
	
	/**
	 * @return		the stack diameter
	 */
	public double getSize() {
		return size;
	}
	
	public Thrustable tweakscale(double scale) {
		if (scale != this.size)
		{
			double ratio = scale / size;
			double dryMass = this.dryMass * Math.pow(ratio, 2.5);
			double totalMass = this.totalMass * Math.pow(ratio, 2.5);
			double thrust = this.thrust * Math.pow(ratio, 2.6);
			return new TweakscaleEngine(name, mod, dryMass, totalMass, thrust, this.isp, this.size, this.propellants);
		}
		return this;
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
	public static Engine[] getEngines(Mod... mods) {
		ArrayList<Engine> engines = new ArrayList<Engine>();
		
		for (Engine engine : Engine.values()) {
			if (Arrays.asList(mods).contains(engine.getMod())) {
				engines.add(engine);
			}
		}
		
		return engines.toArray(new Engine[engines.size()]);
	}
	
	/**
	 * Searches and returns an array of engines with a particular diameter.
	 * 
	 * @param size		the diameter
	 * @return			array of engines
	 */
	public static Engine[] getEngines(double... sizes) {
		ArrayList<Engine> engines = new ArrayList<Engine>();
		
		for (Engine engine : Engine.values()) {
			for (double size : sizes) {
				if (size == engine.getSize()) {
					engines.add(engine);
				}
			}
		}
		
		return engines.toArray(new Engine[engines.size()]);
	}
	
	public double getDeltaV() {
		return getIsp() * Math.log(getTotalMass() / getDryMass()) * KERBIN_GRAVITY;
	}
	
	public double getDryMass() {
		return dryMass;
	}
	
	public double getIsp() {
		return isp * getIspScaler();
	}
	
	public void changeMass(double massChange) {
		double mass = getMass() + massChange;
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
	
	public double getMaxTWR() {
		return getThrust() / (KERBIN_GRAVITY * getDryMass());
	}
	
	public double getMinTWR() {
		return getThrust() / (KERBIN_GRAVITY * getTotalMass());
	}
	
	public Propellant[] getPropellants() {
		return propellants;
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
