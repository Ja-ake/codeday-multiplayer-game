package com.jakespringer.engine.collisions;

import com.jakespringer.engine.core.Main;
import com.jakespringer.engine.shapes.Circle;
import com.jakespringer.engine.shapes.CollisionShape;
import com.jakespringer.engine.util.Vec2;
import java.util.ArrayList;

public abstract class CollisionUtil {

    public static ArrayList<CollisionComponent> listAt(Vec2 point) {
        ArrayList<CollisionComponent> r = new ArrayList();
        for (CollisionComponent cc : Main.gameManager.elc.getComponentList(CollisionComponent.class)) {
            Vec2 diff = point.subtract(cc.pc.pos);
            if (diff.lengthSquared() < cc.width * cc.width) {
                r.add(cc);
            }
        }
        return r;
    }

    public static ArrayList<CollisionComponent> listAt(CollisionShape shape) {
        ArrayList<CollisionComponent> r = new ArrayList();
        for (CollisionComponent cc : Main.gameManager.elc.getComponentList(CollisionComponent.class)) {
            Circle c = new Circle(cc.pc.pos, cc.width);
            if (shape.intersects(c)) {
                r.add(cc);
            }
        }
        return r;
    }
}
