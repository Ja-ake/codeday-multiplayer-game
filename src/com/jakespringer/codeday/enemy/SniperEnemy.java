package com.jakespringer.codeday.enemy;

import com.jakespringer.codeday.combat.HealthComponent;
import com.jakespringer.engine.collisions.CollisionComponent;
import com.jakespringer.engine.collisions.CollisionSystem;
import com.jakespringer.engine.core.AbstractEntity;
import com.jakespringer.engine.graphics.SpriteComponent;
import com.jakespringer.engine.graphics.SpriteSystem;
import com.jakespringer.engine.movement.PositionComponent;
import com.jakespringer.engine.movement.PreviousPositionComponent;
import com.jakespringer.engine.movement.PreviousPositionSystem;
import com.jakespringer.engine.movement.VelocityComponent;
import com.jakespringer.engine.movement.VelocitySystem;
import com.jakespringer.engine.util.Vec2;

public class SniperEnemy extends Enemy {
	public SniperEnemy(Vec2 pos) {
		super(pos);
        //Components
        PositionComponent pc = add(new PositionComponent(pos));
        PreviousPositionComponent ppc = add(new PreviousPositionComponent(pos));
        VelocityComponent vc = add(new VelocityComponent());
        SpriteComponent sc = add(new SpriteComponent("enemy normal"));
        CollisionComponent cc = add(new CollisionComponent(this, pc, 25));
        ShotCooldownComponent scc = add(new ShotCooldownComponent(60, 3, 5));
        HealthComponent hc = add(new HealthComponent(100));
        //Systems
        add(new SniperControlSystem(this, pc, vc, scc));
        add(new VelocitySystem(pc, vc));
        add(new CollisionSystem(pc, vc, ppc, cc));
        add(new SpriteSystem(pc, sc));
        add(new PreviousPositionSystem(pc, ppc));
        add(new EnemyHealthSystem(this, hc));
	}
}
