package com.jakespringer.codeday.combat;

import com.jakespringer.codeday.level.Level;
import com.jakespringer.codeday.level.LevelComponent;
import com.jakespringer.codeday.level.Tile;
import com.jakespringer.codeday.particle.ParticleEmitter;
import com.jakespringer.engine.collisions.CollisionComponent;
import com.jakespringer.engine.collisions.CollisionUtil;
import com.jakespringer.engine.core.AbstractEntity;
import com.jakespringer.engine.core.AbstractSystem;
import com.jakespringer.engine.core.Main;
import com.jakespringer.engine.graphics.SpriteComponent;
import com.jakespringer.engine.movement.PositionComponent;
import com.jakespringer.engine.movement.RotationComponent;
import com.jakespringer.engine.movement.VelocityComponent;
import com.jakespringer.engine.util.Vec2;
import java.util.ArrayList;

public class ProjectileSystem extends AbstractSystem {

    private AbstractEntity projectile;
    private PositionComponent pc;
    private VelocityComponent vc;
    private RotationComponent rc;
    private SpriteComponent sc;
    private ProjectileComponent pjc;

    public ProjectileSystem(AbstractEntity projectile, PositionComponent pc, VelocityComponent vc, RotationComponent rc, SpriteComponent sc, ProjectileComponent pjc) {
        this.projectile = projectile;
        this.pc = pc;
        this.vc = vc;
        this.rc = rc;
        this.sc = sc;
        this.pjc = pjc;
    }

    @Override
    public void update() {
        if (pjc.duration-- <= 0) {
            projectile.destroySelf();
        }
        ArrayList<CollisionComponent> hits = CollisionUtil.listAt(pc.pos);
        for (CollisionComponent cc : hits) {
            if (cc.ae != pjc.shooter) {
                HealthComponent hc = cc.ae.getComponent(HealthComponent.class);
                if (hc != null) {
                    hc.damage += pjc.damage;
                }
                projectile.destroySelf();
            }
        }
        LevelComponent lc = Main.gameManager.elc.getEntity(Level.class).getComponent(LevelComponent.class);
        Tile t = lc.tileAt(pc.pos.add(vc.vel.multiply(.5)));
        if (t != null && t.isWall) {
            if (pjc.bounces-- < 0) {
                projectile.destroySelf();
            } else {
                Vec2 hit = lc.rayCast(pc.pos.subtract(vc.vel.multiply(1)), pc.pos.add(vc.vel.multiply(3)));
//                Graphics2D.drawLine(pc.pos.subtract(vc.vel.multiply(40)), hit, Color4d.WHITE, 4);
                if (!hit.equals(pc.pos.add(vc.vel))) {
                    boolean hitX = Math.abs((hit.x + Tile.SIZE / 2) % Tile.SIZE - Tile.SIZE / 2) < .0000001;
                    boolean hitY = Math.abs((hit.y + Tile.SIZE / 2) % Tile.SIZE - Tile.SIZE / 2) < .0000001;
//                boolean hor = Math.abs(pc.pos.subtract(vc.vel).subtract(t.center()).x) > Math.abs(pc.pos.subtract(vc.vel).subtract(t.center()).y);
                    if (hitX) {
                        vc.vel = vc.vel.setX(-vc.vel.x);
                        new ParticleEmitter(pc.pos, new Vec2(vc.vel.x, 0), 12, 1, 10, 5, sc.color);
                    }
                    if (hitY) {
                        vc.vel = vc.vel.setY(-vc.vel.y);
                        new ParticleEmitter(pc.pos, new Vec2(0, vc.vel.y), 12, 1, 10, 5, sc.color);
                    }
                    vc.vel = vc.vel.add(Vec2.random(.1));
                    pc.pos = pc.pos.add(vc.vel);
                }
            }
        }
        rc.rot = vc.vel.direction();

//        Vec2 cast = Main.gameManager.elc.getEntity(Level.class).getComponent(LevelComponent.class).rayCast(pc.pos, MouseInput.mouse());
//        Graphics2D.drawLine(pc.pos, cast, Color4d.RED, 2);
//        if (cast.y != 0) {
//            System.out.println(cast);
//        }
    }

}
