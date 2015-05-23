package com.jakespringer.codeday.player;

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

    private Bullet bullet;
    private AbstractEntity shooter;
    private PositionComponent pc;
    private VelocityComponent vc;
    private RotationComponent rc;

    public BulletSystem(Bullet bullet, AbstractEntity shooter, PositionComponent pc, VelocityComponent vc, RotationComponent rc) {
        this.bullet = bullet;
        this.shooter = shooter;
        this.pc = pc;
        this.vc = vc;
        this.rc = rc;
    }

    @Override
    public void update() {
        rc.rot = vc.vel.direction();
        ArrayList<CollisionComponent> hit = CollisionUtil.listAt(pc.pos);
        for (CollisionComponent cc : hit) {
            if (cc.ae != shooter) {
                bullet.destroySelf();
            }
        }
        Tile t = Main.gameManager.elc.getEntity(Level.class).getComponent(LevelComponent.class).tileAt(pc.pos);
        if (t != null && t.isWall) {
            bullet.destroySelf();
        }
    }

}
