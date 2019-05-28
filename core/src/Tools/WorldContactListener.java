package Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.mission.Mission;

import Sprites.InteractiveObject;
import Sprites.Robot;

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        //Who collided with whom
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        if (fixA.getUserData() == "body" || fixB.getUserData() == "body") {
            Fixture body = fixA.getUserData() == "body" ? fixA : fixB;
            Fixture object = body == fixA ? fixB : fixA;

            if (object.getUserData() != null && InteractiveObject.class.isAssignableFrom(object.getUserData().getClass())) {
                //calling the computer's collision function
                ((InteractiveObject) object.getUserData()).onCollision();
            } else if (fixA.getFilterData().categoryBits == Mission.DEFAULT_BIT) {
                //ground
            } else if ((fixA.getFilterData().categoryBits == Mission.ROBOT_BIT && fixB.getFilterData().categoryBits == Mission.PLAYER_BIT)
                    | (fixB.getFilterData().categoryBits == Mission.ROBOT_BIT && fixA.getFilterData().categoryBits == Mission.PLAYER_BIT)) {
                //calling the robot's collision function
                ((Robot) object.getUserData()).onCollision();
            }
        }

    }

    @Override
    public void endContact(Contact contact) {
        //who collided with whom
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        if (fixA.getUserData() == "body" || fixB.getUserData() == "body") {
            Fixture body = fixA.getUserData() == "body" ? fixA : fixB;
            Fixture object = body == fixA ? fixB : fixA;

            if (object.getUserData() != null && InteractiveObject.class.isAssignableFrom(object.getUserData().getClass())) {
                //End of collision with computer
                ((InteractiveObject) object.getUserData()).offCollision();
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
