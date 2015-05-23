package com.jakespringer.codeday.jake.netinterface;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;

import com.jakespringer.codeday.jake.networking.ClientServerConnection;
import com.jakespringer.codeday.testgame.OtherRed;
import com.jakespringer.codeday.testgame.Red;
import com.jakespringer.engine.core.AbstractSystem;
import com.jakespringer.engine.movement.PositionComponent;
import com.jakespringer.engine.util.Vec2;

public class NetworkSystem extends AbstractSystem {
	
	private ClientServerConnection conn;
	private Red you;
	private OtherRed other;

	public NetworkSystem(ClientServerConnection c, Red y, OtherRed o) {
		conn = c;
		you = y;
		other = o;
	}
	
	@Override
	public void update() {
		byte[] msg;
		try {
			msg = conn.next();
			if (msg != null) System.out.println(msg.length);
			if (msg != null && msg.length == 17) {
//				if (msg.startsWith("~")) {
					ByteBuffer b = ByteBuffer.allocate(32);
					b.put(msg);
					System.out.println("Recv: " + b.get() + " " + b.get() + " " +  b.get() + " " + b.get() + " " + b.get() + " " +b.get() + " " +b.get() + " " +b.get() + ";");
					b.position(0);
					other.getComponent(PositionComponent.class).pos = new Vec2(b.getDouble(), b.getDouble());
//				}
			}
			ByteBuffer b = ByteBuffer.allocate(16);
			Vec2 p = you.getComponent(PositionComponent.class).pos;
			b.putDouble(p.x);
			b.putDouble(p.y);
			b.flip();
//			System.out.println("Sent: " + b.get() + " " + b.get() + " " +  b.get() + " " + b.get() + " " + b.get() + " " +b.get() + " " +b.get() + " " +b.get() + ";");
			b.flip();
			conn.send(b.array());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
