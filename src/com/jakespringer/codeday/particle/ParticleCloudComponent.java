package com.jakespringer.codeday.particle;

import com.jakespringer.engine.core.AbstractComponent;
import com.jakespringer.engine.util.Color4d;
import com.jakespringer.engine.util.Vec2;
import java.util.LinkedList;
import java.util.Random;

public class ParticleCloudComponent extends AbstractComponent {

    public LinkedList<Particle> particles = new LinkedList<>();
    public int ttl, ppt, pttl; //Time to live, particles/step, particles' time to live
    public Vec2 bias; //Add to speed
    public double magnitude; //Randomness in speed
    public Color4d color;
    public Random random = new Random();

    public ParticleCloudComponent(int pp, double mag, Vec2 bia, int tt, Color4d col, int ptt) {
        ppt = pp;
        magnitude = mag;
        bias = bia;
        ttl = tt;
        color = col;
        pttl = ptt;
    }
}
