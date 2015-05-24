package com.jakespringer.codeday.player;

import com.jakespringer.engine.core.*;
import com.jakespringer.engine.graphics.SpriteComponent;
import com.jakespringer.engine.movement.PositionComponent;
import com.jakespringer.engine.movement.RotationComponent;
import com.jakespringer.engine.movement.VelocityComponent;
import com.jakespringer.engine.util.Color4d;
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
            if (vc.vel.lengthSquared() > .1) {
                rc.rot = vc.vel.direction();
            }
            if (Gamepad.getRightAxis().lengthSquared() > .25) {
                rc.rot = Gamepad.getRightAxis().direction();
            }
            if (Gamepad.getZAxis() < -.5) {
                shoot(new Vec2(Math.cos(rc.rot), Math.sin(rc.rot)));
            }
            if (Gamepad.isPressed(5)) {
                grenade(new Vec2(Math.cos(rc.rot), Math.sin(rc.rot)));
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
            if (MouseInput.isDown(0)) {
                shoot(MouseInput.mouse().subtract(pc.pos));
            }
            if (MouseInput.isPressed(1)) {
                grenade(MouseInput.mouse().subtract(pc.pos));
            }
        }

        Main.gameManager.rmc2.viewPos = pc.pos;
    }

    private void grenade(Vec2 dir) {
        new Grenade(player, pc.pos.add(new Vec2(Math.cos(rc.rot - Math.PI / 4), Math.sin(rc.rot - Math.PI / 4)).multiply(22)), dir.setLength(6));
    }

    private void shoot(Vec2 dir) {
        Bullet b = new Bullet(player, pc.pos.add(new Vec2(Math.cos(rc.rot - Math.PI / 4), Math.sin(rc.rot - Math.PI / 4)).multiply(22)), dir.setLength(16));
        b.getComponent(SpriteComponent.class).color = new Color4d(1, 0, 1);
    }
}
