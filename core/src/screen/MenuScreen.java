package screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import main.Bomberman;
import resources.ResourceManager;

public class MenuScreen extends AbstractScreen {

    public MenuScreen(Bomberman game, ResourceManager resource) {
        super(game, resource);

        initBackground();
        initButton();
        /*
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = resource.bitmapFont;
        style.fontColor = Color.RED;

        Label titleLabel = new Label("BOMBERMAN", style);

        Table table = new Table();

        table.setFillParent(true);

        //resource.bitmapFont.getData().setScale(0.5f);

        TextButton.TextButtonStyle styleTextButton = new TextButton.TextButtonStyle();
        styleTextButton.font = resource.bitmapFont;
        styleTextButton.fontColor = Color.BLUE;

        TextButton startButton = new TextButton("Start", styleTextButton);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        final TextButton exitButton = new TextButton("Exit", styleTextButton);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        table.add(titleLabel).padBottom(60.0f).colspan(2).row();
        table.add(startButton).padBottom(60.0f).colspan(2).row();
        table.add(exitButton).padBottom(60.0f).colspan(2).row();
        table.addListener(new InputListener() {
            @Override
            public boolean keyDown(final InputEvent event, final int keycode) {
                switch (keycode) {
                    case Input.Keys.W:
                    case Input.Keys.UP:
                    case Input.Keys.S:
                    case Input.Keys.DOWN:
                        exitButton.setColor(Color.RED);
                        exitButton.setText("ADDDDDDDDDDDDDDDDDDDDDDDD");
                        break;
                    case Input.Keys.SPACE:
                    case Input.Keys.ENTER:
                    case Input.Keys.BUTTON_A:
                        exitButton.setVisible(true);
                        break;
                }
                return super.keyDown(event, keycode);
            }
        });

        stage = new Stage(new ScreenViewport(), game.batch);
        stage.addActor(table);

        stage.setKeyboardFocus(table);
        Gdx.input.setInputProcessor(stage);

         */
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

        stage = new Stage(new ScreenViewport(), game.batch);
        stage.addActor(table);
        stage.setDebugAll(true);

        Gdx.input.setInputProcessor(stage);
    }

    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    public void disposee() {
        this.dispose();
    }
}
