package com.jakespringer.engine.graphics;

import java.util.ArrayList;

import com.jakespringer.engine.core.AbstractComponent;
import com.jakespringer.engine.graphics.data.Model;
import com.jakespringer.engine.graphics.data.Texture;
import com.jakespringer.engine.graphics.loading.ModelContainer;
import com.jakespringer.engine.util.Color4d;

public class ModelComponent extends AbstractComponent {

    public String name;
    public ArrayList<Model> modelList;
    public Texture tex;
    public double animIndex;
    public double animSpeed;
    public boolean visible;
    public boolean shadow;
    public double scale;
    public Color4d color;

    public ModelComponent(String name, int length, double scale, Color4d color) {
        modelList = new ArrayList();
        setAnim(name, length);
        visible = true;
        shadow = false;
        this.scale = scale;
        this.color = color;
    }

    public ModelComponent(String name, double scale, Color4d color) {
        modelList = new ArrayList();
        setModel(name);
        visible = true;
        shadow = false;
        this.scale = scale;
        this.color = color;
    }

    public boolean animComplete() {
        return animIndex >= modelList.size();
    }

    public Model getModel() {
        return modelList.get((int) animIndex % modelList.size());
    }

    public void setAnim(String name, int length) {
        if (this.name.equals(name)) {
            animIndex = animIndex % modelList.size();
            return;
        }
        this.name = name;
        animIndex = 0;
        modelList.clear();
        for (int i = 1; i <= length; i++) {
            modelList.add(ModelContainer.loadModel(name + "/" + i));
        }
    }

    public void setModel(String name) {
        this.name = name;
        animIndex = 0;
        modelList.clear();
        modelList.add(ModelContainer.loadModel(name));
    }
}
