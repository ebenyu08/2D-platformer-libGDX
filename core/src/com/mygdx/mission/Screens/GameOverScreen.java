  package com.mygdx.mission.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.mission.Mission;

public class GameOverScreen implements Screen {

    private static final float TIME_UNTIL_JUMP_TO_MENU = 2f;

    private SpriteBatch batch;

    private OrthographicCamera camera;

    private FitViewport viewport;

    private Stage stage;

    private Mission game;

    private boolean hasRendered = false;

    private float timer;

    public GameOverScreen(Mission game) {
        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(Mission.V_WIDTH, Mission.V_HEIGHT, camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        stage = new Stage(viewport, batch);

        timer = 0f;
    }

    @Override
    public void show() {
        Table mainTable = new Table();
        Texture texture = new Texture("gameover.jpg");
        mainTable.setBackground(
            new TextureRegionDrawable(
                new TextureRegion(
                    texture)));
        //Set table to fill stage
        mainTable.setFillParent(true);
        //Set alignment of contents in the table.
        mainTable.center();

        stage.addActor(mainTable);
    }

    @Override
    public void render(float delta) {

        timer += Gdx.graphics.getRawDeltaTime();
        if (hasRendered && timer > TIME_UNTIL_JUMP_TO_MENU) {
            game.setScreen(new MenuScreen(game));
        }

        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        hasRendered = true;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
