package entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSorter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import map.TileMap;
import resources.ResourceManager;

import javax.security.sasl.SaslServer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Bomb extends Entity {

    private TileMap tileMap;

    private short power;

    public enum State {
        NORMAL,
        EXPLODING
    }

    public int state;
    float timeCountDown;

    public List<Flame> flames = new ArrayList<Flame>();

    public Bomb(ResourceManager resource, World world, TileMap tileMap, Vector2 position) {
        super(resource, world);
        this.tileMap = tileMap;

        this.position = position;

        timeCountDown = 2f;
        power = 5;

        state = State.NORMAL.ordinal();

        animationManager.addAnimation(State.NORMAL.ordinal(), resourceManager.spriteBomb[0], 1/4f);
        animationManager.setCurrentState(state);
        animationManager.setLooping(true);

        createBody();
        checkDirection();

    }

    @Override
    public void update() {

        timeCountDown -= Gdx.graphics.getDeltaTime();
        if (timeCountDown <= 0) {
            state = State.EXPLODING.ordinal();
        }

        if (flames.isEmpty()) {
            destroy();
        }


        Iterator<Flame> it = flames.iterator();
        while (it.hasNext()) {
            Flame flame = it.next();


            if (flame.isFinished()) {
                flame.destroy();
                it.remove();
            }

        }


    }

    public void setSensor(Body bodyPlayer) {
        /*
        if (this.body == null) {
            return;
        }
        if (this.body.getFixtureList().isEmpty()) {
            return;
        }

         */
        if (!this.body.getFixtureList().get(0).isSensor()) {
            return;
        }
        if (!Box2dManager.checkOverlap(this.body, bodyPlayer)) {
            this.body.getFixtureList().get(0).setSensor(false);
        }
    }

    public void render(SpriteBatch batch) {
        if (state == State.NORMAL.ordinal()) {
            super.render(batch, new Vector2(this.body.getPosition().x - 0.3f, this.body.getPosition().y - 0.3f));
        } else {
            System.out.println(flames.size() + "   " + "render flame");
            for (Flame flame : flames) {
                flame.render(batch);
            }
        }

    }

    @Override
    public void createBody() {
        body = Box2dManager.createCircle(this.getCoordinate(), 5, BodyDef.BodyType.StaticBody, true, world);
        body.setUserData("bomb");
    }

    public void checkDirection() {
        Vector2 coord = this.getTile();

        // center
        flames.add(new Flame(resourceManager, world, Flame.Type.CENTER, position));

        // up
        int length = 0;
        for (int i = 1; i < power; ++i) {
            if (tileMap.checkMoveableTile((int) coord.x, (int) coord.y + i)) {
                ++length;
            } else {
                break;
            }
        }
        for (int i = 1; i < length; ++i) {
            flames.add(new Flame(resourceManager, world, Flame.Type.UP, (int) coord.x, (int) coord.y + i));
        }
        if (length != 0) {
            flames.add(new Flame(resourceManager, world, Flame.Type.LAST_UP, (int) coord.x, (int) coord.y + length));
        }

        // down
        length = 0;
        for (int i = 1; i < power; ++i) {
            if (tileMap.checkMoveableTile((int) coord.x, (int) coord.y - i)) {
                ++length;
            } else {
                break;
            }
        }
        for (int i = 1; i < length; ++i) {
            flames.add(new Flame(resourceManager, world, Flame.Type.DOWN, (int) coord.x, (int) coord.y - i));
        }
        if (length != 0) {
            flames.add(new Flame(resourceManager, world, Flame.Type.LAST_DOWN, (int) coord.x, (int) coord.y - length));
        }
        // left
        length = 0;
        for (int i = 1; i < power; ++i) {
            if (tileMap.checkMoveableTile((int) coord.x - i, (int) coord.y)) {
                ++length;
            } else {
                break;
            }
        }
        for (int i = 1; i < length; ++i) {
            flames.add(new Flame(resourceManager, world, Flame.Type.LEFT, (int) coord.x - i, (int) coord.y));
        }
        if (length != 0) {
            flames.add(new Flame(resourceManager, world, Flame.Type.LAST_LEFT, (int) coord.x - length, (int) coord.y));
        }
        // right
        length = 0;
        for (int i = 1; i < power; ++i) {
            if (tileMap.checkMoveableTile((int) coord.x + i, (int) coord.y)) {
                ++length;
            } else {
                break;
            }
        }
        for (int i = 1; i < length; ++i) {
            flames.add(new Flame(resourceManager, world, Flame.Type.RIGHT, (int) coord.x + i, (int) coord.y));
        }
        if (length != 0) {
            flames.add(new Flame(resourceManager, world, Flame.Type.LAST_RIGHT, (int) coord.x + length, (int) coord.y));
        }
    }
}
