/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.mission.Screens;

import Sprites.Player;
import Tools.WorldContactListener;
import Tools.WorldCreator;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.mission.Mission;
import com.mygdx.mission.Scenes.HUD;
import com.sun.prism.image.ViewPort;

/**
 * @author User
 */
public class PlayScreen implements Screen {

  private Mission game;

  private TextureAtlas atlas;

  private TextureAtlas computerHP;

  private OrthographicCamera gameCamera;

  private FitViewport gamePort;

  private HUD hud;

  //Tiled map variables
  private TiledMap map;

  private TmxMapLoader mapLoader;

  private OrthogonalTiledMapRenderer renderer;

  //box 2d variables
  public int jumpCount = 0;

  private World world;

  private Box2DDebugRenderer b2dr;

  private Player player;

  public PlayScreen(Mission game) {
    atlas = new TextureAtlas("core/assets/Character.pack");
    computerHP = new TextureAtlas("core/assets/pc.pack");
    this.game = game;
    gameCamera = new OrthographicCamera();
    gamePort = new FitViewport(Mission.V_WIDTH / Mission.PPM, Mission.V_HEIGHT / Mission.PPM, gameCamera);
    hud = new HUD(game.batch);

    mapLoader = new TmxMapLoader();
    map = mapLoader.load("core/assets/map1.tmx");
    renderer = new OrthogonalTiledMapRenderer(map, 1 / Mission.PPM);
    gameCamera.position.set(gamePort.getScreenWidth() / Mission.PPM, gamePort.getScreenHeight() / Mission.PPM, 0);
    //gravity vector,
    world = new World(new Vector2(0, -10), true);
    b2dr = new Box2DDebugRenderer();

    new WorldCreator(this);

    //create player
    player = new Player(this);

    world.setContactListener(new WorldContactListener());

  }

  @Override
  public void show() {

  }

  public void handleInput(float dt) {

    if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && player.b2body.getLinearVelocity().y == 0) {
      player.b2body.applyLinearImpulse(new Vector2(0, 5f), player.b2body.getWorldCenter(), true);

    }
    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && (player.b2body.getLinearVelocity().x <= 2)) {
      player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);

    }
    if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && (player.b2body.getLinearVelocity().x >= -2)) {
      player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);

    }
  }

  public void update(float dt) {
    handleInput(dt);

    world.step(1 / 60f, 6, 2);

    player.update(dt);
    hud.update(dt);
    //camera follows player
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

    b2dr.render(world, gameCamera.combined);

    game.batch.setProjectionMatrix(gameCamera.combined);
    game.batch.begin();
    player.draw(game.batch);
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

  public TiledMap getMap() {
    return map;
  }

  public World getWorld() {
    return world;
  }

  public TextureAtlas getAtlas() {
    return atlas;
  }

  public TextureAtlas getPCAtlas() {
    return computerHP;
  }
}
