package entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import entity.enemies.Enemy;
import manager.Box2dManager;
import manager.GameManager;
import manager.ResourceManager;
import manager.SoundManager;
import map.TileMap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Bomb extends Entity {

    public int state;
    public List<Flame> flames = new ArrayList<Flame>();
    float timeCountDown;
    private int power;

    public Bomb(GameManager gameManager, int x, int y, int power) {
        super(gameManager);

        timeCountDown = 2f;
        this.power = power;

        state = State.NORMAL.ordinal();

        animationManager.addAnimation(State.NORMAL.ordinal(), ResourceManager.INSTANCE.spriteBomb[0], 1 / 4f);
        animationManager.setCurrentState(state);
        animationManager.setLooping(true);

        this.x = x;
        this.y = y;

        this.position = new Vector2(x * 16 + 8, y * 16 + 8);

        createBody();

        checkDirection();
    }

    @Override
    public void update() {

        timeCountDown -= Gdx.graphics.getDeltaTime();
        if (timeCountDown <= 0) {
            if (state != State.EXPLODING.ordinal()) {
                SoundManager.playSound("explosion");

                for (Flame flame : flames) {
                    if (flame.body == null) {
                        flame.createBody();
                    }
                }
            }
            state = State.EXPLODING.ordinal();

            Iterator<Flame> it = flames.iterator();
            while (it.hasNext()) {
                Flame flame = it.next();

                if (flame.isFinished()) {
                    flame.destroy();
                    it.remove();
                }

            }
        }

        if (flames.isEmpty()) {
            destroy();
        }

        Iterator<Flame> it = flames.iterator();
        while (it.hasNext()) {
            Flame flame = it.next();
            if (flame.body == null) {
                continue;
            }

            if (Box2dManager.isColliding(flame.body, gameManager.player.body)) {
                gameManager.player.setState(Player.State.DEFEAT.ordinal());
            }

            for (Enemy enemy : gameManager.getEnemies()) {
                if (Box2dManager.isColliding(flame.body, enemy.body)) {
                    enemy.setState(Enemy.State.DEAD.ordinal());
                }
            }

            if (flame.isFinished()) {
                flame.destroy();
                it.remove();
            }
        }
    }

    public void setSensor(Body bodyPlayer) {
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
            for (Flame flame : flames) {
                System.out.println("flame why" + flame.x + " " + flame.y);
                gameManager.getMap().changleTile(flame.x, flame.y, 1, 3);
                flame.render(batch);
            }
        }

    }

    @Override
    public void createBody() {
        body = Box2dManager.createCircle(this.position, 5, BodyDef.BodyType.StaticBody, true, gameManager.getWorld());
        body.setUserData("bomb");
    }

    public void checkDirection() {

        System.out.println("X  _____ Y" +  x + " " + y);
        // center
        flames.add(new Flame(gameManager, Flame.Type.CENTER, x, y));

        // up
        int length = 0;
        for (int i = 1; i <= power; ++i) {
            if (gameManager.getMap().checkMoveableTile(x, y + i)) {
                ++length;
            } else {
                break;
            }
        }
        for (int i = 1; i < length; ++i) {
            flames.add(new Flame(gameManager, Flame.Type.UP, x, y + i));
        }
        if (length != 0) {
            flames.add(new Flame(gameManager, Flame.Type.LAST_UP, x, y + length));
        }
        if (gameManager.getMap().checkBreakableTile(x, y + length + 1) && length < power) {
            flames.add(new Flame(gameManager, Flame.Type.EXPLOSION_BRICK, x, y + length + 1));
        }

        // down
        length = 0;
        for (int i = 1; i <= power; ++i) {
            if (gameManager.getMap().checkMoveableTile(x, y - i)) {
                ++length;
            } else {
                break;
            }
        }
        for (int i = 1; i < length; ++i) {
            flames.add(new Flame(gameManager, Flame.Type.DOWN, x, y - i));
        }
        if (length != 0) {
            flames.add(new Flame(gameManager, Flame.Type.LAST_DOWN, x, y - length));
        }
        if (gameManager.getMap().checkBreakableTile(x, y - length - 1) && length < power) {
            flames.add(new Flame(gameManager, Flame.Type.EXPLOSION_BRICK, x, y - length - 1));
        }
        // left
        length = 0;
        for (int i = 1; i <= power; ++i) {
            if (gameManager.getMap().checkMoveableTile(x - i, y)) {
                ++length;
            } else {
                break;
            }
        }
        for (int i = 1; i < length; ++i) {
            flames.add(new Flame(gameManager, Flame.Type.LEFT, x - i, y));
        }
        if (length != 0) {
            flames.add(new Flame(gameManager, Flame.Type.LAST_LEFT, x - length, y));
        }
        if (gameManager.getMap().checkBreakableTile(x - 1 - length, y) && length < power) {
            flames.add(new Flame(gameManager, Flame.Type.EXPLOSION_BRICK, x - length - 1, y));
        }
        // right
        length = 0;
        for (int i = 1; i <= power; ++i) {
            if (gameManager.getMap().checkMoveableTile(x + i, y)) {
                ++length;
            } else {
                break;
            }
        }
        for (int i = 1; i < length; ++i) {
            flames.add(new Flame(gameManager, Flame.Type.RIGHT, x + i, y));
        }
        if (length != 0) {
            flames.add(new Flame(gameManager, Flame.Type.LAST_RIGHT, x + length, y));
        }
        if (gameManager.getMap().checkBreakableTile(x + 1 + length, y) && length < power) {
            flames.add(new Flame(gameManager, Flame.Type.EXPLOSION_BRICK, x + length + 1, y));
        }
    }

    public void setPower(int power) {
        this.power = power;
    }

    public enum State {
        NORMAL,
        EXPLODING
    }
}
