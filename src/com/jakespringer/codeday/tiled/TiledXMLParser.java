package com.jakespringer.codeday.tiled;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.jakespringer.engine.graphics.data.Texture;

public class TiledXMLParser {
	private static DocumentBuilderFactory dbf;
	private static DocumentBuilder db;
	static { 
		try {	
			dbf = DocumentBuilderFactory.newInstance(); 
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
	}
	
	private Document document;
	
	private TiledMap map;
	private List<TiledTileset> tilesets;
	private Stack<TiledObject> objects;
	private Stack<TiledTile> tiles;

	
	public TiledXMLParser(File file) {
		try {
			document = db.parse(file);
		} catch (SAXException | IOException e) {
			throw new RuntimeException(e);
		}
		
		objects = new Stack<TiledObject>();
		tiles = new Stack<TiledTile>();
		map = null;
		tilesets = new ArrayList<TiledTileset>();
	}
	
	public void parse() {		
		map = new TiledMap(
				document.getDocumentElement().getAttribute("version"),
				document.getDocumentElement().getAttribute("orientation"),
				document.getDocumentElement().getAttribute("renderorder"),
				Integer.parseInt(document.getDocumentElement().getAttribute("width")),
				Integer.parseInt(document.getDocumentElement().getAttribute("height")),
				Integer.parseInt(document.getDocumentElement().getAttribute("tilewidth")),
				Integer.parseInt(document.getDocumentElement().getAttribute("tileheight"))
						  );
		
		NodeList baseChildNodes = document.getDocumentElement().getChildNodes();
		for (int i=0; i<baseChildNodes.getLength(); i++) {
			if (baseChildNodes.item(i).getNodeName().equals("tileset")) {
				if (baseChildNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element tileset = (Element) baseChildNodes.item(i);
					Element tilesetsource = (Element) baseChildNodes.item(i).getChildNodes().item(1);
					tilesets.add(new TiledTileset("levels/" + tilesetsource.getAttribute("source"), 
							Integer.parseInt(tilesetsource.getAttribute("width")) / Integer.parseInt(tileset.getAttribute("tilewidth")), 
							Integer.parseInt(tilesetsource.getAttribute("height")) / Integer.parseInt(tileset.getAttribute("tileheight")), 
							Integer.parseInt(tileset.getAttribute("firstgid"))));
				}
			} else if (baseChildNodes.item(i).getNodeName().equals("layer")) {
				// Parse tiles:
				NodeList tileNodes = baseChildNodes.item(i).getChildNodes().item(1).getChildNodes();
				for (int j=0; j<tileNodes.getLength(); j++) {
					if (tileNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
						Element tile = (Element) tileNodes.item(j);
						
						String gid = tile.getAttribute("gid");
						
						if (gid == null) continue;
						
						tiles.push(new TiledTile(Integer.parseInt(gid)));
					}
				}
			} else if (baseChildNodes.item(i).getNodeName().equals("objectgroup")) {
				// Parse objects:
				NodeList tileNodes = baseChildNodes.item(i).getChildNodes();
				for (int j=0; j<tileNodes.getLength(); j++) {
					if (tileNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
						Element object = (Element) tileNodes.item(j);
						
						String name = object.getAttribute("name");
						String type = object.getAttribute("type");
						String x = object.getAttribute("x");
						String y = object.getAttribute("y");
						String width = object.getAttribute("width");
						String height = object.getAttribute("height");
						String rotation = object.getAttribute("rotation");
						String gid = object.getAttribute("gid");
						String visible = object.getAttribute("visible");
						
						if (name == null || name == "") name = "";
						if (type == null || type == "") type = "";
						if (x == null || x == "") x = "0";
						if (y == null || y == "") y = "0";
						if (width == null || width == "") width = "0";
						if (height == null || height == "") height = "0";
						if (rotation == null || rotation == "") rotation = "0";
						if (gid == null || gid == "") gid = "0";
						if (visible == null || visible == "") visible = "1";
						
						if (visible == "0") visible = "false";
						else visible = "true";
						
						objects.push(new TiledObject(name, type, Double.parseDouble(x), Double.parseDouble(y)
								, Double.parseDouble(width), Double.parseDouble(height), Double.parseDouble(rotation)
								, Integer.parseInt(gid), Boolean.parseBoolean(visible)));
						
//						System.out.println(name + "," + type + "," + x + "," + y + "\n"
//												+ width + "," + height + "," + rotation + "," + gid + "," + visible);
					}
				}
			}
		}
		
		System.out.println("Loaded " + tiles.size() + " tiles.");
	}
	
	public TiledObject nextObject() {
		return objects.size() > 0 ? objects.pop() : null;
	}
	
	public TiledTile nextTile() {
		return tiles.size() > 0 ? tiles.pop() : null;
	}
	
	public TiledMap getMap() {
		return map;
	}
	
	public Texture getTileTexture(int i) {
		for (TiledTileset t : tilesets) {
			if (t.getTile(i) != null) return t.getTile(i);
		}
		
		throw new RuntimeException("Tile index out of bounds: " + i);
	}
}
