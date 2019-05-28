package Sprites;

import static com.mygdx.mission.Mission.PIXELS_PER_METER;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.mission.Mission;
import com.mygdx.mission.Screens.PlayScreen;

public class Computer extends InteractiveObject {

    public int hp;

    private static TiledMapTileSet tileSet;

    private boolean hasPuzzlePiece;

    private Sprite sprite;

    private TextureRegion texture;

    public Computer(PlayScreen screen, Rectangle bounds, int hp) {
        super(screen, bounds);
        this.hp = hp;
//        sprite = new Sprite();
//        sprite.setPosition(bounds.x, bounds.y);
//        sprite.setBounds(sprite.getX(), sprite.getY(), 16 / PIXELS_PER_METER, 16 / PIXELS_PER_METER);
//        texture = new TextureRegion(screen.getComputers().findRegion("1"), 16, 0, 16, 16);
//        sprite.setRegion(texture.getRegionX(),
//              texture.getRegionY(),
//              texture.getRegionWidth(),
//              texture.getRegionHeight());
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

    public Sprite getSprite() {
        return sprite;
    }
}
