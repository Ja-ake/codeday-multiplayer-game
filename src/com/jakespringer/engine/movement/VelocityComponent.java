package com.jakespringer.engine.movement;

import com.jakespringer.engine.core.AbstractComponent;
import com.jakespringer.engine.util.Vec3;

public class VelocityComponent extends AbstractComponent {

    public Vec3 vel;

    public VelocityComponent() {
        vel = new Vec3();
    }
}
