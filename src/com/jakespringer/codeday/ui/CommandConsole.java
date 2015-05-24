package com.jakespringer.codeday.ui;

import java.util.List;

import com.jakespringer.engine.core.AbstractEntity;

public class CommandConsole extends AbstractEntity {
	
	private static CommandConsole one;
	
	public CommandConsole() {
		one = this;
		
		CommandInputComponent cic = new CommandInputComponent();
		
		add(cic);
		add(new CommandInputSystem(cic));
		add(new ConsoleDisplaySystem(cic));
		
		String[] startMessage = { "Welcome to the developer console!" , 
								  "Type !help to print the help menu.", 
								};
		
		List<String> history = cic.history;
		for (String msg : startMessage) history.add(msg);
	}
	
	public static void println(String s) {
		if (one != null) one.getComponent(CommandInputComponent.class).history.add(s);
	}
}
