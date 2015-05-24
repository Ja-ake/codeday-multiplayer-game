package com.jakespringer.codeday.dm;

import java.awt.Font;

import org.newdawn.slick.Color;

import com.jakespringer.engine.core.AbstractSystem;
import com.jakespringer.engine.graphics.Graphics2D;
import com.jakespringer.engine.graphics.loading.FontContainer;
import com.jakespringer.engine.graphics.loading.SpriteContainer;
import com.jakespringer.engine.util.Color4d;
import com.jakespringer.engine.util.Vec2;

public class DMGui extends AbstractSystem {

    private DMComponent dmc;
    public Color4d backgroundAssault = new Color4d(0.2, 0.2, 0.2, 0.8);
    public Color4d backgroundScout = new Color4d(0.2, 0.2, 0.2, 0.8);

    public DMGui(DMComponent dmc) {
        this.dmc = dmc;
        
        FontContainer.add("Monster", "Times New Roman", Font.PLAIN, 24);
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
        
        Graphics2D.fillRect(new Vec2(40-30, 200), new Vec2(200, 46), backgroundAssault);
        Graphics2D.drawSprite(SpriteContainer.loadSprite("enemy normal"), new Vec2(102-30, 224), new Vec2(1, 1), 0.0, Color4d.WHITE);
        Graphics2D.drawText("Assault", "Monster", new Vec2(130-30, 236), Color.lightGray);
        
        Graphics2D.fillRect(new Vec2(40-30, 200+60), new Vec2(200, 46), backgroundScout);
        Graphics2D.drawSprite(SpriteContainer.loadSprite("enemyfast"), new Vec2(82-30, 224+60), new Vec2(1, 1), 0.0, Color4d.WHITE);
        Graphics2D.drawText("Scout", "Monster", new Vec2(130-30, 236+60), Color.lightGray);
        
        Graphics2D.fillRect(new Vec2(40-30, 200+120), new Vec2(200, 46), backgroundScout);
        Graphics2D.drawSprite(SpriteContainer.loadSprite("enemy normal"), new Vec2(82-30, 224+120), new Vec2(1, 1), 0.0, Color4d.WHITE);
        Graphics2D.drawText("Sniper", "Monster", new Vec2(130-30, 236+120), Color.lightGray);
    }
}
