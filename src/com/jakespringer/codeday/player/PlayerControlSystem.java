package com.jakespringer.codeday.player;

import com.jakespringer.engine.core.*;
import com.jakespringer.engine.movement.PositionComponent;
import com.jakespringer.engine.movement.VelocityComponent;
import com.jakespringer.engine.util.Vec2;
import org.lwjgl.input.Keyboard;

public class PlayerControlSystem extends AbstractSystem {

    private Player player;
    private PositionComponent pc;
    private VelocityComponent vc;

    public PlayerControlSystem(Player player, PositionComponent pc, VelocityComponent vc) {
        this.player = player;
        this.pc = pc;
        this.vc = vc;
    }

    @Override
    public void update() {
        vc.vel = new Vec2();
        double speed = 4;
        if (Keys.isDown(Keyboard.KEY_W)) {
            vc.vel = vc.vel.setY(vc.vel.y + speed);
        }
        if (Keys.isDown(Keyboard.KEY_A)) {
            vc.vel = vc.vel.setX(vc.vel.x - speed);
        }
        if (Keys.isDown(Keyboard.KEY_S)) {
            vc.vel = vc.vel.setY(vc.vel.y - speed);
        }
        if (Keys.isDown(Keyboard.KEY_D)) {
            vc.vel = vc.vel.setX(vc.vel.x + speed);
        }
        if (Gamepad.hasController()) {
            vc.vel = Gamepad.getLeftAxis().multiply(speed);
        }

        if (Gamepad.getZAxis() < -.5) {
            if (Gamepad.getRightAxis().lengthSquared() > .25) {
                new Bullet(player, pc.pos, Gamepad.getRightAxis().setLength(12));
            }
        }
        System.out.println(pc.pos);

        Main.gameManager.rmc2.viewPos = pc.pos;
    }

}
