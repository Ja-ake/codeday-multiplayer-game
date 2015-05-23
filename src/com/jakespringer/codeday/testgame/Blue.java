package com.jakespringer.codeday.testgame;

import com.jakespringer.engine.core.AbstractEntity;
import com.jakespringer.engine.graphics.SpriteComponent;
import com.jakespringer.engine.graphics.SpriteSystem;
import com.jakespringer.engine.movement.PositionComponent;
import com.jakespringer.engine.util.Vec2;

public class Blue extends AbstractEntity {

    public Blue(Vec2 pos) {
        //Components
        PositionComponent pc = add(new PositionComponent(pos));
        SpriteComponent sc = add(new SpriteComponent("blue"));
        //Systems
        add(new SpriteSystem(pc, sc));
    }
}
