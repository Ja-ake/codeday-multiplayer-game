package com.jakespringer.codeday.testgame;

import com.jakespringer.engine.core.AbstractEntity;
import com.jakespringer.engine.graphics.SpriteComponent;
import com.jakespringer.engine.graphics.SpriteSystem;
import com.jakespringer.engine.movement.PositionComponent;
import com.jakespringer.engine.movement.VelocityComponent;
import com.jakespringer.engine.movement.VelocitySystem;
import com.jakespringer.engine.util.Vec2;

public class OtherRed extends AbstractEntity {

    public OtherRed(Vec2 pos) {
        //Components
        PositionComponent pc = add(new PositionComponent(pos));
        VelocityComponent vc = add(new VelocityComponent());
        SpriteComponent sc = add(new SpriteComponent("red"));
        //Systems
        add(new VelocitySystem(pc, vc));
        add(new SpriteSystem(pc, sc));
    }
}
