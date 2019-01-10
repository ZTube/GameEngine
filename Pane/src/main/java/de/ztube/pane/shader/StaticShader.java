package de.ztube.pane.shader;

import de.ztube.pane.Loader;
import de.ztube.pane.maths.Matrix4f;
import de.ztube.pane.model.DirectionalLight;
import de.ztube.pane.model.PointLight;

// Represents the current shader for simple light calculations and projection into 2dimensional space
public class StaticShader extends ShaderProgram {

    private static final String VERTEX_FILE = Loader.RES_PREFIX + "shaders/vertexShader.gl";
    private static final String FRAGMENT_FILE = Loader.RES_PREFIX + "shaders/fragmentShader.gl";

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_pointLightPosition;
    private int location_pointLightColor;
    private int location_dirLightDirection;
    private int location_dirLightColor;

    public StaticShader(){
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_pointLightPosition = super.getUniformLocation("pointLightPosition");
        location_pointLightColor = super.getUniformLocation("pointLightColor");
        location_dirLightDirection = super.getUniformLocation("dirLightDirection");
        location_dirLightDirection = super.getUniformLocation("dirLightColor");
    }

    public void loadTransformationMatrix(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    public void loadProjectionMatrix(Matrix4f matrix4f){
        super.loadMatrix(location_projectionMatrix, matrix4f);
    }

    public void loadViewMatrix(Matrix4f matrix4f){
        super.loadMatrix(location_viewMatrix, matrix4f);
    }

    public void loadPointLight(PointLight pointLight){
        super.loadVector(location_pointLightPosition, pointLight.getPosition());
        super.loadVector(location_pointLightColor, pointLight.getColor());
    }

    public void loadDirectionalLight(DirectionalLight directionalLight){
        super.loadVector(location_dirLightDirection, directionalLight.getDirection());
        super.loadVector(location_dirLightColor, directionalLight.getColor());
    }
}
