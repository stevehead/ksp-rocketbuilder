package com.stevehead.ksp.rocketbuilder.rocket;

import java.util.ArrayList;
import java.util.Arrays;

import com.stevehead.ksp.rocketbuilder.game.Mod;
import com.stevehead.ksp.rocketbuilder.interfaces.Moddable;
import com.stevehead.ksp.rocketbuilder.interfaces.Nameable;
import com.stevehead.ksp.rocketbuilder.interfaces.Thrustable;
import com.stevehead.ksp.rocketbuilder.interfaces.Tweakscalable;

public class Engine extends BaseThruster implements Moddable, Nameable, Tweakscalable {
	/**
	 * The scalar that is used to scale ISP. If the mod Kerbal Isp Difficulty
	 * Scaler (KIDS) is used in game, need to set this value to that.
	 */
	private static double ispScaler = 1;
	
	/**
	 * The string format used for its toString method. 
	 */
	private static final String TO_STRING_FORMAT = "Engine: %s";
	
	/**
	 * The proper display name of the engine.
	 */
	protected final String name;
	
	/**
	 * The game mod the engine is found in.
	 */
	protected final Mod mod;
	
	/**
	 * The diameter of the engine in meters.
	 */
	protected final double size;
	
	/**
	 * @param name			the display name
	 * @param mod			the KSP mod
	 * @param dryMass		the dry mass in kg
	 * @param mass			the mass in kg
	 * @param thrust		the thrust in N
	 * @param isp			the Isp in seconds
	 * @param size			the diameter in meters
	 * @param propellants	the propellants used
	 */
	public Engine(String name, Mod mod, double dryMass, double mass, double thrust, double isp, double size, Propellant... propellants) {
		super(dryMass, mass, thrust, isp, propellants);
		this.name = name;
		this.mod = mod;
		this.size = size;
	}
	
	/**
	 * @param name			the display name
	 * @param mod			the KSP mod
	 * @param mass			the mass in kg
	 * @param thrust		the thrust in N
	 * @param isp			the Isp in seconds
	 * @param size			the diameter in meters
	 * @param propellants	the propellants used
	 */
	public Engine(String name, Mod mod, double mass, double thrust, double isp, double size, Propellant... propellants) {
		this(name, mod, mass, mass, thrust, isp, size, propellants);
	}
	
