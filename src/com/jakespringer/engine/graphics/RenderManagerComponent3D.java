package com.jakespringer.engine.graphics;

import com.jakespringer.engine.core.AbstractComponent;
import com.jakespringer.engine.util.Vec2;
import com.jakespringer.engine.util.Vec3;

public class RenderManagerComponent3D extends AbstractComponent {

    public Vec2 viewSize;
    public boolean startFullscreen;
    public Vec3 pos, lookAt;
    public double fov;
    public static final Vec3 UP = new Vec3(0, 0, 1);

    public RenderManagerComponent3D() {
        viewSize = new Vec2(800, 600);
        startFullscreen = false;

        pos = new Vec3(25, 15, 5);
        lookAt = new Vec3(0, 1, 5);
        fov = 70;
    }

    public double aspectRatio() {
        return viewSize.x / viewSize.y;
    }
}
