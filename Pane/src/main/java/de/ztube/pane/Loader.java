package de.ztube.pane;

import de.ztube.pane.maths.Vector2f;
import de.ztube.pane.maths.Vector3f;
import de.ztube.pane.model.Mesh;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

// General class to load stuff
public class Loader {

    public static final String RES_PREFIX = "Pane/src/main/resources/";

    // List of all Vertex Array Objects (vertices, colors...)
    private List<Integer> vaos = new ArrayList<>();

    // List of all Vertex Buffer Objects (single vertices)
    private List<Integer> vbos = new ArrayList<>();

    // List of all Textures
    private List<Integer> textures = new ArrayList<>();


    // Load a Mesh from java into openGL
    public Mesh loadToVAO(float[] positions, float[] textureCoords, float[] normals, int[] indices) {
        int vaoID = createVAO();
        bindIndicesBuffer(indices);
        storeInVAO(0, 3, positions);
        storeInVAO(1, 2, textureCoords);
        storeInVAO(2, 3, normals);
        unbindVAO();
        return new Mesh(vaoID, indices.length);
    }

    // Create a Mesh from an obj file
    public Mesh loadObjModel(String fileName){
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(new File( fileName + ".obj"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        BufferedReader reader = new BufferedReader(fileReader);
        String line = null;
        List<Vector3f> vertices = new ArrayList<>();
        List<Vector2f> texCoords = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();

        float[] verticesArray;
        float[] texCoordsArray = null;
        float[] normalsArray = null;
        int[] indicesArray = null;


        while(true){
            try {
                line = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }

            if(line == null)
                break;
            String[] currentLine = line.split(" ");

            switch (currentLine[0]){
                case "v":
                    Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]),
                            Float.parseFloat(currentLine[3]));
                    vertices.add(vertex);
                    break;
                case "vt":
                    Vector2f texCoord = new Vector2f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]));
                    texCoords.add(texCoord);
                    break;
                case "vn":
                    Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]),
                            Float.parseFloat(currentLine[3]));
                    normals.add(normal);
                    break;
                case "f":
                    if(texCoordsArray == null)
                        texCoordsArray = new float[vertices.size() * 2];
                    if(normalsArray == null)
                        normalsArray = new float[vertices.size() * 3];


                    for(int i = 1; i < currentLine.length; i++){
                        String[] indexList = currentLine[i].split("/");

                        int currentVertexPointer = Integer.parseInt(indexList[0]) - 1;
                        indices.add(currentVertexPointer);

                        Vector2f currentTex = texCoords.get(Integer.parseInt(indexList[1]) - 1);
                        texCoordsArray[currentVertexPointer * 2] = currentTex.x;
                        texCoordsArray[currentVertexPointer * 2 + 1] = 1 - currentTex.y;

                        Vector3f currentNorm = normals.get(Integer.parseInt(indexList[2]) - 1);
                        normalsArray[currentVertexPointer * 3] = currentNorm.x;
                        normalsArray[currentVertexPointer * 3 + 1] = currentNorm.y;
                        normalsArray[currentVertexPointer * 3 + 2] = currentNorm.z;
                    }

                    break;
                case "#":
                    System.out.println(line.replace("# ", ""));

            }
        }

        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        verticesArray = new float[vertices.size() * 3];
        indicesArray = new int[indices.size()];

        int verticesPointer = 0;
        for(Vector3f vertex : vertices){
            verticesArray[verticesPointer++] = vertex.x;
            verticesArray[verticesPointer++] = vertex.y;
            verticesArray[verticesPointer++] = vertex.z;
        }

        for(int i = 0; i < indices.size(); i++){
            indicesArray[i] = indices.get(i);
        }

        return loadToVAO(verticesArray, texCoordsArray, normalsArray, indicesArray);

    }

    // Load a Texture from a file into openGL (returns ID)
    public int loadTexture(String fileName){
        BufferedImage texture = null;

        try {
            texture = ImageIO.read(new FileInputStream(fileName + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        int pixels[] = new int[texture.getWidth() * texture.getHeight()];
        texture.getRGB(0, 0, texture.getWidth(), texture.getHeight(), pixels, 0, texture.getWidth());
        ByteBuffer buffer = BufferUtils.createByteBuffer(texture.getWidth() * texture.getHeight() * 4); // <-- 4 for RGBA, 3 for RGB

        for(int y = 0; y < texture.getHeight(); y++){
            for(int x = 0; x < texture.getWidth(); x++){
                int pixel = pixels[y * texture.getWidth() + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
                buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
                buffer.put((byte) (pixel & 0xFF));               // Blue component
                buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
            }
        }

        buffer.flip();

        int textureID = GL11.glGenTextures(); //Generate texture ID
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

        // Setup wrap mode
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

        //Setup texture scaling filtering
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

        //Send texel data to OpenGL
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, texture.getWidth(), texture.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

        textures.add(textureID);
        return textureID;
    }

    // Clean up the memory
    public void cleanUp() {
        for(int vao : vaos){
            GL30.glDeleteVertexArrays(vao);
        }
        for(int vbo : vbos){
            GL15.glDeleteBuffers(vbo);
        }
        for(int texture : textures){
            GL11.glDeleteTextures(texture);
        }
    }

    // Create an empty VAO
    private int createVAO(){
        int vaoID = GL30.glGenVertexArrays();
        vaos.add(vaoID);
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }

    // Store a vbo in an vao
    private void storeInVAO(int attributeNumber, int coordinateSize, float[] data) {
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = storeInFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    // Unbind the current VAO
    private void unbindVAO(){
        GL30.glBindVertexArray(0);
    }

    private void bindIndicesBuffer(int[] indices) {
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
        IntBuffer buffer = storeInIntBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    }

    // Create an IntBuffer out of an int array
    private IntBuffer storeInIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    // Create a Float Buffer out of a float array
    private FloatBuffer storeInFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
}
