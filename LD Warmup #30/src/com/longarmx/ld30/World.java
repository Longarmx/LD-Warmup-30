package com.longarmx.ld30;

import java.io.File;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class World {
    
    private static final int VIEWPORT_WIDTH = Gdx.graphics.getWidth()/2;
    private static final int VIEWPORT_HEIGHT = Gdx.graphics.getHeight();
    
    public static int levelNum = 1;
    
    private OrthographicCamera camera;
    private Player player;
    private Level level;
    private WorldPosition position;
    
    public World(WorldPosition position, InputMultiplexer multiplexer) {
	this.position = position;
	
	camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	centerCamera(0, 0);

	level = new Level("level_1", position);

	player = new Player(level);
	multiplexer.addProcessor(player);
    }
    
    public void update() {
	player.update();
	
	if(player.shouldReset())
	    ((Main)Gdx.app.getApplicationListener()).resetWorlds();
	
	centerCamera(player.getX(), player.getY());
	camera.update();
    }
    
    public void render(SpriteBatch batch) {
	// Clips rendering to one section of the screen
	Gdx.gl.glScissor(getViewportX(), 0, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
	
	batch.setProjectionMatrix(camera.combined);
	level.render(batch);
	
	player.render(batch);
    }
    
    // Smooth scroll between desired position and current position
    private void centerCamera(int x, int y) {
	float deltax = x + VIEWPORT_WIDTH/2 - getViewportX() - camera.position.x;
	float deltay = y - camera.position.y;
	camera.translate(deltax / 10.0f, deltay / 10.0f, 0);
    }
    
    public void reset() {
	player.setPosition(level.getStartPos());
    }
    
    public void nextLevel() {
	if(!new File("res/levels/level_" + levelNum + "_0.lvl").exists()) {
	    System.out.println("no new level! " + levelNum);
	    return;
	}
	
	level = new Level("level_" + levelNum, position);
	player.setLevel(level);
    }
    
    public boolean winCondition() {
	return player.isOnEndingTile();
    }
    
    private int getViewportX() {
	return position.ordinal() * VIEWPORT_WIDTH;
    }
    
    public enum WorldPosition {
	LEFT, RIGHT
    }

}
