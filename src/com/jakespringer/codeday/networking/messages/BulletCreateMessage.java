package com.jakespringer.codeday.networking.messages;

import com.jakespringer.codeday.networking.Message;
import com.jakespringer.codeday.combat.Bullet;
import com.jakespringer.engine.core.Main;
import com.jakespringer.engine.movement.RotationComponent;
import com.jakespringer.engine.util.Vec2;

public class BulletCreateMessage extends Message {

    public long id;
    public double x, y;
    public double vx, vy;
    public double rot;

    public BulletCreateMessage() {
    }

    public BulletCreateMessage(long i, double xv, double yv, double vxv, double vyv, double r) {
        id = i;
        x = xv;
        y = yv;
        vx = vxv;
        vy = vyv;
        rot = r;
    }

    @Override
    public void act() {
        Bullet e = new Bullet(Main.gameManager.elc.getId(id), new Vec2(x, y), new Vec2(vx, vy));
        e.getComponent(RotationComponent.class).rot = rot;
    }

    @Override
    public void initialize(String[] parts) {
        id = Long.parseLong(parts[1]);
        x = Double.parseDouble(parts[2]);
        y = Double.parseDouble(parts[3]);
        vx = Double.parseDouble(parts[4]);
        vy = Double.parseDouble(parts[5]);
        rot = Double.parseDouble(parts[6]);
    }

    @Override
    public String toString() {
        return super.toString() + "|" + id + "|" + x + "|" + y + "|" + vx + "|" + vy + "|" + rot;
    }

}
