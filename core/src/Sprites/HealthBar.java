package Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.mission.Mission;
import com.mygdx.mission.Screens.PlayScreen;

public final class HealthBar extends Sprite {

    public TextureAtlas atlasHP;
    public Body body;
    public World world;
    public PlayScreen screen;
    private final TextureRegion fullhp;
    private final TextureRegion zerohp;
    private final TextureRegion thirdhp;
    private final TextureRegion twothirdhp;

    public HealthBar(PlayScreen screen) {
        super(screen.getPCAtlas().findRegion("1"));
        this.screen = screen;
        this.world = screen.getWorld();
        zerohp = new TextureRegion(getTexture(), 0, 0, 16, 16);
        thirdhp = new TextureRegion(getTexture(), 16, 0, 16, 16);
        twothirdhp = new TextureRegion(getTexture(), 32, 0, 16, 16);
        fullhp = new TextureRegion(getTexture(), 48, 0, 16, 16);
        defineHealthBar(true);
        setBounds(0, 0, 16 / Mission.PixelsPerMeter, 16 / Mission.PixelsPerMeter);
        setRegion(fullhp);
    }

    public void update(float dt) {
        setRegion(getFrame(dt));
        if (screen.getPlayer().hasComputer && screen.getPlayer().collided) {
            setPosition(screen.getPlayer().comp.bounds.x / 100, screen.getPlayer().comp.bounds.y / 100 + 0.25f);
        }
    }

    public void defineHealthBar(boolean right) {

    }

    public TextureRegion getFrame(float dt) {
        if (screen.getPlayer().hasComputer) {
            Computer comp = screen.getPlayer().comp;
            if (comp.hp <= 100 && comp.hp > 66) {
                return fullhp;
            } else if (comp.hp <= 66 && comp.hp > 33) {
                return twothirdhp;
            } else if (comp.hp <= 33 && comp.hp > 0) {
                return thirdhp;
            } else {
                if (comp.hasPuzzlePiece()) {
                    screen.getPlayer().foundPiece();
                }
                comp.changeColor();
                return zerohp;
            }
        }
        return fullhp;

    }
}
