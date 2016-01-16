package org.frc4931.robot.commands.grapple;

import org.frc4931.robot.Grapple;
import org.frc4931.robot.drive.Drive;
import org.frc4931.robot.drive.DriveSystem;

public class Climb extends org.strongback.command.CommandGroup{
	public static final double driveSpeed = 0.0; // in percent throttle
	public static final double driveTime = 0.0; // in seconds
	
	public Climb(Grapple grapple, DriveSystem driveSystem){
		sequentially(new OpenAndClose(grapple),new Drive(driveSystem,driveSpeed,0.0,driveTime),new Raise(grapple) );
	}
}
