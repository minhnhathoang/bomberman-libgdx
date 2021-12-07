package entity.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import manager.Box2dManager;
import manager.GameManager;
import manager.ResourceManager;
import helper.Rand;
import helper.Vars;

public class Pass extends Enemy {

    protected float timer;

    public Pass(GameManager gameManager, int x, int y) {
        super(gameManager, x, y);

        animationManager.addAnimation(State.LEFT.ordinal(), ResourceManager.INSTANCE.pass[0], 1 / 5f, true);
        animationManager.addAnimation(State.RIGHT.ordinal(), ResourceManager.INSTANCE.pass[0], 1 / 5f);

        animationManager.setCurrentState(State.LEFT.ordinal());
        animationManager.setLooping(true);

        createBody();

        currentDirection = Rand.rand(4);
        this.body.setLinearVelocity(Vars.dx[currentDirection], Vars.dy[currentDirection]);

        score = 200;

        timer = 2.5f;
    }

    public void update() {

        timer -= Gdx.graphics.getDeltaTime();
        if (timer <= 0) {
            timer = 2f;
        }

        if (Box2dManager.isStopped(this.body) || timer == 2f) {
            currentDirection = Rand.rand(8);
            Vector2 v = new Vector2(Vars.dx[currentDirection], Vars.dy[currentDirection]);
            this.body.setLinearVelocity(v.scl(100).limit(5));
        }

        if (this.body.getLinearVelocity().x <= 0) {
            animationManager.setCurrentState(State.LEFT.ordinal());
        } else {
            animationManager.setCurrentState(State.RIGHT.ordinal());
        }

        if (this.getState() == State.DEAD.ordinal()) {
            animationManager.setCurrentState(State.DEAD.ordinal());
        }
    }
}
