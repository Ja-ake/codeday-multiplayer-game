package com.jakespringer.codeday.networking;

import com.jakespringer.engine.core.Main;

public abstract class Message {

    public abstract void act();

    public abstract void initialize(String[] parts);

    public void send() {
        Main.gameManager.getSystem(NetworkSystem.class).sendMessage(this);
    }

    @Override
    public String toString() {
        return getClass().getName();
    }
}
