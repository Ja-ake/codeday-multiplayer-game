package com.jakespringer.codeday.particle;

import com.jakespringer.engine.core.AbstractEntity;
import com.jakespringer.engine.movement.PositionComponent;
import com.jakespringer.engine.util.Color4d;
import com.jakespringer.engine.util.Vec2;

public class ParticleEmitter extends AbstractEntity {

    public ParticleEmitter(Vec2 pos, Vec2 vel, double randomness, int duration, int particlesPerStep, int particleDuration, Color4d color) {
        //Components
        add(new PositionComponent(pos));
        add(new ParticleCloudComponent(particlesPerStep, randomness, vel, duration, color, particleDuration));
        //Systems
        add(new EmitterSystem(getComponent(ParticleCloudComponent.class), getComponent(PositionComponent.class), this));
    }
}
