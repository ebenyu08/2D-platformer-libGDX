package Sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.mission.Mission;
import com.mygdx.mission.Screens.PlayScreen;

public class Computer extends InteractiveObject {

    public int hp;

    private static TiledMapTileSet tileSet;

    private boolean hasPuzzlePiece;

    private final TextureRegion pcAlive;

    private final TextureRegion pcDead;

    public Computer(PlayScreen screen, Rectangle bounds, int hp) {
        super(screen, bounds);
        this.hp = hp;
        tileSet = map.getTileSets().getTileSet("done");
        fixture.setUserData(this);
        setCategoryFilter(Mission.COMPUTER_BIT);
        setBounds(0, 0, 16 / Mission.PIXELS_PER_METER, 16 / Mission.PIXELS_PER_METER);
        setPosition(bounds.x/Mission.PIXELS_PER_METER, bounds.y/Mission.PIXELS_PER_METER);
        pcAlive = new TextureRegion(screen.getComputersAtlas().findRegion("1"), 0, 0, 16, 16);
        pcDead = new TextureRegion(screen.getComputersAtlas().findRegion("2"), 0, 0, 16, 16);

        setRegion(pcAlive);
    }

    public void update(float dt) {
        if (this.hp <=0) {
            setRegion(pcDead);
            setPosition(bounds.x/Mission.PIXELS_PER_METER-0.0120f, bounds.y/Mission.PIXELS_PER_METER);
        }
    }

    @Override
    public void onCollision() {
        //Gdx.app.log("Computer", "Collision");
        screen.getPlayer().collided = true;
        screen.getPlayer().setComputer(this);
    }

    @Override
    public void offCollision() {
        //Gdx.app.log("Computer", "End of Collision");
        screen.getPlayer().collided = false;
        screen.getHealthBar().setPosition(0, 0);
    }

    public void changeColor() {
    }

    public boolean hasPuzzlePiece() {
        return hasPuzzlePiece;
    }

    public void setHasPuzzlePiece(boolean hasPuzzlePiece) {
        this.hasPuzzlePiece = hasPuzzlePiece;
    }
}
