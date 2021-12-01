package map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import entity.Box2dManager;
import main.Bomberman;
import resources.ResourceManager;
import resources.Utils;

import static resources.Utils.PPM;


public class TileMap {
    private static final float TILE_SIZE = 16f;
    protected Bomberman game;
    protected ResourceManager resource;

    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;

    private TiledMapTileLayer layer;

    int width;
    int height;

    public TileMap(Bomberman game, ResourceManager resource) {
        this.game = game;
        this.resource = resource;

        loadMap("map/stage1.tmx");

        layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);

        width = layer.getWidth();
        height = layer.getHeight();

    }

    public void buildBox(World world) {

    }

    public static Shape getShapeFromRectangle(Rectangle rectangle){
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(rectangle.width*0.5F/ TILE_SIZE,rectangle.height*0.5F/ TILE_SIZE);
        return polygonShape;
    }

    public static Vector2 getTransformedCenterForRectangle(Rectangle rectangle){
        Vector2 center = new Vector2();
        rectangle.getCenter(center);
        return center.scl(1/TILE_SIZE);
    }


    public void loadMap(String path) {
        tiledMap = new TmxMapLoader().load(path);
        //tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, Utils.GAME_SCALE, game.batch);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, Utils.GAME_SCALE / PPM, game.batch);


        //width = tiledMap.getLayers().getCount();

    }

    public void render(OrthographicCamera camera) {
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();


    }

    public void update(float delta) {

    }

    public void dispose() {

    }

    public void changleTile(int x, int y, int oldType, int newType) {
        if (oldType == newType) {
            System.out.println("error change tile!!! " + x + " " + y);
        }
        // layer start with index 0
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();

        // new Tile object
        cell.setTile(tiledMap.getTileSets().getTileSet("tiles").getTile(newType));

        layer.setCell(x, y, cell);
    }

    public void createBody(World world) {

        Array<RectangleMapObject> wall = tiledMap.getLayers().get("wall").getObjects().getByType(RectangleMapObject.class);

        for (RectangleMapObject rectObject : wall) {
            rectObject.getProperties().get("x");
            Rectangle rect = rectObject.getRectangle();
            Box2dManager.createRectangle(
                    new Vector2(rect.getX() + rect.getWidth() / 2f, rect.getY() + rect.getHeight() / 2f),
                    rect.getWidth() / 2,
                    rect.getHeight() / 2,
                    world
            );
        }



        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                TiledMapTileLayer.Cell cell = layer.getCell(i, j);
                if (cell.getTile().getId() != 3) {

                    float x = i * 16 + 16 / 2f;
                    float y = j * 16 + 16 / 2f;

                    Box2dManager.createRectangle(
                            new Vector2(x, y),
                            8, 8, world
                    );
                }
            }
        }
    }
}
