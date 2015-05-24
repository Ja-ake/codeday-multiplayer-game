package com.jakespringer.codeday.jake.netinterface;

import java.util.Arrays;

import com.jakespringer.codeday.jake.netinterface.message.PlayerJoinMessage;

public abstract class MessageFactory {
	
    public static Message createMessage(byte[] bytes) {
    	byte[] msg = Arrays.copyOfRange(bytes, 1, bytes.length);
    	
    	switch (bytes[0]) { // PlayerJoinMessage
    	case 1: {
    		PlayerJoinMessage m = new PlayerJoinMessage();
    		m.initialize(msg);
    		return m;
    	}
    	}
    	
        return null;
    }
    
    public static byte[] createBytes(Message m) {
    	byte type = 0;
    	
    	if (m instanceof PlayerJoinMessage) type = 1;
    	
    	byte[] msg = m.toBytes();
    	byte[] tosend = new byte[msg.length+1];
    	tosend[0] = type;
    	for (int i=1; i<tosend.length; i++) tosend[i] = msg[i-1];
    	
    	return tosend;
    }
}
