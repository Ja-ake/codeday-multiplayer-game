package com.jakespringer.codeday.ui;

import org.lwjgl.input.Keyboard;

import com.jakespringer.codeday.ui.command.Command;
import com.jakespringer.engine.core.AbstractEntity;
import com.jakespringer.engine.core.AbstractSystem;
import com.jakespringer.engine.core.Keys;

public class CommandInputSystem extends AbstractSystem {

	private CommandInputComponent cic;
	
	public CommandInputSystem(CommandInputComponent a) {
		cic = a;
		final CommandInputSystem thus = this;

	}

	@Override
	public void update() {
		if (Keys.isPressed(Keyboard.KEY_GRAVE))
			cic.visible = !cic.visible;
		if (!cic.visible)
			return;

		if (Keys.isPressed(Keyboard.KEY_LSHIFT)) {
			cic.shift = true;
		}
		if (Keys.isPressed(Keyboard.KEY_RSHIFT)) {
			cic.shift = true;
		}
		if (Keys.isPressed(Keyboard.KEY_BACK)) {
			if (cic.current.length() > 0)
				cic.current = cic.current.substring(0, cic.current.length() - 1);
		}
		if (Keys.isPressed(Keyboard.KEY_RETURN)) {
			if (cic.current == "")
				cic.history.add(cic.current);
			Command cmd = parseCommand(cic.current);
			if (cmd == null)
				Console.p("Invalid command.");
			cic.current = "";
		}

		for (int key : Keys.pressed) {
			if (Keyboard.getKeyName(key).equals("SPACE"))
				cic.current = cic.current + " ";
			else if ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890".contains(Keyboard.getKeyName(key))) {
				if (cic.shift) {
					if (Keyboard.getKeyName(key).equals("1"))
						cic.current = cic.current + "!";
					else if (Keyboard.getKeyName(key).equals("2"))
						cic.current = cic.current + "@";
					else if (Keyboard.getKeyName(key).equals("3"))
						cic.current = cic.current + "#";
					else if (Keyboard.getKeyName(key).equals("4"))
						cic.current = cic.current + "$";
					else if (Keyboard.getKeyName(key).equals("5"))
						cic.current = cic.current + "%";
					else if (Keyboard.getKeyName(key).equals("6"))
						cic.current = cic.current + "^";
					else if (Keyboard.getKeyName(key).equals("7"))
						cic.current = cic.current + "&";
					else if (Keyboard.getKeyName(key).equals("8"))
						cic.current = cic.current + "*";
					else if (Keyboard.getKeyName(key).equals("9"))
						cic.current = cic.current + "(";
					else if (Keyboard.getKeyName(key).equals("0"))
						cic.current = cic.current + ")";
					else
						cic.current = cic.current + Keyboard.getKeyName(key).toUpperCase();
				} else
					cic.current = cic.current + Keyboard.getKeyName(key).toLowerCase();
			} else if (cic.shift) {
				if (Keyboard.KEY_MINUS == key)
					cic.current = cic.current + "_";
				else if (Keyboard.KEY_EQUALS == key)
					cic.current = cic.current + "+";
				else if (Keyboard.KEY_LBRACKET == key)
					cic.current = cic.current + "{";
				else if (Keyboard.KEY_RBRACKET == key)
					cic.current = cic.current + "}";
				else if (Keyboard.KEY_BACKSLASH == key)
					cic.current = cic.current + "|";
				else if (Keyboard.KEY_SEMICOLON == key)
					cic.current = cic.current + ":";
				else if (Keyboard.KEY_APOSTROPHE == key)
					cic.current = cic.current + "\"";
				else if (Keyboard.KEY_COMMA == key)
					cic.current = cic.current + "<";
				else if (Keyboard.KEY_PERIOD == key)
					cic.current = cic.current + ">";
				else if (Keyboard.KEY_SLASH == key)
					cic.current = cic.current + "?";
			} else {
				if (Keyboard.KEY_MINUS == key)
					cic.current = cic.current + "-";
				else if (Keyboard.KEY_EQUALS == key)
					cic.current = cic.current + "=";
				else if (Keyboard.KEY_LBRACKET == key)
					cic.current = cic.current + "[";
				else if (Keyboard.KEY_RBRACKET == key)
					cic.current = cic.current + "]";
				else if (Keyboard.KEY_BACKSLASH == key)
					cic.current = cic.current + "\\";
				else if (Keyboard.KEY_SEMICOLON == key)
					cic.current = cic.current + ";";
				else if (Keyboard.KEY_APOSTROPHE == key)
					cic.current = cic.current + "\'";
				else if (Keyboard.KEY_COMMA == key)
					cic.current = cic.current + ",";
				else if (Keyboard.KEY_PERIOD == key)
					cic.current = cic.current + ".";
				else if (Keyboard.KEY_SLASH == key)
					cic.current = cic.current + "/";
			}
			if (!Keys.isPressed(Keyboard.KEY_LSHIFT)) {
				cic.shift = false;
			}
			if (!Keys.isPressed(Keyboard.KEY_RSHIFT)) {
				cic.shift = false;
			}
		}
	}
	
	private Command parseCommand(String parse) {
		if (parse.startsWith("!")) {
			String[] parsed = parse.split(" ");
			String[] args = new String[parsed.length-1];
			for (int i=1; i<parsed.length; i++) {
				args[i-1] = parsed[i];
			}
			return new Command(parsed[0].substring(1), args);
		} else return null;
	}
}
