package com.jakespringer.engine.movement;

import com.jakespringer.engine.core.AbstractComponent;
import com.jakespringer.engine.util.Vec3;

public class PositionComponent extends AbstractComponent {

    public Vec3 pos;

    public PositionComponent() {
        this(new Vec3());
    }

    public PositionComponent(Vec3 pos) {
        this.pos = pos;
    }
}
