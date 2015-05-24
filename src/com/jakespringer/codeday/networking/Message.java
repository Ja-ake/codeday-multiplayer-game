package com.jakespringer.codeday.networking;

import com.jakespringer.engine.core.Main;

public abstract class Message {

    public abstract void act();

    public abstract void initialize(String[] parts);

    public void send() {
        NetworkSystem ns = Main.gameManager.getSystem(NetworkSystem.class);
        if (ns != null) {
            ns.sendMessage(this);
        } else {
            Main.gameManager.getSystem(ServerNetworkSystem.class).sendMessage(this);
        }
    }

    @Override
    public String toString() {
        return getClass().getName();
    }
}
