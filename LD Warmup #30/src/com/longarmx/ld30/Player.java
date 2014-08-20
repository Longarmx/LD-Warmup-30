package com.longarmx.ld30;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Player implements InputProcessor{
    
    private Sprite sprite;
    private Level level;
    
    public Player(Level level) {
	sprite = new Sprite(Spritesheet.entities.getRegion(0, 0));
	setPosition(level.getStartPos());
	sprite.setSize(64, 64);
	
	this.level = level;
    }
    
    public void update() {
	
    }
    
    public void render(SpriteBatch batch) {
	sprite.draw(batch);
    }
    
    private void move(int x, int y) {
	sprite.translate(x * Tile.SIZE, y * Tile.SIZE);
	
	if(level.getTileAt(getX(), getY()).getType() == Tile.TREE)
	    sprite.translate(-x * Tile.SIZE, -y * Tile.SIZE);
    }
    
    public int getX() {
	 return (int)sprite.getX();
    }
    
    public int getY() {
	 return (int)sprite.getY();
    }
    
    public void setPosition(Vector2 position) {
	sprite.setPosition(position.x, position.y);
    }
    
    public void setLevel(Level level) {
	this.level = level;
	setPosition(level.getStartPos());
    }
    
    public boolean isOnEndingTile() {
	return level.getTileAt(getX(), getY()).getType() == Tile.END;
    }
    
    public boolean shouldReset() {
	return level.getTileAt(getX(), getY()).getType() == Tile.TRAP;
    }

    @Override
    public boolean keyDown(int keycode) {
	switch(keycode) {
	case Keys.W:
	    move(0, 1);
	    break;
	case Keys.S:
	    move(0, -1);
	    break;
	case Keys.D:
	    move(1, 0);
	    break;
	case Keys.A:
	    move(-1, 0);
	    break;
	}
	return false;
    }

    @Override
    public boolean keyUp(int keycode) {
	return false;
    }

    @Override
    public boolean keyTyped(char character) {
	return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
	return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
	return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
	return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
	return false;
    }

    @Override
    public boolean scrolled(int amount) {
	return false;
    }

}
