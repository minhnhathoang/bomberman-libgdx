package animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import main.Bomberman;
import resources.ResourceManager;

import java.util.Arrays;
import java.util.HashMap;

public class AnimationManager {

    protected ResourceManager resourceManager;

    public HashMap<Integer, Animation<TextureRegion>> map;

    int currentState;
    float elapsedTime;

    float posX, posY;
    boolean running;

    public AnimationManager(ResourceManager resourceManager) {
        map = new HashMap<>();
        this.resourceManager = resourceManager;
    }

    public void addAnimation(int state, TextureRegion[] sprite, float frameDuration) {
        if (map.get(state) == null) {
            map.put(state, new Animation<TextureRegion>(frameDuration, sprite));
        }
    }

    public void addAnimation(int state, TextureRegion[] sprite, float frameDuration, boolean flip) {
        if (flip) {
            TextureRegion[] newSprite = new TextureRegion[sprite.length];
            for (int i = 0; i < newSprite.length; ++i) {
                newSprite[i] = new TextureRegion(sprite[i]);
                newSprite[i].flip(true, false);
            }
            map.put(state, new Animation<TextureRegion>(frameDuration, newSprite));
        } else if (map.get(state) == null) {
            map.put(state, new Animation<TextureRegion>(frameDuration, sprite));
        }
    }


    public void update(float offsetX, float offsetY, boolean running) {
        posX += offsetX;
        posY += offsetY;
        this.running = running;
    }

    public void render(SpriteBatch batch, float delta) {
        elapsedTime += delta;
        if (!running) {
            elapsedTime = 0;
            batch.begin();
            batch.draw(map.get(currentState).getKeyFrame(0, true), posX, posY, 18 * 3, 26 * 3);
            batch.end();
        } else {
            batch.begin();
            batch.draw(map.get(currentState).getKeyFrame(elapsedTime, true), posX, posY, 18 * 3, 26 * 3);
            batch.end();
        }
    }

    public void setCurrentState(int newState) {
        currentState = newState;
        System.out.println(currentState);
    }

    public int getCurrentState() {
        return currentState;
    }

}
