package com.jakespringer.codeday.tiled;

public class TiledObject {
	public String name, type;
	public double x, y, width, height, rotation;
	public int gid;
	public boolean visible;
	
	public TiledObject(String n, String t, double xn, double yn, double w, double h, double r, int g, boolean v) {
		name = n; type = t; x = xn; y = yn; width = w; height = h; rotation = r; gid = g; visible = v;
	}
}
