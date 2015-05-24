package com.jakespringer.codeday;

import com.jakespringer.codeday.netinterface.NetworkSystem;
import com.jakespringer.codeday.networking.Connection;
import com.jakespringer.codeday.ui.CommandConsole;
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
            new CommandConsole();

            // NETWORKING
            boolean networking = true;
            Connection conn = new Connection();
            try {
                conn.start("localhost", 1225);
            } catch (IOException ioe) {
                ioe.printStackTrace();
                networking = false;
            }

            if (networking) {
                NetworkSystem ns = new NetworkSystem(conn);
                Main.gameManager.add(ns);
            }

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
