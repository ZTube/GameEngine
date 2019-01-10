package de.ztube.pane.model;

import de.ztube.pane.maths.Vector3f;
import de.ztube.pane.texture.Texture;

// An entity is an object in 3dimensional space with rotation, scale and a position
public class Entity {

    private Vector3f position;
    private float rotX, rotY, rotZ;
    private float scaleX, scaleY, scaleZ;

    private Mesh mesh;
    private Texture texture;



    public Entity(Mesh mesh, Texture texture, Vector3f position, float rotX, float rotY, float rotZ, float scaleX, float scaleY, float scaleZ) {
        this.mesh = mesh;
        this.texture = texture;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.scaleZ = scaleZ;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public Texture getTexture(){
        return texture;
    }

    public void setTexture(Texture texture){
        this.texture = texture;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getRotX() {
        return rotX;
    }

    public void setRotX(float rotX) {
        this.rotX = rotX;
    }

    public float getRotY() {
        return rotY;
    }

    public void setRotY(float rotY) {
        this.rotY = rotY;
    }

    public float getRotZ() {
        return rotZ;
    }

    public void setRotZ(float rotZ) {
        this.rotZ = rotZ;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public float getScaleZ() {
        return scaleZ;
    }

    public void setScaleZ(float scaleZ) {
        this.scaleZ = scaleZ;
    }

    public void move(float dx, float dy, float dz){
        this.position.x += dx;
        this.position.y += dy;
        this.position.z += dz;

    }

    public void rotate(float dx, float dy, float dz){
        rotX += dx;
        rotY += dy;
        rotZ += dz;
    }
}
