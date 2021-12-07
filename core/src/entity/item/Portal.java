package entity.item;

import animation.AnimationManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import entity.Player;
import manager.Box2dManager;
import manager.ResourceManager;

public class Portal extends Item {

    private AnimationManager animationManager;

    public Portal(World world, int x, int y) {
        super(x, y);

        this.world = world;

        animationManager = new AnimationManager();
        animationManager.addAnimation(0, ResourceManager.INSTANCE.portal[0], 1 / 10f);
        animationManager.setLooping(true);
        animationManager.setRunning(true);
        animationManager.setCurrentState(0);
        time = 99000f;

        setCurrentState(true);
        createBody();
    }

    @Override
    public void update(Player player, float delta) {
        if (Box2dManager.isCollidingInCenter(player.getBody(), this.body)) {
            player.setState(Player.State.VICTORY.ordinal());
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        animationManager.render(batch, new Vector2(body.getPosition().x - 0.33f, body.getPosition().y - 0.3f), false);
    }
}
