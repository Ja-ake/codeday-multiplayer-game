package com.jakespringer.codeday;

import static com.jakespringer.engine.core.Main.destroy;
import static com.jakespringer.engine.core.Main.init;
import static com.jakespringer.engine.core.Main.run;

import java.awt.Font;
import java.io.File;

import com.jakespringer.codeday.combat.HealthComponent;
import com.jakespringer.codeday.level.Level;
import com.jakespringer.codeday.level.Tile;
import com.jakespringer.codeday.networking.NetworkSystem;
import com.jakespringer.codeday.player.Player;
import com.jakespringer.codeday.tiled.TiledObject;
import com.jakespringer.codeday.tiled.TiledTile;
import com.jakespringer.codeday.tiled.TiledXMLParser;
import com.jakespringer.codeday.ui.CommandConsole;
import com.jakespringer.engine.core.Main;
import com.jakespringer.engine.graphics.Graphics2D;
import com.jakespringer.engine.graphics.data.Texture;
import com.jakespringer.engine.graphics.loading.FontContainer;

public class TiledTest {
    public static void main(String[] args) {
        System.setProperty("org.lwjgl.librarypath", new File("natives").getAbsolutePath());
        try {
            init();

            FontContainer.add("Console-Font", "Times New Roman", Font.PLAIN, 12);

            
            
            
            new Level("lvl");
//            Player p = new Player(new Vec2());
//            p.getComponent(HealthComponent.class).health = 1000;

            new CommandConsole();

            // NETWORKING
            Main.gameManager.add(new NetworkSystem());

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
