package com.jakespringer.codeday.enemy;

import com.jakespringer.codeday.combat.HealthComponent;
import com.jakespringer.engine.collisions.CollisionComponent;
import com.jakespringer.engine.collisions.CollisionSystem;
import com.jakespringer.engine.graphics.SpriteComponent;
import com.jakespringer.engine.graphics.SpriteSystem;
import com.jakespringer.engine.movement.*;
import com.jakespringer.engine.util.Vec2;

public class ScoutEnemy extends Enemy {

    public ScoutEnemy(Vec2 pos) {
        super(pos);

        PositionComponent pc = add(new PositionComponent(pos));
        PreviousPositionComponent ppc = add(new PreviousPositionComponent(pos));
        VelocityComponent vc = add(new VelocityComponent());
        RotationComponent rc = add(new RotationComponent());
        SpriteComponent sc = add(new SpriteComponent("enemyfast"));
        CollisionComponent cc = add(new CollisionComponent(this, pc, 25));
        ShotCooldownComponent scc = add(new ShotCooldownComponent(60, 3, 5));
        HealthComponent hc = add(new HealthComponent(100));
        //Systems
        add(new ScoutControlSystem(this, pc, vc, scc));
        add(new VelocitySystem(pc, vc));
        add(new CollisionSystem(pc, vc, ppc, cc));
        add(new SpriteSystem(pc, rc, sc));
        add(new PreviousPositionSystem(pc, ppc));
        add(new EnemyHealthSystem(this, hc));
    }

}
