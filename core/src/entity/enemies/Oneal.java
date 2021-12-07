package entity.enemies;

import animation.AnimationManager;
import manager.GameManager;
import manager.ResourceManager;

public class Oneal extends Enemy {

    public Oneal(GameManager gameManager) {
        super(gameManager);
        this.position = gameManager.player.getPosition();

        animationManager = new AnimationManager();

        animationManager.addAnimation(State.LIVE.ordinal(), ResourceManager.INSTANCE.onealLive[0], 1/10f);
        animationManager.addAnimation(State.DEAD.ordinal(), ResourceManager.INSTANCE.onealDead[0], 1/10f);

        animationManager.setCurrentState(State.DEAD.ordinal(), 0.75f);
        animationManager.setLooping(true);

        createBody();
    }
}
