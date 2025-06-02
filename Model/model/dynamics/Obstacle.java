// ==== Package ==== :
package Model.model.dynamics;

// ==== General ==== :
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import Engine.user.User;
import Graphics.Camera;
import Math.Vector;
import Util.api.ThreadElement;
// ==== Interfaces ==== :
import World.api.Action;
import World.api.engine.ContinuumIntegration;
import World.api.engine.ObstacleTemplateAccessibility;
import World.api.engine.Renderable;
import World.api.registeries.ApiBindable;
import World.model.statics.Cell;



/*  General Documentation:
 * 
 *  Obstacle instances simulate dynamic objects that follow a path (Trajectory) and interact with the "player" (Frog).
 *  Unlike the static terrain, access to Obstacle instances is only supported through targeted iterative algorithms.
 */
public abstract class Obstacle extends Rectangle implements
/* Raw-engine associated APIs */
ContinuumIntegration, Renderable, Action,
/* Thread-layering*/
ApiBindable
{

    // ==== Fields ==== :

    // Instances :
    public Image image;
    public Trajectory path;

    /* Loop integration fields */
    private boolean updated = false;

    // Concretes: (to prevent any malfunction)
    protected final static int UNDEFINED = -1;
    public final static int DEAFULT_LOW_DURATION_LIMIT = 0, DEAFULT_UP_DURATION_LIMIT = 1;

    public static double[] deafultDurationLimits = new double[2];
    static{
        Obstacle.deafultDurationLimits[ Obstacle.DEAFULT_LOW_DURATION_LIMIT ] = 1;
        Obstacle.deafultDurationLimits[ Obstacle.DEAFULT_UP_DURATION_LIMIT ] = 3;
    }

    public static final Dimension
    lowDimension = new Dimension( Cell.WIDTH, Cell.WIDTH ),
    upDimension = new Dimension( Cell.WIDTH, Cell.WIDTH );



    // ==== Interfaces ==== :

    // Renderable:
    @Override
    public void render( Graphics g, Camera camera ){
        Vector cameraDisp = camera.getPosition();

        int dipsX = (int)( this.getX() - cameraDisp.getX());
        int dipsY = (int)( this.getY() - cameraDisp.getY());
        
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

    // ApiBindable
    @Override
    public ThreadElement<?> getAsThreadElementOf( Class<?> clazz ) throws IllegalArgumentException{
        if( clazz.isInterface() && clazz.isAssignableFrom( Obstacle.class ) ){
            return
        }
    }



    // ==== Methods ==== :

    // Instances:
    public void tweakDuration( double durationSecs ){
        this.path.tweakDuration( durationSecs );
    }

    // Concretes:

    // Concretes:
    public static Dimension randomHitbox( Dimension upLim, Dimension lowLim ){
        double dWidth = upLim.getWidth() - lowLim.getWidth();
        double dHeight = upLim.getHeight() - lowLim.getHeight();

        int assignedW = (int) ( dWidth * Math.random() ) + lowLim.width;
        int assignedH = (int) ( dHeight * Math.random() ) + lowLim.height;

        return new Dimension( assignedW, assignedH );
    }

    public static double[] getDeafultDurationLimits(){
        return Obstacle.deafultDurationLimits;
    }



    // ==== Constructors ==== :

    public Obstacle( Trajectory path, Dimension hitbox, Image image ){
        super( (int) path.getAt(0).getX(), (int) path.getAt(0).getY(), hitbox.width, hitbox.height );

        this.path = path;
        this.image = image;
    }
}
