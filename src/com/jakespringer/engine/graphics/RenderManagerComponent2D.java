package com.jakespringer.engine.graphics;

import com.jakespringer.engine.core.AbstractComponent;
import com.jakespringer.engine.util.Vec2;

public class RenderManagerComponent2D extends AbstractComponent {

    public Vec2 viewPos;
    public Vec2 viewSize;
    public Vec2 displaySize;
    public boolean startFullscreen;

    public RenderManagerComponent2D() {
        displaySize = new Vec2(1200, 800);
        startFullscreen = false;
        viewPos = new Vec2();
        viewSize = displaySize;
    }

    public double aspectRatio() {
        return viewSize.x / viewSize.y;
    }

    public boolean inView(Vec2 pos) {
        return pos.x > LL().x && pos.x < UR().x && pos.y > LL().y && pos.y < UR().y;
    }

    public Vec2 LL() {
        return viewPos.subtract(viewSize.multiply(.5));
    }

    public boolean nearInView(Vec2 pos, Vec2 buffer) {
        return pos.containedBy(LL().subtract(buffer), UR().add(buffer));
    }

    public Vec2 UR() {
        return viewPos.add(viewSize.multiply(.5));
    }
}
