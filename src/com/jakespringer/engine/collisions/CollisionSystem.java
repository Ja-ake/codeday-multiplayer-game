package com.jakespringer.engine.collisions;

import com.jakespringer.engine.core.AbstractSystem;
import com.jakespringer.engine.core.Main;
import com.jakespringer.engine.movement.PositionComponent;
import com.jakespringer.engine.movement.PreviousPositionComponent;
import com.jakespringer.engine.movement.VelocityComponent;
import com.jakespringer.engine.util.Util;
import com.jakespringer.engine.util.Vec2;

public class CollisionSystem extends AbstractSystem {

    private PositionComponent pc;
    private VelocityComponent vc;
    private PreviousPositionComponent ppc;
    private CollisionComponent cc;

    public CollisionSystem(PositionComponent pc, VelocityComponent vc, PreviousPositionComponent ppc, CollisionComponent cc) {
        this.pc = pc;
        this.vc = vc;
        this.ppc = ppc;
        this.cc = cc;
    }

    @Override
    public void update() {
        cc.collisions.clear();
        cc.xHit = cc.yHit = cc.zHit = 0;
        //Collide with units
        for (CollisionComponent other : Main.gameManager.elc.getComponentList(CollisionComponent.class)) {
            if (other != cc) {
                Vec2 diff = other.pc.pos.subtract(pc.pos);
                if (diff.lengthSquared() < 3 * cc.width * other.width) {
                    cc.collisions.add(other);
                    other.collisions.add(cc);
                    Vec2 diffN = diff.normalize();
                    Vec2 change = diff.subtract(diffN.multiply(cc.width + other.width)).multiply(.1);
                    pc.pos = pc.pos.add(change);
                    other.pc.pos = other.pc.pos.subtract(change);
                    vc.vel = vc.vel.subtract(diffN.multiply(vc.vel.dot(diffN)));
                }
            }
        }
        //Collide with walls
        if (!cc.open(pc.pos)) {
            Vec2 diff = pc.pos.subtract(ppc.pos);
            pc.pos = ppc.pos;
            if (!cc.open(pc.pos)) {
                pc.pos = pc.pos.add(diff);
                return;
                //throw new RuntimeException("Creature trapped in wall");
            }
            for (int i = 0; i < 10; i++) {
                if (cc.open(pc.pos.add(new Vec2(diff.x * .1, 0)))) {
                    pc.pos = pc.pos.add(new Vec2(diff.x * .1, 0));
                } else {
                    cc.xHit = (int) Util.sign(diff.x);
                    vc.vel = vc.vel.setX(0);
                    break;
                }
            }
            for (int i = 0; i < 10; i++) {
                if (cc.open(pc.pos.add(new Vec2(0, diff.y * .1)))) {
                    pc.pos = pc.pos.add(new Vec2(0, diff.y * .1));
                } else {
                    cc.yHit = (int) Util.sign(diff.y);
                    vc.vel = vc.vel.setY(0);
                    break;
                }
            }
        }
    }
}
