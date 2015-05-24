package com.jakespringer.codeday.enemy;

import com.jakespringer.codeday.combat.Bullet;
import com.jakespringer.codeday.networking.messages.EntityStateMessage;
import com.jakespringer.codeday.networking.messages.ProjectileCreateMessage;
import com.jakespringer.codeday.player.*;
import com.jakespringer.engine.core.*;
import com.jakespringer.engine.graphics.SpriteComponent;
import com.jakespringer.engine.movement.PositionComponent;
import com.jakespringer.engine.movement.RotationComponent;
import com.jakespringer.engine.movement.VelocityComponent;
import com.jakespringer.engine.util.Color4d;
import com.jakespringer.engine.util.Vec2;
import java.util.ArrayList;

public class EnemyControlSystem extends AbstractSystem {

    private AssaultEnemy enemy;
    private PositionComponent pc;
    private VelocityComponent vc;
    private ShotCooldownComponent scc;

    public EnemyControlSystem(AssaultEnemy enemy, PositionComponent pc, VelocityComponent vc, ShotCooldownComponent scc) {
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
        vc.vel = closest.getComponent(PositionComponent.class).pos.subtract(pc.pos).setLength(speed);
        if (Double.isNaN(vc.vel.lengthSquared())) {
            vc.vel = new Vec2();
        }

        scc.currentCooldown--;
        if (scc.currentCooldown <= 0) {
            Bullet b = new Bullet(enemy, pc.pos, closest.getComponent(PositionComponent.class).pos.subtract(pc.pos).setLength(12));
            b.getComponent(SpriteComponent.class).color = new Color4d(0, 1, .5);
            new ProjectileCreateMessage(Bullet.class, b.id, enemy.id, pc.pos,
                    closest.getComponent(PositionComponent.class).pos.subtract(pc.pos).setLength(12)).send();
            scc.currentShots--;
            if (scc.currentShots <= 0) {
                scc.currentCooldown = scc.maxCooldown;
                scc.currentShots = scc.maxShots;
            } else {
                scc.currentCooldown = scc.shotSeperation;
            }
        }
        System.out.println(pc.pos);
        System.out.println(vc.vel);
        new EntityStateMessage(enemy.id, pc.pos.x, pc.pos.y, vc.vel.x, vc.vel.y, enemy.getComponent(RotationComponent.class).rot).send();
    }

}
