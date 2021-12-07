package main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.esotericsoftware.kryonet.Client;
import manager.SoundManager;
import screen.MenuScreen;

public class Bomberman extends Game {

    public static final String GAME_TITLE = "Bomberman | fps: %s";

    public OrthographicCamera camera;
    private Client client;

    public static Bomberman getInstance() {
        return (Bomberman) Gdx.app.getApplicationListener();
    }

    @Override
    public void create() {

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 1000, 900);

        SoundManager.playMusic("music_menu");

        this.setScreen(new MenuScreen());
        //this.setScreen(new ConnectScreen(this, resource));
    }

    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.graphics.setTitle(String.format(GAME_TITLE, Gdx.graphics.getFramesPerSecond()));

        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
