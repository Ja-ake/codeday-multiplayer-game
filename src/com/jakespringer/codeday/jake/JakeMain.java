package com.jakespringer.codeday.jake;

import static com.jakespringer.engine.core.Main.destroy;
import static com.jakespringer.engine.core.Main.init;
import static com.jakespringer.engine.core.Main.run;

import com.jakespringer.codeday.enemy.Enemy;
import com.jakespringer.codeday.jake.netinterface.NetworkSystem;
import com.jakespringer.codeday.jake.netinterface.message.PlayerJoinMessage;
import com.jakespringer.codeday.jake.networking.Connection;
import com.jakespringer.codeday.level.Level;
import com.jakespringer.codeday.player.Player;
import com.jakespringer.engine.core.Main;
import com.jakespringer.engine.util.Vec2;

import java.io.File;
import java.io.IOException;

public abstract class JakeMain {

	public static void main(String[] args) {
        System.setProperty("org.lwjgl.librarypath", new File("natives").getAbsolutePath());
        try {

        	
            init();
            new Level("lvl");
            Player p = new Player(new Vec2());
            new Enemy(new Vec2(10, 200));
            new Enemy(Vec2.random(10));
            
            
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
            	ns.sendMessage(new PlayerJoinMessage(p.id));
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
