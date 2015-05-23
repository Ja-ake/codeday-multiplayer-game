package com.jakespringer.engine.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.HashMap;
import net.java.games.input.ControllerEnvironment;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;

public abstract class Gamepad {

    private static ArrayList<Integer> down = new ArrayList();
    private static ArrayList<Integer> pressed = new ArrayList();
    private static ArrayList<Integer> released = new ArrayList();
    private static HashMap<Integer, Integer> time = new HashMap();
    private static Controller controller;

    private static void hack() {
        try {
            Class<?> clazz = Class.forName("net.java.games.input.DefaultControllerEnvironment");
            Constructor<?> defaultConstructor = clazz.getDeclaredConstructor();
            Field defaultEnvironementField = ControllerEnvironment.class.getDeclaredField("defaultEnvironment");
            defaultEnvironementField.setAccessible(true);

            defaultEnvironementField.set(ControllerEnvironment.getDefaultEnvironment(), defaultConstructor.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void init() {
        Controllers.poll();
        for (int i = 0; i < Controllers.getControllerCount(); i++) {
            if (Controllers.getController(i).getName().startsWith("Controller")) {
                controller = Controllers.getController(i);
                break;
            }
        }
        if (controller == null) {
            return;
        }
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

    static void preventWarnings() {
        AccessController.doPrivileged(new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                String os = System.getProperty("os.name", "").trim();
                if (os.startsWith("Windows 8")) {  // 8, 8.1 etc.

                    // disable default plugin lookup
                    System.setProperty("jinput.useDefaultPlugin", "false");

                    // set to same as windows 7 (tested for windows 8 and 8.1)
                    System.setProperty("net.java.games.input.plugins", "net.java.games.input.DirectAndRawInputEnvironmentPlugin");

                }
                return null;
            }
        });
    }

    public static void update() {
        if (controller == null) {
            return;
        }
        if (controller.getXAxisValue() == -1
                && controller.getYAxisValue() == -1
                && controller.getZAxisValue() == -1
                && controller.getRXAxisValue() == -1
                && controller.getRYAxisValue() == -1) {
            return;
        }

        hack();
        if (ControllerEnvironment.getDefaultEnvironment().getControllers().length != Controllers.getControllerCount() + 4) {
            controller = null;
            return;
        }

        pressed.clear();
        released.clear();
        System.out.println(Controllers.getController(3));
        while (Controllers.next()) {
            if (Controllers.isEventButton()) {
                Integer button = Controllers.getEventControlIndex();
                if (Controllers.getEventButtonState()) {
                    down.add(button);
                    pressed.add(button);
                    time.put(button, 0);
                } else {
                    down.remove(button);
                    released.add(button);
                }
            }
        }
        for (Integer i : down) {
            time.put(i, time.get(i) + 1);
        }
    }
}
