package screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import main.Bomberman;
import resources.ResourceManager;

public class MenuScreen extends AbstractScreen {

    public MenuScreen(Bomberman game, ResourceManager resource) {
        super(game, resource);

        viewport = new FitViewport(1000, 800);
        stage = new Stage(viewport, game.batch);

        initBackground();
        initButton();
    }

    public void initBackground() {

    }

    public void initButton() {
        Table table = new Table();

        //TextureRegionDrawable background = new TextureRegionDrawable(new Texture("bg.png"));

        //table.setBackground(background);

        // title
        TextButton tilte = this.createTextButton("Bomberman", table, 100, 100, 100);

        // start button
        final TextButton start = this.createTextButton("Start", table, 100, 100, 50);
        start.addListener(new ClickListener() {
             @Override
             public void clicked(InputEvent inputEvent, float x, float y) {
                  game.setScreen(new GameScreen(game, resource));

            }
        });

        // exit button
        TextButton exit = this.createTextButton("Exit", table, 100, 100, 50);
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
                Gdx.app.exit();
            }
        });

        table.add(tilte).padBottom(300).row();
        table.add(start).padBottom(50).row();
        table.add(exit).padBottom(50);
        table.setFillParent(true);

        stage.addActor(table);
        stage.setDebugAll(true);

        Gdx.input.setInputProcessor(stage);
    }

    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //stage.getBatch().setProjectionMatrix(stage.getCamera().combined);
        super.render(delta);
    }

    public void disposee() {
        this.dispose();
    }
}
