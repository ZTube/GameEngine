package de.ztube.pane.maths;

import de.ztube.pane.Camera;

public class MatrixUtils {

    // Creates a transformation matrix, which is responsible for scaling, rotating and translating objects
    public static Matrix4f createTransformationMatrix(Vector3f translation, float rotX, float rotY, float rotZ, float scaleX, float scaleY, float scaleZ){
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();
        matrix = matrix.multiply(Matrix4f.translate(translation.x, translation.y, translation.z));
        matrix = matrix.multiply(Matrix4f.rotate((float) Math.toRadians(rotX), 1, 0, 0));
        matrix = matrix.multiply(Matrix4f.rotate((float) Math.toRadians(rotY), 0, 1, 0));
        matrix = matrix.multiply(Matrix4f.rotate((float) Math.toRadians(rotZ), 0, 0, 1));
        matrix = matrix.multiply(Matrix4f.scale(scaleX, scaleY, scaleZ));

        return matrix;
    }

    // Creates a view matrix which rotates and moves the world according to the current camera
    public static Matrix4f createViewMatrix(Camera camera){
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();
        matrix = matrix.multiply(Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), 1, 0, 0));
        matrix = matrix.multiply(Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), 0, 1, 0));
        matrix = matrix.multiply(Matrix4f.rotate((float) Math.toRadians(camera.getRoll()), 0, 0, 1));
        matrix = matrix.multiply(Matrix4f.translate(-camera.getPosition().x, -camera.getPosition().y, -camera.getPosition().z));

        return matrix;
    }

    // Creates a projection matrix which projects the 3dimensional world into the 2 dimensions of a screen
    public static Matrix4f createProjectionMatrix(Camera camera){
        return Matrix4f.perspective(camera.getFov(), camera.getAspect(), camera.getNearClipping(), camera.getFarClipping());
    }
}
