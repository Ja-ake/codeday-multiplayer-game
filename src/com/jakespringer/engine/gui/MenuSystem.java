package com.jakespringer.engine.gui;

import com.jakespringer.codeday.netinterface.NetworkSystem;
import com.jakespringer.codeday.netinterface.message.PlayerJoinMessage;
import com.jakespringer.codeday.player.Player;
import com.jakespringer.engine.core.AbstractSystem;
import com.jakespringer.engine.core.Gamepad;
import com.jakespringer.engine.core.Keys;
import com.jakespringer.engine.core.Main;
import com.jakespringer.engine.util.Vec2;
import org.lwjgl.input.Keyboard;

public class MenuSystem extends AbstractSystem {

    private Menu menu;

    public MenuSystem(Menu menu) {
        this.menu = menu;
    }

    private void start() {
        menu.destroySelf();
        Player p = new Player(new Vec2());
        Main.gameManager.getSystem(NetworkSystem.class).sendMessage(new PlayerJoinMessage(p.id));
    }

    @Override
    public void update() {
        if (Gamepad.hasController()) {
            if (Gamepad.isPressed(7)) {
                start();
            }
        } else {
            if (Keys.isPressed(Keyboard.KEY_RETURN)) {
                start();
            }
        }
    }

}
