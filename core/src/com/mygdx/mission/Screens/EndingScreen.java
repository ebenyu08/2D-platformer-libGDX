package com.mygdx.mission.Screens;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.mission.Mission;

public class EndingScreen implements Screen {

      private static final String ENDING_SOLUTION = "nothing";

      private final OrthographicCamera camera;

      private final FitViewport viewport;

      private final Stage stage;

      private final Skin skin;

      public EndingScreen() {

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

          AtomicInteger index = new AtomicInteger();
          List letters = ENDING_SOLUTION.chars().mapToObj(c -> (char)c).collect(Collectors.toList());
          Collections.shuffle(letters);
          letters.forEach(letter -> {
              TextButton button = new TextButton(String.valueOf(letter), skin);
              button.getLabel().setAlignment(Align.center);
              main.add(button).pad(5f).width(30f).height(30f);
              if (index.incrementAndGet() == 4) {
                main.row();
                index.set(0);
              }
          });
          main.left();
          main.pad(15f);

          Image image = new Image(new TextureRegion(new Texture("empty.png")));
          image.setScale(0.15f);
          image.setPosition(Gdx.graphics.getWidth() / 2f - image.getWidth() / 5f,
              Gdx.graphics.getHeight() / 2f - image.getHeight() / 2f);
          stage.addActor(main);
          stage.addActor(image);
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
