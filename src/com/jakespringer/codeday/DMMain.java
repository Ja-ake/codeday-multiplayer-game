package com.jakespringer.codeday;

import com.jakespringer.codeday.dm.DM;
import com.jakespringer.codeday.level.Level;
import com.jakespringer.codeday.networking.NetworkSystem;
import com.jakespringer.codeday.ui.CommandConsole;
import com.jakespringer.engine.core.Main;
import static com.jakespringer.engine.core.Main.*;
import com.jakespringer.engine.graphics.loading.FontContainer;
import java.awt.Font;
import java.io.File;

public class DMMain {

    public static void main(String[] args) {
        System.setProperty("org.lwjgl.librarypath", new File("natives").getAbsolutePath());
        try {
            init();

            FontContainer.add("Console-Font", "Times New Roman", Font.PLAIN, 12);

            new Level("lvl");
            new DM();

            new CommandConsole();

            // NETWORKING
            NetworkSystem ns = new NetworkSystem();
            Main.gameManager.add(ns);

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
