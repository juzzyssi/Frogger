package Model.model.templates.dynamics.accelerators;

import java.awt.event.KeyEvent;

import Engine.user.User;
import Engine.user.api.UserListener;
import Math.Vector;
import Model.model.dynamics.api.Accelerator;
import Model.model.statics.primitives.Tile;



public class PImpluse implements Accelerator, UserListener{

    // ==== Fields ==== :

    /* INSTANCES: */
    private User user; /* Shallow reference */
    private int jumpDisp;

    public Vector queuedDisp;
    
    // ==== Interfaces ==== :

    @Override
    public Vector getAccDisp() {
        Vector out;
        if( this.queuedDisp != null ) {
            out = new Vector( this.queuedDisp );
            this.queuedDisp = null;
        } else {
            out = new Vector(0, 0);
        }
        return out;
    }

    @Override
    public void KeyboardActionPerformed( UserKeyboardEvent e ) {
        if( e.getLabel().equals( UserListener.UserKeyboardEvent.TRIGGERED ) ) {
            this.queuedDisp = this.getDispUnitVector().scale( this.jumpDisp );            
        }
    }

    @Override
    public void MouseActionPerformed( UserMouseEvent e ) {
        // Nothing.
    }

    // ==== Method ==== :

    private Vector getDispUnitVector() {
        Vector sum = new Vector(0, 0);

        // Down:
        if( user.keyboard.keyTriggerTo( KeyEvent.VK_W, true ) ) {
            sum = Vector.add( sum, new Vector(0, -1));
        }

        // Left:
        if( user.keyboard.keyTriggerTo( KeyEvent.VK_A, true ) ) {
            sum = Vector.add( sum, new Vector(-1, 0));
        } 

        // Down:
        if( user.keyboard.keyTriggerTo( KeyEvent.VK_S, true ) ) {
            sum = Vector.add( sum, new Vector(0, 1));
        } 

        // Right:
        if( user.keyboard.keyTriggerTo( KeyEvent.VK_D, true ) ) {
            sum = Vector.add( sum, new Vector(1, 0));
        }

        return sum;
    }

    // ==== Constructors ==== :
    
    public PImpluse( User user ) {
        this.user = user;
        user.addUserKeyboardListener( this );
        this.queuedDisp = null;
        this.jumpDisp = Tile.BLOCK.width;
    }
}
