package org.frc4931.robot;

import org.strongback.components.Motor;

import edu.wpi.first.wpilibj.Solenoid;

public class Grapple {
	private Motor winch; // pulls robot up after arm attaches to tower
	private Solenoid extender; // releases arm/puts it up (arm is spring loaded)
	
	public Grapple(Motor motor, Solenoid solenoid){
		winch = motor;
		extender = solenoid;
	}
	
	/**
	 * Releases the arm so the robot can scale the tower.
	 */
	public void open(){
		
	}
	
	/**
	 * Moves the solenoid back in place.
	 */
	public void close(){
		
	}
	
	/**
	 * Gets the distance between the robot and the ground.
	 * @return the distance in _.
	 */
	public double getPosition(){
		return 0;
	}
	
	/**
	 * Makes the robot climb the tower.
	 */
	public void raise(){
		
	}
}
