package com.stevehead.ksp.rocketbuilder.rocket;

import com.stevehead.ksp.rocketbuilder.interfaces.Thrustable;

public class EngineCluster extends BaseThruster {
	/**
	 * The string format used for its toString method. 
	 */
	private static final String TO_STRING_FORMAT = "Cluster: %s";
	
	/**
	 * The engines that makes up the cluster.
	 */
	private final Thrustable[] engines;
	
	/**
	 * @param engines		the engines that make up the cluster
	 */
	public EngineCluster(Thrustable... engines) {
		super(calculateDryMass(engines), calculateMass(engines),
				calculateThrust(engines),calculateIsp(engines),
				combinePropellants(engines));
		this.engines = engines;
	}
	
	/**
	 * The engines that makes up the cluster.
	 * 
	 * @return			the engines that make up the cluster
	 */
	public Thrustable[] getEngines() {
		return engines;
	}
	
	@Override
	public String toString() {
		String output = "{";
		for (int i = 0; i < engines.length; i++) {
			if (i > 0) output += " | ";
			output += engines[i].toString();
		}
		output += "}";
		return String.format(TO_STRING_FORMAT, output);
	}
}
