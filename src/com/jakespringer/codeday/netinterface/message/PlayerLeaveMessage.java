package com.jakespringer.codeday.netinterface.message;

import java.nio.ByteBuffer;

import com.jakespringer.codeday.netinterface.Message;
import com.jakespringer.codeday.player.OtherPlayer;
import com.jakespringer.codeday.ui.CommandConsole;
import com.jakespringer.engine.core.Main;
import com.jakespringer.engine.util.Vec2;

public class PlayerLeaveMessage implements Message {

	public long id;
	
	public PlayerLeaveMessage() {
		
	}
	
	public PlayerLeaveMessage(long id) {
		
	}
	
	@Override
	public void act() {
		Main.gameManager.elc.getId(id).destroySelf();
		CommandConsole.println("A player has left.");
	}

	@Override
	public byte[] toBytes() {
		ByteBuffer b = ByteBuffer.allocate(8);
		b.putLong(id);
		return b.array();
	}

	@Override
	public void initialize(byte[] data) {
		ByteBuffer b = ByteBuffer.wrap(data);
		id = b.getLong();
	}

}
