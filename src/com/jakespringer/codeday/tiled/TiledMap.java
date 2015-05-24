package com.jakespringer.codeday.tiled;

public class TiledMap {
	public String version, orientation, renderorder;
	public int width, height, tilewidth, tileheight;
	
	public TiledMap(String v, String o, String ro, int w, int h, int tw, int th) {
		version = v; orientation = o; renderorder = ro; width = w; height = h; tilewidth = tw; tileheight = th;
	}
}
