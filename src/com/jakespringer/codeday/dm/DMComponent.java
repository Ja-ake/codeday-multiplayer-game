package com.jakespringer.codeday.dm;

import com.jakespringer.engine.core.AbstractComponent;

public class DMComponent extends AbstractComponent {

    public double threat;
    public double maxThreat;

    public DMComponent(double maxThreat) {
        this.maxThreat = maxThreat;
        threat = maxThreat;
    }
}
