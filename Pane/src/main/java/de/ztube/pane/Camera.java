package de.ztube.pane;

import de.ztube.pane.maths.Vector3f;

// Represents a camera in a 3dimensional world
public class Camera {

    // The camera's position
    private Vector3f position = new Vector3f(0,0,0);

    // Rotation
    private float pitch, yaw, roll;

    // The field of view
    private float fov;

    // The aspect ratio
    private float aspect;

    // The clipping planes. Everything in front of "nearClipping" or after "farClipping" is not shown
    private float nearClipping, farClipping;

    public Camera(float fov, float aspect, float nearClipping, float farClipping){
        this.fov = fov;
        this.aspect = aspect;
        this.nearClipping = nearClipping;
        this.farClipping = farClipping;
    }

    public void setFov(float fov) {
        this.fov = fov;
    }

    public float getFov() {
        return fov;
    }

    public void setPosition(Vector3f position){
        this.position = position;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setX(float x){
        this.position.x = x;
    }

    public void setY(float y){
        this.position.y = y;
    }

    public void setZ(float z){
        this.position.z = z;
    }

    public float getX(){
        return position.x;
    }

    public float getY(){
        return position.y;
    }

    public float getZ(){
        return position.z;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public void setRoll(float roll) {
        this.roll = roll;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }

    public void setAspect(float aspect) {
        this.aspect = aspect;
    }

    public void setNearClipping(float nearClipping) {
        this.nearClipping = nearClipping;
    }

    public void setFarClipping(float farClipping) {
        this.farClipping = farClipping;
    }


    public float getAspect() {
        return aspect;
    }

    public float getNearClipping() {
        return nearClipping;
    }

    public float getFarClipping() {
        return farClipping;
    }
}
