package com.jakespringer.codeday.jake;

import com.jakespringer.codeday.jake.netinterface.NetworkSystem;
import com.jakespringer.codeday.jake.networking.Connection;
import com.jakespringer.codeday.testgame.OtherRed;
import com.jakespringer.codeday.testgame.Red;
import com.jakespringer.engine.core.Main;
import com.jakespringer.engine.util.Vec2;

import java.io.File;
import java.io.IOException;

public abstract class JakeMain {

    public static void main(String[] args) throws IOException {
        System.setProperty("org.lwjgl.librarypath", new File("natives").getAbsolutePath());
        try {
    		Connection c = new Connection();
    		c.start("127.0.0.1", 1225);
    		
//    		c.setDaemon(true);
//    		c.connect();
//    		c.start();
            Main.init();
            new Red(new Vec2());
            new OtherRed(new Vec2());
            Main.gameManager.add(new NetworkSystem(c));
            Main.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        	Main.destroy();
        }
        System.exit(0);
    }
}
