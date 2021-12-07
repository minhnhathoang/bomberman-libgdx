package entity.enemies;

import manager.Box2dManager;
import manager.GameManager;
import manager.ResourceManager;
import helper.Rand;
import helper.Vars;

public class Balloom extends Enemy {

    public Balloom(GameManager gameManager, int x, int y) {
        super(gameManager, x, y);

        animationManager.addAnimation(State.LEFT.ordinal(), ResourceManager.INSTANCE.balloom[0], 1 / 5f, true);
        animationManager.addAnimation(State.RIGHT.ordinal(), ResourceManager.INSTANCE.balloom[0], 1 / 5f);

        animationManager.setCurrentState(State.LEFT.ordinal());
        animationManager.setLooping(true);

        createBody();

        currentDirection = Rand.rand(4);
        this.body.setLinearVelocity(Vars.dx[currentDirection], Vars.dy[currentDirection]);

        score = 200;
    }

    public void update() {

        if (Box2dManager.isStopped(this.body)) {
            currentDirection = Rand.rand(4);
            this.body.setLinearVelocity(Vars.dx[currentDirection], Vars.dy[currentDirection]);
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
