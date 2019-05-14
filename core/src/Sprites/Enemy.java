package Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.mission.Screens.PlayScreen;

public abstract class Enemy extends Sprite {

    public World world;
    public Vector2 velocity;
    public Vector2 velocityNegative;
    public Body b2body;
    protected Fixture fixture;
    private final PlayScreen screen;

    public Enemy(PlayScreen screen, float x, float y) {
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        defineEnemy();
        velocity = new Vector2(0.5f, 0);
    }

    protected abstract void defineEnemy();

    public abstract void update(float dt);

    public void reverseVelocity(boolean x, boolean y) {
        if (x) {
            velocity.x = -velocity.x;
        }
        if (y) {
            velocity.y = -velocity.y;
        }
    }

    public PlayScreen getScreen() {
        return screen;
    }

    public abstract void onCollision();

    public abstract void offCollision();
}
