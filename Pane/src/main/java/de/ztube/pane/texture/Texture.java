package de.ztube.pane.texture;

// Represents a texture, to be more accurate the ID which points to the texture
public class Texture {

    private int textureID;

    public Texture(int id){
        this.textureID = id;
    }

    public int getID(){
        return textureID;
    }
}
