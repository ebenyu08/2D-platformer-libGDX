/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sprites;

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

/**
 * @author User
 */
public abstract class InteractiveObject {

  Fixture fixture;

  private World world;

  private TiledMap map;

  private TiledMapTile tile;

  private Rectangle bounds;

  private Body body;

  public InteractiveObject(World world, TiledMap map, Rectangle bounds) {
    this.world = world;
    this.map = map;
    this.bounds = bounds;

    BodyDef bdef = new BodyDef();
    FixtureDef fdef = new FixtureDef();
    PolygonShape shape = new PolygonShape();

    bdef.type = BodyDef.BodyType.StaticBody;
    bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / Mission.PPM,
        (bounds.getY() + bounds.getHeight() / 2) / Mission.PPM);
    body = world.createBody(bdef);
    shape.setAsBox(bounds.getWidth() / 2 / Mission.PPM, bounds.getHeight() / 2 / Mission.PPM);

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

  public World getWorld() {
    return world;
  }

  public TiledMap getMap() {
    return map;
  }

  public TiledMapTile getTile() {
    return tile;
  }

  public Rectangle getBounds() {
    return bounds;
  }

  public Body getBody() {
    return body;
  }
}
