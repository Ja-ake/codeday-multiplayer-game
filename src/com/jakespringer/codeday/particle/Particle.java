package com.jakespringer.codeday.particle;

import com.jakespringer.engine.util.Color4d;
import com.jakespringer.engine.util.Vec2;

public class Particle {
	public Color4d color;
	public Vec2 position;
	public Vec2 velocity;
	public int ttl;
	
	public Particle(Color4d c, Vec2 ps, Vec2 vc, int tt) {
		color = c;
		position = ps;
		velocity = vc;
		ttl = tt;
	}
}
