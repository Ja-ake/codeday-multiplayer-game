package com.jakespringer.codeday.networking;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerMain implements Runnable {

	public ServerThread[] clients = new ServerThread[16];
	private ServerSocket server = null;
	private Thread thread = null;
	private int clientCount = 0;
	
	public static void main(String[] args) {
		new ServerMain(13373);
	}
	
	public ServerMain(int port) {
		try {
            System.out.println("Binding to port " + port + ", please wait  ...");
			server = new ServerSocket(port);
            System.out.println("Server started: " + server);
			start();
		} catch (IOException ioe) {
            System.out.println("Can not bind to port " + port + ": " + ioe.getMessage());
		}
	}
	
	@Override
	public void run() {
		while (thread != null) {
			try {
				addThread(server.accept());
			} catch (IOException ioe) {
				stop();
			}
		}
	}
	
	public void start() {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}
	
	public void stop() {
		if (thread != null) {
			thread.stop();
			thread = null;
		}
	}
	
    private int findClient(int ID) {
        for (int i = 0; i < clientCount; i++) {
            if (clients[i].getID() == ID) {
                return i;
            }
        }
        return -1;
    }
    
    public synchronized void handle(int ID, byte[] data) {
    	System.out.println(data.length);
    	
    	for (int i=0; i<clientCount; i++) {
    		if (clients[i].getID() != ID) {
    			clients[i].send(data);
    		}
    	}
    }
    
    public synchronized void remove(int ID) {
        int pos = findClient(ID);
        if (pos >= 0) {
            ServerThread toTerminate = clients[pos];
            System.out.println("Removing client thread " + ID + " at " + pos);
            if (pos < clientCount - 1) {
                for (int i = pos + 1; i < clientCount; i++) {
                    clients[i - 1] = clients[i];
                }
            }
            clientCount--;
            try {
                toTerminate.close();
            } catch (IOException ioe) {
                System.out.println("Error closing thread: " + ioe);
            }
            toTerminate.stop();
        }
    }
    
    private void addThread(Socket socket) {
        if (clientCount < clients.length) {
            System.out.println("Client accepted: " + socket);
            clients[clientCount] = new ServerThread(this, socket);
            try {
                clients[clientCount].open();
                clients[clientCount].start();
                clientCount++;
            } catch (IOException ioe) {
                System.out.println("Error opening thread: " + ioe);
            }
        } else {
            System.out.println("Client refused: maximum " + clients.length + " reached.");
        }
    }
}
