package com.longarmx.ld30;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Spritesheet {
    
    public static final Spritesheet tiles = new Spritesheet("tiles", 32);
    public static final Spritesheet entities = new Spritesheet("entities", 32);
    
    private Texture texture;
    private TextureRegion[][] regions;
    private String name;
    private int tilesX, tilesY;
    
    private Spritesheet(String name, int tileSize) {
	this.name = name;
	
	texture = new Texture(Gdx.files.local("res/textures/" + name + ".png"));
	
	tilesX = (int) Math.floor(texture.getWidth() / tileSize);
	tilesY = (int) Math.floor(texture.getHeight() / tileSize);
	
	regions = new TextureRegion[tilesX][tilesY];
	for(int i = 0; i < regions.length; i++) {
	    for(int j = 0; j < regions[0].length; j++) {
		regions[i][j] = new TextureRegion(texture, i * tileSize, j * tileSize, tileSize, tileSize);
	    }
	}
    }
    
    public TextureRegion getRegion(int x, int y) {
	if(x < 0 || x >= tilesX || y < 0 || y > tilesY) {
	    System.out.println("Region doesn't exist: " + x + " : " + y + " in " + name);
	    return null;
	}
	
	return regions[x][y];
    }
    
    public TextureRegion getRaw(int x, int y, int width, int height) {
	return new TextureRegion(texture, x, y, width, height);
    }
    
    @Override
    public void finalize() {
	texture.dispose();
    }

}
