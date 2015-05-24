package com.jakespringer.codeday.particle;

import java.util.LinkedList;
import java.util.Random;

import com.jakespringer.engine.core.AbstractComponent;
import com.jakespringer.engine.util.Color4d;
import com.jakespringer.engine.util.Vec2;

public class ParticleCloudComponent extends AbstractComponent {
	public LinkedList<Particle> particles = new LinkedList<>();
	public int ttl, ppt, pttl;
	public Vec2 bias;
	public double magnitude;
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
