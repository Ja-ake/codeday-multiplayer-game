package com.jakespringer.engine.movement;

import com.jakespringer.engine.core.AbstractComponent;
import com.jakespringer.engine.util.Vec2;

public class PositionComponent extends AbstractComponent {

    public Vec2 pos;

    public PositionComponent() {
        this(new Vec2());
    }

    public PositionComponent(Vec2 pos) {
        this.pos = pos;
    }

}
