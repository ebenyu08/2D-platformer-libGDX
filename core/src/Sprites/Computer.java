package Sprites;

import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.mission.Mission;
import com.mygdx.mission.Screens.PlayScreen;

public class Computer extends InteractiveObject {

    public int hp;

    private static TiledMapTileSet tileSet;

    private boolean hasPuzzlePiece;

    public Computer(PlayScreen screen, Rectangle bounds, int hp) {
        super(screen, bounds);
        this.hp = hp;
        tileSet = map.getTileSets().getTileSet("done");
        fixture.setUserData(this);
        setCategoryFilter(Mission.COMPUTER_BIT);
    }

    public void update(float dt) {

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
