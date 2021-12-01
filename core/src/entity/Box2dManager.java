package entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static resources.Utils.GAME_SCALE;

public class Box2dManager {

    public static final float PPM = 100f;

    public World world;

    public Box2dManager() {

    }

    public static Body createCircle(Vector2 position, float width, float height, float radius, World world) {
        position.x *= GAME_SCALE;
        position.y *= GAME_SCALE;
        width *= GAME_SCALE;
        height *= GAME_SCALE;
        radius *= GAME_SCALE;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position.x / PPM, position.y / PPM);
        Body body = world.createBody(bodyDef);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(radius / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;

        body.createFixture(fixtureDef);

        return body;
    }

    public static Body createRectangle(Vector2 position, float width, float height, World world) {
        position.x *= GAME_SCALE;
        position.y *= GAME_SCALE;
        width *= GAME_SCALE;
        height *= GAME_SCALE;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(position.x / PPM, position.y / PPM);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / PPM, height / PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
        shape.dispose();

        body.createFixture(fixtureDef);

        return body;
    }

}
