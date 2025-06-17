// ==== Package ==== :
package Model.model.primitives.interactives;

// ==== Generals ==== :
import java.awt.Dimension;
import java.awt.Rectangle;

import Math.Vector;
// ==== Interfaces ==== :
import Engine.api.components.Renderable;
import Engine.api.management.ifaces.ApiBindable;
import Model.model.primitives.dynamics.Dynamic;



public abstract class Toy extends Dynamic implements Renderable, Interactive, ApiBindable {

    // ==== Fields ==== :

    /* INSTANCES: */
    protected Dimension hitbox;

    // ==== Methods ==== :

    /* INSTANCES: */
    public Rectangle getHitbox() {
        Vector disp = this.getDisplacement();
        return new Rectangle( (int) disp.get(0), (int) disp.get(1), this.hitbox.width, this.hitbox.height );
    }

    // ==== Constructors ==== :
    public Toy( Vector vector, Dimension hitbox ) {
        super( vector );
        this.hitbox = hitbox;
    }
}
