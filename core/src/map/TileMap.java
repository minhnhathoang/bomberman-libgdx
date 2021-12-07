package map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import entity.enemies.*;
import entity.item.BombItem;
import entity.item.FlameItem;
import entity.item.SpeedItem;
import manager.Box2dManager;
import manager.GameManager;
import helper.Vars;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static helper.Vars.PPM;


public class TileMap {
    public static int STAGE;

    private static final float TILE_SIZE = 16f;
    public int width;
    public int height;
    public int[][] textMap;
    public Map<Integer, Body> bodies;
    private GameManager gameManager;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private TiledMapTileLayer layer;

    public TileMap(GameManager gameManager) {
        this.gameManager = gameManager;

        loadMap("map/stage" + STAGE + ".tmx");

        layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);

        bodies = new HashMap<>();

        width = layer.getWidth();
        height = layer.getHeight();

        textMap = new int[width][height];

        this.randomBrick();
        this.randomItem();

        this.createBody();

        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                textMap[i][j] = layer.getCell(i, j).getTile().getId();
            }
        }

        this.randomEnemy();
    }

    public static Vector2 coordToPos(int x, int y) {
        return new Vector2(x * 16 + 8, y * 16 + 8);
    }

    public void loadMap(String path) {
        tiledMap = new TmxMapLoader().load(path);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, Vars.GAME_SCALE / PPM, gameManager.batch);
    }

    public void render(OrthographicCamera camera) {
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
    }

    public void update(float delta) {

    }

    public void randomBrick() {
        Random random = new Random();
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                textMap[i][j] = layer.getCell(i, j).getTile().getId();
                if (i <= 2 && j >= height - 3) {
                    continue;
                }
                if (layer.getCell(i, j).getTile().getId() == 3) {
                    if (random.nextInt(3) == 1) {
                        changleTile(i, j, 3, 1);
                        textMap[i][j] = 1;
                    }
                }
            }
        }
    }

    public void randomItem() {
        Random random = new Random();
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                if (textMap[i][j] == 1) {
                    int rand = random.nextInt(15);
                    switch (rand) {
                        case 0:
                            gameManager.getItems().add(new SpeedItem(gameManager.getWorld(), i, j));
                            break;
                        case 1:
                            gameManager.getItems().add(new BombItem(gameManager.getWorld(), i, j));
                            break;
                        case 2:
                            gameManager.getItems().add(new FlameItem(gameManager.getWorld(), i, j));
                            break;
                    }
                }
            }
        }
    }

    public void randomEnemy() {
        Random random = new Random();
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                if (i <= 2 && j >= height - 3) {
                    continue;
                }
                if (textMap[i][j] == 3) {
                    int rand = random.nextInt(200);
                    switch (rand) {
                        case 0:
                            gameManager.getEnemies().add(new Balloom(gameManager, i, j));
                            break;
                        case 1:
                            gameManager.getEnemies().add(new Oneal(gameManager, i, j));
                            break;
                        case 2:
                            gameManager.getEnemies().add(new Dahl(gameManager, i, j));
                            break;
                        case 3:
                            gameManager.getEnemies().add(new Minvo(gameManager, i, j));
                            break;
                        case 4:
                            gameManager.getEnemies().add(new Doria(gameManager, i, j));
                            break;
                        case 5:
                            gameManager.getEnemies().add(new Ovape(gameManager, i, j));
                            break;
                        case 6:
                            gameManager.getEnemies().add(new Pass(gameManager, i, j));
                            break;
                        case 7:
                            gameManager.getEnemies().add(new Pontan(gameManager, i, j));
                            break;
                        default:
                            break;

                    }


                }
            }
        }
    }

    public void changleTile(int x, int y, int oldType, int newType) {
        if (oldType == newType) {
            System.out.println("error change tile!!! " + x + " " + y);
            return;
        }
        if (newType == 3) {
            if (this.bodies.containsKey(x * width + y)) {
                gameManager.getBodies().add(this.bodies.get(x * width + y));
                this.bodies.remove(x * width + y);
            }
        }
        // layer start with index 0
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();

        // new Tile object
        cell.setTile(tiledMap.getTileSets().getTileSet("tiles").getTile(newType));

        layer.setCell(x, y, cell);
        textMap[x][y] = newType;
    }

    public void createBody() {
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                TiledMapTileLayer.Cell cell = layer.getCell(i, j);
                if (cell.getTile().getId() != 3) {

                    float x = i * 16 + 16 / 2f;
                    float y = j * 16 + 16 / 2f;

                    this.bodies.put(i * width + j,
                            Box2dManager.createRectangle(
                                    new Vector2(x, y),
                                    8, 8,
                                    gameManager.getWorld()
                            ));
                }
            }
        }
    }

    public boolean checkMoveableTile(int i, int j) {
        if (i >= width - 1 || j >= height - 1 || i <= 0 || j <= 0) {
            return false;
        }
        return textMap[i][j] == 3;
    }

    public boolean checkBreakableTile(int i, int j) {
        if (i >= width - 1 || j >= height - 1 || i <= 0 || j <= 0) {
            return false;
        }
        return textMap[i][j] == 1;
    }
}
