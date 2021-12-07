package entity.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import graph.AStar;
import graph.Node;
import manager.GameManager;
import manager.ResourceManager;
import map.TileMap;
import helper.Rand;
import helper.Vars;

import java.util.ArrayList;
import java.util.List;

public class Minvo extends Enemy implements AI {

    protected AStar pathFinder;
    protected float timer;
    protected List<Node> fixedPath;

    public Minvo(GameManager gameManager, int x, int y) {
        super(gameManager, x, y);

        animationManager.addAnimation(State.LEFT.ordinal(), ResourceManager.INSTANCE.minvo[0], 1 / 5f, true);
        animationManager.addAnimation(State.RIGHT.ordinal(), ResourceManager.INSTANCE.minvo[0], 1 / 5f);

        animationManager.setCurrentState(State.LEFT.ordinal());
        animationManager.setLooping(true);

        createBody();

        currentDirection = Rand.rand(4);
        this.body.setLinearVelocity(Vars.dx[currentDirection], Vars.dy[currentDirection]);

        fixedPath = new ArrayList<>();

        score = 800;

        timer = 5f;

        speedLimit = 2f;
    }

    @Override
    public void pathFinding() {
        if (fixedPath.isEmpty()) {
            pathFinder = new AStar(gameManager.getMap().width, gameManager.getMap().height, gameManager.getMap().textMap);

            int x1 = gameManager.player.getX();
            int y1 = gameManager.player.getY();

            fixedPath = pathFinder.search(pathFinder.nodes[x][y], pathFinder.nodes[x1][y1]);
        }

        if (!fixedPath.isEmpty()) {
            Node temp = fixedPath.get(0);

            if (!isCenterTile(temp.x, temp.y)) {

                Vector2 u = TileMap.coordToPos(temp.x, temp.y);
                Vector2 v = new Vector2((u.x - this.position.x), (u.y - this.position.y));

                this.body.setLinearVelocity(v.scl(100).limit(speedLimit));
                timer -= Gdx.graphics.getDeltaTime();

                if (timer <= 0) {
                    fixedPath.clear();
                    currentDirection = Rand.rand(8);
                    Vector2 newV = new Vector2(Vars.dx[currentDirection], Vars.dy[currentDirection]);
                    this.body.setLinearVelocity(newV.scl(100).limit(speedLimit));
                }

            } else {
                timer = 5f;
                fixedPath.remove(0);
            }
        }
    }

    public void update() {
        super.update();

        pathFinding();

        if (this.body.getLinearVelocity().x <= 0) {
            animationManager.setCurrentState(State.LEFT.ordinal());
        } else {
            animationManager.setCurrentState(State.RIGHT.ordinal());
        }
    }
}
