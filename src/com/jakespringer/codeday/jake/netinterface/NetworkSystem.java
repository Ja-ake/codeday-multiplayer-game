package com.jakespringer.codeday.jake.netinterface;

import com.jakespringer.codeday.jake.networking.Connection;
import com.jakespringer.engine.core.AbstractSystem;

public class NetworkSystem extends AbstractSystem {

    private Connection conn;

    public NetworkSystem(Connection con) {
    	conn = con;
    }

    @Override
    public void update() {
    	while (conn.hasNext()) {
            byte[] msg = conn.next();
            Message m = MessageFactory.createMessage(msg);
            if (m != null) m.act();
            
            if (m != null) System.out.println(m.getClass().getSimpleName());
    	}
    }
    
    public void sendMessage(Message m) {
    	conn.send(MessageFactory.createBytes(m));
    }
}
