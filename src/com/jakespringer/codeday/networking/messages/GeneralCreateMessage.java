package com.jakespringer.codeday.networking.messages;

import com.jakespringer.codeday.networking.Message;
import com.jakespringer.codeday.networking.NetworkSystem;
import com.jakespringer.engine.core.AbstractEntity;
import com.jakespringer.engine.core.Main;
import com.jakespringer.engine.util.Vec2;
import java.lang.reflect.InvocationTargetException;

public class GeneralCreateMessage extends Message {

    public String classType;
    public long id;
    public Vec2 pos;

    public GeneralCreateMessage() {
    }

    public GeneralCreateMessage(Class<? extends AbstractEntity> classType, long id, Vec2 pos) {
        this.classType = classType.getName();
        this.id = id;
        this.pos = pos;
    }

    @Override
    public void act() {
        try {
            AbstractEntity ae = (AbstractEntity) Class.forName(classType).getConstructor(Vec2.class).newInstance(pos);
            ae.id = id;
        } catch (InvocationTargetException ex) {
            System.out.println(classType);
            ex.printStackTrace();
            System.out.println(ex.getTargetException());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(String[] parts) {
        classType = parts[1];
        id = Long.parseLong(parts[2]);
        pos = Vec2.parseVec2(parts[3]);
    }

    @Override
    public void send() {
        final Message thus = this;
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                Main.gameManager.getSystem(NetworkSystem.class).sendMessage(thus);
            }
        }).start();
    }

    @Override
    public String toString() {
        return super.toString() + "|" + classType + "|" + id + "|" + pos;
    }

}
