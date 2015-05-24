package com.jakespringer.codeday.player;

import com.jakespringer.codeday.combat.HealthComponent;
import com.jakespringer.codeday.level.Level;
import com.jakespringer.codeday.level.LevelComponent;
import com.jakespringer.codeday.level.Tile;
import com.jakespringer.engine.collisions.CollisionComponent;
import com.jakespringer.engine.collisions.CollisionUtil;
import com.jakespringer.engine.core.AbstractEntity;
import com.jakespringer.engine.core.AbstractSystem;
import com.jakespringer.engine.core.Main;
import com.jakespringer.engine.movement.PositionComponent;
import com.jakespringer.engine.movement.RotationComponent;
import com.jakespringer.engine.movement.VelocityComponent;
import java.util.ArrayList;

public class BulletSystem extends AbstractSystem {

    private AbstractEntity bullet;
    private PositionComponent pc;
    private VelocityComponent vc;
    private RotationComponent rc;
    private DamageComponent dc;

    public BulletSystem(AbstractEntity bullet, PositionComponent pc, VelocityComponent vc, RotationComponent rc, DamageComponent dc) {
        this.bullet = bullet;
        this.pc = pc;
        this.vc = vc;
        this.rc = rc;
        this.dc = dc;
    }

    @Override
    public void update() {
        rc.rot = vc.vel.direction();
        ArrayList<CollisionComponent> hit = CollisionUtil.listAt(pc.pos);
        for (CollisionComponent cc : hit) {
            if (cc.ae != dc.shooter) {
                HealthComponent hc = cc.ae.getComponent(HealthComponent.class);
                if (hc != null) {
                    hc.damage += dc.damage;
                }
                bullet.destroySelf();
            }
        }
        Tile t = Main.gameManager.elc.getEntity(Level.class).getComponent(LevelComponent.class).tileAt(pc.pos);
        if (t != null && t.isWall) {
            bullet.destroySelf();
        }
    }

}
