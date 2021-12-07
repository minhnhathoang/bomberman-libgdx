package entity;

import animation.AnimationManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import manager.GameManager;

import static helper.Vars.GAME_SCALE;
import static helper.Vars.PPM;

public abstract class Entity {

    protected GameManager gameManager;

    protected AnimationManager animationManager;


    protected float offsetX, offsetY;
    protected float speed;

    protected Body body;
    protected boolean destroyed;

    // pixel coordinates at zoom = 1
    protected Vector2 position;

    // coordinates at tilemap
    protected int x, y;

    // coordinates at World when body != null
    protected Vector2 positionBody;

    public Entity(GameManager gameManager) {
        animationManager = new AnimationManager();

        this.gameManager = gameManager;

        destroyed = false;
    }

    public void updateCoords() {
        x = (int) position.x / 16;
        y = (int) position.y / 16;

        if (body != null) {
            position.x = body.getPosition().x * PPM / GAME_SCALE;
            position.y = body.getPosition().y * PPM / GAME_SCALE;
        }
    }

    public void update() {

    }

    public void render(SpriteBatch batch, Vector2 position) {
        animationManager.render(batch, position, gameManager.getState() == GameManager.State.PAUSE);
    }

    public int getState() {
        return animationManager.getCurrentState();
    }

    public void setState(int newStage) {
        animationManager.setCurrentState(newStage);
    }

    // factory method
    public abstract void createBody();

    public void destroy() {
        destroyed = true;
        gameManager.getBodies().add(this.body);
    }

    public Body getBody() {
        return body;
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
