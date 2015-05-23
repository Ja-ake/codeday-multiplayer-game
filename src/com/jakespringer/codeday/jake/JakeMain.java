package com.jakespringer.codeday.jake;

import com.jakespringer.codeday.jake.netinterface.NetworkSystem;
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
//    		ClientServerConnection c = new ClientServerConnection("127.0.0.1", 1337);
//    		c.setDaemon(true);
//    		c.connect();
//    		c.start();
            Main.init();
            new OtherRed(new Vec2(0, 0));
//            Main.gameManager.add(new NetworkSystem(c, Main.gameManager.elc.getEntity(Red.class), Main.gameManager.elc.getEntity(OtherRed.class)));
            Main.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        	Main.destroy();
        }
        System.exit(0);
    }
}
