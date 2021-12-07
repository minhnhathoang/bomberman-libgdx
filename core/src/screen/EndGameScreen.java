package screen;

import animation.AnimationManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import entity.Player;
import main.Bomberman;
import manager.FontManager;
import manager.ResourceManager;
import manager.SoundManager;

public class EndGameScreen extends AbstractScreen {

    AnimationManager ani;
    TextButton exit, start;

    int state;

    AnimationManager animation;

    public EndGameScreen(int state) {
        super();

        this.state = state;

        camera = new OrthographicCamera(1000, 1000);
        camera.position.set(500, 500, 0);
        camera.update();

        viewport = new FitViewport(500, 500, camera);

        ani = new AnimationManager();
        ani.addAnimation(Player.State.RIGHT.ordinal(), ResourceManager.INSTANCE.spritePlayerRight[0], 1 / 10f);
        ani.setCurrentState(Player.State.RIGHT.ordinal());

        animation = new AnimationManager();
        animation.addAnimation(0, ResourceManager.INSTANCE.spritePlayerIdle[0], 1 / 5f);
        animation.setCurrentState(0, 50);
        animation.setLooping(true);

        stage = new Stage(viewport, batch);
    }


    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render(delta);

        if (state == Player.State.DEFEAT.ordinal()) {
            batch.begin();
            FontManager.drawText(batch, "Game Over", 100, Color.RED, 70, 300);
            SoundManager.playMusic("gameover");
            batch.end();

        } else {
            batch.begin();
            FontManager.drawText(batch, "Victory", 100, Color.YELLOW, 120, 300);
            SoundManager.playMusic("victory");
            batch.end();
        }

        animation.render(batch, new Vector2(50, 50), false);
        animation.render(batch, new Vector2(400, 50), false);

        batch.begin();
        FontManager.drawText(batch, "press any key to continue", 24, Color.WHITE, 120, 80);
        batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            this.dispose();
            Bomberman.getInstance().setScreen(new MenuScreen());
        }

    }

    public void dispose() {
        super.dispose();
    }
}
