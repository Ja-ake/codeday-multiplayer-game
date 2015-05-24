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
//            tex = SpriteContainer.loadSprite("walls and floor", 4, 4).get(5);
        }
    }

    public Tile(int x, int y, String sprite) {
        this.x = x;
        this.y = y;
        this.isWall = true;
        tex = SpriteContainer.loadSprite(sprite);
    }
    
    public Tile(int x, int y, Texture sprite, boolean wall) {
    	this.x = x;
    	this.y = y;
    	this.isWall = wall;
    	tex = sprite;
    }

    public Vec2 center() {
        return new Vec2((x + .5) * SIZE, (y + .5) * SIZE);
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
