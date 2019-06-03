package com.mygdx.mission.Screens;

import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.mission.Mission;

public class EndingScreen implements Screen {

      private static final String SOLUTION = "nothing";

      private static float POS = 25f;

      private static int CURRENT_LETTER_NO = 0;

      private static Stack<Button> BUTTONS_STACK = new Stack<>();

      private static String GUESS = "";

      private final OrthographicCamera camera;

      private final FitViewport viewport;

      private final Stage stage;

      private final Skin skin;

      private final Mission game;

      public EndingScreen(Mission game) {
          this.game = game;
          TextureAtlas atlas = new TextureAtlas("skin/menuskin.atlas");
          skin = new Skin(Gdx.files.internal("skin/menuskin.json"), atlas);
          // add letters
          skin.getFont("menu-font").getData().setScale(0.5f);
          SpriteBatch batch = new SpriteBatch();
          camera = new OrthographicCamera();
          viewport = new FitViewport(Mission.V_WIDTH, Mission.V_HEIGHT, camera);
          viewport.apply();

          camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
          camera.update();

          stage = new Stage(viewport, batch);
      }

      @SuppressWarnings("unchecked")
      @Override
      public void show() {
          Gdx.input.setInputProcessor(stage);
          Table main = new Table();
          main.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("ending.png"))));
          main.setFillParent(true);
          main.left();
          main.pad(15f);

          Image johnCena = new Image(new TextureRegion(new Texture("empty.png")));
          johnCena.setScale(0.15f);
          johnCena.setPosition(Gdx.graphics.getWidth() / 2f - johnCena.getWidth() / 5f,
              Gdx.graphics.getHeight() / 2f - johnCena.getHeight() / 2f);

          TextureRegionDrawable winningScreen =
              new TextureRegionDrawable(
                  new TextureRegion(
                      new Texture("you-win.png")));

          AtomicInteger index = new AtomicInteger();
          List letters = SOLUTION.chars().mapToObj(c -> (char)c).collect(Collectors.toList());
          Collections.shuffle(letters);
          addListenersToButtons(main, johnCena, winningScreen, index, letters);
          stage.addActor(main);
          stage.addActor(johnCena);
      }

      @SuppressWarnings("unchecked")
      private void addListenersToButtons(Table main, Image johnCena, TextureRegionDrawable winningScreen,
                                     AtomicInteger index, List letters) {
          letters.forEach(letter -> {
              TextButton button = new TextButton(String.valueOf(letter), skin);
              button.getLabel().setAlignment(Align.center);
              button.addListener(new ClickListener() {

                  private float X;

                  private float Y;

                  private boolean posModified = false;

                  @Override
                  public void clicked(InputEvent event, float x, float y) {
                      if (!posModified && CURRENT_LETTER_NO++ != 7) {
                          X = button.getX();
                          Y = button.getY();
                          button.setPosition(POS, 30f);
                          POS += 52f;
                          posModified = true;
                          GUESS += button.getLabel().getText();
                          BUTTONS_STACK.push(button);
                      } else if (posModified && BUTTONS_STACK.peek().equals(button)) {
                          button.setPosition(X, Y);
                          posModified = false;
                          POS -= 52f;
                          CURRENT_LETTER_NO--;
                          GUESS = GUESS.substring(0, GUESS.length() - 1);
                          main.setColor(Color.WHITE);
                          BUTTONS_STACK.pop();

                      }

                      if (CURRENT_LETTER_NO == 7) {
                          evaluateSolution();
                      }
                  }

                  private void evaluateSolution() {
                      if (GUESS.equals(SOLUTION)) {
                          main.clearChildren();
                          johnCena.remove();
                          main.setBackground(winningScreen);
                          stage.addListener(new ClickListener() {
                              @Override
                              public void clicked(InputEvent event, float x, float y) {
                                  game.setScreen(new MenuScreen(game));
                              }
                          });
                      } else {
                            main.setColor(Color.RED);
                      }
                  }
              });
              main.add(button).pad(5f).width(30f).height(30f);
              if (index.incrementAndGet() == 4) {
                  main.row();
                  index.set(0);
              }
          });
      }

  @Override
      public void render(float delta) {
          Gdx.gl.glClearColor(0f, 0f, 0f, 1);
          Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
          stage.act();
          stage.draw();
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
