package com.jakespringer.codeday.networking;

import com.jakespringer.codeday.networking.messages.EntityDestroyMessage;
import com.jakespringer.codeday.networking.messages.GeneralCreateMessage;
import com.jakespringer.codeday.player.OtherPlayer;
import com.jakespringer.codeday.player.Player;
import com.jakespringer.engine.core.AbstractSystem;
import com.jakespringer.engine.core.Main;
import com.jakespringer.engine.movement.PositionComponent;
import java.io.IOException;

public class NetworkSystem extends AbstractSystem {

    private ChatClient conn;

    public NetworkSystem() {
    }

    @Override
    public void update() {
        if (conn == null) {
            return;
        }
        while (!conn.messageList.isEmpty()) {
            String msg = conn.messageList.poll();
            String[] parts = msg.split("\\|");
            try {
                Message m = ((Message) Class.forName(parts[0]).newInstance());
                m.initialize(parts);
                m.act();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void sendMessage(Message m) {
        if (conn != null) {
            conn.send(m.toString());
        }
    }

    public
            void disconnect() {
        if (conn != null) {
            sendMessage(new EntityDestroyMessage(Main.gameManager.elc.getEntity(Player.class
            ).id));

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
            conn.stop();
            conn = null;
        }
    }

    public void connect(String ip, int port) {
        if (conn != null) {
            disconnect();
        }
        conn = new ChatClient(ip, port);
        try {
            conn.start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (Main.gameManager.elc.getEntity(Player.class) != null) {
            new GeneralCreateMessage(OtherPlayer.class, Main.gameManager.elc.getEntity(Player.class).id,
                    Main.gameManager.elc.getEntity(Player.class).getComponent(PositionComponent.class).pos).send();
        }
    }
}
