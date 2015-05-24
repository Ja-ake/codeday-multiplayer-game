package com.jakespringer.codeday;

import com.jakespringer.codeday.level.Level;
import com.jakespringer.codeday.networking.ChatServer;
import com.jakespringer.codeday.networking.ServerNetworkSystem;
import com.jakespringer.codeday.ui.CommandConsole;
import com.jakespringer.engine.core.Main;
import static com.jakespringer.engine.core.Main.*;
import com.jakespringer.engine.graphics.Camera;
import com.jakespringer.engine.graphics.loading.FontContainer;
import com.jakespringer.engine.util.Vec2;
import java.awt.Font;
import java.io.File;

public class ServerMain {

    public static void main(String[] args) {
        ChatServer cs = new ChatServer(55555);
        System.setProperty("org.lwjgl.librarypath", new File("natives").getAbsolutePath());
        try {
            init();
            Camera.setDisplayMode(new Vec2(40, 40), false);

            FontContainer.add("Console-Font", "Times New Roman", Font.PLAIN, 12);

            new Level("lvl");
            new CommandConsole();
            Main.gameManager.add(new ServerNetworkSystem(cs));

            run();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            destroy();
        }
        System.exit(0);
    }
}
