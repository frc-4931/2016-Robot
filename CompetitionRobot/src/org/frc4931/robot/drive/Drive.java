package org.frc4931.robot.drive;

public class Drive extends org.strongback.command.Command {
	private DriveSystem driveSystem;
	private double driveSpeed; // in percent throttle
	private double turnSpeed; // in percent throttle 
	private double driveTime; // in seconds
	
	public Drive(DriveSystem driveSystem, double driveSpeedPercent, double turnSpeedPercent, double driveTimeSeconds){
		this.driveSystem = driveSystem;
		driveSpeed = driveSpeedPercent;
		turnSpeed = turnSpeedPercent;
		driveTime = driveTimeSeconds;
	}
	
	@Override
	public boolean execute() {
		driveSystem.arcade(driveSpeed, turnSpeed);
		this.pause(driveTime);
		driveSystem.stop();
		return false;
	}
}
