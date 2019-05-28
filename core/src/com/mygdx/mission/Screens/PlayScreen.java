/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.mission.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.SerializationException;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.mission.Mission;
import com.mygdx.mission.Scenes.HUD;

import Sprites.HealthBar;
import Sprites.Player;
import Sprites.Robot;
import Tools.WorldContactListener;
import Tools.WorldCreator;

public class PlayScreen implements Screen {

    private static final int FINAL_MAP = 7;
    private static int mapNumber = 1;

    private final Mission game;
    private final TextureAtlas atlas;
    private final TextureAtlas computers;
    private final TextureAtlas computerHP;
    private final TextureAtlas robots;
    private final OrthographicCamera gameCamera;
    private final FitViewport gamePort;
    private final HUD hud;
    //Tiled map variables
    private final TmxMapLoader mapLoader;
    private final OrthogonalTiledMapRenderer renderer;
    //box 2d variables
    private final World world;
    private final Box2DDebugRenderer b2dr;
    private final WorldCreator creator;
    private final Player player;
    private final HealthBar healthbar;

    private TiledMap map;

    public PlayScreen(Mission game, String mapName) {
        this.game = game;
        //packs containing sprites for animations
        atlas = new TextureAtlas("character.pack");
        computerHP = new TextureAtlas("load.pack");
        robots = new TextureAtlas("robot.pack");
        computers = new TextureAtlas("pc.pack");
        gameCamera = new OrthographicCamera();
        gamePort = new FitViewport(Mission.V_WIDTH / Mission.PIXELS_PER_METER, Mission.V_HEIGHT / Mission.PIXELS_PER_METER, gameCamera);
        hud = new HUD(game.batch);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load(mapName);
        mapNumber = 1;
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Mission.PIXELS_PER_METER);
        gameCamera.position.set(gamePort.getScreenWidth() / Mission.PIXELS_PER_METER, gamePort.getScreenHeight() / Mission.PIXELS_PER_METER, 0);
        //gravity vector, 
        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();
        //create the world, walls, grounds, computers, robots
        creator = new WorldCreator(this);
        //create player
        player = new Player(this);
        healthbar = new HealthBar(this);
        world.setContactListener(new WorldContactListener());
    }

    @Override
    public void show() {

    }

    //controlling player movements
    public void handleInput(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && player.b2body.getLinearVelocity().y == 0) {
            player.b2body.applyLinearImpulse(new Vector2(0, 2.75f), player.b2body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && (player.b2body.getLinearVelocity().x <= 1.5)) {
            player.b2body.applyLinearImpulse(new Vector2(0.15f, 0), player.b2body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && (player.b2body.getLinearVelocity().x >= -1.5)) {
            player.b2body.applyLinearImpulse(new Vector2(-0.15f, 0), player.b2body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && player.collided) {
            if (player.comp.hp > 0) {
                player.comp.hp--;
            } else if (player.foundAllPieces()) {
                loadNextLevel();
            }
        }
    }

    public void update(float dt) {
        healthbar.update(dt);
        handleInput(dt);
        world.step(1 / 60f, 6, 2);
        player.update(dt);
        for (Robot robot : creator.getRobots()) {
            robot.update(dt);
        }
        hud.update(dt);
        if (hud.getTime() == 0) {
            game.setScreen(new GameOverScreen(game));
        }
        gameCamera.position.x = player.b2body.getPosition().x;
        gameCamera.position.y = player.b2body.getPosition().y;
        gameCamera.update();
        renderer.setView(gameCamera);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
        //b2dr.render(world, gameCamera.combined);
        game.batch.setProjectionMatrix(gameCamera.combined);
        game.batch.begin();
        player.draw(game.batch);
        if (player.collided) {
            healthbar.draw(game.batch);
        }
        for (Robot robot : creator.getRobots()) {
            robot.draw(game.batch);
        }

//        creator.getComputers().forEach(computer -> computer.getSprite().draw(game.batch));

        game.batch.end();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
    }

    private void loadNextLevel() {
        if (mapNumber != FINAL_MAP) {
            try {
                String nextMapName = "map" + (++mapNumber) + ".tmx";
                game.setScreen(new PlayScreen(game, nextMapName));
            } catch (Exception e) {
                if (e instanceof SerializationException) {
                    game.setScreen(new MenuScreen(game));
                }
            }
        } else {
            game.setScreen(new EndingScreen(game));
        }
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public TextureAtlas getPCAtlas() {
        return computerHP;
    }

    public TextureAtlas getComputers() {
        return computers;
    }

    public TextureAtlas getRobotsAtlas() {
        return robots;
    }

    public TiledMap getMap() {
        return map;
    }

    public Player getPlayer() {
        return player;
    }

    public World getWorld() {
        return world;
    }

    public HealthBar getHealthBar() {
        return healthbar;
    }
}
