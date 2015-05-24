package com.jakespringer.codeday.jake.netinterface;

public interface Message {

    public void act();

    public byte[] toBytes();
    
    public void initialize(byte[] data);
}
