package de.ztube.pane.model;

// A mesh represents some points in 3dimensional space.
public class Mesh {

    // The ID of the Vertex Array Object
    private int vaoID;
    // The number of vertices which the mesh consists of
    private int vertexCount;

    public Mesh(int vaoID, int vertexCount){
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
    }


    public int getVaoID() {
        return vaoID;
    }

    public int getVertexCount() {
        return vertexCount;
    }
}
