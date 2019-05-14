package Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.mission.Mission;
import com.mygdx.mission.Screens.PlayScreen;

public class Robot extends Enemy {

    private float stateTime;
    private final Animation walkAnimation;
    private final Array<TextureRegion> frames;
    private final float startingPosition;

    public Robot(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        startingPosition = x;
        frames = new Array<TextureRegion>();
        for (int i = 0; i < 2; i++) {
            frames.add(new TextureRegion(screen.getRobotsAtlas().findRegion("1"), i * 16, 0, 16, 16));
        }
        walkAnimation = new Animation(0.4f, frames);
        stateTime = 0;
        //size of texture
        setBounds(getX(), getY(), 16 / Mission.PixelsPerMeter, 16 / Mission.PixelsPerMeter);
    }

    @Override
    public void update(float dt) {
        stateTime += dt;

        //changing velocity, so that robots remain in their computers 
        if (b2body.getPosition().x > startingPosition + 0.5) {
            this.reverseVelocity(true, false);
        } else if (b2body.getPosition().x < startingPosition - 0.5) {
            this.reverseVelocity(true, false);
        }
        //moving the robot with the given velocity
        b2body.setLinearVelocity(velocity);
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion((TextureRegion) walkAnimation.getKeyFrame(stateTime, true));

    }

    @Override
    protected void defineEnemy() {

        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(7 / Mission.PixelsPerMeter);
        fdef.filter.categoryBits = Mission.ROBOT_BIT;
        fdef.filter.maskBits = Mission.PLAYER_BIT | Mission.DEFAULT_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

    }

    @Override
    public void onCollision() {
        //Gdx.app.log("Robot", "Collision");
        this.getScreen().getPlayer().collidedToRobot = true;
    }

    @Override
    public void offCollision() {
        //Gdx.app.log("Robot", "END");

    }

}
