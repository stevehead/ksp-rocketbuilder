package com.stevehead.ksp.rocketbuilder.rocket;

import com.stevehead.ksp.rocketbuilder.interfaces.Thrustable;

public class EngineCluster extends BaseThruster {
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
}
