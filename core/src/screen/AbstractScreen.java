package screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.Viewport;
import main.Bomberman;
import manager.FontManager;
import manager.SoundManager;

public class AbstractScreen implements Screen {

    protected Viewport viewport;
    protected OrthographicCamera camera;

    protected SpriteBatch batch;
    protected Stage stage;

    public AbstractScreen() {
        this.batch = new SpriteBatch();
        this.batch.setProjectionMatrix(Bomberman.getInstance().camera.combined);
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

    public TextButton createTextButton(String name, Table table, float x, float y, final int fontSize) {
        final TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();

        //style.font = ResourceManager.INSTANCE.font50px;
        style.font = FontManager.getFont(fontSize, Color.RED);
        style.fontColor = Color.WHITE;

        final TextButton textButton = new TextButton(name, style);
        textButton.addListener(new InputListener() {
            private boolean hover;

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                style.fontColor = Color.RED;
                SoundManager.playSound("click");
                Texture logo = new Texture("logo.png");

            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                style.fontColor = Color.WHITE;
            }
        });
        return textButton;
    }
}
