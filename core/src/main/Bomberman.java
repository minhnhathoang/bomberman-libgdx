package main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import entity.Player;
import map.Map;
import processor.GameInputProcessor;
import resources.ResourceManager;
import screen.MenuScreen;

public class Bomberman extends Game {

	public static final String GAME_TITLE = "Bomberman | fps: %s";

	// Renderer
	public SpriteBatch batch;
	//
	public ResourceManager resource;

	//
	public Player player;

	private MenuScreen menu;
	private Map map;
	OrthographicCamera camera;

	@Override
	public void create () {
		batch = new SpriteBatch();
		resource = new ResourceManager();

		menu = new MenuScreen(this, resource);
		map = new Map(this, resource);
		map.loadMap("map/stage1.tmx");

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 4);
		camera.update();

		this.setScreen(menu);
	}

	public void render () {
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
