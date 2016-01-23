package org.frc4931.robot.commands.portcullis;

public class Portcullis extends org.strongback.command.CommandGroup{
	public Climb(Portcullis portcullis, DriveSystem driveSystem){
		sequentially(new Lift(driveSystem,driveSpeed,0.0,driveTime),new Climb(grapple));
	}
}
