package de.ztube.pane;

import de.ztube.pane.maths.MatrixUtils;
import de.ztube.pane.maths.Matrix4f;
import de.ztube.pane.model.Entity;
import de.ztube.pane.model.Mesh;
import de.ztube.pane.shader.StaticShader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Renderer {

    // Render an Entity using a given shader through the view of a camera
    public void render(Entity entity, StaticShader shader, Camera camera){
        Mesh model = entity.getMesh();

        // Bind all the data from the mesh
        GL30.glBindVertexArray(model.getVaoID());

        // Enable the single Attributes such as vertices or color
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);

        // Create and load a transformation matrix
        Matrix4f transformationMatrix = MatrixUtils.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScaleX(), entity.getScaleY(), entity.getScaleZ());
        shader.loadTransformationMatrix(transformationMatrix);

        // Create and load a view matrix
        Matrix4f viewMatrix = MatrixUtils.createViewMatrix(camera);
        shader.loadViewMatrix(viewMatrix);

        // Create and load a projection matrix
        Matrix4f projectionMatrix = MatrixUtils.createProjectionMatrix(camera);
        shader.loadProjectionMatrix(projectionMatrix);

        // Set the currently used texture to the entity's texture
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, entity.getTexture().getID());
        // Draw the texture
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);

        // Disable vertex Attributes again
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);

        // Unbind the data
        GL30.glBindVertexArray(0);
    }
}
