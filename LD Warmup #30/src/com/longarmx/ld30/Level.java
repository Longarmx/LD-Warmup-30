package com.longarmx.ld30;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.longarmx.ld30.World.WorldPosition;

public class Level {

    private Tile[][] tiles;
    
    public Level(String name, WorldPosition pos) {
	tiles = loadLevel(name, pos);
    }
    
    public void render(SpriteBatch batch) {
	for(int i = 0; i < tiles.length; i++) {
	    for(int j = 0; j < tiles[0].length; j++) {
		tiles[i][j].render(batch);
	    }
	}
    }
    
    public Vector2 getStartPos() {
	Vector2 pos = new Vector2(0, 0);
	
	for(int i = 0; i < tiles.length; i++) {
	    for(int j = 0; j < tiles[0].length; j++) {
		if(tiles[i][j].getType() == Tile.START) {
		    pos = new Vector2(i * Tile.SIZE, j * Tile.SIZE);
		    break;
		}
	    }
	}
	
	return pos;
    }
    
    public Tile getTileAt(int x, int y) {
	int fx = (int) Math.floor(x / Tile.SIZE);
	int fy = (int) Math.floor(y / Tile.SIZE);
	
	if(fx < 0 || fy < 0 || fx >= tiles.length || fy >= tiles[0].length) {
	    System.out.println("No tile exists at: " + fx + " : " + fy);
	    return null;
	}
	    
	return tiles[fx][fy];
    }
    
    private static String readValue(String line) {
	line = line.replace("\n", "");
	
	return line.split("=")[1];
    }
    
    public static Tile[][] loadLevel(String name, WorldPosition pos) {
	
	name = "res/levels/" + name;
	
	int width, height;
	Tile[][] tiles = null;
	
	// Read the .dat file for level information and file names
	BufferedReader input;
	try {
	    //input = new BufferedReader(new FileReader(name + ".dat"));
	    
	    input = new BufferedReader(new FileReader(name + "_" + pos.ordinal() + ".lvl"));
	    
	    // Width of the level
	    width = Integer.parseInt(readValue(input.readLine()));
	    // Height of the level
	    height = Integer.parseInt(readValue(input.readLine()));
	    
	    tiles = new Tile[width][height];
	    
	    // Read left side
	    //input = new BufferedReader(new FileReader(name + "_" + pos.ordinal() + ".lvl"));
	    
	    int value = 0;
	    int x = 0, y = height-1;
	    while((value = input.read()) != -1) {
		// Value is line feed or carriage return
		if(value == 10)
		    continue;
		if(value == 13) {
		    y--;
		    x = 0;
		    continue;
		}
		
		tiles[x][y] = new Tile(value - 48, x, y);
		x++;
	    }
	    
	    input.close();
	    
	} catch (IOException e) {
	    e.printStackTrace();
	    Gdx.app.exit();
	}
	
	return tiles;		
    }

}
