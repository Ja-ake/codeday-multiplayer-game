package com.jakespringer.codeday.player;

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
        SpriteComponent sc = add(new SpriteComponent("red"));
        CollisionComponent cc = add(new CollisionComponent(this, pc, 16));
        //Systems
        add(new PlayerControlSystem(this, pc, vc));
        add(new VelocitySystem(pc, vc));
        add(new CollisionSystem(pc, vc, ppc, cc));
        add(new SpriteSystem(pc, sc));
        add(new PreviousPositionSystem(pc, ppc));
    }
}
