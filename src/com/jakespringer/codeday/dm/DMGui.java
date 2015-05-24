package com.jakespringer.codeday.dm;

import com.jakespringer.engine.core.AbstractSystem;
import com.jakespringer.engine.graphics.Graphics2D;
import com.jakespringer.engine.util.Color4d;
import com.jakespringer.engine.util.Vec2;

public class DMGui extends AbstractSystem {

    private DMComponent dmc;

    public DMGui(DMComponent dmc) {
        this.dmc = dmc;
    }

    @Override
    public int getLayer() {
        return 3;
    }

    @Override
    public void update() {
        Graphics2D.fillRect(new Vec2(6, 36), new Vec2(208, 28), new Color4d(0, 1, 1));
        Graphics2D.fillRect(new Vec2(10, 40), new Vec2(200, 20), Color4d.BLACK);
        Graphics2D.fillRect(new Vec2(10, 40), new Vec2(200 * dmc.threat / dmc.maxThreat, 20), new Color4d(.5, .5, .5));
    }

}
