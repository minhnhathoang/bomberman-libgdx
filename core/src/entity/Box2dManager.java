package entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Box2dManager {

    public static final float PPM = 100f;

    public World world;

    public Box2dManager() {

    }

    public static Body createCircle(Vector2 position, float width, float height, World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position.x / PPM, position.y / PPM);
        Body body = world.createBody(bodyDef);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(0.45f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;

        body.createFixture(fixtureDef);

        return body;
    }

    public static Body createRectangle(Vector2 position, float width, float height, World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
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
