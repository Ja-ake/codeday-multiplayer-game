package com.jakespringer.codeday.jake.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class RelayServer {
	private List<Connection> connections;
	public final Object notification = new Object();
	
	public void run() throws UnknownHostException, IOException, InterruptedException {
		connections = Collections.synchronizedList(new LinkedList<Connection>());
		
		final ServerSocket listener = new ServerSocket(1225);
		
		try {
			final Thread handle2 = new Thread() {
				@Override
				public void run() {
					while (true) {
						synchronized (connections) {
							for (Connection connection : connections) {
								byte[] input = null;
								while ((input = connection.next()) != null) {
									for (Connection c : connections) {
										if (!connection.equals(c)) c.send(input);
									}
								}
							}
						}
						try {
							synchronized (notification) {
								notification.wait();
							}
						} catch (InterruptedException e) {
						}
					}
				}
			};
			
			final Thread handle = new Thread() {
				@Override
				public void run() {
					while (true) {
						Socket socket;
						try {
							socket = listener.accept();
							Connection c = new Connection();
							c.notifyOnReceive(notification);
							c.start(socket);
							System.out.println("Connected: " + socket.getInetAddress().getHostName());
							synchronized (connections) {
								connections.add(c);
							}

						} catch (IOException e1) {
							e1.printStackTrace();
						}

					}
				}
			};
			
			handle.setDaemon(true);
			handle.start();
			handle2.setDaemon(true);
			handle2.start();
			
			handle2.join();
		} finally {
			listener.close();
		}
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		RelayServer rs = new RelayServer();
		rs.run();
	}
}
