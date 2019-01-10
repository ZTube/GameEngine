package de.ztube.testgame;

import de.ztube.pane.*;
import de.ztube.pane.Loader;
import de.ztube.pane.maths.Vector3f;
import de.ztube.pane.model.DirectionalLight;
import de.ztube.pane.model.PointLight;
import de.ztube.pane.model.Entity;
import de.ztube.pane.shader.StaticShader;
import de.ztube.pane.texture.Texture;
import org.lwjgl.opengl.GL11;

// A simple class to test out the features of the engine
public class TestGame extends Game {

    Loader loader;
    Renderer renderer;
    Entity entity;
    StaticShader shader;
    Camera camera;
    PointLight pointLight;
    DirectionalLight dirLight;

    // Start the game
    public static void main(String[] args){
        new TestGame();
    }

    @Override
    public void onInit() {
        System.out.println("Init");

        loader = new Loader();
        renderer = new Renderer();
        shader = new StaticShader();





        // Load a texture
        Texture texture = new Texture(loader.loadTexture("TestGame/src/main/resources/stallTexture"));

        // Create an entity from an obj file
        entity = new Entity(loader.loadObjModel("TestGame/src/main/resources/stall"), texture,
                new Vector3f(0, 0, 0), 0, 0, 0, 1, 1, 1);

        // Set up a camera
        camera = new Camera(70, 1, 0.1f, 1000);
        camera.setPosition(new Vector3f(0f, 3f, 10f));
        //GL11.glLineWidth(2.0f);
        //GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
        pointLight = new PointLight(new Vector3f(0, 3, 5), new Vector3f(1, 1,1));
        dirLight = new DirectionalLight(new Vector3f(0, -1, 0), new Vector3f(1, 1,1 ));
    }


    // Called every time the scene is rendered
    @Override
    public void onUpdate() {
        GL11.glClearColor(0f, 0f, 0f, 1f);

        // Load the StaticShader
        shader.start();

        //entity.move(0, 0, -0.01f);
        shader.loadPointLight(pointLight);
        shader.loadDirectionalLight(dirLight);

        // Slowly rotates the shown object
        entity.rotate(0f, 10f, 0f);

        renderer.render(entity, shader, camera);
        // Unload the StaticShader
        shader.stop();
    }

    // Called when the program exits
    @Override
    public void onExit() {
        shader.cleanUp();
        loader.cleanUp();
        System.out.println("Exit");
    }
}
