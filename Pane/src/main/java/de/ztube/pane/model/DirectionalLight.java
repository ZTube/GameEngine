package de.ztube.pane.model;

import de.ztube.pane.maths.Vector3f;

// Represents a light where all rays are parallel to each other
public class DirectionalLight {

    // The Vector pointing in the direction of the light
    private Vector3f direction;

    // The color of the light as rgb-vector. (0|0|0) is black and (1|1|1) is white
    private Vector3f color;

    public DirectionalLight(Vector3f direction, Vector3f color){
        this.direction = direction;
        this.color = color;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }
}
