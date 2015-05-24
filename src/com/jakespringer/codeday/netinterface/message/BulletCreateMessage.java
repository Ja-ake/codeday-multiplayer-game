package com.jakespringer.codeday.netinterface.message;

import java.nio.ByteBuffer;

import com.jakespringer.codeday.netinterface.Message;
import com.jakespringer.engine.core.AbstractEntity;
import com.jakespringer.engine.core.Main;
import com.jakespringer.engine.movement.PositionComponent;
import com.jakespringer.engine.movement.RotationComponent;
import com.jakespringer.engine.movement.VelocityComponent;
import com.jakespringer.engine.util.Vec2;

public class BulletCreateMessage implements Message {

	public long id;
	public double x, y;
	public double vx, vy;
	public double rot;
	
	public BulletCreateMessage() {
		
	}
	
	public BulletCreateMessage(long i, double xv, double yv, double vxv, double vyv, double r) {
		id = i;
		x = xv;
		y = yv;
		vx = vxv;
		vy = vyv;
		rot = r;
	}
	
	@Override
	public void act() {
		AbstractEntity e = Main.gameManager.elc.getId(id);
		if (e == null) return;
		e.getComponent(PositionComponent.class).pos = new Vec2(x, y);
		e.getComponent(VelocityComponent.class).vel = new Vec2(vx, vy);
		e.getComponent(RotationComponent.class).rot = rot;
	}

	@Override
	public byte[] toBytes() {
		ByteBuffer b = ByteBuffer.allocate(8*6);
		b.putLong(id);
		b.putDouble(x);
		b.putDouble(y);
		b.putDouble(vx);
		b.putDouble(vy);
		b.putDouble(rot);
		
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
		rot = b.getDouble();
	}

}
