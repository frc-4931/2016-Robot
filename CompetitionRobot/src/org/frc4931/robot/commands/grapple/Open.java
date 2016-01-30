package org.frc4931.robot.commands.grapple;

import org.frc4931.robot.Grapple;

public class Open extends org.strongback.command.Command {
	private Grapple grapple;
	
	public Open(Grapple grapple){
		this.grapple = grapple;
	}
	
	@Override
	public boolean execute() {
		grapple.open();
		return true;
	}
}
