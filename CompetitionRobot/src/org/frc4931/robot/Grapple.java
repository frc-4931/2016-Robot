package org.frc4931.robot;

import org.strongback.components.Motor;
import org.strongback.components.Solenoid;

public class Grapple {
	private Motor winch; // pulls robot up after arm attaches to tower
	private Solenoid extender; // releases arm/puts it up (arm is spring loaded)
	private static final double climbSpeed = 0.0; // in percent throttle
	
	public Grapple(Motor motor, Solenoid solenoid){
		winch = motor;
		extender = solenoid;
	}
	
	/**
	 * Releases the arm so the robot can scale the tower.
	 */
	public void open(){
		extender.retract();
	}
	
	/**
	 * Moves the solenoid back in place.
	 */
	public void close(){
		extender.extend();
	}
	
	/**
	 * Makes the robot climb the tower.
	 */
	public void climb(){
		winch.setSpeed(climbSpeed);
	}
}