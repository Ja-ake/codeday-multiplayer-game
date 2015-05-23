package com.jakespringer.engine.movement;

import com.jakespringer.engine.core.AbstractComponent;
import com.jakespringer.engine.util.Vec3;

public class PreviousPositionComponent extends AbstractComponent {

    public Vec3 pos;

    public PreviousPositionComponent(Vec3 pos) {
        this.pos = pos;
    }
}
