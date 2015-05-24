package com.jakespringer.codeday.networking.messages;

import com.jakespringer.codeday.networking.Message;
import com.jakespringer.engine.core.AbstractEntity;
import com.jakespringer.engine.util.Vec2;

public class GeneralCreateMessage extends Message {

    public String classType;
    public long id;
    public Vec2 pos;

    public GeneralCreateMessage() {
    }

    public GeneralCreateMessage(Class<? extends AbstractEntity> classType, long id, Vec2 pos) {
        System.out.println("general const");
        this.classType = classType.getName();
        this.id = id;
        this.pos = pos;
    }

    @Override
    public void act() {
        System.out.println("created entity");
        try {
            Class.forName(classType).getConstructor(Vec2.class).newInstance(pos);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(String[] parts) {
        System.out.println("init");
        classType = parts[1];
        id = Long.parseLong(parts[2]);
        pos = Vec2.parseVec2(parts[3]);
    }

    @Override
    public String toString() {
        return super.toString() + "|" + classType + "|" + id + "|" + pos;
    }

}
