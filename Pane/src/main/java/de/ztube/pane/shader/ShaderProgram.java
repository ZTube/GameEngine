package de.ztube.pane.shader;

import de.ztube.pane.maths.Matrix4f;
import de.ztube.pane.maths.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;


// A ShaderProgram represents a shader / OpenGL script
public abstract class ShaderProgram {

    // The ID of the shaderprogram
    private int programID;

    // The ID of the vertexShader responsible for moving the vertices according to the shader
    private int vertexShaderID;

    // The ID of the fragmentShader responsible for drawing the screen (applying texture, light etc)
    private int fragmentShaderID;

    private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);


    public ShaderProgram(String vertexFile, String fragmentFile){
        vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
        fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);

        programID = GL20.glCreateProgram();
        GL20.glAttachShader(programID, vertexShaderID);
        GL20.glAttachShader(programID, fragmentShaderID);
        bindAttributes();
        GL20.glLinkProgram(programID);
        GL20.glValidateProgram(programID);
        getAllUniformLocations();
    }

    // Initialize all uniform variables for the shaders
    protected abstract void getAllUniformLocations();

    // Get the ID of a uniform variable
    protected int getUniformLocation(String uniformName){
        return GL20.glGetUniformLocation(programID, uniformName);
    }

    // Start using this shader
    public void start(){
        GL20.glUseProgram(programID);
    }

    // Stop using this shader
    public void stop(){
        GL20.glUseProgram(0);
    }

    // Clean up this shader from memory
    public void cleanUp(){
        stop();
        GL20.glDetachShader(programID, vertexShaderID);
        GL20.glDetachShader(programID, fragmentShaderID);
        GL20.glDeleteShader(vertexShaderID);
        GL20.glDeleteShader(fragmentShaderID);
        GL20.glDeleteProgram(programID);
    }

    // Bind all attributes in the Vertex Array Object to the indices (eg. index 0 is the position of this vertex)
    protected abstract void bindAttributes();

    // Bind single attribute
    protected void bindAttribute(int attribute, String variableName){
        GL20.glBindAttribLocation(programID, attribute, variableName);
    }

    // Load a float into the opengl shader uniform variable
    protected void loadFloat(int location, float value){
        GL20.glUniform1f(location, value);
    }

    // Load a 3dimensional vector
    protected void loadVector(int location, Vector3f vector){
        GL20.glUniform3f(location, vector.x, vector.y, vector.z);
    }

    // Load a boolean (in opengl either int 0 or 1)
    protected void loadBoolean(int location, boolean value){
        GL20.glUniform1f(location, value ? 1 : 0);
    }

    // Load a matrix
    protected void loadMatrix(int location, Matrix4f matrix){
        matrix.toBuffer(matrixBuffer);
        //matrixBuffer.flip();
        GL20.glUniformMatrix4fv(location, false, matrixBuffer);
    }

    // Read a shader from file into memory
    private static int loadShader(String file, int type){
        StringBuilder shaderSource = new StringBuilder();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine()) != null){
                shaderSource.append(line).append("\n");
            }
            reader.close();
        }catch(IOException e){
            System.err.println("Could not read file!");
            e.printStackTrace();
            System.exit(-1);
        }

        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);
        if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
            System.err.println("Could not compile shader!");
            System.exit(-1);
        }
        return shaderID;
    }
}
