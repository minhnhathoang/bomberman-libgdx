package animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import resources.ResourceManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import static resources.Vars.GAME_SCALE;
import static resources.Vars.PPM;


public class AnimationManager {

    protected ResourceManager resourceManager;

    public HashMap<Integer, Animation<TextureRegion>> map;

    int currentState;
    float elapsedTime;

    float width, height;

    boolean running;
    boolean looping;

    public AnimationManager(ResourceManager resourceManager) {
        map = new HashMap<>();
        this.resourceManager = resourceManager;

        elapsedTime = 0;
        running = true;
    }

    public void addAnimation(int state, TextureRegion[] sprite, float frameDuration) {
        if (map.get(state) == null) {
            Animation<TextureRegion> ani = new Animation<>(frameDuration, sprite);
            map.put(state, ani);
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

    public void render(SpriteBatch batch, Vector2 position) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        if (looping == false) {
            if (map.get(currentState).isAnimationFinished(elapsedTime)) {
                return;
            }
        }
        if (!running) {
            batch.begin();
            batch.draw(map.get(currentState).getKeyFrame(0, true), position.x, position.y, width, height);
            batch.end();
        } else {
            batch.begin();
            batch.draw(map.get(currentState).getKeyFrame(elapsedTime, looping), position.x, position.y, width, height);
            batch.end();
        }
    }

    public void setCurrentState(int newState) {
        currentState = newState;
        width = map.get(currentState).getKeyFrame(0, false).getRegionWidth() / PPM * GAME_SCALE;
        height = map.get(currentState).getKeyFrame(0, false).getRegionHeight() / PPM * GAME_SCALE;
    }

    public int getCurrentState() {
        return currentState;
    }

    public void setLooping(boolean looping) {
        this.looping = looping;
    }

    public boolean isLooping() {
        return this.looping;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isRunning() {
        return this.running;
    }

    public boolean isFinished() {
        float delta = Gdx.graphics.getDeltaTime();
        // elapsedTime += Gdx.graphics.getDeltaTime();
        return map.get(currentState).isAnimationFinished(elapsedTime + delta);
    }

}
