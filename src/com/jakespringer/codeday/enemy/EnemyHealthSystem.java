package com.jakespringer.codeday.enemy;

import com.jakespringer.codeday.combat.HealthComponent;
import com.jakespringer.engine.core.AbstractEntity;
import com.jakespringer.engine.core.AbstractSystem;

public class EnemyHealthSystem extends AbstractSystem {

    private AbstractEntity e;
    private HealthComponent hc;

    public EnemyHealthSystem(AbstractEntity e, HealthComponent hc) {
        this.e = e;
        this.hc = hc;
    }

    @Override
    public void update() {
        hc.health -= hc.damage * .2;
        hc.damage *= .8;

        if (hc.health <= 0) {
            e.destroySelf();
        }
    }

}
