package com.jakespringer.codeday.dm;

import com.jakespringer.engine.core.AbstractEntity;

public class DM extends AbstractEntity {

    public DM() {
        //Components
        DMComponent dmc = add(new DMComponent(10));
        //Systems
        add(new DMSystem(dmc, this));
        add(new DMGui(dmc));
    }
}
