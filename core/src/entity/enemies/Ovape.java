package entity.enemies;

import com.badlogic.gdx.math.Vector2;
import entity.Bomb;
import manager.Box2dManager;
import manager.GameManager;
import manager.ResourceManager;
import helper.Rand;
import helper.Vars;

import java.util.Iterator;

public class Ovape extends Enemy {

    public Ovape(GameManager gameManager, int x, int y) {
        super(gameManager, x, y);

        animationManager.addAnimation(State.LEFT.ordinal(), ResourceManager.INSTANCE.ovape[0], 1 / 5f, true);
        animationManager.addAnimation(State.RIGHT.ordinal(), ResourceManager.INSTANCE.ovape[0], 1 / 5f);

        animationManager.setCurrentState(State.LEFT.ordinal());
        animationManager.setLooping(true);

        createBody();

        currentDirection = Rand.rand(4);
        this.body.setLinearVelocity(Vars.dx[currentDirection], Vars.dy[currentDirection]);

        score = 1000;

        speedLimit = 1f;
    }

    public void update() {

        Iterator<Bomb> itBomb = gameManager.getBombs().iterator();
        while (itBomb.hasNext()) {
            Bomb bomb = itBomb.next();

            if (Box2dManager.isColliding(bomb.getBody(), this.body)) {
                bomb.destroy();
                itBomb.remove();
                speedLimit += 0.25f;
                return;
            }
        }

        if (Box2dManager.isStopped(this.body)) {
            currentDirection = Rand.rand(4);
            Vector2 v = new Vector2(Vars.dx[currentDirection], Vars.dy[currentDirection]);
            this.body.setLinearVelocity(v.scl(100).limit(speedLimit));
        }

        if (this.body.getLinearVelocity().x <= 0) {
            animationManager.setCurrentState(State.LEFT.ordinal());
        } else {
            animationManager.setCurrentState(State.RIGHT.ordinal());
        }

    }
}
