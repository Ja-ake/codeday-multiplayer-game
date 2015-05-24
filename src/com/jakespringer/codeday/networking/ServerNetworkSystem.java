package com.jakespringer.codeday.networking;

import com.jakespringer.codeday.enemy.Enemy;
import com.jakespringer.codeday.networking.messages.EntityStateMessage;
import com.jakespringer.codeday.player.OtherPlayer;
import com.jakespringer.codeday.util.Tuple;
import com.jakespringer.engine.core.AbstractSystem;
import com.jakespringer.engine.core.Main;
import com.jakespringer.engine.movement.PositionComponent;
import com.jakespringer.engine.movement.RotationComponent;
import com.jakespringer.engine.movement.VelocityComponent;
import com.jakespringer.engine.util.Vec2;

public class ServerNetworkSystem extends AbstractSystem {

    private ChatServer conn;
    private int step;

    public ServerNetworkSystem(ChatServer conn) {
        this.conn = conn;
    }

    public void sendMessage(Message m) {
        conn.messages.add(new Tuple(-1, m.toString()));
    }

    @Override
    public void update() {
        if (Math.random() < .01) {
            for (OtherPlayer p : Main.gameManager.elc.getEntityList(OtherPlayer.class)) {
                p.getComponent(PositionComponent.class).pos = new Vec2(-10000, -10000);
            }
        }
        if (step++ % 3 == 0) {
            while (!conn.messages.isEmpty()) {
                Tuple<Integer, String> t = conn.messages.poll();
                conn.handle(t.right, t.left);
            }
            for (Enemy e : Main.gameManager.elc.getEntityList(Enemy.class)) {
                new EntityStateMessage(e.id, e.getComponent(PositionComponent.class).pos.x, e.getComponent(PositionComponent.class).pos.y,
                        e.getComponent(VelocityComponent.class).vel.x, e.getComponent(VelocityComponent.class).vel.y, e.getComponent(RotationComponent.class).rot).send();
            }
        }
    }

}
