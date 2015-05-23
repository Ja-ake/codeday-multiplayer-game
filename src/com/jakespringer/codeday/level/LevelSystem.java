package com.jakespringer.codeday.level;

import com.jakespringer.engine.core.AbstractSystem;
import static org.lwjgl.opengl.GL11.glCallList;

public class LevelSystem extends AbstractSystem {

    private LevelComponent lc;

    public LevelSystem(LevelComponent lc) {
        this.lc = lc;
    }

    @Override
    public void update() {
        glCallList(lc.list);
    }
}
