package entity;

import animation.AnimationManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import processor.GameInputProcessor;
import resources.ResourceManager;
import resources.Utils;

import java.security.Key;

public class Player extends Entity {

    public Player(ResourceManager resourceManager) {
        super(resourceManager);

        animationManager.addAnimation(Utils.PlayerState.UP.ordinal(), resourceManager.spritePlayerUp[0], 1/10f);
        animationManager.addAnimation(Utils.PlayerState.DOWN.ordinal(), resourceManager.spritePlayerDown[0], 1/10f);
        animationManager.addAnimation(Utils.PlayerState.RIGHT.ordinal(), resourceManager.spritePlayerRight[0], 1/10f);
        animationManager.addAnimation(Utils.PlayerState.LEFT.ordinal(), resourceManager.spritePlayerRight[0], 1/10f, true);
        speed = 3;
    }

    @Override
    public void update() {
        offsetX = 0;
        offsetY = 0;
        if (GameInputProcessor.isHold(Input.Keys.DOWN)) {
            offsetY -= speed;
            setState(Utils.PlayerState.DOWN.ordinal());
        }
        if (GameInputProcessor.isHold(Input.Keys.UP)) {
            offsetY += speed;
            setState(Utils.PlayerState.UP.ordinal());
        }
        if (GameInputProcessor.isHold(Input.Keys.LEFT)) {
            offsetX -= speed;
            setState(Utils.PlayerState.LEFT.ordinal());
        }
        if (GameInputProcessor.isHold(Input.Keys.RIGHT)) {
            offsetX += speed;
            setState(Utils.PlayerState.RIGHT.ordinal());
        }

        animationManager.update(offsetX, offsetY, offsetX != 0 || offsetY != 0);
        System.out.println(offsetX == 0f && offsetY == 0f);
    }


}