	/**
	 * Searches and returns an array of engines from a particular mod.
	 * 
	 * @param mod		the mod in question
	 * @return			array of engines
	 */
	public static Engine[] getEngines(Mod... mods) {
		ArrayList<Engine> engines = new ArrayList<Engine>();
		
		for (Engine.PreDefined engine : Engine.PreDefined.values()) {
			if (Arrays.asList(mods).contains(engine.getMod())) {
				engines.add(engine.toEngine());
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
		
		for (Engine.PreDefined engine : Engine.PreDefined.values()) {
			for (double size : sizes) {
				if (size == engine.getSize()) {
					engines.add(engine.toEngine());
				}
			}
		}
		
		return engines.toArray(new Engine[engines.size()]);
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public Mod getMod() {
		return mod;
	}
	
	@Override
	public double getSize() {
		return size;
	}
	
	@Override
	public String toString() {
		return String.format(TO_STRING_FORMAT, name);
	}
	
	@Override
	public Engine tweakScale(double scale) {
		// If the scale is the same, then no point in re-creating the object.
		if (scale != getSize())
		{
			double ratio = scale / getSize();
			double dryMass = getDryMass() * Math.pow(ratio, TWEAKSCALE_MASS_EXPONENT);
			double mass = getMass() * Math.pow(ratio, TWEAKSCALE_MASS_EXPONENT);
			double thrust = getThrust() * Math.pow(ratio, TWEAKSCALE_THRUST_EXPONENT);
			return new Engine(getName() + " (TweakScale " + scale + "m)", getMod(), dryMass, mass, thrust, getIsp(), scale, getPropellants());
		}
		return this;
	}
	
	@Override
	public double getIsp() {
		return getIspScaler() * isp;
	}
	
	/**
	 * Gets the current ISP Scaler.
	 * 
	 * @return		the ISP scaler
	 */
	public final static double getIspScaler() {
		return Engine.ispScaler;
	}
	
	/**
	 * Sets the ISP Scaler.
	 * 
	 * @param ispScaler		the ISP scaler
	 */
	public final static void setIspScaler(double ispScaler) {
		Engine.ispScaler = ispScaler;
	}
	
	/**
	 * PreDefined is a set of pre-defined engines from stock and mods.
	 * 
	 * @author Steve Johnson
	 */
	public enum PreDefined implements Moddable, Nameable, Thrustable, Tweakscalable {
		// Stock Engines
		ROCKOMAX_48_7S								("Rockomax 48-7S", Mod.STOCK, 100, 30000, 350, 0.625),
		LV_909_LIQUID_FUEL_ENGINE					("LV-909 Liquid Fuel Engine", Mod.STOCK, 500, 50000, 390, 1.25),
		LV_T30_LIQUID_FUEL_ENGINE					("LV-T30 Liquid Fuel Engine", Mod.STOCK, 1250, 215000, 370, 1.25),
		LV_T45_LIQUID_FUEL_ENGINE					("LV-T45 Liquid Fuel Engine", Mod.STOCK, 1500, 200000, 370, 1.25),
		ROCKOMAX_POODLE_LIQUID_ENGINE				("Rockomax \"Poodle\" Liquid Engine", Mod.STOCK, 2000, 220000, 390, 2.5),
		ROCKOMAX_SKIPPER_LIQUID_ENGINE				("Rockomax \"Skipper\" Liquid Engine", Mod.STOCK, 3000, 650000, 370, 2.5),
		ROCKOMAX_MAINSAIL_LIQUID_ENGINE				("Rockomax \"Mainsail\" Liquid Engine", Mod.STOCK, 6000, 1500000, 360, 2.5),
		LFB_KR_1X2									("LFB KR-1x2", Mod.STOCK, 10000, 42000, 2000000, 380, 2500),
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
		THE_MICRO_MOTHER							("The Micro Mother", Mod.NOVAPUNCH, 4500, 1000000, 370, 2.5);
		
		/**
		 * The engine object.
		 */
		private final Engine engine;
		
		/**
		 * @param name			the display name
		 * @param mod			the KSP mod
		 * @param dryMass		the dry mass in kg
		 * @param mass			the mass in kg
		 * @param thrust		the thrust in N
		 * @param isp			the Isp in seconds
		 * @param size			the diameter in meters
		 * @param propellants	the propellants used
		 */
		PreDefined(String name, Mod mod, double dryMass, double mass, double thrust, double isp, double size, Propellant... propellants) {
			this.engine = new Engine(name, mod, dryMass, mass, thrust, isp, size, propellants);
		}
		
		/**
		 * @param name			the display name
		 * @param mod			the KSP mod
		 * @param mass			the mass in kg
		 * @param thrust		the thrust in N
		 * @param isp			the Isp in seconds
		 * @param size			the diameter in meters
		 * @param propellants	the propellants used
		 */
		PreDefined(String name, Mod mod, double mass, double thrust, double isp, double size, Propellant... propellants) {
			this(name, mod, mass, mass, thrust, isp, size, propellants);
		}
		
		/**
		 * Returns the engine object. Useful when you need an explicit Engine
		 * object.
		 * 
		 * @return		the engine object
		 */
		public Engine toEngine() {
			return engine;
		}
		
		@Override
		public Engine tweakScale(double scale) {
			return engine.tweakScale(scale);
		}

		@Override
		public double getDryMass() {
			return engine.getDryMass();
		}

		@Override
		public double getMass() {
			return engine.getMass();
		}

		@Override
		public Propellant[] getPropellants() {
			return engine.getPropellants();
		}

		@Override
		public double getDeltaV() {
			return engine.getDeltaV();
		}

		@Override
		public double getIsp() {
			return engine.getIsp();
		}

		@Override
		public double getMinTWR() {
			return engine.getMinTWR();
		}

		@Override
		public double getMaxTWR() {
			return engine.getMaxTWR();
		}

		@Override
		public double getThrust() {
			return engine.getThrust();
		}
		
		@Override
		public String toString() {
			return engine.toString();
		}

		@Override
		public double getSize() {
			return engine.getSize();
		}

		@Override
		public String getName() {
			return engine.getName();
		}

		@Override
		public Mod getMod() {
			return engine.getMod();
		}
		
		@Override
		public Type getType() {
			return engine.getType();
		}
	}
}
