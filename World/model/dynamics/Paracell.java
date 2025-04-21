// ==== Package ==== :
package World.model.dynamics;

// ==== General ==== :
import java.awt.Rectangle;

import Math.Vector;

import java.awt.Point;

import World.model.templates.CellTemplate;
import World.model.statics.Supercell;

// ==== Interfaces ==== :
import World.api.*;



/*  Paracells instances are complementary cells for dynamic scenarios
 * 
 */
public class Paracell extends Supercell implements Interactive, LoopIntegration{

    // ==== Fields ==== :

    public Dynamic dynamism = new Dynamic();



    // ==== Interfaces ==== :

    // Interactive
    @Override
    public boolean interact(){
        // W.I.P.
        return false;
    }

    // LoopIntegration
    @Override
    public void update( long time ){
        this.dynamism.update(time);
    }



    // ==== Methods ==== :

    public void setSweepers( boolean acc, boolean vel, boolean dis ){
        this.dynamism.setSweepers(acc, vel, dis);
    }
    public void sweep(){
        this.dynamism.sweep();
    }
    public void sweep( boolean acc, boolean vel, boolean dis ){
        this.dynamism.sweep( acc, vel, dis );
    }

    public void addAcceleration( Vector vector ){
        this.dynamism.addAcceleration( vector );
    }
    public void addVelocity( Vector vector ){
        this.dynamism.addAcceleration( vector );
    }
    public void addDisplacement( Vector vector ){
        this.dynamism.addDisplacement( vector );
    }



    // ==== Constructors ==== :

    public Paracell( Rectangle rectangle, CellTemplate traits ){
        super( new Point( rectangle.x, rectangle.y), traits );
        this.width = rectangle.width;
        this.height = rectangle.height;
    }
    public Paracell( int x, int y, int width, int height, CellTemplate traits ){
        super( new Point( x, y ), traits );
        this.width = width;
        this.height = height;
    }
    public Paracell( double x, double y, double width, double height, CellTemplate traits ){
        super( new Point( (int)(x), (int)(y) ), traits );
        this.width = (int)(width);
        this.height = (int)(height);
    }
}
