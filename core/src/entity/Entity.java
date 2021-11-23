package entity;

import animation.AnimationManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import resources.ResourceManager;

public abstract class Entity {

    protected ResourceManager resourceManager;
    protected AnimationManager animationManager;

    protected float offsetX, offsetY;
    protected float speed;

    public Entity(ResourceManager resourceManager) {
        animationManager = new AnimationManager(resourceManager);
        this.resourceManager = resourceManager;
    }

    public Entity(ResourceManager resourceManager, TextureRegion[][] sprites, int numFrame) {
        animationManager = new AnimationManager(resourceManager);
    }

    public abstract void update();

    public void render(SpriteBatch batch, float delta) {
        animationManager.render(batch, delta);
    }

    public void setState(int newStage) {
        animationManager.setCurrentState(newStage);
    }


}
