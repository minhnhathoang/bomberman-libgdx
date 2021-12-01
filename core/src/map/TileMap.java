package map;

import com.badlogic.gdx.graphics.OrthographicCamera;
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
import main.Bomberman;
import resources.ResourceManager;
import resources.Utils;

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
        MapObjects objects = tiledMap.getLayers().get("objects").getObjects();

        System.out.println(objects.getCount());

        for (MapObject object: objects) {
            Rectangle rectangle = ((RectangleMapObject)object).getRectangle();

            //create a dynamic within the world body (also can be KinematicBody or StaticBody
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            bodyDef.position.set(1, 1);
            Body body = world.createBody(bodyDef);

            //create a fixture for each body from the shape
            Fixture fixture = body.createFixture(getShapeFromRectangle(rectangle), 0);
            fixture.setFriction(0.1F);

            //setting the position of the body's origin. In this case with zero rotation
            body.setTransform(getTransformedCenterForRectangle(rectangle),0);
        }
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
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, Utils.GAME_SCALE, game.batch);


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

    }
}
