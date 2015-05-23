package com.jakespringer.engine.core;

import com.jakespringer.engine.graphics.RenderManagerComponent2D;
import com.jakespringer.engine.graphics.RenderManagerComponent3D;
import com.jakespringer.engine.graphics.RenderManagerSystem2D;
import com.jakespringer.engine.graphics.SunComponent;
import com.jakespringer.engine.gui.GUISystem;

public class GameManager extends AbstractEntity {

    public RenderManagerComponent2D rmc2;
    public RenderManagerComponent3D rmc3;
    public EntityListComponent elc;

    public GameManager() {
        elc = add(new EntityListComponent());

        rmc2 = add(new RenderManagerComponent2D());
        add(new RenderManagerSystem2D(rmc2));

        add(new SunComponent());

        FPSManagerComponent fmc = add(new FPSManagerComponent());
        add(new FPSManagerSystem(fmc));

        add(new GUISystem());
    }
}
