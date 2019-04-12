/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import Sprites.InteractiveObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 *
 * @author User
 */
public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        //Kiderítjük, hogy ki ütközik bele kibe
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        if(fixA.getUserData()=="body" ||fixB.getUserData()=="body"){
            Fixture body = fixA.getUserData()=="body" ? fixA : fixB;
            Fixture object = body == fixA ? fixB : fixA;
            
            if(object.getUserData()!=null && InteractiveObject.class.isAssignableFrom(object.getUserData().getClass())){
                ((InteractiveObject)object.getUserData()).onCollision();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
         //Kiderítjük, hogy ki ütközik bele kibe
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        if(fixA.getUserData()=="body" ||fixB.getUserData()=="body"){
            Fixture body = fixA.getUserData()=="body" ? fixA : fixB;
            Fixture object = body == fixA ? fixB : fixA;
            
            if(object.getUserData()!=null && InteractiveObject.class.isAssignableFrom(object.getUserData().getClass())){
                ((InteractiveObject)object.getUserData()).offCollision();
            }
        }
    }

    @Override
    public void preSolve(Contact cntct, Manifold mnfld) {

    }

    @Override
    public void postSolve(Contact cntct, ContactImpulse ci) {

    }

}
