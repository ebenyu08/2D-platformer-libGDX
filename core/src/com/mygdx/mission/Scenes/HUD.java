/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.mission.Scenes;

import com.mygdx.mission.Mission;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.Disposable;

/**
 * @author User
 */
public class HUD implements Disposable {

  public Stage stage;

  Label countdownLabel;

  Label scoreLabel;

  Label timeLabel;

  Label levelLabel;

  Label worldLabel;

  Label playerLabel;

  private FitViewport viewport;

  private Integer worldTimer;

  private float timeCount;

  private Integer score;

  public HUD(SpriteBatch sb) {
    worldTimer = 300;
    timeCount = 0;
    score = 0;
    viewport = new FitViewport(Mission.V_WIDTH, Mission.V_HEIGHT, new OrthographicCamera());
    stage = new Stage(viewport, sb);

    Table table = new Table();
    table.top();
    table.setFillParent(true); // table size = stage size
    countdownLabel = new Label(String.format("%03d", worldTimer),
        new Label.LabelStyle(new BitmapFont(), com.badlogic.gdx.graphics.Color.WHITE));
    scoreLabel = new Label(String.format("%02d", score),
        new Label.LabelStyle(new BitmapFont(), com.badlogic.gdx.graphics.Color.WHITE));
    timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), com.badlogic.gdx.graphics.Color.WHITE));
    levelLabel = new Label("Map: 1", new Label.LabelStyle(new BitmapFont(), com.badlogic.gdx.graphics.Color.WHITE));
    playerLabel = new Label("PLAYER 1", new Label.LabelStyle(new BitmapFont(), com.badlogic.gdx.graphics.Color.WHITE));

    table.add(playerLabel).expandX().padTop(160);//10
    table.add(worldLabel).expandX().padTop(160);//10
    table.add(timeLabel).expandX().padTop(160);//10
    table.row();
    table.add(scoreLabel).expandX();
    table.add(levelLabel).expandX();
    table.add(countdownLabel).expandX();
    stage.addActor(table);

  }

  public void update(float dt) {
    timeCount += dt;
    if (timeCount >= 1) {
      worldTimer--;
      countdownLabel.setText(String.format("%03d", worldTimer));
      timeCount = 0;
    }
  }

  @Override
  public void dispose() {
    stage.dispose();
  }

  public int getTime() {
    return worldTimer;
  }
}
