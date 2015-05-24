package com.jakespringer.engine.gui;

import com.jakespringer.engine.core.AbstractEntity;
import com.jakespringer.engine.graphics.SpriteComponent;
import com.jakespringer.engine.graphics.SpriteSystem;
import com.jakespringer.engine.movement.PositionComponent;

public class Menu extends AbstractEntity {

    public Menu() {
        //Components
        PositionComponent pc = add(new PositionComponent());
        SpriteComponent sc = add(new SpriteComponent("menu"));
        //Systems
        add(new SpriteSystem(pc, sc));
        add(new MenuSystem(this));
    }
}
