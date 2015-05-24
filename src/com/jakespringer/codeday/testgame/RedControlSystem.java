package com.jakespringer.codeday.testgame;

import com.jakespringer.engine.core.AbstractSystem;
import com.jakespringer.engine.core.Keys;
import com.jakespringer.engine.core.MouseInput;
import com.jakespringer.engine.movement.VelocityComponent;
import com.jakespringer.engine.util.Vec2;
import org.lwjgl.input.Keyboard;

public class RedControlSystem extends AbstractSystem {

    private VelocityComponent vc;

    public RedControlSystem(VelocityComponent vc) {
        this.vc = vc;
    }

    @Override
    public void update() {
        vc.vel = new Vec2();
        if (Keys.isDown(Keyboard.KEY_W)) {
            vc.vel = vc.vel.setY(vc.vel.y + 1);
        }
        if (Keys.isDown(Keyboard.KEY_A)) {
            vc.vel = vc.vel.setX(vc.vel.x - 1);
        }
        if (Keys.isDown(Keyboard.KEY_S)) {
            vc.vel = vc.vel.setY(vc.vel.y - 1);
        }
        if (Keys.isDown(Keyboard.KEY_D)) {
            vc.vel = vc.vel.setX(vc.vel.x + 1);
        }
        if (MouseInput.isPressed(0)) {
//            new ParticleEmitter(MouseInput.mouse());
        }
    }

}
