package entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import manager.Box2dManager;
import manager.GameManager;
import manager.ResourceManager;
import map.TileMap;

public class Flame extends Entity {

    public Flame(GameManager gameManager, Type type, int x, int y) {
        super(gameManager);

        this.x = x;
        this.y = y;

        System.out.println("flame why111" + x + " " + y);

        position = new Vector2(x * 16 + 8, y * 16 + 8);

        switch (type) {
            case UP:
                animationManager.addAnimation(type.ordinal(), ResourceManager.INSTANCE.flameUp, 1 / 5f);
                break;
            case DOWN:
                animationManager.addAnimation(type.ordinal(), ResourceManager.INSTANCE.flameDown, 1 / 5f);
                break;
            case LEFT:
                animationManager.addAnimation(type.ordinal(), ResourceManager.INSTANCE.flameLeft, 1 / 5f);
                break;
            case RIGHT:
                animationManager.addAnimation(type.ordinal(), ResourceManager.INSTANCE.flameRight, 1 / 5f);
                break;
            case CENTER:
                animationManager.addAnimation(type.ordinal(), ResourceManager.INSTANCE.flameCenter, 1 / 5f);
                break;
            case LAST_UP:
                animationManager.addAnimation(type.ordinal(), ResourceManager.INSTANCE.flameLastUp, 1 / 5f);
                break;
            case LAST_DOWN:
                animationManager.addAnimation(type.ordinal(), ResourceManager.INSTANCE.flameLastDown, 1 / 5f);
                break;
            case LAST_LEFT:
                animationManager.addAnimation(type.ordinal(), ResourceManager.INSTANCE.flameLastLeft, 1 / 5f);
                break;
            case LAST_RIGHT:
                animationManager.addAnimation(type.ordinal(), ResourceManager.INSTANCE.flameLastRight, 1 / 5f);
                break;
            case EXPLOSION_BRICK:
                //animationManager.addAnimation(type.ordinal(), ResourceManager.INSTANCE.explosionBrick, 1/5f);
                animationManager.addAnimation(type.ordinal(), ResourceManager.INSTANCE.explo[0], 1 / 7f);
                break;
        }

        this.position = position;
        animationManager.setCurrentState(type.ordinal(), 1.1f);
        animationManager.setLooping(false);
    }

    public void update() {
        /*
        for (Enemy enemy : gameManager.getEnemies()) {
            if (Box2dManager.isColliding(this.body, enemy.getBody())) {
                enemy.setState(Enemy.State.DEAD.ordinal());
                System.out.println("AAAAAAAAAAAAAAAAA");
            }
        }

         */
    }

    public void render(SpriteBatch batch) {
        super.render(batch, new Vector2(this.body.getPosition().x - 0.3f, this.body.getPosition().y - 0.3f));
    }

    @Override
    public void createBody() {
        body = Box2dManager.createCircle(TileMap.coordToPos(x, y), 5, BodyDef.BodyType.StaticBody, true, gameManager.getWorld());
    }

    public boolean isFinished() {
        return animationManager.isFinished();
    }

    public enum Type {
        LAST_UP,
        LAST_DOWN,
        LAST_LEFT,
        LAST_RIGHT,
        UP,
        DOWN,
        LEFT,
        RIGHT,
        CENTER,
        EXPLOSION_BRICK,
    }
}
