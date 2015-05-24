package com.jakespringer.codeday;

import com.jakespringer.codeday.level.Level;
import com.jakespringer.codeday.networking.ChatServer;
import com.jakespringer.codeday.ui.CommandConsole;
import static com.jakespringer.engine.core.Main.*;
import com.jakespringer.engine.graphics.loading.FontContainer;
import java.awt.Font;
import java.io.File;

public class ServerMain {

    public static void main(String[] args) {
        new ChatServer(55555);
        System.setProperty("org.lwjgl.librarypath", new File("natives").getAbsolutePath());
        try {
            init();

            FontContainer.add("Console-Font", "Times New Roman", Font.PLAIN, 12);

            new Level("lvl");
            new CommandConsole();

            run();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            destroy();
        }
        System.exit(0);
    }
}
