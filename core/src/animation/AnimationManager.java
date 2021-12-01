package animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import main.Bomberman;
import resources.ResourceManager;

import java.util.Arrays;
import java.util.HashMap;

import static resources.Utils.GAME_SCALE;
import static resources.Utils.PPM;


public class AnimationManager {

    protected ResourceManager resourceManager;

    public HashMap<Integer, Animation<TextureRegion>> map;

    int currentState;
    float elapsedTime;

    float width, height;

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
        this.running = running;

        width = map.get(currentState).getKeyFrame(0, false).getRegionWidth() / PPM * GAME_SCALE;
        height = map.get(currentState).getKeyFrame(0, false).getRegionHeight() / PPM * GAME_SCALE - 0.3f;
    }

    public void render(SpriteBatch batch, Vector2 position, float delta) {
        //width = 0;
        //height = 0;
        elapsedTime += delta;
        if (!running) {
            batch.begin();
            batch.draw(map.get(currentState).getKeyFrame(0, true), position.x - width / 2f, position.y - height / 2f, 18 / PPM * GAME_SCALE, 26 / PPM * GAME_SCALE);
            batch.end();
        } else {
            batch.begin();
            batch.draw(map.get(currentState).getKeyFrame(elapsedTime, true), position.x - width / 2f, position.y - height / 2f, 18 / PPM * GAME_SCALE, 26 / PPM * GAME_SCALE);
            batch.end();
        }
    }

    public void setCurrentState(int newState) {
        currentState = newState;
    }

    public int getCurrentState() {
        return currentState;
    }

}
