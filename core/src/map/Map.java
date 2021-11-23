package map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import main.Bomberman;
import resources.ResourceManager;

public class Map {
    protected Bomberman game;
    protected ResourceManager resource;

    TiledMap tiledMap;
    OrthogonalTiledMapRenderer tiledMapRenderer;

    public Map(Bomberman game, ResourceManager resource) {
        this.game = game;
        this.resource = resource;
    }

    public void loadMap(String path) {
        tiledMap = new TmxMapLoader().load(path);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    }

    public void render(OrthographicCamera camera) {
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
    }

    public void update(float delta) {

    }

    public void dispose() {

    }
}
