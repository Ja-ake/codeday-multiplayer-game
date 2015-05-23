package com.jakespringer.codeday.jake.networking;

import java.util.List;

public class CommandHandler {
	List<ServerClientConnection> connections;
	
	public CommandHandler(List<ServerClientConnection> connects) {
		connections = connects;
	}
	
	public void handleCommand(String command, ServerClientConnection cc) {
		if (command.startsWith("!say")) {
			String arguments = command.substring(4, command.length());
			for (ServerClientConnection connection : connections) {
				/*if (!connection.equals(cc)) */connection.send(cc.alias + ": " + arguments);
			}
			return;
		}
		
		if (command.startsWith("!help")) {
			cc.send(  "   [ Welcome to the help menu, page 1! ]\n"
					+ " To use a command, type !server !<command>\n"
					+ " Avaliable commands:\n"
					+ " !help  : Displays this help menu. Usage:\n"
					+ " !help <?page>\n"
					+ " !say   : Displays a server wide message that can\n"
					+ " be read by anyone.\n"
					+ "   [ There are no more help pages ]");
			return;
		}
		
		cc.send("Invalid command, use !help to list all commands.");
	}
}
