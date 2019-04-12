/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sprites;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.mission.Mission;
import com.mygdx.mission.Screens.PlayScreen;

/**
 *
 * @author User
 */
public class Robot extends Enemy{
    
    public Robot(PlayScreen screen, float x, float y){
        super(screen, x, y);
    }
    
    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(70 / Mission.PPM, 70 / Mission.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(7 / Mission.PPM);
        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
    
}
