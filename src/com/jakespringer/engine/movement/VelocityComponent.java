package com.jakespringer.engine.movement;

import com.jakespringer.engine.core.AbstractComponent;
import com.jakespringer.engine.util.Vec2;

public class VelocityComponent extends AbstractComponent {

    public Vec2 vel;

    public VelocityComponent() {
        vel = new Vec2();
    }
}
