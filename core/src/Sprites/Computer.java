/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.mission.Mission;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;

/**
 * @author User
 */
public class Computer extends InteractiveObject {

  private static TiledMapTileSet tileSet;

  private float hp;

  public Computer(World world, TiledMap map, Rectangle bounds, float hp) {
    super(world, map, bounds);
    this.hp = hp;
    tileSet = map.getTileSets().getTileSet("done");
    fixture.setUserData(this);
    setCategoryFilter(Mission.COMPUTER_BIT);
  }

  @Override
  public void onCollision() {
    Gdx.app.log("Computer", "Collision");
  }

  @Override
  public void offCollision() {
    Gdx.app.log("Computer", "END");
  }

  public float getHp() {
    return hp;
  }
}
