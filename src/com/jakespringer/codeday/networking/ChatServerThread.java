package com.jakespringer.codeday.networking;

import com.jakespringer.codeday.combat.Bullet;
import com.jakespringer.codeday.combat.Grenade;
import com.jakespringer.codeday.combat.ProjectileComponent;
import com.jakespringer.codeday.enemy.Enemy;
import com.jakespringer.codeday.networking.messages.GeneralCreateMessage;
import com.jakespringer.codeday.networking.messages.ProjectileCreateMessage;
import com.jakespringer.codeday.player.OtherPlayer;
import com.jakespringer.codeday.util.Tuple;
import com.jakespringer.engine.core.Main;
import com.jakespringer.engine.movement.PositionComponent;
import com.jakespringer.engine.movement.VelocityComponent;
import java.io.*;
import java.net.*;

public class ChatServerThread extends Thread {

    private ChatServer server = null;
    private Socket socket = null;
    private int ID = -1;
    private DataInputStream streamIn = null;
    private DataOutputStream streamOut = null;

    public ChatServerThread(ChatServer _server, Socket _socket) {
        super();
        server = _server;
        socket = _socket;
        ID = socket.getPort();
        try {
            open();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        start();

        for (OtherPlayer p : Main.gameManager.elc.getEntityList(OtherPlayer.class)) {
            send(new GeneralCreateMessage(OtherPlayer.class, p.id, p.getComponent(PositionComponent.class).pos).toString());
        }
        for (Enemy e : Main.gameManager.elc.getEntityList(Enemy.class)) {
            send(new GeneralCreateMessage(e.getClass(), e.id, e.getComponent(PositionComponent.class).pos).toString());
        }
        for (Bullet b : Main.gameManager.elc.getEntityList(Bullet.class)) {
            send(new ProjectileCreateMessage(Bullet.class, b.id, b.getComponent(ProjectileComponent.class).shooter.id,
                    b.getComponent(PositionComponent.class).pos, b.getComponent(VelocityComponent.class).vel).toString());
        }
        for (Grenade g : Main.gameManager.elc.getEntityList(Grenade.class)) {
            send(new ProjectileCreateMessage(Bullet.class, g.id, g.getComponent(ProjectileComponent.class).shooter.id,
                    g.getComponent(PositionComponent.class).pos, g.getComponent(VelocityComponent.class).vel).toString());
        }
    }

    public void send(String msg) {
        try {
            streamOut.writeUTF(msg);
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
        System.out.println("Server Thread " + ID + " running.");
        while (true) {
            try {
                server.messages.add(new Tuple(ID, streamIn.readUTF()));
//                server.handle(ID, streamIn.readUTF());
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
