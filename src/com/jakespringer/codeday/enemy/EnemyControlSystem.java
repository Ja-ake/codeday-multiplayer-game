package com.jakespringer.codeday.enemy;

import com.jakespringer.codeday.player.*;
import com.jakespringer.engine.core.*;
import com.jakespringer.engine.movement.PositionComponent;
import com.jakespringer.engine.movement.VelocityComponent;
import com.jakespringer.engine.util.Vec2;
import java.util.ArrayList;

public class EnemyControlSystem extends AbstractSystem {

    private PositionComponent pc;
    private VelocityComponent vc;

    public EnemyControlSystem(PositionComponent pc, VelocityComponent vc) {
        this.pc = pc;
        this.vc = vc;
    }

    @Override
    public void update() {
        vc.vel = new Vec2();
        double speed = 3;
        ArrayList<Player> players = Main.gameManager.elc.getEntityList(Player.class);
        if (players.isEmpty()) {
            return;
        }
        Player closest = players.get(0);
        for (Player p : players) {
            if (pc.pos.subtract(p.getComponent(PositionComponent.class).pos).lengthSquared()
                    < pc.pos.subtract(closest.getComponent(PositionComponent.class).pos).lengthSquared()) {
                closest = p;
            }
        }
        vc.vel = closest.getComponent(PositionComponent.class).pos.subtract(pc.pos).setLength(speed);
        if (Double.isNaN(vc.vel.lengthSquared())) {
            vc.vel = new Vec2();
        }
        System.out.println(pc.pos);
        System.out.println(vc.vel);
    }

}
