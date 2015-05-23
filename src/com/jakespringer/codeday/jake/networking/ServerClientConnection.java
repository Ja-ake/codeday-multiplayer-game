package com.jakespringer.codeday.jake.networking;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ServerClientConnection extends Thread {
	protected String clientIP;
	protected String alias;
	protected int clientPort;
	
	protected Socket socket;
	protected BufferedOutputStream out;
	protected BufferedReader in;

	protected Queue<byte[]> inputQueue, outputQueue;
	protected boolean closed;
	
	public ServerClientConnection(String ip, int port, Socket cli) {
		closed = false;
		clientIP = ip;
		alias = ip;
		clientPort = port;
		socket = cli;
		inputQueue = new ConcurrentLinkedQueue<byte[]>();
		outputQueue = new ConcurrentLinkedQueue<byte[]>();
		try {
			out = new BufferedOutputStream(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) { }
	}
	
	public ServerClientConnection(String ip, int port, Socket cli, String al) {
		this(ip, port, cli);
		alias = al;
	}
	
	public void setAlias(String a) {
		alias = a;
	}
	
	public String getAlias() {
		return alias;
	}
	
	public Socket getSocket() {
		return socket;
	}

	public byte[] next() throws IOException {
		if (!inputQueue.isEmpty()) return inputQueue.remove();
		else return null;
	}

	public void send(String msg) {
		outputQueue.add(msg.getBytes());
	}
	
	public void send(byte[] msg) {
		outputQueue.add(msg);
	}

	public void close() throws IOException {
		closed = true;
		socket.close();
		out.close();
		in.close();
	}

	@Override
	public void run() {
		String input = null;
		
		// listen and add to message queue
		try {
			MAINLOOP:
			while (input == null ? true : !input.startsWith("!exit123protocol")) {
				if (socket.isClosed()) {
					inputQueue.add(("Connection was lost with " + clientIP + ".").getBytes());
					break;
				}
				
				// until message queue is out send them
				while (outputQueue.size() > 0) out.write(outputQueue.remove());
				
				while (in.ready()) {
					input = in.readLine();

					if (input != null) {
						if (input.startsWith("!exit123protocol")) {
							inputQueue.add("Connection was closed with client from client request.".getBytes());
							close();
							break MAINLOOP;
						}

						inputQueue.add(input.getBytes());
					}
				}
			}
		} catch (IOException e) {
			inputQueue.add(("There was an error receiving from " + clientIP + ", closing connection.").getBytes());
			try {
				close();
			} catch (IOException e1) { }
		} finally {
			try {
				close();
			} catch (IOException e) { }
		}
		
		closed = true;
	}
	
	@Override
	public void finalize() {
		try {
			close();
		} catch (IOException e) {}
	}

	public boolean isClosed() {
		return closed;
	}
}
