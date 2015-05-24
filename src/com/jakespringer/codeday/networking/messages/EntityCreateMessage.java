package com.jakespringer.codeday.networking.messages;

import com.jakespringer.codeday.networking.Message;
import com.jakespringer.engine.core.AbstractEntity;
import com.jakespringer.engine.util.Vec2;

public class EntityCreateMessage extends Message {

    public String entityName;
    public long id;
    public Object[] parameters;

    public EntityCreateMessage() {
    }

    public EntityCreateMessage(String entityName, long id, Object... parameters) {
        this.entityName = entityName;
        this.id = id;
        this.parameters = parameters;
    }

    @Override
    public void act() {
        Class[] classList = new Class[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            classList[i] = parameters[i].getClass();
        }
        try {
            AbstractEntity e = (AbstractEntity) Class.forName(entityName).getConstructor(classList).newInstance(parameters);
            e.id = id;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(String[] parts) {
        entityName = parts[1];
        id = Long.parseLong(parts[2]);
        parameters = new Object[parts.length - 3];
        for (int i = 3; i < parts.length; i++) {
            switch (parts[i].charAt(0)) {
                case 'd':
                    parameters[i - 3] = Double.parseDouble(parts[i].substring(1));
                    break;
                case 'i':
                    parameters[i - 3] = Integer.parseInt(parts[i].substring(1));
                    break;
                case 'v':
                    parameters[i - 3] = Vec2.parseVec2(parts[i].substring(1));
                    break;
            }
        }
    }

    @Override
    public String toString() {
        String r = super.toString() + "|" + entityName + "|" + id;
        for (Object o : parameters) {
            r += "|" + o.getClass().getSimpleName().substring(0, 1).toLowerCase() + o.toString();
        }
        return r;
    }

}
