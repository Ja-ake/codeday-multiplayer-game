package com.jakespringer.codeday.netinterface;

import com.jakespringer.codeday.netinterface.message.PlayerLeaveMessage;
import com.jakespringer.codeday.networking.Connection;
import com.jakespringer.codeday.player.Player;
import com.jakespringer.engine.core.AbstractSystem;
import com.jakespringer.engine.core.Main;
import java.io.IOException;

public class NetworkSystem extends AbstractSystem {

    private Connection conn;

    public NetworkSystem() {
    }

    @Override
    public void update() {
        if (conn == null) {
            return;
        }

        while (conn.hasNext()) {
            byte[] msg = conn.next();
            Message m = MessageFactory.createMessage(msg);
            if (m != null) {
                m.act();
            }

            if (m != null) {
                System.out.println(m.getClass().getSimpleName());
            }
        }
    }

    public void sendMessage(Message m) {
        if (conn != null) {
            conn.send(MessageFactory.createBytes(m));
        }
    }

    public void disconnect() {
        if (conn != null) {
            if (conn.isRunning()) {
                sendMessage(new PlayerLeaveMessage(Main.gameManager.elc.getEntity(Player.class).id));
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
                conn.disconnect();
                conn = null;
            }
        }
    }

    public void connect(String ip, int port) {
        if (conn != null) {
            disconnect();
        }
        conn = new Connection();
        try {
            conn.start(ip, port);
//			sendMessage(new PlayerJoinMessage(Main.gameManager.elc.getEntity(Player.class).id));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
