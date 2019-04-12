/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.mission.Screens.PlayScreen;

/**
 *
 * @author User
 */

public abstract class Enemy extends Sprite{
    
    protected World world;
    protected PlayScreen screen;
    public Body b2body;
    
    public Enemy(PlayScreen screen, float x,float y){
        this.world = screen.getWorld();
        this.screen = screen;
    }
    
    protected abstract void defineEnemy();
}
