package com.jakespringer.codeday.netinterface.message;

import com.jakespringer.codeday.netinterface.Message;
import com.jakespringer.codeday.ui.CommandConsole;
import com.jakespringer.engine.core.AbstractEntity;
import com.jakespringer.engine.core.Main;

public class PlayerLeaveMessage extends Message {

    public long id;

    public PlayerLeaveMessage() {
    }

    public PlayerLeaveMessage(long i) {
        id = i;
    }

    @Override
    public void act() {
        AbstractEntity e = Main.gameManager.elc.getId(id);
        if (e != null) {
            e.destroySelf();
            CommandConsole.println("A player has left.");
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
