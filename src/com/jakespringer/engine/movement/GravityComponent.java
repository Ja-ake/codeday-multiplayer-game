package com.jakespringer.engine.movement;

import com.jakespringer.engine.core.AbstractComponent;
import com.jakespringer.engine.util.Vec3;

public class GravityComponent extends AbstractComponent {

    public Vec3 g;

    public GravityComponent() {
        g = new Vec3(0, 0, -.01);
    }
}
