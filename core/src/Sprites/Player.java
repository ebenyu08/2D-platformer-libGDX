package Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.mission.Mission;
import com.mygdx.mission.Screens.PlayScreen;

public final class Player extends Sprite {

    public enum State {
        FALLING, JUMPING, STANDING, RUNNING
    };
    public boolean hasComputer = false;
    public boolean collided = false;
    public boolean collidedToRobot = false;
    public Body b2body;
    public Computer comp;

    private CircleShape shape;
    private CircleShape head;
    private State currentState;
    private State previousState;
    private final World world;
    private final TextureRegion playerStand;
    private final Animation playerRun;
    private final Animation playerJump;
    private float stateTimer;
    private boolean runningRight;
    private int numberOfPieces;

    public Player(PlayScreen screen) {
        super(screen.getAtlas().findRegion("character1"));
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;
        numberOfPieces = 0;
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 1; i < 3; i++) {
            frames.add(new TextureRegion(getTexture(), i * 16, 0, 16, 16));
        }
        playerRun = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 3; i < 5; i++) {
            frames.add(new TextureRegion(getTexture(), i * 16, 0, 16, 16));
        }
        playerJump = new Animation(0.1f, frames);
        playerStand = new TextureRegion(getTexture(), 0, 0, 16, 16);
        definePlayer(true);
        setBounds(0, 0, 16 / Mission.PixelsPerMeter, 16 / Mission.PixelsPerMeter);
        setRegion(playerStand);
    }

    public void update(float dt) {
        if (collidedToRobot == true) {
            b2body.setTransform(new Vector2(0.2f, 0.5f), b2body.getAngle());
            collidedToRobot = false;
        } else {
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        }
        setRegion(getFrame(dt));
    }

    public void definePlayer(boolean right) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(16 / Mission.PixelsPerMeter, 32 / Mission.PixelsPerMeter);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        shape = new CircleShape();
        shape.setRadius(7 / Mission.PixelsPerMeter);
        fdef.filter.categoryBits = Mission.PLAYER_BIT;
        fdef.filter.maskBits = Mission.DEFAULT_BIT;
        fdef.shape = shape;
        b2body.createFixture(fdef);
        head = new CircleShape();
        head.setRadius(8 / Mission.PixelsPerMeter);
        fdef.filter.categoryBits = Mission.PLAYER_BIT;
        fdef.filter.maskBits = Mission.DEFAULT_BIT | Mission.COMPUTER_BIT | Mission.ROBOT_BIT;
        fdef.shape = head;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("body");
    }

    public void setComputer(Computer computer) {
        this.comp = computer;
        hasComputer = true;
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;
        switch (currentState) {
            case JUMPING:
                region = (TextureRegion) playerJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = (TextureRegion) playerRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = playerStand;
                break;
        }
        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    public State getState() {
        if (b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING)) {
            return State.JUMPING;
        } else if (b2body.getLinearVelocity().y < 0) {
            return State.FALLING;
        } else if (b2body.getLinearVelocity().x != 0) {
            return State.RUNNING;
        } else {
            return State.STANDING;
        }
    }

    public void foundPiece() {
        numberOfPieces++;
    }

    public boolean foundAllPieces() {
        return numberOfPieces == 4;
    }
}
