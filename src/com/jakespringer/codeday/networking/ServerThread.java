package com.jakespringer.codeday.networking;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {

    private ServerMain server;
    private Socket socket;
    private int ID = -1;
    private DataInputStream streamIn;
    private DataOutputStream streamOut;

    public ServerThread(ServerMain serv, Socket sock) {
        super();
        server = serv;
        socket = sock;
    }

    public void send(byte msg) {
        try {
            streamOut.write(msg);
            streamOut.flush();
        } catch (IOException ioe) {
            System.out.println(ID + " ERROR sending: " + ioe.getMessage());
            server.remove(ID);
            stop();
        }
    }

    public int getID() {
        return ID;
    }

    public void run() {
        while (true) {
            try {
                server.handle(ID, streamIn.readByte());
            } catch (IOException ioe) {
                System.out.println(ID + " ERROR reading: " + ioe.getMessage());
                server.remove(ID);
                stop();
            }
        }
    }

    public void open() throws IOException {
        streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        streamOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
    }

    public void close() throws IOException {
        if (socket != null) {
            socket.close();
        }
        if (streamIn != null) {
            streamIn.close();
        }
        if (streamOut != null) {
            streamOut.close();
        }
    }
}
