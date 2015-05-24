package com.jakespringer.codeday.level;

import com.jakespringer.engine.core.AbstractComponent;
import com.jakespringer.engine.graphics.Graphics2D;
import static com.jakespringer.engine.graphics.Graphics2D.drawSpriteFast;
import com.jakespringer.engine.graphics.data.Texture;
import com.jakespringer.engine.graphics.loading.SpriteContainer;
import static com.jakespringer.engine.util.Color4d.WHITE;
import com.jakespringer.engine.util.Vec2;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import static org.lwjgl.opengl.GL11.*;

public class LevelComponent extends AbstractComponent {

    public String fileName;
    public Tile[][] tileGrid;
    public int width;
    public int height;
    public int list;
    private static String path = "levels/";
    private static String type = ".png";

    public LevelComponent(String fileName) {
        this.fileName = fileName;
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(path + fileName + type));
        } catch (IOException ex) {
            throw new RuntimeException("Level " + fileName + " doesn't exist");
        }

        ArrayList<Texture> tiles = SpriteContainer.loadSprite("walls and floor", 4, 4);

        width = image.getWidth();
        height = image.getHeight();
        tileGrid = new Tile[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tileGrid[x][y] = createTile(x, y, image.getRGB(x, height - y - 1));
            }
        }
//        TiledXMLParser tmx = new TiledXMLParser(new File("levels/test.tmx"));
//        tmx.parse();
//
//        this.fileName = fileName;
//        BufferedImage image = null;
//        try {
//            image = ImageIO.read(new File(path + fileName + type));
//        } catch (IOException ex) {
//            throw new RuntimeException("Level " + fileName + " doesn't exist");
//        }
//
//        ArrayList<Texture> tiles = SpriteContainer.loadSprite("walls and floor", 4, 4);
//
//        width = 48;
//        height = 48;
//        tileGrid = new Tile[width][height];
////        for (int y = 0; y < height; y++) {
////            for (int x = 0; x < width; x++) {
////                tileGrid[x][y] = createTile(x, y, image.getRGB(x, height - y - 1));
////            }
////        }
//
//        TiledTile tile;
//        int ix = 0;
//        int jy = 0;
//        while ((tile = tmx.nextTile()) != null) {
//            Texture t = tmx.getTileTexture(tile.gid + 1);
//            System.out.println(t);
//            tileGrid[ix][jy] = new Tile(ix, jy, t, tile.gid != 0);
//            ix++;
//            if (ix > tmx.getMap().width - 1) {
//                jy++;
//                ix = 0;
//            }
//        }
//
////        for (int x = 0; x < width; x++) {
////            for (int y = 0; y < height; y++) {
////            	boolean[][] a = new boolean[3][3];
////            	a[0] = new boolean[3];
////            	a[1] = new boolean[3];
////            	a[2] = new boolean[3];
////
////            	for (int i=0; i<3; i++) for (int j=0; j<3; j++) a[i][j] = false;
////
////            	a[1][1] = tileGrid[x][y].isWall;
////
////				if (x != 0) a[0][1] = tileGrid[x-1][y].isWall;
////				if (x != width-1) a[2][1] = tileGrid[x+1][y].isWall;
////				if (y != 0) a[1][0] = tileGrid[x][y-1].isWall;
////				if (y != height-1) a[1][2] = tileGrid[x][y+1].isWall;
////
////				if (x != 0 && y != 0) a[0][0] = tileGrid[x-1][y-1].isWall;
////				if (x != 0 && y != height-1) a[0][2] = tileGrid[x-1][y+1].isWall;
////				if (x != width-1 && y != 0) a[2][0] = tileGrid[x+1][y-1].isWall;
////				if (x != width-1 && y != height-1) a[2][2] = tileGrid[x+1][y+1].isWall;
////
////				if (a[1][1]) tileGrid[x][y].tex = tiles.get(5);
////
////				if (a[1][0] && a[1][2]) if (x < 20) tileGrid[x][y].tex = tiles.get(6);
////				if (a[0][1] && a[2][1]) if (y > 20) tileGrid[x][y].tex = tiles.get(1);
////
////				if (a[0][1] && tileGrid[x][y].tex.equals(tiles.get(6))) tileGrid[x][y].tex = tiles.get(4);
////				if (a[1][0] && tileGrid[x][y].tex.equals(tiles.get(1))) tileGrid[x][y].tex = tiles.get(9);
////
////				if (!a[2][2] && a[1][0] && a[2][1]) tileGrid[x][y].tex = tiles.get(8);
////				if (a[0][2] && a[1][2] && a[0][1] && !a[1][0] && !a[2][0]) tileGrid[x][y].tex = tiles.get(9);
////				if (a[0][0] && tileGrid[x][y].tex.equals(tiles.get(8))) tileGrid[x][y].tex = tiles.get(9);
////            }
////        }
        //List
        list = glGenLists(1);
        glNewList(list, GL_COMPILE);
        //Grid
        for (int i = 0; i < width; i++) {
            Graphics2D.drawLine(new Vec2(i, 0), new Vec2(i, height));
        }
        for (int i = 0; i < height; i++) {
            Graphics2D.drawLine(new Vec2(0, i), new Vec2(width, i));
        }
        glEnable(GL_TEXTURE_2D);
