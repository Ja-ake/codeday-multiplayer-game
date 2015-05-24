package com.jakespringer.codeday.enemy;

import java.util.ArrayList;

import com.jakespringer.codeday.combat.Bullet;
import com.jakespringer.codeday.player.Player;
import com.jakespringer.engine.core.AbstractSystem;
import com.jakespringer.engine.core.Main;
import com.jakespringer.engine.graphics.SpriteComponent;
import com.jakespringer.engine.movement.PositionComponent;
import com.jakespringer.engine.movement.VelocityComponent;
import com.jakespringer.engine.util.Color4d;
import com.jakespringer.engine.util.Vec2;

public class SniperControlSystem extends AbstractSystem {
	private SniperEnemy enemy;
    private PositionComponent pc;
    private VelocityComponent vc;
    private ShotCooldownComponent scc;

    public SniperControlSystem(SniperEnemy enemy, PositionComponent pc, VelocityComponent vc, ShotCooldownComponent scc) {
        this.enemy = enemy;
        this.pc = pc;
        this.vc = vc;
        this.scc = scc;
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
        
        Vec2 near = closest.getComponent(PositionComponent.class).pos;
        if (near.length() > 500) vc.vel = near.subtract(pc.pos).setLength(5.0d);
        if (Double.isNaN(vc.vel.lengthSquared())) {
            vc.vel = new Vec2();
        }

        scc.currentCooldown--;
        if (scc.currentCooldown <= 0) {
            Bullet b = new Bullet(enemy, pc.pos, closest.getComponent(PositionComponent.class).pos.subtract(pc.pos).setLength(12));
            b.getComponent(SpriteComponent.class).color = new Color4d(0, 1, .5);
            scc.currentShots--;
            if (scc.currentShots <= 0) {
                scc.currentCooldown = scc.maxCooldown;
                scc.currentShots = scc.maxShots;
            } else {
                scc.currentCooldown = scc.shotSeperation;
            }
        }
    }
}
