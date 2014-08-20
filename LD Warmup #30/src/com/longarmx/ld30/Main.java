package com.longarmx.ld30;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.longarmx.ld30.World.WorldPosition;

public class Main implements ApplicationListener {

    public static void main(String[] args) {
	LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
	config.width = 1280;
	config.height = 720;
	config.title = "LD #30 Warmup: Parallel Worlds";
	config.useGL20 = true;
	config.vSyncEnabled = true;
	config.resizable = false;
	new LwjglApplication(new Main(), config);
    }

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private World leftWorld, rightWorld;
    private Texture divider;

    @Override
    public void create() {
	batch = new SpriteBatch();
	camera = new OrthographicCamera(Gdx.graphics.getWidth(),
		Gdx.graphics.getHeight());
	camera.translate(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
	
	InputMultiplexer multiplexer = new InputMultiplexer();
	
	leftWorld = new World(WorldPosition.LEFT, multiplexer);
	rightWorld = new World(WorldPosition.RIGHT, multiplexer);
	
	Gdx.input.setInputProcessor(multiplexer);
	
	divider = new Texture(Gdx.files.local("res/textures/divider.png"));
    }

    @Override
    public void resize(int width, int height) {
    }

    private void update() {
	camera.update();
	leftWorld.update();
	rightWorld.update();
	
	if(leftWorld.winCondition() ^ rightWorld.winCondition()) {
	   resetWorlds();
	}
	
	if(leftWorld.winCondition() && rightWorld.winCondition()) {
	    World.levelNum++;
	    leftWorld.nextLevel();
	    rightWorld.nextLevel();
	}
	
	if(Gdx.input.isKeyPressed(Keys.R))
	    resetWorlds();
    }
    
    public void resetWorlds() {
	 leftWorld.reset();
	 rightWorld.reset();
    }

    @Override
    public void render() {
	update();
	
	Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	Gdx.gl.glClearColor(0.263f, 0.62f, 0.878f, 1);
	
	batch.begin();
	
	Gdx.gl.glEnable(GL10.GL_SCISSOR_TEST);
	
	leftWorld.render(batch);
	batch.flush();
	rightWorld.render(batch);
	batch.flush();
	
	Gdx.gl.glDisable(GL10.GL_SCISSOR_TEST);
	
	batch.setProjectionMatrix(camera.combined);
	batch.draw(divider, Gdx.graphics.getWidth()/2 - divider.getWidth(), 0, divider.getWidth() * 2, divider.getHeight() * 2);

	batch.end();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
	batch.dispose();
	divider.dispose();
    }

}
