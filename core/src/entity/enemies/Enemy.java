package entity.enemies;

import animation.AnimationManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import entity.Entity;
import manager.Box2dManager;
import manager.GameManager;
import manager.ResourceManager;
import map.TileMap;

public class Enemy extends Entity {

    protected int currentDirection;

    protected float hp;
    protected int score;

    protected float speedLimit;

    public Enemy(GameManager gameManager) {
        super(gameManager);
    }

    public Enemy(GameManager gameManager, int x, int y) {
        super(gameManager);

        this.position = TileMap.coordToPos(x, y);

        animationManager = new AnimationManager();

        animationManager.addAnimation(State.DEAD.ordinal(), ResourceManager.INSTANCE.enemyDeath[0], 1 / 3f);
    }

    public void update() {
        super.update();
        super.updateCoords();
        if (this.getState() == Enemy.State.DEAD.ordinal()) {
            this.destroy();
        }
    }

    @Override
    public void render(SpriteBatch batch, Vector2 position) {
        if (gameManager.getState() == GameManager.State.PAUSE) {
            body.setLinearVelocity(0, 0);
        }
        if (this.getState() == State.DEAD.ordinal() && animationManager.isFinished()) {
            super.render(batch, new Vector2(body.getPosition().x - 0.3f, body.getPosition().y - 0.3f));
            destroy();
            return;
        }
        super.render(batch, new Vector2(body.getPosition().x - 0.3f, body.getPosition().y - 0.3f));
    }

    @Override
    public void createBody() {
        body = Box2dManager.createCircle(new Vector2(this.position), 7, BodyDef.BodyType.DynamicBody, false, gameManager.getWorld());
    }

    public int getScore() {
        return score;
    }

    public enum State {
        LEFT,
        RIGHT,

        DEAD
    }

    public boolean isCenterTile(int x, int y) {
        Vector2 u = TileMap.coordToPos(x, y);
        return 1f > Math.hypot(this.position.x - u.x, this.position.y - u.y);
    }
}
