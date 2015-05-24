package com.jakespringer.codeday.player;

import com.jakespringer.engine.core.AbstractComponent;
import com.jakespringer.engine.core.AbstractEntity;

public class DamageComponent extends AbstractComponent {

    public AbstractEntity shooter;
    public double damage;

    public DamageComponent(AbstractEntity shooter, double damage) {
        this.shooter = shooter;
        this.damage = damage;
    }
}
