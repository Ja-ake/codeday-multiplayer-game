package com.jakespringer.engine.core;

import com.jakespringer.engine.graphics.RenderManagerComponent2D;
import com.jakespringer.engine.util.Vec2;
import java.util.ArrayList;
import java.util.HashMap;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public abstract class MouseInput {

    public static ArrayList<Integer> down = new ArrayList();
    public static ArrayList<Integer> pressed = new ArrayList();
    public static ArrayList<Integer> released = new ArrayList();
    public static HashMap<Integer, Integer> time = new HashMap();
    public static int wheel;
    public static Vec2 mouse;
    public static Vec2 mouseDelta;

    public static void clear() {
        down.clear();
        pressed.clear();
        released.clear();
        time.clear();
    }

    public static boolean isDown(int button) {
        return down.contains(button);
    }

    public static boolean isPressed(int button) {
        return pressed.contains(button);
    }

    public static boolean isReleased(int button) {
        return released.contains(button);
    }

    public static int getTime(int button) {
        if (!time.containsKey(button)) {
            return 0;
        }
        return time.get(button);
    }

    public static Vec2 mouse() {
        return mouse;
    }

    public static Vec2 mouseDelta() {
        return mouseDelta;
    }

    public static Vec2 mouseScreen() {
        return mouse.subtract(Main.gameManager.rmc2.viewPos).add(Main.gameManager.rmc2.viewSize.multiply(.5));
    }

    public static void update() {
        wheel = Mouse.getDWheel() / 120;
        pressed.clear();
        released.clear();
        while (Mouse.next()) {
            Integer button = Mouse.getEventButton();
            if (Mouse.getEventButtonState()) {
                down.add(button);
                pressed.add(button);
                time.put(button, 0);
            } else {
                down.remove(button);
                released.add(button);
            }
        }
        for (Integer i : down) {
            time.put(i, time.get(i) + 1);
        }

        RenderManagerComponent2D rmc = Main.gameManager.rmc2;
        double w = Display.getWidth();
        double h = Display.getHeight();
        double ar = rmc.aspectRatio();
        double vw, vh;

        if (w / h > ar) {
            vw = ar * h;
            vh = h;
        } else {
            vw = w;
            vh = w / ar;
        }
        double left = (w - vw) / 2;
        double bottom = (h - vh) / 2;

        mouse = new Vec2((Mouse.getX() - left) / vw, (Mouse.getY() - bottom) / vh).multiply(rmc.viewSize).subtract(rmc.viewSize.multiply(.5)).add(rmc.viewPos);
        mouseDelta = new Vec2(Mouse.getDX() / vw, Mouse.getDY() / vh).multiply(rmc.viewSize);
    }

    public static int wheel() {
        return wheel;
    }
}
