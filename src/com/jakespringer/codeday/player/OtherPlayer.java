package com.jakespringer.codeday.player;

import com.jakespringer.engine.collisions.CollisionComponent;
import com.jakespringer.engine.collisions.CollisionSystem;
import com.jakespringer.engine.core.AbstractEntity;
import com.jakespringer.engine.graphics.SpriteComponent;
import com.jakespringer.engine.graphics.SpriteSystem;
import com.jakespringer.engine.movement.*;
import com.jakespringer.engine.util.Vec2;

public class OtherPlayer extends AbstractEntity {

    public OtherPlayer(Vec2 pos) {
        //Components
        PositionComponent pc = add(new PositionComponent(pos));
        PreviousPositionComponent ppc = add(new PreviousPositionComponent(pos));
        VelocityComponent vc = add(new VelocityComponent());
        RotationComponent rc = add(new RotationComponent());
        SpriteComponent sc = add(new SpriteComponent("player"));
        CollisionComponent cc = add(new CollisionComponent(this, pc, 16));
        //Systems
        add(new VelocitySystem(pc, vc));
        add(new CollisionSystem(pc, vc, ppc, cc));
        add(new SpriteSystem(pc, rc, sc));
        add(new PreviousPositionSystem(pc, ppc));
    }
}
