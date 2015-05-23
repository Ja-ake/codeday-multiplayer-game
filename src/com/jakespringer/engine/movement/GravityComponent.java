package com.jakespringer.engine.movement;

import com.jakespringer.engine.core.AbstractComponent;
import com.jakespringer.engine.util.Vec2;

public class GravityComponent extends AbstractComponent {

    public Vec2 g;

    public GravityComponent() {
        g = new Vec2(0, -.6);
    }
}
