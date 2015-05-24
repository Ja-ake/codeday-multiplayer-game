package com.jakespringer.codeday.level;

import com.jakespringer.engine.graphics.data.Texture;
import com.jakespringer.engine.graphics.loading.SpriteContainer;
import com.jakespringer.engine.util.Vec2;

public class Tile {

    public static final int SIZE = 32;
    public int x;
    public int y;
    public boolean isWall;
    public Texture tex;
    public int zone;

    public Tile(int x, int y, boolean isWall) {
        this.x = x;
        this.y = y;
        this.isWall = isWall;
        if (isWall) {
            tex = SpriteContainer.loadSprite("wall");
        } else {
            tex = SpriteContainer.loadSprite("floor");
        }
    }

    public Vec2 LL() {
        return new Vec2(x * SIZE, y * SIZE);
    }

    public Vec2 LR() {
        return new Vec2(x * SIZE + SIZE, y * SIZE);
    }

    public Vec2 UL() {
        return new Vec2(x * SIZE, y * SIZE + SIZE);
    }

    public Vec2 UR() {
        return new Vec2(x * SIZE + SIZE, y * SIZE + SIZE);
    }
}
