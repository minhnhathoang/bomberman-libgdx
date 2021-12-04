package entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static resources.Vars.GAME_SCALE;

public class Box2dManager {

    public static final float PPM = 100f;

    public World world;

    public Box2dManager() {

    }

    public static Body createCircle(Vector2 position, float radius, BodyDef.BodyType bodyType, boolean sensor, World world) {
        position.x *= GAME_SCALE;
        position.y *= GAME_SCALE;

        radius *= GAME_SCALE;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(position.x / PPM, position.y / PPM);
        Body body = world.createBody(bodyDef);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(radius / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.isSensor = sensor;
        body.createFixture(fixtureDef);
        circleShape.dispose();

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
        // fixtureDef.isSensor = true;
        body.createFixture(fixtureDef);
        shape.dispose();

        body.createFixture(fixtureDef);

        return body;
    }

    public static boolean checkOverlap(Body b1, Body b2) {
        float distance = b1.getFixtureList().get(0).getShape().getRadius()
                + b2.getFixtureList().get(0).getShape().getRadius();
        return distance > Math.hypot(b1.getPosition().x - b2.getPosition().x, b1.getPosition().y - b2.getPosition().y);
    }

}
