package com.jakespringer.codeday.netinterface;

import com.jakespringer.codeday.networking.ChatClient;
import com.jakespringer.engine.core.AbstractSystem;

public class NetworkSystem extends AbstractSystem {

    private ChatClient conn;

    public NetworkSystem() {
    }

    @Override
    public void update() {
        if (conn == null) {
            return;
        }
        for (String msg : conn.messageList) {
            String[] parts = msg.split("\\|");
            try {
                Message m = ((Message) Class.forName(parts[0]).newInstance());
                m.initialize(parts);
                m.act();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        conn.messageList.clear();
    }

    public void sendMessage(Message m) {
        if (conn != null) {
            conn.send(m.toString());
        }
    }

//    public void disconnect() {
//        if (conn != null) {
//            if (conn.isRunning()) {
//                sendMessage(new PlayerLeaveMessage(Main.gameManager.elc.getEntity(Player.class).id));
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                }
//                conn.disconnect();
//                conn = null;
//            }
//        }
//    }
    public void connect(String ip, int port) {
        if (conn != null) {
//            disconnect();
        }
        conn = new ChatClient(ip, port);
    }
}
