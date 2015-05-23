package com.jakespringer.engine.movement;

import com.jakespringer.engine.core.AbstractComponent;
import com.jakespringer.engine.util.Vec2;

public class PreviousPositionComponent extends AbstractComponent {

    public Vec2 pos;

    public PreviousPositionComponent(Vec2 pos) {
        this.pos = pos;
    }
}
