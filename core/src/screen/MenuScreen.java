package screen;

import animation.AnimationManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import entity.Player;
import main.Bomberman;
import manager.FontManager;
import manager.ResourceManager;

public class MenuScreen extends AbstractScreen {

    AnimationManager ani;
    Table table;
    TextButton exit, start;

    public MenuScreen() {
        super();

        viewport = new FitViewport(1000, 800);
        stage = new Stage(viewport, batch);

        table = new Table();

        ani = new AnimationManager();
        ani.addAnimation(Player.State.RIGHT.ordinal(), ResourceManager.INSTANCE.spritePlayerRight[0], 1 / 10f);
        ani.setCurrentState(Player.State.RIGHT.ordinal());

        initButton();
    }

    public void initButton() {

        TextureRegionDrawable background = new TextureRegionDrawable(new Texture("background.png"));

        table.setBackground(background);

        // title

        // start button
        start = this.createTextButton("start", table, 100, 100, 70);
        start.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
                Bomberman.getInstance().setScreen(new SelectScreen());

            }
        });

        /*
        multiplayer = this.createTextButton("multiplayer", table, 100, 100, 70);
        multiplayer.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
                Bomberman.getInstance().setScreen(new ConnectScreen());

            }
        });

         */

        // exit button
        exit = this.createTextButton("exit", table, 100, 100, 70);
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
                Gdx.app.exit();
            }
        });

        // table.add(tilte).padBottom(300).row();
        table.add(start).padBottom(20).row();
        // table.add(multiplayer).padBottom(20).row();
        table.add(exit).padBottom(20);
        table.setFillParent(true);

        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }

    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render(delta);

        //ani.render(game.batch, new Vector2(500, 500));

        batch.begin();
        FontManager.drawText(batch, "Bomberman", 200, Color.LIME, 150, 650);
        batch.end();

        //ani.render(game.batch, new Vector2(100, 100));
    }

    public void dispose() {
        super.dispose();
        stage.dispose();
        exit.setDisabled(true);
        start.setDisabled(true);
    }
}
