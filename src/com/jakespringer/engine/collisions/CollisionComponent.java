package com.jakespringer.engine.collisions;

import com.jakespringer.codeday.level.Level;
import com.jakespringer.codeday.level.LevelComponent;
import com.jakespringer.engine.core.AbstractComponent;
import com.jakespringer.engine.core.AbstractEntity;
import com.jakespringer.engine.core.Main;
import com.jakespringer.engine.movement.PositionComponent;
import com.jakespringer.engine.util.Vec2;
import java.util.HashSet;

public class CollisionComponent extends AbstractComponent {

    public AbstractEntity ae;
    public PositionComponent pc;
    public double width;
    public HashSet<CollisionComponent> collisions;
    public int xHit, yHit, zHit;

    public CollisionComponent(AbstractEntity ae, PositionComponent pc, double width) {
        this.ae = ae;
        this.pc = pc;
        this.width = width;
        collisions = new HashSet();
    }

    public boolean open(Vec2 pos) {
        LevelComponent lc = Main.gameManager.elc.getEntity(Level.class).getComponent(LevelComponent.class);
        for (int i = (int) Math.floor(pos.x - width); i < pos.x + width; i++) {
            for (int j = (int) Math.floor(pos.y - width); j < pos.y + width; j++) {
                if (lc.tileAt(new Vec2(i, j)) != null && lc.tileAt(new Vec2(i, j)).isWall) {
                    return false;
                }
            }
        }
        return true;
    }
}
