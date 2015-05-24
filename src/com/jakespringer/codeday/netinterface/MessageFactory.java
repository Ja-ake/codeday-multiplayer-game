package com.jakespringer.codeday.netinterface;

import java.util.Arrays;

import com.jakespringer.codeday.netinterface.message.PlayerJoinMessage;
import com.jakespringer.codeday.netinterface.message.PlayerLeaveMessage;
import com.jakespringer.codeday.netinterface.message.PlayerStateMessage;

public abstract class MessageFactory {
	
    public static Message createMessage(byte[] bytes) {
    	byte[] msg = Arrays.copyOfRange(bytes, 1, bytes.length);
    	
    	switch (bytes[0]) { // PlayerJoinMessage
    	case 1: {
    		PlayerJoinMessage m = new PlayerJoinMessage();
    		m.initialize(msg);
    		return m;
    	}
    	case 2: {
    		PlayerLeaveMessage m = new PlayerLeaveMessage();
    		m.initialize(msg);
    		return m;
    	}
    	case 3: {
    		PlayerStateMessage m = new PlayerStateMessage();
    		m.initialize(msg);
    		return m;
    	}
    	}
    	
        return null;
    }
    
    public static byte[] createBytes(Message m) {
    	byte type = 0;
    	
    	if (m instanceof PlayerJoinMessage) type = 1;
    	if (m instanceof PlayerLeaveMessage) type = 2;
    	if (m instanceof PlayerStateMessage) type = 3;
    	
    	byte[] msg = m.toBytes();
    	byte[] tosend = new byte[msg.length+1];
    	tosend[0] = type;
    	for (int i=1; i<tosend.length; i++) tosend[i] = msg[i-1];
    	
    	return tosend;
    }
}
