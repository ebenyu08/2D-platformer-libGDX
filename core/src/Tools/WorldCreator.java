package Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Sprites.Computer;
import Sprites.Robot;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.mission.Mission;
import com.mygdx.mission.Screens.PlayScreen;

public class WorldCreator {

    private final Array<Robot> robots;

    private final List<Computer> computers;

    public WorldCreator(PlayScreen screen) {

        computers = new ArrayList<>();

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
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Mission.PIXELS_PER_METER, (rect.getY() + rect.getHeight() / 2) / Mission.PIXELS_PER_METER);
            body = world.createBody(bdef);
            shape.setAsBox((rect.getWidth() / 2) / Mission.PIXELS_PER_METER, (rect.getHeight() / 2) / Mission.PIXELS_PER_METER); //starts at center
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        //create walls,get(3) -> 0 = bckgrnd, 1 =graphics , 2 =ground , 3 = walls
        for (RectangleMapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Mission.PIXELS_PER_METER, (rect.getY() + rect.getHeight() / 2) / Mission.PIXELS_PER_METER);
            body = world.createBody(bdef);
            shape.setAsBox((rect.getWidth() / 2) / Mission.PIXELS_PER_METER, (rect.getHeight() / 2) / Mission.PIXELS_PER_METER); //starts at center
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        //create computers 4 = computers
        for (RectangleMapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            computers.add(new Computer(screen, rect, 100));
        }

        int puzzlePieces = 4;
        while (puzzlePieces != 0) {
            if (!computers.get(puzzlePieces - 1).hasPuzzlePiece()) {
                computers.get(puzzlePieces - 1).setHasPuzzlePiece(true);
                puzzlePieces--;
            }
        }

        //create robots
        robots = new Array<Robot>();

        for (RectangleMapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            robots.add(new Robot(screen, rect.getX() / Mission.PIXELS_PER_METER,
                    rect.getY() / Mission.PIXELS_PER_METER));
        }
    }

    public Array<Robot> getRobots() {
        return robots;
    }

    public List<Computer> getComputers() {
        return computers;
    }
}
