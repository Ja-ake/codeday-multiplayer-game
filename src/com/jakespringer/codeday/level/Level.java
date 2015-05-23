package com.jakespringer.codeday.level;

import com.jakespringer.codeday.enemy.Enemy;
import com.jakespringer.codeday.player.Player;
import com.jakespringer.engine.core.AbstractEntity;
import static com.jakespringer.engine.core.Main.*;
import com.jakespringer.engine.util.Vec2;
import java.io.File;

public class Level extends AbstractEntity {

    public static void main(String[] args) {
        System.setProperty("org.lwjgl.librarypath", new File("natives").getAbsolutePath());
        try {
            init();
            new Level("lvl");
            new Player(new Vec2());
            new Enemy(new Vec2(10, 200));
            new Enemy(Vec2.random(10));
            run();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            destroy();
        }
        System.exit(0);
    }

    public Level(String fileName) {
        //Components
        LevelComponent lc = add(new LevelComponent(fileName));
        //Systems
        add(new LevelSystem(lc));
    }
}
