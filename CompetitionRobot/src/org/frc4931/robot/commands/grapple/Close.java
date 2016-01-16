package org.frc4931.robot.commands.grapple;

import org.frc4931.robot.Grapple;

public class Close extends org.strongback.command.Command {
	private Grapple grapple;
	
	public Close(Grapple grapple){
		this.grapple = grapple;
	}
	
	@Override
	public boolean execute() {
		grapple.close();
		return false;
	}
}
