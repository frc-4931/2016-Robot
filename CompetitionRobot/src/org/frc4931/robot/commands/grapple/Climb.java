package org.frc4931.robot.commands.grapple;

import org.frc4931.robot.Grapple;
import org.frc4931.robot.drive.Drive;
import org.frc4931.robot.drive.DriveSystem;
import org.strongback.command.Command;

public class Climb extends org.strongback.command.CommandGroup{
	public static final double driveSpeed = 0.0; // in percent throttle
	public static final double driveTime = 0.0; // in seconds
	
	public Climb(Grapple grapple, DriveSystem driveSystem){
		OpenAndClose open = new OpenAndClose(grapple);
		Drive drive = new Drive(driveSystem,driveSpeed,0.0,driveTime);
		Raise raise = new Raise(grapple);
	}
}
