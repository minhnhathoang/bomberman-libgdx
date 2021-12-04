package entity;

import animation.AnimationManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import map.TileMap;
import resources.ResourceManager;
import screen.GameScreen;

import static resources.Vars.*;

public abstract class Entity {

    protected ResourceManager resourceManager;
    protected AnimationManager animationManager;

    protected World world;

    protected float offsetX, offsetY;
    protected float speed;

    protected Body body;
    protected boolean destroyed;

    // pixel coordinates at zoom = 1
    protected Vector2 position;

    public Entity(ResourceManager resourceManager, World world) {
        animationManager = new AnimationManager(resourceManager);
        this.resourceManager = resourceManager;
        this.world = world;

        destroyed = false;
    }

    public void update() {
        position.x = body.getPosition().x * PPM / GAME_SCALE;
        position.y = body.getPosition().y * PPM / GAME_SCALE;
    }

    public void render(SpriteBatch batch, Vector2 position) {
        animationManager.render(batch, position);
    }

    public void setState(int newStage) {
        animationManager.setCurrentState(newStage);
    }

    // factory method
    public abstract void createBody();

    public void destroy() {
        destroyed = true;
        GameScreen.deathBodies.add(this.body);
    }

    public Body getBody() {
        return body;
    }

    public Vector2 getPosition() {
        return position;
    }

    /*
     * return x, y coordinates in tilemap.
     */
    public Vector2 getTile() {
        return new Vector2((int) position.x / 16, (int) position.y / 16);
    }

    public Vector2 getCoordinate() {
        Vector2 cell = this.getTile();
        return new Vector2(cell.x * 16 + 8, cell.y * 16 + 8);
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
