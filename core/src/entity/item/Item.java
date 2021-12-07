package entity.item;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import entity.Player;
import manager.Box2dManager;
import manager.GameManager;
import map.TileMap;

import static helper.Vars.GAME_SCALE;
import static helper.Vars.PPM;

public abstract class Item {

    protected TextureRegion texture;
    protected Vector2 position;
    protected Body body;
    protected World world;
    protected float time;
    protected State currentState;
    int x;
    int y;
    public Item(int x, int y) {
        currentState = State.HIDE;
        this.x = x;
        this.y = y;
        position = new Vector2(16 * x + 8, 16 * y + 8);

        time = 10000f;
    }

    public abstract void update(Player player, float delta);

    public boolean checkToUnhide(TileMap map) {
        return map.checkMoveableTile(x, y);
    }

    public void render(SpriteBatch batch) {
        if (currentState == State.HIDE) {
            return;
        }
        batch.begin();
        batch.draw(texture, body.getPosition().x - 0.32f, body.getPosition().y - 0.3f, 16f / PPM * GAME_SCALE, 16f / PPM * GAME_SCALE);
        batch.end();
    }

    public void destroy(GameManager gameManager) {
        gameManager.getBodies().add(this.body);
    }

    public void createBody() {
        body = Box2dManager.createCircle(this.position, 7, BodyDef.BodyType.StaticBody, true, world);
    }

    ;

    public float getTime() {
        return time;
    }

    public void setCurrentState(boolean b) {
        if (b) {
            currentState = State.UNHIDE;
        } else {
            currentState = State.HIDE;
        }
    }

    public enum State {
        HIDE,
        UNHIDE
    }
}
