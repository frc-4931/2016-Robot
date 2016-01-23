package org.frc4931.robot.commands.portcullis;

import org.frc4931.robot.Portcullis;

public class Lift extends org.strongback.command.Command{
	private Portcullis portcullis;
	
	public Lift(Portcullis portcullis){
		this.portcullis = portcullis;
	}
	
	@Override
	public boolean execute() {
		portcullis.lift();
		return true;
	}
}
