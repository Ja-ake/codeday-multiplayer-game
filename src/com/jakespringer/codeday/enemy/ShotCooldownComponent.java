package com.jakespringer.codeday.enemy;

import com.jakespringer.engine.core.AbstractComponent;

public class ShotCooldownComponent extends AbstractComponent {

    public int currentCooldown;
    public int currentShots;
    public int maxCooldown;
    public int maxShots;
    public int shotSeperation;

    public ShotCooldownComponent(int maxCooldown, int maxShots, int shotSeperation) {
        this.maxCooldown = maxCooldown;
        this.maxShots = maxShots;
        this.shotSeperation = shotSeperation;
        currentCooldown = maxCooldown;
        currentShots = maxShots;
    }

}
