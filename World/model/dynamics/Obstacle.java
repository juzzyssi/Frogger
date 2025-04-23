// ==== Package ==== :
package World.model.dynamics;

// ==== General ==== :
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import Engine.user.User;
import Graphics.Camera;
import Math.Vector;

// ==== Interfaces ==== :
import World.api.Action;
import World.api.engine.LoopIntegration;
import World.api.engine.Renderable;



/*  General Documentation:
 * 
 *  Obstacle instances simulate dynamic objects that follow a path (Trajectory) and interact with the "player" (Frog).
 *  Unlike the static terrain, access to Obstacle instances is only supported through targeted iterative algorithms.
 */
public abstract class Obstacle extends Rectangle implements LoopIntegration, Renderable, Action{

    // ==== Fields ==== :

    // Instances :
    public Image image;
    public Trajectory path;

    /* Loop integration fields */
    private boolean updated = false;

    // Concretes:
    protected final static int UNDEFINED = -1;



    // ==== Interfaces ==== :

    // Renderable:
    @Override
    public void render( Graphics g, Camera camera ){
        Vector cameraDisp = camera.getPosition();

        int dipsX = (int)( this.getX() - cameraDisp.getX() );
        int dipsY = (int)( this.getY() - cameraDisp.getY() );
        
        g.drawImage( this.image , dipsX, dipsY, this.width, this.height, null);
    }

    // LoopIntegration:
    @Override
    public void checkIn( long time ){
        /* Updates the path field */
        if( !this.updated ){
            this.updated = true;

            this.path.checkIn(time);
            this.x = (int) this.path.position.getX();
            this.y = (int) this.path.position.getY();
        }
    }
    @Override
    public void checkOut( long time ){
        /* Set's the instance ready for the next call */
        if( this.updated ){
            this.updated = false;
            this.path.checkOut(time);
        }
    }

    // Action
    public void act( int token ){
        /* Customizable */
    }


    public int interact( User player ){
        /* Customizable */
        return Obstacle.UNDEFINED;
    }



    // ==== Constructors ==== :

    public Obstacle( Trajectory path, Dimension hitbox, Image image ){
        super( (int) path.getAt(0).getX(), (int) path.getAt(0).getY(), hitbox.width, hitbox.height );

        this.path = path;
        this.image = image;
    }
}
