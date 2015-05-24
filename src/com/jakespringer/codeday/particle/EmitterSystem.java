package com.jakespringer.codeday.particle;

import com.jakespringer.engine.core.AbstractEntity;
import com.jakespringer.engine.core.AbstractSystem;
import com.jakespringer.engine.movement.PositionComponent;
import com.jakespringer.engine.util.Color4d;
import com.jakespringer.engine.util.Vec2;
import java.util.Iterator;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;

public class EmitterSystem extends AbstractSystem {

    private ParticleCloudComponent pcc;
    private PositionComponent pc;
    private AbstractEntity wrapper;

    public EmitterSystem(ParticleCloudComponent p, PositionComponent psc, AbstractEntity e) {
        pcc = p;
        pc = psc;
        wrapper = e;
    }

    @Override
    public void update() {

        boolean needsCreate = true;

        if (pcc.ttl-- < 0) {
            if (pcc.particles.isEmpty()) {
                wrapper.destroySelf();
                return;
            } else {
                needsCreate = false;
            }
        }

        if (needsCreate) {
            for (int i = 0; i < pcc.ppt; i++) {
                Color4d col = pcc.color.setR(pcc.color.r + (pcc.random.nextDouble()) * 0.6)
                        .setB(pcc.color.b + (pcc.random.nextDouble()) * 0.6)
                        .setG(pcc.color.g + (pcc.random.nextDouble()) * 0.6);

                double rand = 2 * Math.PI * (pcc.random.nextDouble());
                double randv = Math.sqrt(pcc.magnitude * pcc.random.nextGaussian());

                Vec2 velo = new Vec2(Math.cos(rand) * randv,
                        Math.sin(rand) * randv);

                pcc.particles.add(new Particle(col, pc.pos, pcc.bias.add(velo), (int) (pcc.pttl * pcc.random.nextDouble())));
            }
        }

        glBlendFunc(GL_ONE, GL_ONE);
        glDisable(GL_TEXTURE_2D);
        glEnable(GL_COLOR);
        GL11.glPointSize(2);

        glBegin(GL_POINTS);
        Iterator<Particle> iter = pcc.particles.iterator();
        while (iter.hasNext()) {
            Particle p = iter.next();
            if (p.ttl-- < 0) {
                iter.remove();
                continue;
            }

            p.position = p.position.add(p.velocity);
            p.velocity = p.velocity.multiply(0.8);
            p.color = p.color.setA(p.color.a * 0.95);

            p.color.glColor();
            p.position.glVertex();
        }
        glEnd();
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }
}
