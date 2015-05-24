package com.jakespringer.codeday.networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class ServerThread extends Thread {
	private ServerMain server;
	private Socket socket;
	private int ID = -1;
	private InputStream is;
	private OutputStream os;
	
	public ServerThread(ServerMain serv, Socket sock) {
		super();
		server = serv;
		socket = sock;
	}
	
	public void send(byte[] msg) {
		try {
			ByteBuffer b = ByteBuffer.wrap(new byte[4]);
			b.putShort((short)2888);
			b.putShort((short)msg.length);
			os.write(b.array(), 0, 4);
			os.write(msg);
			os.flush();
		} catch (IOException ioe) {
			server.remove(ID);
			stop();
		}
	}
	
	public int getID() {
		return ID;
	}
	
	public void run() {
		while (true) {
			byte[] header = new byte[4];
			ByteBuffer b = ByteBuffer.wrap(header);
			int len = 0;
			
			try {
				is.read(header);
				if (b.getShort() == (short)2888) {
					len = (int) b.getShort();
				}
				
				byte[] data = new byte[len];
				is.read(data);
				
				server.handle(ID, data);
			} catch (IOException ioe) {
                System.out.println(ID + " ERROR reading: " + ioe.getMessage());
				server.remove(ID);
				stop();
			}
		}
	}
	
	public void open() throws IOException {
		is = socket.getInputStream();
		os = socket.getOutputStream();
	}
	
	public void close() throws IOException {
		if (socket != null) {
			socket.close();
		}
		if (is != null) {
			is.close();
		}
		if (os != null) {
			os.close();
		}
	}
}
