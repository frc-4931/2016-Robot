package org.frc4931.robot.commands.grapple;

import org.frc4931.robot.Grapple;

public class OpenAndClose extends org.strongback.command.CommandGroup{
	public static final double openTime = 0.0; // in seconds
	
	public OpenAndClose(Grapple grapple){
		Open open = new Open(grapple);
		this.pause(openTime);
		Close close = new Close(grapple);
	}
}
