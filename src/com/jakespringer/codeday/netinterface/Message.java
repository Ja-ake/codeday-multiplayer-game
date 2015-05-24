package com.jakespringer.codeday.netinterface;

public abstract class Message {

    public abstract void act();

    public abstract void initialize(String[] parts);

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