//        Texture[] texList = {loadSprite("floor"), loadSprite("wall")};
        WHITE.glColor();
        //Draw
        for (Texture tex : SpriteContainer.all()) {
            tex.bind();
            glBegin(GL_QUADS);

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    Tile t = tileGrid[i][j];
                    if (t.tex != tex) {
                        continue;
                    }
                    drawSpriteFast(tex, t.LL(), t.LR(), t.UR(), t.UL());
                }
            }
            glEnd();
        }
        glEndList();
    }

    public Tile createTile(int x, int y, int color) {
        switch (color) {
            case 0xFF000000: //0 0 0
                return new Tile(x, y, true);
            case 0xFF22B14C: //34 176 76
                //new Player(new Vec2(x, y).multiply(Tile.SIZE));
                return new Tile(x, y, false);
            default:
                return new Tile(x, y, false);
        }
    }

    public Vec2 rayCast(Vec2 start, Vec2 goal) {
        Vec2 diff = goal.subtract(start);
        Vec2 pos = start;
        for (int i = 0; i < 1000; i++) {
            double nextX;
            if (diff.x > 0) {
                nextX = Math.ceil(pos.x / Tile.SIZE) * Tile.SIZE;
                if (nextX == pos.x) {
                    nextX += 1;
                }
            } else {
                nextX = Math.floor(pos.x / Tile.SIZE) * Tile.SIZE;
                if (nextX == pos.x) {
                    nextX -= 1;
                }
            }
            double nextY;
            if (diff.y > 0) {
                nextY = Math.ceil(pos.y / Tile.SIZE) * Tile.SIZE;
                if (nextY == pos.y) {
                    nextY += 1;
                }
            } else {
                nextY = Math.floor(pos.y / Tile.SIZE) * Tile.SIZE;
                if (nextY == pos.y) {
                    nextY -= 1;
                }
            }
            Vec2 time = new Vec2(nextX, nextY).subtract(pos).divide(diff);
            if (time.x > time.y) {
                //y hit
                pos = new Vec2(pos.x + (nextY - pos.y) * diff.x / diff.y, nextY);
            } else {
                //x hit
                pos = new Vec2(nextX, pos.y + (nextX - pos.x) * diff.y / diff.x);
            }
            pos = pos.add(diff.multiply(.00000001));
            Tile t = tileAt(pos);
            if (t != null && t.isWall) {
                return pos.subtract(diff.multiply(.00000001));
            }
            if ((int) pos.x / Tile.SIZE == (int) goal.x / Tile.SIZE && (int) pos.y / Tile.SIZE == (int) goal.y / Tile.SIZE) {
                return goal;
            }
            if (pos.x * diff.x > goal.x * diff.x || pos.y * diff.y > goal.y * diff.y) {
                return goal;
            }
        }
        return goal;
//        double x0 = start.x / Tile.SIZE;
//        double y0 = start.y / Tile.SIZE;
//        double x1 = goal.x / Tile.SIZE;
//        double y1 = goal.y / Tile.SIZE;
//
//        double dx = Math.abs(x1 - x0);
//        double dy = Math.abs(y1 - y0);
//
//        int x = (int) Math.floor(x0);
//        int y = (int) Math.floor(y0);
//
//        int n = 1;
//        int x_inc, y_inc;
//        double error;
//
//        if (dx == 0) {
//            x_inc = 0;
//            error = Double.POSITIVE_INFINITY;
//        } else if (x1 > x0) {
//            x_inc = 1;
//            n += (int) Math.floor(x1) - x;
//            error = (Math.floor(x0) + 1 - x0) * dy;
//        } else {
//            x_inc = -1;
//            n += x - (int) Math.floor(x1);
//            error = (x0 - Math.floor(x0)) * dy;
//        }
//
//        if (dy == 0) {
//            y_inc = 0;
//            error = Double.NEGATIVE_INFINITY;
//        } else if (y1 > y0) {
//            y_inc = 1;
//            n += (int) Math.floor(y1) - y;
//            error -= (Math.floor(y0) + 1 - y0) * dx;
//        } else {
//            y_inc = -1;
//            n += y - (int) Math.floor(y1);
//            error -= (y0 - Math.floor(y0)) * dx;
//        }
//
//        for (; n > 0; --n) {
//            Tile t = tileAt(new Vec2(x * Tile.SIZE, y * Tile.SIZE));
//            if (t != null && t.isWall) {
//                if (error > 0) {
//                    //y hit
//                    return new Vec2(x + .5, y).multiply(Tile.SIZE);
//                } else {
//                    //x hit
//                    return new Vec2(x, y + .5).multiply(Tile.SIZE);
//                }
//            }
//
//            if (error > 0) {
//                y += y_inc;
//                error -= dx;
//            } else {
//                x += x_inc;
//                error += dy;
//            }
//        }
//        return goal;
    }

    public Tile tileAt(Vec2 pos) {
        if (pos.x >= 0 && pos.y >= 0 && pos.x < width * Tile.SIZE && pos.y < height * Tile.SIZE) {
            return tileGrid[(int) pos.x / Tile.SIZE][(int) pos.y / Tile.SIZE];
        }
        return null;
    }

    public boolean wallAt(Vec2 pos) {
        return tileAt(pos) == null || tileAt(pos).isWall;
    }
}
