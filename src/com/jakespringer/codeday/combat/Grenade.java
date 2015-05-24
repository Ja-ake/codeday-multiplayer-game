package com.jakespringer.codeday.combat;

import com.jakespringer.codeday.networking.messages.EntityDestroyMessage;
import com.jakespringer.codeday.particle.ParticleEmitter;
import com.jakespringer.engine.collisions.CollisionComponent;
import com.jakespringer.engine.collisions.CollisionUtil;
import com.jakespringer.engine.core.AbstractEntity;
import com.jakespringer.engine.graphics.SpriteComponent;
import com.jakespringer.engine.graphics.SpriteSystem;
import com.jakespringer.engine.movement.PositionComponent;
import com.jakespringer.engine.movement.RotationComponent;
import com.jakespringer.engine.movement.VelocityComponent;
import com.jakespringer.engine.movement.VelocitySystem;
import com.jakespringer.engine.shapes.Circle;
import com.jakespringer.engine.util.Color4d;
import com.jakespringer.engine.util.Vec2;

public class Grenade extends AbstractEntity {

    public Grenade(AbstractEntity shooter, Vec2 pos, Vec2 vel) {
        //Components
        PositionComponent pc = add(new PositionComponent(pos));
        VelocityComponent vc = add(new VelocityComponent(vel));
        RotationComponent rc = add(new RotationComponent());
        SpriteComponent sc = add(new SpriteComponent("grenade"));
        sc.scale = new Vec2(.2, .2);
        ProjectileComponent pjc = add(new ProjectileComponent(shooter, 20, 600, 10));
        //Systems
        add(new VelocitySystem(pc, vc));
        add(new ProjectileSystem(this, pc, vc, rc, sc, pjc));
        add(new SpriteSystem(pc, rc, sc));
    }

    @Override
    public void destroySelf() {
        super.destroySelf();
        new ParticleEmitter(getComponent(PositionComponent.class).pos, new Vec2(), 150, 1, 10000, 30, new Color4d(1, .2, 0));
        for (CollisionComponent cc : CollisionUtil.listAt(new Circle(getComponent(PositionComponent.class).pos, 80))) {
            HealthComponent hc = cc.ae.getComponent(HealthComponent.class);
            if (hc != null) {
                hc.damage += 51;
            }
        }
        new EntityDestroyMessage(id).send();
    }
}
