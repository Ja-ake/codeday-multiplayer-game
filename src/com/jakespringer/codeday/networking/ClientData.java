package com.jakespringer.codeday.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ClientData {

    public PrintWriter out;
    public BufferedReader in;
    public ConcurrentLinkedQueue<String> messages;

    public ClientData(Socket clientSocket) throws IOException {
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        messages = new ConcurrentLinkedQueue();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        messages.add(in.readLine());
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }
}
