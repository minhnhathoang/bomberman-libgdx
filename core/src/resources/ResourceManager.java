package resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.utils.JsonReader;

public class ResourceManager {
    // contains all sprites
    private TextureAtlas atlas;

    public AssetManager assetManager;

    // FreeType font
    public FreeTypeFontGenerator freeTypeFontGenerator;
    public FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    public BitmapFont font50px;
    public BitmapFont font100px;

    // tiledmap

    // sprite's player
    public TextureRegion[][] spritePlayerUp;
    public TextureRegion[][] spritePlayerDown;
    public TextureRegion[][] spritePlayerRight;


    public ResourceManager() {
        assetManager = new AssetManager();
        JsonReader jsonReader = new JsonReader();

        assetManager.load("player/player.atlas", TextureAtlas.class);
        assetManager.finishLoading();
        atlas = assetManager.get("player/player.atlas", TextureAtlas.class);

        // generate BitmapFont object from ttf file
        //freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font/press-start2p.ttf"));
        freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font/arcadeclassic.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 100;
        fontParameter.color = Color.VIOLET;
        fontParameter.borderStraight = true;

        font100px = freeTypeFontGenerator.generateFont(fontParameter);

        fontParameter.size = 50;
        font50px = freeTypeFontGenerator.generateFont(fontParameter);

        spritePlayerUp = atlas.findRegion("up").split(16, 24);
        spritePlayerDown = atlas.findRegion("down").split(16, 24);
        spritePlayerRight = atlas.findRegion("right").split(16, 24);
    }

    public void dispose() {

    }
}
