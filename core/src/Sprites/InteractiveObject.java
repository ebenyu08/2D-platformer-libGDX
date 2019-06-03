package Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.mission.Mission;
import com.mygdx.mission.Screens.PlayScreen;

/**
 * @author User
 */
public abstract class InteractiveObject extends Sprite {

    public boolean collided = false;

    protected World world;

    protected TiledMap map;

    protected TiledMapTile tile;

    protected Rectangle bounds;

    protected Body body;

    protected Fixture fixture;

    protected PlayScreen screen;

    public InteractiveObject(PlayScreen screen, Rectangle bounds) {
        this.screen = screen;
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.bounds = bounds;
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / Mission.PIXELS_PER_METER, (bounds.getY() + bounds.getHeight() / 2) / Mission.PIXELS_PER_METER);
        body = world.createBody(bdef);
        shape.setAsBox(bounds.getWidth() / 2 / Mission.PIXELS_PER_METER, bounds.getHeight() / 2 / Mission.PIXELS_PER_METER);
        fdef.shape = shape;
        fixture = body.createFixture(fdef);
    }

    public abstract void onCollision();

    public abstract void offCollision();

    public void setCategoryFilter(short filterBit) {
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

}
