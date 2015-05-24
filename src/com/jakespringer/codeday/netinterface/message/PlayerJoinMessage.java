package com.jakespringer.codeday.netinterface.message;

import java.nio.ByteBuffer;

import com.jakespringer.codeday.netinterface.Message;
import com.jakespringer.codeday.player.OtherPlayer;
import com.jakespringer.engine.util.Vec2;

public class PlayerJoinMessage implements Message {

	public long id;
	
	public PlayerJoinMessage() {
		
	}
	
	public PlayerJoinMessage(long id) {
		
	}
	
	@Override
	public void act() {
		OtherPlayer p = new OtherPlayer(new Vec2());
		p.id = this.id;
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
