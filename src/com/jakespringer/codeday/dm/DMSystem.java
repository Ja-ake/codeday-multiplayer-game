package com.jakespringer.codeday.dm;

import com.jakespringer.codeday.enemy.AssaultEnemy;
import com.jakespringer.codeday.enemy.Enemy;
import com.jakespringer.codeday.enemy.ScoutEnemy;
import com.jakespringer.codeday.networking.messages.GeneralCreateMessage;
import com.jakespringer.engine.collisions.CollisionComponent;
import com.jakespringer.engine.core.AbstractSystem;
import com.jakespringer.engine.core.Keys;
import com.jakespringer.engine.core.Main;
import com.jakespringer.engine.core.MouseInput;
import com.jakespringer.engine.graphics.Graphics2D;
import com.jakespringer.engine.graphics.RenderManagerComponent2D;
import com.jakespringer.engine.movement.PositionComponent;
import com.jakespringer.engine.util.Color4d;
import com.jakespringer.engine.util.Vec2;
import java.lang.reflect.InvocationTargetException;
import org.lwjgl.input.Keyboard;

public class DMSystem extends AbstractSystem {

    private DMComponent dmc;
    private DM dm;
    private Class current = AssaultEnemy.class;

    public DMSystem(DMComponent dmc, DM d) {
        this.dmc = dmc;
        this.dm = d;
    }

    @Override
    public void update() {
        dmc.threat += .01;
        if (dmc.threat > dmc.maxThreat) {
            dmc.threat = dmc.maxThreat;
        }
        if (MouseInput.isPressed(0)) {
            Vec2 mouse = MouseInput.mouseScreen();

            if (mouse.x < 220 && mouse.y < 316) {
            } else if (dmc.threat > 1) {
                try {
                    Enemy e = (Enemy) current.getConstructor(Vec2.class).newInstance(MouseInput.mouse());
                    if (threatAt(MouseInput.mouse()) < 3 && e.getComponent(CollisionComponent.class).open(MouseInput.mouse())) {
                        dmc.threat -= 1;
                        new GeneralCreateMessage(e.getClass(), e.id, e.getComponent(PositionComponent.class).pos).send();
                    } else {
                        e.destroySelf();
                    }
                } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e1) {
                    e1.printStackTrace();
                }
            }
        }

        if (MouseInput.isDown(0)) {
            Vec2 mouse = MouseInput.mouseScreen();

            if (mouse.x < 220 && mouse.y < 316) {
                if (mouse.y > 250) {
                    dm.getSystem(DMGui.class).backgroundScout = new Color4d(0.1, 0.1, 0.1, 0.8);
                    current = ScoutEnemy.class;
                } else if (mouse.y > 190) {
                    dm.getSystem(DMGui.class).backgroundAssault = new Color4d(0.1, 0.1, 0.1, 0.8);
                    current = AssaultEnemy.class;
                }
            }
        } else {
            dm.getSystem(DMGui.class).backgroundAssault = new Color4d(0.2, 0.2, 0.2, 0.8);
            dm.getSystem(DMGui.class).backgroundScout = new Color4d(0.2, 0.2, 0.2, 0.8);
        }
        RenderManagerComponent2D rmc = Main.gameManager.rmc2;
        if (Keys.isDown(Keyboard.KEY_W)) {
            rmc.viewPos = rmc.viewPos.setY(rmc.viewPos.y + 5);
        }
        if (Keys.isDown(Keyboard.KEY_A)) {
            rmc.viewPos = rmc.viewPos.setX(rmc.viewPos.x - 5);
        }
        if (Keys.isDown(Keyboard.KEY_S)) {
            rmc.viewPos = rmc.viewPos.setY(rmc.viewPos.y - 5);
        }
        if (Keys.isDown(Keyboard.KEY_D)) {
            rmc.viewPos = rmc.viewPos.setX(rmc.viewPos.x + 5);
        }
        for (Enemy e : Main.gameManager.elc.getEntityList(Enemy.class)) {
            Graphics2D.fillEllipse(e.getComponent(PositionComponent.class).pos, new Vec2(150, 150), Color4d.RED.setA(.2), 40);
        }
    }

    private double threatAt(Vec2 pos) {
        double r = 0;
        for (Enemy e : Main.gameManager.elc.getEntityList(Enemy.class)) {
            if (pos.subtract(e.getComponent(PositionComponent.class).pos).lengthSquared() < 150 * 150) {
                r += 1;
            }
        }
        return r;
    }

}
