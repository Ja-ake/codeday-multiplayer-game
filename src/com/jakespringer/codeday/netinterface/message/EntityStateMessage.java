package com.jakespringer.codeday.netinterface.message;

import com.jakespringer.codeday.netinterface.Message;
import com.jakespringer.engine.core.AbstractEntity;
import com.jakespringer.engine.core.Main;
import com.jakespringer.engine.movement.PositionComponent;
import com.jakespringer.engine.movement.RotationComponent;
import com.jakespringer.engine.movement.VelocityComponent;
import com.jakespringer.engine.util.Vec2;

public class EntityStateMessage extends Message {

    public long id;
    public double x, y;
    public double vx, vy;
    public double rot;

    public EntityStateMessage() {
    }

    public EntityStateMessage(long i, double xv, double yv, double vxv, double vyv, double r) {
        id = i;
        x = xv;
        y = yv;
        vx = vxv;
        vy = vyv;
        rot = r;
    }

    @Override
    public void act() {
        AbstractEntity e = Main.gameManager.elc.getId(id);
        if (e == null) {
            return;
        }
        e.getComponent(PositionComponent.class).pos = new Vec2(x, y);
        e.getComponent(VelocityComponent.class).vel = new Vec2(vx, vy);
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
