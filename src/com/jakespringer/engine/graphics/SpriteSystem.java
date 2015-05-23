package com.jakespringer.engine.graphics;

import com.jakespringer.engine.core.AbstractSystem;
import com.jakespringer.engine.movement.PositionComponent;
import com.jakespringer.engine.movement.RotationComponent;

public class SpriteSystem extends AbstractSystem {

    private PositionComponent position;
    private RotationComponent rotation;
    private SpriteComponent sprite;

    public SpriteSystem(PositionComponent position, RotationComponent rotation, SpriteComponent sprite) {
        this.position = position;
        this.rotation = rotation;
        this.sprite = sprite;
    }

    public SpriteSystem(PositionComponent position, SpriteComponent sprite) {
        this(position, new RotationComponent(), sprite);
    }

    @Override
    public void update() {
        sprite.imageIndex += sprite.imageSpeed;
        if (sprite.visible) {
            Graphics2D.drawSprite(sprite.getTexture(), position.pos, sprite.scale, rotation.rot, sprite.color);
        }
    }

}
