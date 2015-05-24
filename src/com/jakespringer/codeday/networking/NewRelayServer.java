package com.jakespringer.codeday.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.jakespringer.codeday.util.Tuple;

public class NewRelayServer {
	private List<Connection> connections;
	private List<Tuple<byte[], Connection>> tosend;
	
	Thread handle = new Thread(new Runnable() {
		
		@Override
		public void run() {
			while (true) {
				synchronized (connections) {
					Iterator<Connection> iter = connections.iterator();
					while (iter.hasNext()) {
						Connection c = iter.next();
						while (c.hasNext()) {
							byte[] data = c.next();
							synchronized (tosend) {tosend.add(new Tuple<byte[], Connection>(data, c));}
                            System.out.println("message received: " + tosend.size());
						}
					}
				}
			}
		}
	});
	
	Thread handle2 = new Thread(new Runnable() {
		
		@Override
		public void run() {
			synchronized (connections) {
				Iterator<Connection> iter = connections.iterator();
				while (iter.hasNext()) {
					Connection c = iter.next();
					synchronized (tosend) {for (Tuple<byte[], Connection> t : tosend) {
						if (t.left != c)  {
							if (c.isRunning()) {
								c.send(t.right);
                                System.out.println("message sent");
							} else {
								iter.remove();
							}
						}
					}}
				}
			}
		}
	});
	
	Thread handle3 = new Thread(new Runnable() {

		@Override
		public void run() {
			ServerSocket listener = null;
			try {
				listener = new ServerSocket(1235);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			while (true) {
				Socket socket;
                try {
                    socket = listener.accept();
                    Connection c = new Connection();
                    c.start(socket);
                    System.out.println("Connected: " + socket.getInetAddress().getHostName());
                    synchronized (connections) {
                        connections.add(c);
                    }

                } catch (IOException e1) {
                    e1.printStackTrace();
        			try {
						if (listener != null) listener.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
                    return;
                }
			}
			
		}
	});

	public NewRelayServer() throws IOException {
		connections = Collections.synchronizedList(new LinkedList<Connection>());
		tosend = Collections.synchronizedList(new LinkedList<Tuple<byte[], Connection>>());
	}
	
	public void start() throws IOException {
		handle.start();
		handle2.start();
		handle3.start();
	}
	
	public static void main(String[] args) throws IOException {
		(new NewRelayServer()).start();
		
        Scanner scan = new Scanner(System.in);
        while (true) {
        	String line = scan.nextLine().toLowerCase();
        	
        	if (line.contains("exit")) {
        		System.out.println("Exiting.");
        		scan.close();
        		System.exit(0);
        	} else if (line.contains("poll")) {
        		System.out.println("Active thread count: " + Thread.activeCount());
        	}
        }
	}
}
