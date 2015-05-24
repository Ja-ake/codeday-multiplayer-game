package com.jakespringer.codeday.networking.messages;

import com.jakespringer.codeday.networking.Message;
import com.jakespringer.codeday.player.OtherPlayer;
import com.jakespringer.codeday.ui.CommandConsole;
import com.jakespringer.engine.core.Main;
import com.jakespringer.engine.util.Vec2;

public class PlayerJoinMessage extends Message {

    public long id;

    public PlayerJoinMessage() {
    }

    public PlayerJoinMessage(long id) {
        this.id = id;
    }

    @Override
    public void act() {
        if (Main.gameManager.elc.getId(id) != null) {
            return;
        }
        OtherPlayer p = new OtherPlayer(new Vec2());
        p.id = this.id;
        CommandConsole.println("A player has joined.");
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
