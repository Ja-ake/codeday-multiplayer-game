package com.jakespringer.codeday.jake;

import static com.jakespringer.engine.core.Main.*;

import java.io.File;
import java.io.IOException;

public class Client {

    public static void main(String[] args) throws IOException {
        System.setProperty("org.lwjgl.librarypath", new File("natives").getAbsolutePath());
        try {
            init();
            run();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            destroy();
        }
        System.exit(0);
    }
}
