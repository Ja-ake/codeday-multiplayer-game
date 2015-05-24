package com.jakespringer.codeday.ui.command;

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
}
