/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import Sprites.Computer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.mission.Mission;
import com.mygdx.mission.Screens.PlayScreen;

/**
 *
 * @author User
 */
public class WorldCreator {

    public WorldCreator(PlayScreen screen) {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
        //create ground bodies
        for (RectangleMapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Mission.PPM, (rect.getY() + rect.getHeight() / 2) / Mission.PPM);

            body = world.createBody(bdef);
            shape.setAsBox((rect.getWidth() / 2) / Mission.PPM, (rect.getHeight() / 2) / Mission.PPM); //starts at center
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        //create walls,get(3) -> 0 = bckgrnd, 1 =graphics , 2 =ground , 3 = walls
        for (RectangleMapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Mission.PPM, (rect.getY() + rect.getHeight() / 2) / Mission.PPM);

            body = world.createBody(bdef);
            shape.setAsBox((rect.getWidth() / 2) / Mission.PPM, (rect.getHeight() / 2) / Mission.PPM); //starts at center
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        //create computers 4 = computers
        for (RectangleMapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Computer(world, map, rect,1);
        }   
    }
}
