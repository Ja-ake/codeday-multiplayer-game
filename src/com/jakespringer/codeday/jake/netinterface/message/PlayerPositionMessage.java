package com.jakespringer.codeday.jake.netinterface.message;

import java.nio.ByteBuffer;

import com.jakespringer.codeday.jake.netinterface.Message;
import com.jakespringer.engine.core.AbstractEntity;
import com.jakespringer.engine.core.Main;
import com.jakespringer.engine.movement.PositionComponent;
import com.jakespringer.engine.movement.VelocityComponent;
import com.jakespringer.engine.util.Vec2;

public class PlayerPositionMessage implements Message {

	public long id;
	public double x, y;
	public double vx, vy;
	
	@Override
	public void act() {
		AbstractEntity e = Main.gameManager.elc.getId(id);
		e.getComponent(PositionComponent.class).pos = new Vec2(x, y);
		e.getComponent(VelocityComponent.class).vel = new Vec2(vx, vy);
	}

	@Override
	public byte[] toBytes() {
		ByteBuffer b = ByteBuffer.allocate(8*4);
		b.putLong(id);
		b.putDouble(x);
		b.putDouble(y);
		b.putDouble(vx);
		b.putDouble(vy);
		
		return b.array();
	}

	@Override
	public void initialize(byte[] data) {
		ByteBuffer b = ByteBuffer.wrap(data);
		id = b.getLong();
		x = b.getDouble();
		y = b.getDouble();
		vx = b.getDouble();
		vy = b.getDouble();
	}

}
