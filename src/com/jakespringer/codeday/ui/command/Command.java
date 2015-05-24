package com.jakespringer.codeday.ui.command;

import com.jakespringer.codeday.netinterface.NetworkSystem;
import com.jakespringer.codeday.ui.CommandConsole;
import com.jakespringer.engine.core.Main;

public class Command {
	protected String name;
	protected String[] arguments;
	
	public Command(String n, String...args) {
		name = n;
		arguments = args;
	}
	
	public int getArgumentNumber() {
		return arguments.length;
	}
	
	public String getName() {
		return name;
	}
	
	public String[] getArguments() {
		return arguments;
	}
	
	public String getArgument(int index) {
		return arguments[index];
	}
	
	public void execute() {
		String ip = "localhost";
		int port = 1235;
		if (name.equalsIgnoreCase("connect")) {
			if (arguments.length >= 1) ip = arguments[0];
			if (arguments.length >= 2) try {
				port = Integer.parseInt(arguments[1]);
			} catch (NumberFormatException e) {
				port = 1235;
			}
			
			CommandConsole.println("Connecting to " + ip + ":" + port);
			Main.gameManager.getSystem(NetworkSystem.class).disconnect();
			Main.gameManager.getSystem(NetworkSystem.class).connect(ip, port);
		}
	}
}
