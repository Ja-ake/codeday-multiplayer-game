package com.jakespringer.codeday.jake.networking;

import java.io.IOException;

public class Client {
	public static void main(String[] args) throws IOException {
		ClientServerConnection c = new ClientServerConnection("127.0.0.1", 1337);
		c.setDaemon(false);
		c.connect();
		c.start();
	}
}
