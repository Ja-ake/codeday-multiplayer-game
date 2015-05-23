package com.jakespringer.engine.core;

import com.jakespringer.engine.graphics.RenderManagerComponent;
import com.jakespringer.engine.graphics.RenderManagerSystem;
import com.jakespringer.engine.graphics.SunComponent;
import com.jakespringer.engine.gui.GUISystem;

public class GameManager extends AbstractEntity {

    public RenderManagerComponent rmc;
    public EntityListComponent elc;

    public GameManager() {
        elc = add(new EntityListComponent());

        rmc = add(new RenderManagerComponent());
        add(new RenderManagerSystem(rmc));

        add(new SunComponent());

        FPSManagerComponent fmc = add(new FPSManagerComponent());
        add(new FPSManagerSystem(fmc));

        add(new GUISystem());
    }
}
