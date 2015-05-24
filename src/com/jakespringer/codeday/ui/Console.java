package com.jakespringer.codeday.ui;

import com.jakespringer.engine.core.Main;

public final class Console {
	private Console() { }
	
	public enum ErrorLevel {
		WARNING, ERROR, SEVERE
	}
	
	/**
	 * Prints out a message to the developer console (if it exists).
	 */
	public static void p(String msg) {
		CommandConsole instance = (CommandConsole) Main.gameManager.elc.getEntity(CommandConsole.class);
		if (instance != null) {
			instance.getComponent(CommandInputComponent.class).history.add(msg);
		}
	}
	
	/**
	 * Prints out a message to the developer console (if it exists), and includes a tag.
	 */
	public static void l(String f, String s) {
		p("["+f+"] "+s);
	}
	
	/**
	 * Prints an error to the developer console (if it exists).
	 */
	public static void e(ErrorLevel l, String s) {
		l(l.toString(), s);
	}
}
