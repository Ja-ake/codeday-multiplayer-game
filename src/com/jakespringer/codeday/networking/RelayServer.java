package com.jakespringer.codeday.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class RelayServer {

    private List<Connection> connections;
    public final Object notification = new Object();

    public void run() throws UnknownHostException, IOException, InterruptedException {
        connections = Collections.synchronizedList(new LinkedList<Connection>());

        final ServerSocket listener = new ServerSocket(1235);

        try {
            final Thread handle2 = new Thread() {
                @Override
                public void run() {
                    while (true) {
                        synchronized (connections) {
                            for (Connection connection : connections) {
                                byte[] input = null;
                                while ((input = connection.next()) != null) {
                                	Iterator<Connection> iter = connections.iterator();
                                    while (iter.hasNext()) {
                                    	Connection c = iter.next();
                                        if (!connection.equals(c)) {
                                            if (c.isRunning()) c.send(input);
                                            else iter.remove();
                                        }
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
                            return;
                        }
                    }
                }
            };

            handle.setDaemon(true);
            handle.start();
            handle2.setDaemon(true);
            handle2.start();

            final Thread handle3 = new Thread(new Runnable() {

				@Override
				public void run() {
					while (true) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
						}
						
//						synchronized (connections) {
//							Iterator<Connection> iter = connections.iterator();
//							while (iter.hasNext()) {
//								Connection c = iter.next();
//								if (c.isRunning()) c.send(new byte[] { 0 });
//							}
//						}
						
						System.gc();
					}
				}
            });
            
            handle3.start();
            
            Scanner scan = new Scanner(System.in);
            while (true) {
            	String line = scan.nextLine().toLowerCase();
            	
            	if (line.contains("exit")) {
            		System.out.println("Exiting.");
            		System.exit(0);
            	} else if (line.contains("poll")) {
            		System.out.println("Active thread count: " + Thread.activeCount());
            	}
            }
        } finally {
            listener.close();
        }
    }

    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
        RelayServer rs = new RelayServer();
        rs.run();
    }
}
