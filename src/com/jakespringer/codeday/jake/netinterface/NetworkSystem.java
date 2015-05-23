package com.jakespringer.codeday.jake.netinterface;

import com.jakespringer.codeday.jake.networking.Connection;
import com.jakespringer.codeday.testgame.OtherRed;
import com.jakespringer.engine.core.AbstractSystem;
import com.jakespringer.engine.core.Main;
import com.jakespringer.engine.movement.PositionComponent;
import com.jakespringer.engine.movement.VelocityComponent;
import com.jakespringer.engine.util.Vec2;

public class NetworkSystem extends AbstractSystem {

    private Connection conn;

    public NetworkSystem() {
        conn = new Connection();
    }

    @Override
    public void update() {
        //Send
        byte[] toSend = new byte[]{(byte) pc.pos.x, (byte) pc.pos.y, (byte) vc.vel.x, (byte) vc.vel.y};
        conn.send(toSend);
        //Receive
        PositionComponent opc = Main.gameManager.elc.getEntity(OtherRed.class).getComponent(PositionComponent.class);
        VelocityComponent ovc = Main.gameManager.elc.getEntity(OtherRed.class).getComponent(VelocityComponent.class);
        while (conn.hasNext()) {
            byte[] msg = conn.next();
            opc.pos = new Vec2(msg[0], msg[1]);
            ovc.vel = new Vec2(msg[2], msg[3]);
        }
//		byte[] msg;
//		try {
//			msg = conn.next();
//			if (msg != null) System.out.println(msg.length);
//			if (msg != null && msg.length == 17) {
////				if (msg.startsWith("~")) {
//					ByteBuffer b = ByteBuffer.allocate(32);
//					b.put(msg);
//					System.out.println("Recv: " + b.get() + " " + b.get() + " " +  b.get() + " " + b.get() + " " + b.get() + " " +b.get() + " " +b.get() + " " +b.get() + ";");
//					b.position(0);
//					other.getComponent(PositionComponent.class).pos = new Vec2(b.getDouble(), b.getDouble());
////				}
//			}
//			ByteBuffer b = ByteBuffer.allocate(16);
//			Vec2 p = you.getComponent(PositionComponent.class).pos;
//			b.putDouble(p.x);
//			b.putDouble(p.y);
//			b.flip();
////			System.out.println("Sent: " + b.get() + " " + b.get() + " " +  b.get() + " " + b.get() + " " + b.get() + " " +b.get() + " " +b.get() + " " +b.get() + ";");
//			b.flip();
//			conn.send(b.array());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
    }
}
