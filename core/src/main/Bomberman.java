package main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import entity.Player;
import resources.ResourceManager;
import screen.MenuScreen;

public class Bomberman extends Game {

	public static final String GAME_TITLE = "Bomberman | fps: %s";
	public static final int WIDTH = 1200;
	public static final int HEIGHT = 1000;

	// Renderer
	public SpriteBatch batch;
	//
	public ResourceManager resource;

	//
	public Player player;

	private MenuScreen menu;

	@Override
	public void create () {
		batch = new SpriteBatch();
		resource = new ResourceManager();

		menu = new MenuScreen(this, resource);

		this.setScreen(menu);
	}

	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.graphics.setTitle(String.format(GAME_TITLE, Gdx.graphics.getFramesPerSecond()));
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		super.dispose();

		resource.dispose();
	}
}
