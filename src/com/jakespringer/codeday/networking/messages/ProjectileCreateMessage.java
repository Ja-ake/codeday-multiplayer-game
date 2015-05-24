package com.jakespringer.codeday.networking.messages;

import com.jakespringer.codeday.networking.Message;
import com.jakespringer.engine.core.AbstractEntity;
import com.jakespringer.engine.core.Main;
import com.jakespringer.engine.util.Color4d;
import com.jakespringer.engine.util.Vec2;

public class ProjectileCreateMessage extends Message {

    public String classType;
    public long id;
    public long shooter;
    public Vec2 pos;
    public Vec2 vel;
    public double r, g, b, a;

    public ProjectileCreateMessage() {
    }

    public ProjectileCreateMessage(Class<? extends AbstractEntity> classType, long id, long shooter, Vec2 pos, Vec2 vel, Color4d color) {
        this.classType = classType.getName();
        this.id = id;
        this.shooter = shooter;
        this.pos = pos;
        this.vel = vel;
        r = color.r;
        g = color.g;
        b = color.b;
        a = color.a;
    }

    @Override
    public void act() {
        try {
            Class.forName(classType).getConstructor(AbstractEntity.class, Vec2.class, Vec2.class, Color4d.class)
                    .newInstance(Main.gameManager.elc.getId(shooter), pos, vel, new Color4d(r, g, b, a));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(String[] parts) {
        classType = parts[1];
        id = Long.parseLong(parts[2]);
        shooter = Long.parseLong(parts[3]);
        pos = Vec2.parseVec2(parts[4]);
        vel = Vec2.parseVec2(parts[5]);
        r = Double.parseDouble(parts[6]);
        g = Double.parseDouble(parts[7]);
        b = Double.parseDouble(parts[8]);
        a = Double.parseDouble(parts[9]);
    }

    @Override
    public String toString() {
        return super.toString() + "|" + classType + "|" + id + "|" + shooter + "|" + pos + "|" + vel + "|" + r + "|" + g + "|" + b + "|" + a;
    }

}
