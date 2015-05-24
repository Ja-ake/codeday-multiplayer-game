package com.jakespringer.codeday;

import com.jakespringer.codeday.netinterface.NetworkSystem;
import com.jakespringer.codeday.networking.Connection;
import com.jakespringer.engine.core.Main;
import static com.jakespringer.engine.core.Main.*;
import com.jakespringer.engine.gui.Menu;
import java.io.File;
import java.io.IOException;

public abstract class ClientMain {

    public static void main(String[] args) {
        System.setProperty("org.lwjgl.librarypath", new File("natives").getAbsolutePath());
        try {

            init();
            new Menu();

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
