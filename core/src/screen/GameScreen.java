package screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import entity.Player;
import main.Bomberman;
import processor.GameInputProcessor;
import resources.ResourceManager;

public class GameScreen extends AbstractScreen {


    Player player;


    public GameScreen(Bomberman game, ResourceManager resource) {
        super(game, resource);

        camera = new OrthographicCamera();
        viewport = new StretchViewport(400, 400, camera);

        camera.position.set(1000, 1000, 0);
        camera.update();

        player = new Player(resource);

        Gdx.input.setInputProcessor(new GameInputProcessor());
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //game.batch.setProjectionMatrix(camera.combined);
        //super.render(delta);
        //Texture u = new Texture("star.png");
        //game.batch.begin();
        //game.batch.draw(u, 100, 100, 100, 100);
        //game.batch.end();
        player.update();
        player.render(game.batch, delta);

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
