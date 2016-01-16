package org.frc4931.robot.commands.grapple;

import org.frc4931.robot.Grapple;
import org.strongback.command.Command;

public class OpenAndClose extends org.strongback.command.CommandGroup{
	public static final double openTime = 0.0; // in seconds
	
	public OpenAndClose(Grapple grapple){
		sequentially(new Open(grapple),Command.pause(openTime),new Close(grapple));
	}
}
