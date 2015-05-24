package com.jakespringer.codeday.networking.messages;

import com.jakespringer.codeday.networking.Message;
import com.jakespringer.engine.core.AbstractComponent;
import com.jakespringer.engine.core.Main;

public class EntitySetFieldMessage extends Message {

    public long id;
    public String component;
    public String field;
    public double value;

    public EntitySetFieldMessage() {
    }

    public EntitySetFieldMessage(long id, Class<? extends AbstractComponent> component, String field, double value) {
        this.id = id;
        this.component = component.getName();
        this.field = field;
        this.value = value;
    }

    @Override
    public void act() {
        try {
            Class<AbstractComponent> c = (Class<AbstractComponent>) Class.forName(component);
            c.getField(field).setDouble(Main.gameManager.elc.getId(id).getComponent(c), value);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(String[] parts) {
        id = Long.parseLong(parts[1]);
        component = parts[2];
        field = parts[3];
        value = Double.parseDouble(parts[4]);
    }

    @Override
    public String toString() {
        return super.toString() + "|" + id + "|" + component + "|" + field + "|" + value;
    }

}
