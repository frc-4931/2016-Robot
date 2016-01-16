package org.frc4931.robot.commands.grapple;

import org.frc4931.robot.Grapple;

public class Raise extends org.strongback.command.Command {
	private Grapple grapple;
	public static final double climbTime = 0.0; // in seconds
	
	public Raise(Grapple grapple){
		super(climbTime);
		this.grapple = grapple;
	}
	
	@Override
	public boolean execute() {
		grapple.raise();
		return true;
	}
}
