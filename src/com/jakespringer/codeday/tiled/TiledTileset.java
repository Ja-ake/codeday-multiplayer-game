package com.jakespringer.codeday.tiled;

import com.jakespringer.engine.graphics.data.Texture;
import com.jakespringer.engine.graphics.loading.SpriteContainer;
import java.util.List;

public class TiledTileset {

    public List<Texture> textures;
    public int start;

    public TiledTileset(String resource, int x, int y, int s) {
//        try {
        //textures = TextureLoader.getTextures(resource, x, y, GL_TEXTURE_2D, GL_RGBA, GL_NEAREST, GL_NEAREST);
        textures = SpriteContainer.loadSprite2(resource, x, y);
        start = s;
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    public Texture getTile(int i) {
        if ((i - start) >= 0 && (i - start) < textures.size()) {
            return textures.get(i - start);
        } else {
            return null;
        }
    }
}
