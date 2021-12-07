package manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class ResourceManager {

    public static ResourceManager INSTANCE = new ResourceManager();
    public AssetManager assetManager;
    // FreeType font
    public FreeTypeFontGenerator freeTypeFontGenerator;
    public FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    public BitmapFont font50px;
    public BitmapFont font100px;
    // sprite's player
    public TextureRegion[][] spritePlayerUp;


    // tiledmap
    public TextureRegion[][] spritePlayerDown;
    public TextureRegion[][] spritePlayerRight;
    public TextureRegion[][] spritePlayerDefeat;
    public TextureRegion[][] spritePlayerVictory;
    public TextureRegion[][] spritePlayerIdle;
    public TextureRegion[][] mugshots;
    // bomb
    public TextureRegion[][] spriteBomb;
    // flame
    public TextureRegion[][] explosion;
    public TextureRegion[] flameUp;
    public TextureRegion[] flameDown;
    public TextureRegion[] flameRight;
    public TextureRegion[] flameLeft;
    public TextureRegion[] flameCenter;
    public TextureRegion[] flameLastUp;
    public TextureRegion[] flameLastDown;
    public TextureRegion[] flameLastLeft;
    public TextureRegion[] flameLastRight;
    public TextureRegion[] explosionBrick;
    public TextureRegion[][] explo;
    // item
    public TextureRegion flameItem;
    public TextureRegion speedItem;
    public TextureRegion bombItem;
    public TextureRegion[][] portal;
    // bar
    public TextureRegion bar;
    public TextureRegion pause;
    public TextureRegion[][] balloom;

    // ememy
    public TextureRegion[][] oneal;
    public TextureRegion[][] dahl;
    public TextureRegion[][] minvo;
    public TextureRegion[][] doria;
    public TextureRegion[][] ovape;
    public TextureRegion[][] pass;
    public TextureRegion[][] pontan;
    public TextureRegion[][] enemyDeath;
    // contains all sprites
    private TextureAtlas atlas;


    public ResourceManager() {
        assetManager = new AssetManager();

        assetManager.load("player/player.atlas", TextureAtlas.class);

        // music
        assetManager.load("sound/music_menu.mp3", Music.class);
        assetManager.load("sound/victory.mp3", Music.class);
        assetManager.load("sound/gameover.mp3", Music.class);
        assetManager.load("sound/battle.mp3", Music.class);

        // sound effect
        //assetManager.load("sound/explosion.wav", Sound.class);
        assetManager.load("sound/click.ogg", Sound.class);
        assetManager.load("sound/pause.ogg", Sound.class);
        assetManager.load("sound/explosion.ogg", Sound.class);

        assetManager.finishLoading();

        atlas = assetManager.get("player/player.atlas", TextureAtlas.class);


        freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font/arcadeclassic.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 150;
        font100px = freeTypeFontGenerator.generateFont(fontParameter);
        fontParameter.size = 30;
        font50px = freeTypeFontGenerator.generateFont(fontParameter);


        spritePlayerUp = atlas.findRegion("up").split(16, 24);
        spritePlayerDown = atlas.findRegion("down").split(16, 24);
        spritePlayerRight = atlas.findRegion("right").split(16, 24);

        spritePlayerIdle = atlas.findRegion("idle").split(22, 24);
        spritePlayerDefeat = atlas.findRegion("defeat").split(24, 24);
        spritePlayerVictory = atlas.findRegion("victory").split(16, 37);

        spriteBomb = atlas.findRegion("bomb").split(16, 16);

        mugshots = atlas.findRegion("mugshots").split(32, 32);

        bar = atlas.findRegion("bar");
        pause = atlas.findRegion("pause");

        // explosion
        explosion = atlas.findRegion("explosion").split(16, 16);

        flameUp = explosion[4];
        flameDown = explosion[4];
        flameLeft = explosion[3];
        flameRight = explosion[3];
        flameCenter = explosion[1];
        flameLastUp = explosion[8];
        flameLastDown = explosion[2];
        flameLastLeft = explosion[7];
        flameLastRight = explosion[0];
        explosionBrick = explosion[6];

        explo = atlas.findRegion("explo").split(16, 26);

        // items
        flameItem = atlas.findRegion("flame-item");
        speedItem = atlas.findRegion("speed-item");
        bombItem = atlas.findRegion("bomb-item");

        portal = atlas.findRegion("portal").split(18, 44);

        // enemy
        balloom = atlas.findRegion("balloom").split(18, 17);
        oneal = atlas.findRegion("oneal").split(22, 19);
        dahl = atlas.findRegion("dahl").split(16, 16);
        minvo = atlas.findRegion("minvo").split(16, 16);
        doria = atlas.findRegion("doria").split(20, 19);
        ovape = atlas.findRegion("ovape").split(16, 16);
        pass = atlas.findRegion("pass").split(16, 16);
        pontan = atlas.findRegion("pontan").split(16, 16);

        enemyDeath = atlas.findRegion("enemy-death").split(16, 16);

    }

    public void dispose() {

    }
}
