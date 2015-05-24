package com.jakespringer.codeday.particle;

import com.jakespringer.engine.core.AbstractEntity;
import com.jakespringer.engine.movement.PositionComponent;
import com.jakespringer.engine.util.Color4d;
import com.jakespringer.engine.util.Vec2;

public class ParticleEmitter extends AbstractEntity {
	public ParticleEmitter(Vec2 pos) {
		this.add(new PositionComponent(pos));
		this.add(new ParticleCloudComponent(20, 5.0d, pos.normalize().multiply(-10), 4, Color4d.GREEN, 40));
		
		this.add(new EmitterSystem(this.getComponent(ParticleCloudComponent.class), this.getComponent(PositionComponent.class), this));
	}
}
