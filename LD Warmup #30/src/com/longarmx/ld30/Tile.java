package com.longarmx.ld30;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Tile {
    
    public static final int DIRT = 0;
    public static final int TREE = 1;
    public static final int START = 2;
    public static final int END = 3;
    public static final int TRAP = 4;
    
    public static final int SIZE = 64;
    
    private int type;
    private TextureRegion region;
    private int x, y;
    
    public Tile(int type, int x, int y) {
	region = Spritesheet.tiles.getRegion(0, type);
	
	this.type = type;
	this.x = x * SIZE;
	this.y = y * SIZE;
    }
    
    public void render(SpriteBatch batch) {
	batch.draw(region, x, y, SIZE, SIZE);
    }
    
    public int getType() {
	return type;
    }
    
}
