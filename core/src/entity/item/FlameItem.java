package entity.item;

import com.badlogic.gdx.physics.box2d.World;
import entity.Player;
import manager.Box2dManager;
import manager.ResourceManager;

public class FlameItem extends Item {

    public FlameItem(World world, int x, int y) {
        super(x, y);

        this.world = world;

        texture = ResourceManager.INSTANCE.flameItem;
    }

    @Override
    public void update(Player player, float delta) {
        if (currentState == State.HIDE) {
            return;
        } else {
            if (this.body == null) {
                this.createBody();
            }
            time -= delta;
            if (Box2dManager.checkOverlap(player.getBody(), this.body)) {
                time = 0;
                player.increasePower();
            }
        }
    }
}
