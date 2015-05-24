package com.jakespringer.codeday.netinterface.message;

import java.nio.ByteBuffer;

import com.jakespringer.codeday.netinterface.Message;
import com.jakespringer.codeday.netinterface.NetworkSystem;
import com.jakespringer.codeday.player.OtherPlayer;
import com.jakespringer.codeday.player.Player;
import com.jakespringer.codeday.ui.CommandConsole;
import com.jakespringer.engine.core.Main;
import com.jakespringer.engine.util.Vec2;

public class PlayerJoinMessage implements Message {

	public long id;
	
	public PlayerJoinMessage() {
		
	}
	
	public PlayerJoinMessage(long i) {
		id = i;
	}
	
	@Override
	public void act() {
		if (Main.gameManager.elc.getId(id) != null) return;
		OtherPlayer p = new OtherPlayer(new Vec2());
		p.id = this.id;
		CommandConsole.println("A player has joined.");
		Main.gameManager.getSystem(NetworkSystem.class).sendMessage(new PlayerJoinMessage(Main.gameManager.elc.getEntity(Player.class).id));
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
