package com.mygdx.mission;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mission.Screens.MenuScreen;

public class Mission extends Game {

    public SpriteBatch batch;
    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 208;
    public static final float PIXELS_PER_METER = 100;
    public static final short DEFAULT_BIT = 1;
    public static final short PLAYER_BIT = 2;
    public static final short COMPUTER_BIT = 4;
    public static final short COMPUTER_BIT2 = 8;
    public static final short ROBOT_BIT = 16;

    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new MenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }
}
