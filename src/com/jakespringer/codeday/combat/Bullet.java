package com.jakespringer.codeday.combat;

import com.jakespringer.codeday.particle.ParticleEmitter;
import com.jakespringer.engine.core.AbstractEntity;
import com.jakespringer.engine.graphics.SpriteComponent;
import com.jakespringer.engine.graphics.SpriteSystem;
import com.jakespringer.engine.movement.PositionComponent;
import com.jakespringer.engine.movement.RotationComponent;
import com.jakespringer.engine.movement.VelocityComponent;
import com.jakespringer.engine.movement.VelocitySystem;
import com.jakespringer.engine.util.Vec2;

public class Bullet extends AbstractEntity {

    public Bullet(AbstractEntity shooter, Vec2 pos, Vec2 vel) {
        //Components
        PositionComponent pc = add(new PositionComponent(pos));
        VelocityComponent vc = add(new VelocityComponent(vel));
        RotationComponent rc = add(new RotationComponent());
        rc.rot = vel.direction();
        SpriteComponent sc = add(new SpriteComponent("bullet two"));
        //sc.scale = new Vec2(2, 2);
        ProjectileComponent dc = add(new ProjectileComponent(shooter, 10, 600, 2));
        //Systems
        add(new VelocitySystem(pc, vc));
        add(new ProjectileSystem(this, pc, vc, rc, sc, dc));
        add(new SpriteSystem(pc, rc, sc));
    }

    @Override
    public void destroySelf() {
        super.destroySelf();
        new ParticleEmitter(getComponent(PositionComponent.class).pos, getComponent(VelocityComponent.class).vel.reverse(), 12, 1, 50, 5, getComponent(SpriteComponent.class).color);
    }
}
