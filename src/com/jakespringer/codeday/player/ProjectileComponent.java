package com.jakespringer.codeday.player;

import com.jakespringer.engine.core.AbstractComponent;
import com.jakespringer.engine.core.AbstractEntity;

public class ProjectileComponent extends AbstractComponent {

    public AbstractEntity shooter;
    public double damage;
    public int duration;
    public int bounces;

    public ProjectileComponent(AbstractEntity shooter, double damage, int duration, int bounces) {
        this.shooter = shooter;
        this.damage = damage;
        this.duration = duration;
        this.bounces = bounces;
    }
}
