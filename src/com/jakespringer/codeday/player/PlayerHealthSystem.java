package com.jakespringer.codeday.player;

import com.jakespringer.codeday.combat.HealthComponent;
import com.jakespringer.engine.core.AbstractSystem;
import com.jakespringer.engine.core.Main;
import com.jakespringer.engine.graphics.Graphics2D;
import com.jakespringer.engine.movement.PositionComponent;
import com.jakespringer.engine.util.Color4d;
import com.jakespringer.engine.util.Vec2;

public class PlayerHealthSystem extends AbstractSystem {

    private PositionComponent pc;
    private HealthComponent hc;

    public PlayerHealthSystem(PositionComponent pc, HealthComponent hc) {
        this.pc = pc;
        this.hc = hc;
    }

    @Override
    public int getLayer() {
        return 3;
    }

    @Override
    public void update() {
        hc.health -= hc.damage * .2;
        hc.damage *= .8;
        double amt = hc.damage / 20;

        Graphics2D.fillRect(new Vec2(10, 40), new Vec2(200, 20), Color4d.BLACK);
        Graphics2D.fillRect(new Vec2(10, 40), new Vec2(200 * hc.health / hc.maxHealth, 20), new Color4d(1, .2, 1));
        Graphics2D.fillRect(new Vec2(), Main.gameManager.rmc2.viewSize, Color4d.RED.setA(amt));

        if (hc.health <= 0) {
//            System.exit(0);
            hc.health = hc.maxHealth;
            pc.pos = Vec2.random(1000).add(new Vec2(1100, 1100));
        }
    }

}
