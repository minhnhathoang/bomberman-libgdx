package manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import entity.Bomb;
import entity.Player;
import entity.enemies.Enemy;
import entity.item.Item;
import entity.item.Portal;
import main.Bomberman;
import map.TileMap;
import processor.GameInputProcessor;
import screen.GameScreen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameManager {

    public SpriteBatch batch;
    public Player player;
    private State state;

    private Box2DDebugRenderer box2DDebugRenderer = new Box2DDebugRenderer();
    private boolean debug;

    // world
    private World world;
    private List<Body> bodies;


    private TileMap tileMap;
    private Portal portal;
    //
    private List<Bomb> bombs;
    private List<Item> items;
    //
    private List<Enemy> enemies;
    // timer
    private float timer;

    public GameManager(SpriteBatch batch) {
        this.batch = batch;

        world = new World(new Vector2(0, 0), true);

        state = State.RUNNING;

        bodies = new ArrayList<>();

        bombs = new ArrayList<>();
        items = new ArrayList<>();
        enemies = new ArrayList<>();

        tileMap = new TileMap(this);
        player = new Player(this);

        timer = 180f;

        Gdx.input.setInputProcessor(new GameInputProcessor());
    }

    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            if (state == State.PAUSE) {
                state = State.RUNNING;
            } else {
                state = State.PAUSE;
            }
            SoundManager.playSound("pause");
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
            debug = !debug;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.J)) {
            Bomberman.getInstance().setScreen(new GameScreen());
        }
    }

    public void update(float delta) {

        if (timer <= 0) {
            player.setState(Player.State.DEFEAT.ordinal());
        }

        if (enemies.isEmpty()) {
            portal = new Portal(world, 9, 7);
        }

        this.handleInput();

        if (state == State.PAUSE) {
            return;
        }

        timer -= delta;

        tileMap.update(delta);
        player.update();

        // bomb
        Iterator<Bomb> itBomb = bombs.iterator();
        while (itBomb.hasNext()) {
            Bomb bomb = itBomb.next();

            bomb.setSensor(player.getBody());
            bomb.update();

            if (bomb.isDestroyed()) {
                itBomb.remove();
            }
        }

        // item
        Iterator<Item> itItem = items.iterator();
        while (itItem.hasNext()) {
            Item item = itItem.next();

            item.setCurrentState(item.checkToUnhide(this.tileMap));

            item.update(this.player, delta);

            if (item.getTime() <= 0) {
                item.destroy(this);
                itItem.remove();
            }
        }

        // enemy
        Iterator<Enemy> itEnemy = enemies.iterator();
        while (itEnemy.hasNext()) {
            Enemy enemy = itEnemy.next();

            if (Box2dManager.isColliding(enemy.getBody(), player.getBody())) {
                player.setState(Player.State.DEFEAT.ordinal());
            }

            if (enemy.getState() == Enemy.State.DEAD.ordinal()) {
                enemy.destroy();
                itEnemy.remove();
                player.setScore(player.getScore() + enemy.getScore());
                continue;
            }
            enemy.update();
        }
        ////////////

        if (portal != null) {
            portal.update(player, delta);
        }
    }

    public void render(SpriteBatch batch, OrthographicCamera camera) {

        Gdx.gl.glClearColor(0, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // destroy
        world.step(1 / 60f, 6, 2);
        for (Body body : bodies) {
            world.destroyBody(body);
        }
        bodies.clear();
        //

        tileMap.render(camera);

        for (Item item : items) {
            item.render(batch);
        }

        for (Bomb bomb : bombs) {
            bomb.render(batch);
        }

        Iterator<Enemy> itEnemy = enemies.iterator();
        while (itEnemy.hasNext()) {
            Enemy enemy = itEnemy.next();
            enemy.render(batch, enemy.getBody().getPosition());
        }

        player.render(batch, player.getBody().getPosition());

        if (state == State.PAUSE) {
            batch.begin();
            batch.draw(ResourceManager.INSTANCE.pause, camera.position.x - 1.5f, camera.position.y, 0.74f * 5, 0.1f * 5);
            batch.end();
        }

        if (debug) {
            box2DDebugRenderer.render(world, camera.combined);
        }
        if (portal != null) {
            portal.render(batch);
        }
        hud(batch);
    }

    public void hud(SpriteBatch batch) {

        OrthographicCamera cameraHud = new OrthographicCamera(500, 500);
        cameraHud.position.set(250, 250, 0);
        cameraHud.update();

        batch.setProjectionMatrix(cameraHud.combined);
        batch.begin();


        batch.draw(ResourceManager.INSTANCE.bar, 0, 460, 250 * 2f, 20 * 2f);
        batch.draw(ResourceManager.INSTANCE.mugshots[0][0], 270, 465, 32, 32);

        FontManager.drawText(batch,
                (int) timer / 60 + ":" + (int) timer % 60,
                24,
                55, 485);

        FontManager.drawText(batch, Integer.toString(player.getScore()), 24, 130, 487);

        batch.end();
    }

    public World getWorld() {
        return world;
    }

    public List<Bomb> getBombs() {
        return bombs;
    }

    public List<Item> getItems() {
        return items;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<Body> getBodies() {
        return bodies;
    }

    public TileMap getMap() {
        return tileMap;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public enum State {
        PAUSE,
        RUNNING
    }
}
