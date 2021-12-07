package entity.enemies;

import entity.Bomb;
import manager.Box2dManager;
import manager.GameManager;
import manager.ResourceManager;
import helper.Rand;
import helper.Vars;

import java.util.Iterator;

public class Dahl extends Enemy {

    public Dahl(GameManager gameManager, int x, int y) {
        super(gameManager, x, y);

        animationManager.addAnimation(State.LEFT.ordinal(), ResourceManager.INSTANCE.dahl[0], 1 / 5f, true);
        animationManager.addAnimation(State.RIGHT.ordinal(), ResourceManager.INSTANCE.dahl[0], 1 / 5f);

        animationManager.setCurrentState(State.LEFT.ordinal());
        animationManager.setLooping(true);

        createBody();

        currentDirection = Rand.rand(4);
        this.body.setLinearVelocity(Vars.dx[currentDirection], Vars.dy[currentDirection]);

        score = 400;
    }

    public void update() {
        Iterator<Bomb> itBomb = gameManager.getBombs().iterator();
        while (itBomb.hasNext()) {
            Bomb bomb = itBomb.next();

            if (Box2dManager.isColliding(bomb.getBody(), this.body)) {
                bomb.destroy();
                itBomb.remove();
            }
        }

        if (Box2dManager.isStopped(this.body)) {
            currentDirection = Rand.rand(4);
            this.body.setLinearVelocity(Vars.dx[currentDirection], Vars.dy[currentDirection]);
        }

        if (this.body.getLinearVelocity().x <= 0) {
            animationManager.setCurrentState(State.LEFT.ordinal());
        } else {
            animationManager.setCurrentState(State.RIGHT.ordinal());
        }

    }
}
