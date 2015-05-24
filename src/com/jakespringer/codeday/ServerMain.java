package com.jakespringer.codeday;

import com.jakespringer.codeday.networking.ChatServer;

public class ServerMain {

    public static void main(String[] args) {
        new ChatServer(55555);
    }
}
