package com.jakespringer.codeday.combat;

import com.jakespringer.engine.core.AbstractComponent;

public class HealthComponent extends AbstractComponent {

    public double damage;
    public double health;
    public double maxHealth;

    public HealthComponent(double maxHealth) {
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }
}
