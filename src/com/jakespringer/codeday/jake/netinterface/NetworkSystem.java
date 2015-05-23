package com.jakespringer.codeday.jake.netinterface;

import java.nio.ByteBuffer;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.jakespringer.codeday.jake.networking.Connection;
import com.jakespringer.codeday.testgame.Blue;
import com.jakespringer.codeday.testgame.OtherRed;
import com.jakespringer.codeday.testgame.Red;
import com.jakespringer.engine.core.AbstractSystem;
import com.jakespringer.engine.core.Main;
import com.jakespringer.engine.movement.PositionComponent;
import com.jakespringer.engine.movement.VelocityComponent;
import com.jakespringer.engine.util.Vec2;

public class NetworkSystem extends AbstractSystem {

    private Connection conn;
    public Queue<Blue> newBlue = new ConcurrentLinkedQueue<>();

    public NetworkSystem(Connection con) {
    	conn = con;
    }

    @Override
    public void update() {
        //Send
    	Vec2 pos = Main.gameManager.elc.getEntity(Red.class).getComponent(PositionComponent.class).pos;
    	Vec2 vel = Main.gameManager.elc.getEntity(Red.class).getComponent(VelocityComponent.class).vel;
    	
    	ByteBuffer b = ByteBuffer.allocate(8*4);
    	b.putDouble(pos.x);
    	b.putDouble(pos.y);
    	b.putDouble(vel.x);
    	b.putDouble(vel.y);
    	
    	while (!newBlue.isEmpty()) {
    		Blue blue = newBlue.poll();
    		ByteBuffer d = ByteBuffer.allocate(8*2);
    		Vec2 position = blue.getComponent(PositionComponent.class).pos;
    		d.putDouble(position.x);
    		d.putDouble(position.y);
    		conn.send(d.array());
    	}
    	
        conn.send(b.array());
        //Receive
        PositionComponent opc = Main.gameManager.elc.getEntity(OtherRed.class).getComponent(PositionComponent.class);
        VelocityComponent ovc = Main.gameManager.elc.getEntity(OtherRed.class).getComponent(VelocityComponent.class);
        while (conn.hasNext()) {
            byte[] msg = conn.next();
            if (msg.length == 8*4) {
				ByteBuffer c = ByteBuffer.wrap(msg);
				opc.pos = new Vec2(c.getDouble(), c.getDouble());
				ovc.vel = new Vec2(c.getDouble(), c.getDouble());	
            } else if (msg.length == 8*2) {
            	ByteBuffer c = ByteBuffer.wrap(msg);
            	new Blue(new Vec2(c.getDouble(), c.getDouble()));
            }
        }
    }
}
