package com.jakespringer.codeday.player;

import com.jakespringer.codeday.combat.HealthComponent;
import com.jakespringer.codeday.networking.messages.EntityDestroyMessage;
import com.jakespringer.engine.collisions.CollisionComponent;
import com.jakespringer.engine.collisions.CollisionSystem;
import com.jakespringer.engine.core.AbstractEntity;
import com.jakespringer.engine.graphics.SpriteComponent;
import com.jakespringer.engine.graphics.SpriteSystem;
import com.jakespringer.engine.movement.*;
import com.jakespringer.engine.util.Vec2;

public class Player extends AbstractEntity {

    public Player(Vec2 pos) {
        //Components
        PositionComponent pc = add(new PositionComponent(pos));
        PreviousPositionComponent ppc = add(new PreviousPositionComponent(pos));
        VelocityComponent vc = add(new VelocityComponent());
        RotationComponent rc = add(new RotationComponent());
        SpriteComponent sc = add(new SpriteComponent("player2"));
        sc.scale = new Vec2(.5, .5);
        CollisionComponent cc = add(new CollisionComponent(this, pc, 25));
        HealthComponent hc = add(new HealthComponent(500));
        //Systems
        add(new PlayerControlSystem(this, pc, vc, rc));
        add(new VelocitySystem(pc, vc));
        add(new CollisionSystem(pc, vc, ppc, cc));
        add(new SpriteSystem(pc, rc, sc));
        add(new PreviousPositionSystem(pc, ppc));
        add(new PlayerHealthSystem(pc, hc));
    }

    @Override
    public void destroySelf() {
        super.destroySelf();
        new EntityDestroyMessage(id).send();
    }
}
