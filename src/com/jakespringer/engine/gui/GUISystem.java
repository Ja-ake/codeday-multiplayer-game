package com.jakespringer.engine.gui;

import com.jakespringer.engine.core.AbstractSystem;
import com.jakespringer.engine.core.Main;
import com.jakespringer.engine.graphics.Camera;
import com.jakespringer.engine.util.Vec2;

public class GUISystem extends AbstractSystem {

    @Override
    public int getLayer() {
        return 3;
    }

    @Override
    public void update() {
        Camera.setProjection2D(new Vec2(), Main.gameManager.rmc2.viewSize);
    }

}
