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
import map.TileMap;

public class SelectScreen extends AbstractScreen {

    AnimationManager ani;
    Table table;
    TextButton stage1, stage2, stage3;
    TextButton back;

    public SelectScreen() {
        super();

        viewport = new FitViewport(1000, 800);
        stage = new Stage(viewport, batch);

        table = new Table();

        initButton();
    }

    public void initButton() {

        final TextureRegionDrawable background = new TextureRegionDrawable(new Texture("background.png"));

        table.setBackground(background);


        // start button
        stage1 = this.createTextButton("stage1", table, 100, 100, 70);
        stage1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
                TileMap.STAGE = 1;
                Bomberman.getInstance().setScreen(new GameScreen());
            }
        });

        stage2 = this.createTextButton("stage2", table, 100, 100, 70);
        stage2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
                TileMap.STAGE = 2;
                Bomberman.getInstance().setScreen(new GameScreen());
            }
        });

        stage3 = this.createTextButton("stage3", table, 100, 100, 70);
        stage3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
                TileMap.STAGE = 3;
                Bomberman.getInstance().setScreen(new GameScreen());
            }
        });

        // back button
        back = this.createTextButton("back", table, 100, 100, 70);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent inputEvent, float x, float y) {
                Bomberman.getInstance().setScreen(new MenuScreen());
            }
        });

        table.padTop(200);
        table.add(stage1).padBottom(20).row();
        table.add(stage2).padBottom(20).row();
        table.add(stage3).padBottom(20).row();

        table.add(back).padBottom(20);

        table.setFillParent(true);

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

    }

    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render(delta);

        //ani.render(game.batch, new Vector2(500, 500));

        batch.begin();
        FontManager.drawText(batch, "Bomberman", 200, Color.YELLOW, 150, 650);
        batch.end();

        //ani.render(game.batch, new Vector2(100, 100));
    }

    public void dispose() {
        super.dispose();
        stage.dispose();
    }
}
