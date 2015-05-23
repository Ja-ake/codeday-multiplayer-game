package com.jakespringer.codeday.jake.networking;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ClientServerConnection extends Thread {
	private final int NUM_CONNECTION_TRIES = 4;

	protected String serverIP;
	protected int serverPort;

	protected Socket socket;
	protected BufferedOutputStream out;
	protected BufferedInputStream in;

	protected Queue<byte[]> inputQueue, outputQueue;

	public ClientServerConnection(String ip, int port) {
		serverIP = ip;
		serverPort = port;

		inputQueue = new ConcurrentLinkedQueue<byte[]>();
		outputQueue = new ConcurrentLinkedQueue<byte[]>();
	}

	public void connect() throws IOException {
		socket = new Socket(InetAddress.getByName(serverIP), serverPort);
		out = new BufferedOutputStream(socket.getOutputStream());
		in = new BufferedInputStream(socket.getInputStream());
	}

	public byte[] next() throws IOException {
		if (!inputQueue.isEmpty()) {
			byte[] ret = inputQueue.remove();
			return ret;
		} else return null;
	}

	public void send(String msg) {
		outputQueue.add((msg + "\n").getBytes());
	}
	
	public void send(byte[] msg) {
		outputQueue.add(msg);
	}

	public void close() throws IOException {
		socket.close();
		out.close();
		in.close();
	}

	public void run() {
//		Thread handle = new Thread() {
//			public void run() {
				String input = null;

				// attempt to connect
				for (int i = 1; i < NUM_CONNECTION_TRIES; i++) {
					try {
						connect();
						if (socket.isClosed())
							inputQueue.add("There was an error connecting to the server, retrying in 5000ms.".getBytes());
						else
							i = NUM_CONNECTION_TRIES;
					} catch (IOException e) {
						inputQueue.add("There was an error connecting to the server, retrying in 5000ms.".getBytes());
					}

					if (i != NUM_CONNECTION_TRIES)
						try {
							Thread.sleep(5000L);
						} catch (InterruptedException e) {
						}
				}

				// no more retries
				if (socket.isClosed())
					try {
						// connect to server
						connect();
						if (socket.isClosed()) {
							inputQueue.add("There was an error connecting to the server, exiting.".getBytes());
							return;
						}
					} catch (IOException e) {
						inputQueue.add("There was an error connecting to the server, exiting.".getBytes());
						return;
					}

				inputQueue.add("Connection to server was successful!".getBytes());

				// listen and add to message queue
				try {
					while (true) {
						if (socket.isClosed()) {
							inputQueue.add("Connection was lost, exiting.".getBytes());
							break;
						}

						while (true) {
//							input = in.readLine();

							if (input != null) {
								if (input.contains("!exit123protocol")) {
									inputQueue.add("Connection was closed with server from server request.".getBytes());
									close();
									break;
								}

								inputQueue.add(input.getBytes());
							}
						}

						// until message queue is out send them
						while (outputQueue.size() > 0) {
							out.write(outputQueue.remove());
						}
					}
				} catch (IOException e) {
					inputQueue.add("There was an error receiving from the server, closing connection.".getBytes());
					try {
						close();
					} catch (IOException e1) {
					}
				} finally {
					try {
						close();
					} catch (IOException e) {
					}
				}
//			};
//		};
		
//		handle.setDaemon(true);
//		handle.start();
	}

	@Override
	public void finalize() {
		try {
			close();
		} catch (IOException e) {}
	}
}
