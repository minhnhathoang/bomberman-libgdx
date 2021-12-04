package screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.Viewport;
import main.Bomberman;
import resources.ResourceManager;

public class AbstractScreen implements Screen {
    protected Bomberman game;
    protected ResourceManager resource;

    protected Viewport viewport;
    protected OrthographicCamera camera;

    protected Stage stage;

    public AbstractScreen(Bomberman game, ResourceManager resource) {
        this.game = game;
        this.resource = resource;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public TextButton createTextButton(String name, Table table, float x, float y, int fontSize) {
        final TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = resource.font100px;
        if (fontSize == 50) {
            style.font = resource.font50px;
        }
        style.fontColor = new Color(252, 151, 0, 255);

        TextButton textButton = new TextButton(name, style);
        textButton.addListener(new InputListener() {
            private boolean hover;
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                style.fontColor = Color.SALMON;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                style.fontColor = new Color(252, 151, 0, 255);
            }
        });
        return textButton;
    }

    public void drawText(String text, float x, float y) {
        game.batch.begin();
        resource.font8px.draw(game.batch, text, x, y);
        game.batch.end();
    }
}
