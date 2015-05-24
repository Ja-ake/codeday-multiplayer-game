package com.jakespringer.codeday;

import com.jakespringer.codeday.level.Level;
import com.jakespringer.codeday.networking.NetworkSystem;
import com.jakespringer.codeday.player.Player;
import com.jakespringer.codeday.ui.CommandConsole;
import com.jakespringer.engine.core.Main;
import static com.jakespringer.engine.core.Main.*;
import com.jakespringer.engine.graphics.loading.FontContainer;
import com.jakespringer.engine.util.Vec2;
import java.awt.Font;
import java.io.File;

public abstract class ClientMain {

    public static void main(String[] args) {
        System.setProperty("org.lwjgl.librarypath", new File("natives").getAbsolutePath());
        try {
            init();

            FontContainer.add("Console-Font", "Times New Roman", Font.PLAIN, 12);

            new Level("lvl");
            Player p = new Player(new Vec2(100, 100));

            new CommandConsole();

            // NETWORKING
            Main.gameManager.add(new NetworkSystem());
            Main.gameManager.getSystem(NetworkSystem.class).disconnect();
            Main.gameManager.getSystem(NetworkSystem.class).connect("10.105.152.13", 55555);

            // END NETWORKING
            run();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            destroy();
        }
        System.exit(0);
    }
}
