package de.ztube.pane.model;

import de.ztube.pane.maths.Vector3f;

// A point light is a light similar to a lamp which emits light in all directions
public class PointLight {

    // The position of the lamp in the world
    private Vector3f position;

    // The color of the light as vector
    private Vector3f color;

    public PointLight(Vector3f position, Vector3f color){
        this.position = position;
        this.color = color;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }
}
