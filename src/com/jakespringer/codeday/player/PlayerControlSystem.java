package com.jakespringer.codeday.player;

import com.jakespringer.engine.core.*;
import com.jakespringer.engine.movement.PositionComponent;
import com.jakespringer.engine.movement.RotationComponent;
import com.jakespringer.engine.movement.VelocityComponent;
import com.jakespringer.engine.util.Vec2;
import org.lwjgl.input.Keyboard;

public class PlayerControlSystem extends AbstractSystem {

    private Player player;
    private PositionComponent pc;
    private VelocityComponent vc;
    private RotationComponent rc;

    public PlayerControlSystem(Player player, PositionComponent pc, VelocityComponent vc, RotationComponent rc) {
        this.player = player;
        this.pc = pc;
        this.vc = vc;
        this.rc = rc;
    }

    @Override
    public void update() {
        vc.vel = new Vec2();
        double speed = 4;
        if (Gamepad.hasController()) {
            //Gamepad
            vc.vel = Gamepad.getLeftAxis().multiply(speed);
            rc.rot = vc.vel.direction();
            if (Gamepad.getRightAxis().lengthSquared() > .25) {
                rc.rot = Gamepad.getRightAxis().direction();
                if (Gamepad.getZAxis() < -.5) {
                    new Bullet(player, pc.pos, Gamepad.getRightAxis().setLength(12));
                }
            }
        } else {
            //keyboard & mouse
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
            rc.rot = MouseInput.mouse().subtract(pc.pos).direction();
            if (MouseInput.isPressed(0)) {
                new Bullet(player, pc.pos, MouseInput.mouse().subtract(pc.pos).setLength(12));
            }
        }

        Main.gameManager.rmc2.viewPos = pc.pos;
    }

}
