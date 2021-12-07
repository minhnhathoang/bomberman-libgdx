package entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import manager.Box2dManager;
import manager.GameManager;
import manager.ResourceManager;
import processor.GameInputProcessor;
import helper.Vars;

import java.util.ArrayList;
import java.util.List;

public class Player extends Entity {

    public List<Bomb> bombs;
    private float delay;
    private float timer;
    private int score;
    private int power;
    private int maxBombs;
    private List<Integer> queue;

    public Player(GameManager gameManager) {
        super(gameManager);

        animationManager.addAnimation(State.UP.ordinal(), ResourceManager.INSTANCE.spritePlayerUp[0], 1 / 10f);
        animationManager.addAnimation(State.DOWN.ordinal(), ResourceManager.INSTANCE.spritePlayerDown[0], 1 / 10f);
        animationManager.addAnimation(State.RIGHT.ordinal(), ResourceManager.INSTANCE.spritePlayerRight[0], 1 / 10f);
        animationManager.addAnimation(State.LEFT.ordinal(), ResourceManager.INSTANCE.spritePlayerRight[0], 1 / 10f, true);
        animationManager.addAnimation(State.IDLE.ordinal(), ResourceManager.INSTANCE.spritePlayerIdle[0], 1 / 7f);
        animationManager.addAnimation(State.DEFEAT.ordinal(), ResourceManager.INSTANCE.spritePlayerDefeat[0], 1 / 10f);
        animationManager.addAnimation(State.VICTORY.ordinal(), ResourceManager.INSTANCE.spritePlayerVictory[0], 1 / 9f);

        speed = 200f;

        position = new Vector2(24, 181);
        x = 1;
        y = 11;

        bombs = new ArrayList<>();

        animationManager.setCurrentState(State.DOWN.ordinal());
        animationManager.setLooping(true);

        timer = 0;

        score = 0;
        power = 1;

        this.createBody();
        maxBombs = 2;

        queue = new ArrayList<>(maxBombs);
        queue.add(1);
        queue.add(1);
    }

    @Override
    public void update() {
        if (this.getCurrentState() == State.DEFEAT.ordinal() || this.getCurrentState() == State.VICTORY.ordinal()) {
            this.body.setLinearVelocity(0, 0);
            this.body.getFixtureList().get(0).setSensor(true);
            return;
        }

        super.updateCoords();

        offsetX = 0;
        offsetY = 0;

        if (GameInputProcessor.isHold(Input.Keys.DOWN)) {
            offsetY -= speed;
            setState(State.DOWN.ordinal());
        }
        if (GameInputProcessor.isHold(Input.Keys.UP)) {
            offsetY += speed;
            setState(State.UP.ordinal());
        }
        if (GameInputProcessor.isHold(Input.Keys.LEFT)) {
            offsetX -= speed;
            setState(State.LEFT.ordinal());
        }
        if (GameInputProcessor.isHold(Input.Keys.RIGHT)) {
            offsetX += speed;
            setState(State.RIGHT.ordinal());
        }

        delay -= Gdx.graphics.getDeltaTime();
        if (delay <= 0) {
            delay = 1.5f;
            if (queue.size() < maxBombs) {
                queue.add(1);
            }
        }

        // kick bomb
        if (Gdx.input.isKeyPressed(Input.Keys.X) && !queue.isEmpty()) {
            delay -= Gdx.graphics.getDeltaTime();
            System.out.println("X*****Y" + x + " " + y);
            gameManager.getBombs().add(new Bomb(gameManager, x, y, this.power));
            queue.remove(0);
        }

        body.setLinearVelocity(offsetX / Vars.PPM, offsetY / Vars.PPM);

        if (animationManager.getCurrentState() <= 4) {
            animationManager.setRunning(offsetX != 0 || offsetY != 0);
        }

        if (offsetX == 0 && offsetY == 0) {
            timer += Gdx.graphics.getDeltaTime();
            if (timer >= 5) {
                animationManager.setCurrentState(State.IDLE.ordinal());
                animationManager.setRunning(true);
            }
        } else {
            timer = 0;
        }
    }

    @Override
    public void render(SpriteBatch batch, Vector2 position) {
        if (gameManager.getState() == GameManager.State.PAUSE) {
            body.setLinearVelocity(0, 0);
        }
        if (this.getCurrentState() == State.DEFEAT.ordinal() || this.getCurrentState() == State.VICTORY.ordinal()) {
            animationManager.setRunning(true);
        }
        super.render(batch, new Vector2(position.x - 0.3f, position.y - 0.3f));
    }

    public void createBody() {
        this.body = Box2dManager.createCircle(new Vector2(position), 7, BodyDef.BodyType.DynamicBody, false, gameManager.getWorld());
        this.positionBody = body.getPosition();
    }

    public int getCurrentState() {
        return animationManager.getCurrentState();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void increaseMaxBombs() {
        this.maxBombs++;
    }

    public void increasePower() {
        this.power++;
    }

    public void increaseSpeed() {
        this.speed += 50;
    }

    public enum State {
        UP,
        DOWN,
        RIGHT,
        LEFT,
        IDLE,
        VICTORY,
        DEFEAT
    }
}
