package com.jakespringer.codeday.networking.messages;

import com.jakespringer.codeday.networking.Message;
import com.jakespringer.engine.core.AbstractEntity;
import com.jakespringer.engine.core.Main;
import com.jakespringer.engine.util.Vec2;

public class ProjectileCreateMessage extends Message {

    public String classType;
    public long id;
    public long shooter;
    public Vec2 pos;
    public Vec2 vel;

    public ProjectileCreateMessage() {
    }

    public ProjectileCreateMessage(Class<? extends AbstractEntity> classType, long id, long shooter, Vec2 pos, Vec2 vel) {
        this.classType = classType.getName();
        this.id = id;
        this.shooter = shooter;
        this.pos = pos;
        this.vel = vel;
    }

    @Override
    public void act() {
        try {
            Class.forName(classType).getConstructor(AbstractEntity.class, Vec2.class, Vec2.class).newInstance(Main.gameManager.elc.getId(shooter), pos, vel);
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
    }

    @Override
    public String toString() {
        return super.toString() + "|" + classType + "|" + id + "|" + shooter + "|" + pos + "|" + vel;
    }

}
