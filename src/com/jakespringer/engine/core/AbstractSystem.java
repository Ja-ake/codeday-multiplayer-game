package com.jakespringer.engine.core;

public abstract class AbstractSystem {

    public AbstractSystem() {
        Main.systems.get(getLayer()).add(this);
    }

    public void destroy() {
        Main.systems.get(getLayer()).remove(this);
    }

    protected int getLayer() {
        return 1;
    }

    protected boolean pauseable() {
        return false;
    }

    public abstract void update();
}
