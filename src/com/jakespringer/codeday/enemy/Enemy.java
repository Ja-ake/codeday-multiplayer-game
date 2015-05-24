package com.jakespringer.codeday.enemy;

import com.jakespringer.codeday.networking.messages.EntityDestroyMessage;
import com.jakespringer.engine.core.AbstractEntity;
import com.jakespringer.engine.util.Vec2;

public class Enemy extends AbstractEntity {

    public Enemy(Vec2 pos) {
    }

    public Enemy() {
    }

    @Override
    public void destroySelf() {
        super.destroySelf();
        new EntityDestroyMessage(id).send();
    }
}
