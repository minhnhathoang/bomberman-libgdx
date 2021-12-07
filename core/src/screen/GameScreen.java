package screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import entity.Player;
import main.Bomberman;
import manager.GameManager;
import manager.SoundManager;

import static helper.Vars.PPM;

public class GameScreen extends AbstractScreen {

    private static float WIDTH = 1200;
    private static float HEIGHT = 900;

    public GameManager gameManager;

    float timer = 5f;

    public GameScreen() {
        super();

        camera = new OrthographicCamera(WIDTH / PPM, HEIGHT / PPM);
        camera.position.set(WIDTH / PPM / 2f, HEIGHT / PPM / 2f, 0);
        camera.update();

        viewport = new FitViewport(WIDTH / PPM, HEIGHT / PPM, camera);

        gameManager = new GameManager(batch);

        SoundManager.playMusic("battle");
    }

    @Override
    public void render(float delta) {
        gameManager.update(delta);

        gameManager.render(batch, camera);

        updateCamera();

        if (gameManager.player.getCurrentState() == Player.State.DEFEAT.ordinal()
                || gameManager.player.getCurrentState() == Player.State.VICTORY.ordinal()) {
            timer -= delta;
            if (timer <= 0) {
                timer = 3f;
                Bomberman.getInstance().setScreen(new EndGameScreen(gameManager.player.getCurrentState()));
            }
        }
    }

    public void updateCamera() {
        Vector2 position = gameManager.player.getBody().getPosition();

        if (position.x > 6f) {
            if (position.x < 10f) {
                camera.position.set(position.x, HEIGHT / PPM / 2f, 0);
                camera.update();
            }
        }
    }
}
