package com.jakespringer.codeday.networking.messages;

import com.jakespringer.codeday.networking.Message;
import com.jakespringer.engine.core.AbstractEntity;
import com.jakespringer.engine.core.Main;

public class EntityDestroyMessage extends Message {

    public long id;

    public EntityDestroyMessage() {
    }

    public EntityDestroyMessage(long i) {
        id = i;
    }

    @Override
    public void act() {
        AbstractEntity e = Main.gameManager.elc.getId(id);
        if (e != null) {
            e.destroySelf();
        }
    }

    @Override
    public void initialize(String[] parts) {
        id = Long.parseLong(parts[1]);
    }

    @Override
    public String toString() {
        return super.toString() + "|" + id;
    }

}
