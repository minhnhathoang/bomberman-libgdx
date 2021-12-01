package screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.utils.viewport.*;
import entity.Player;
import main.Bomberman;
import map.TileMap;
import processor.GameContactListener;
import processor.GameInputProcessor;
import resources.ResourceManager;
import resources.Utils;

import java.awt.*;

import static resources.Utils.PPM;

public class GameScreen extends AbstractScreen {

    private static float WIDTH = 1200;
    private static float HEIGHT = 900;

    public TileMap tileMap;
    public Player player;

    private World world;
    Box2DDebugRenderer box2DDebugRenderer;

    public GameScreen(Bomberman game, ResourceManager resource) {
        super(game, resource);

        camera = new OrthographicCamera(WIDTH / PPM, HEIGHT / PPM);
        camera.position.set(WIDTH / PPM / 2f, HEIGHT / PPM / 2f, 0);

        viewport = new FitViewport(WIDTH / PPM, HEIGHT / PPM, camera);

        box2DDebugRenderer = new Box2DDebugRenderer();

        world = new World(new Vector2(0, 0), true);




        player = new Player(resource);
        tileMap = new TileMap(game, resource);

        player.createBody(world);
        tileMap.createBody(world);

        Gdx.input.setInputProcessor(new GameInputProcessor());
    }

    public void handleInput() {

    }

    public void update(float delta) {
        //camera.position.set(player.getBody().getPosition().x, 1.84f, 0);
        //camera.update();
    }

    @Override
    public void render(float delta) {
        player.update();
        handleInput();
        update(delta);

        Gdx.gl.glClearColor(0, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.step(1 / 60f, 6, 2);

        tileMap.render(camera);
        player.render(game.batch, player.getBody().getPosition(), delta);


        game.batch.setProjectionMatrix(camera.combined);
        box2DDebugRenderer.render(world, camera.combined);
        showHud();
    }


    public void showHud() {
        game.batch.begin();
        game.batch.draw(resource.bar, 0, 830 / PPM, 252 * 3.5f/ PPM, 20 * 3.5f/ PPM);
        game.batch.draw(resource.mugshots[0][0], 100/ PPM, 835 / PPM, 32 * 2/ PPM, 32 * 2/ PPM);
        //resource.font50px.draw(game.batch, "Score", 300/ PPM, 770/ PPM);
        game.batch.end();
    }
}
