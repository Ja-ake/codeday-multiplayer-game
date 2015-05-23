package com.jakespringer.codeday.jake.networking;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Server {
	protected List<ServerClientConnection> connections;
	protected CommandHandler handler;
	
	public Server() {
		connections = Collections.synchronizedList(new LinkedList<ServerClientConnection>());
		handler = new CommandHandler(connections);
	}
	
	public void run() throws IOException, InterruptedException {
		ServerSocket listener = new ServerSocket(1337);
		try {
			Thread handle = new Thread() {
				@Override
				public void run() {
					while (true)
						try {
							synchronized (connections) {
								for (ServerClientConnection connection : connections) {
									byte[] input = null;
									while ((input = connection.next()) != null) {
										if (input[0] == '!') handler.handleCommand(new String(input), connection);
										else for (ServerClientConnection c : connections) {
											if (connection.equals(c)) continue;
											c.send(input);
										}
									}
								}
							}
					} catch (IOException e) {}
				}
			};
			
			handle.setDaemon(false);
			handle.start();
			
			while (true) {
				Socket socket = listener.accept();
				System.out.println("Connected: " + socket.getInetAddress().getHostAddress());
				ServerClientConnection newClient = new ServerClientConnection(socket.getInetAddress().getHostAddress(), socket.getPort(), socket);
				newClient.setDaemon(true);
				newClient.start();
				synchronized (connections) {
					connections.add(newClient);
				}
			}
		} finally {
			listener.close();
		}
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		final Server server = new Server();
		server.run();
	}
}
