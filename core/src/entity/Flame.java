package entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import resources.ResourceManager;

public class Flame extends Entity {

    public enum Type {
        LAST_UP,
        LAST_DOWN,
        LAST_LEFT,
        LAST_RIGHT,
        UP,
        DOWN,
        LEFT,
        RIGHT,
        CENTER
    }

    public Flame(ResourceManager resourceManager, World world, Type type, int x, int y) {
        this(resourceManager, world, type, new Vector2(x * 16 + 8, y * 16 + 8));
    }

    public Flame(ResourceManager resourceManager, World world, Type type, Vector2 position) {
        super(resourceManager, world);

        switch (type) {
            case UP:
                animationManager.addAnimation(type.ordinal(), resourceManager.flameUp, 1/5f);
                break;
            case DOWN:
                animationManager.addAnimation(type.ordinal(), resourceManager.flameDown, 1/5f);
                break;
            case LEFT:
                animationManager.addAnimation(type.ordinal(), resourceManager.flameLeft, 1/5f);
                break;
            case RIGHT:
                animationManager.addAnimation(type.ordinal(), resourceManager.flameRight, 1/5f);
                break;
            case CENTER:
                animationManager.addAnimation(type.ordinal(), resourceManager.flameCenter, 1/5f);
                break;
            case LAST_UP:
                animationManager.addAnimation(type.ordinal(), resourceManager.flameLastUp, 1/5f);
                break;
            case LAST_DOWN:
                animationManager.addAnimation(type.ordinal(), resourceManager.flameLastDown, 1/5f);
                break;
            case LAST_LEFT:
                animationManager.addAnimation(type.ordinal(), resourceManager.flameLastLeft, 1/5f);
                break;
            case LAST_RIGHT:
                animationManager.addAnimation(type.ordinal(), resourceManager.flameLastRight, 1/5f);
                break;
        }

        this.position = position;
        animationManager.setCurrentState(type.ordinal());
        animationManager.setLooping(false);

        createBody();
    }

    public void update() {

    }

    public void render(SpriteBatch batch) {
        super.render(batch, new Vector2(this.body.getPosition().x - 0.3f, this.body.getPosition().y - 0.3f));
    }

    @Override
    public void createBody() {
        body = Box2dManager.createCircle(this.position, 5, BodyDef.BodyType.StaticBody, true, world);
        position = body.getPosition();
    }

    public boolean isFinished() {
        return animationManager.isFinished();
    }
}
