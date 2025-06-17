// ==== Package ==== :
package Model.model.subprimitives.statics.tiles;

import Math.Vector;

// ==== Interfaces ==== :
import java.util.List;
import java.util.Map;

// ==== Exceptions ==== :
import Util.threads.IllegalOrderException;

import Model.model.primitives.interactives.Interactive;
import Model.model.primitives.statics.Terrain;
import Model.model.primitives.statics.Tile;
import Model.model.subprimitives.interactives.Entity;



public abstract class Abyss extends Tile{
    
    // ==== Fields ==== :

    /* INSTANCES: */
    public final static boolean TRAVERSABILITY = true;

    // ==== Constructors ==== :

    public Abyss( Terrain parent, Vector pos, Map<Integer, List<Class<?>>> order ) throws IllegalOrderException {
        super(parent, pos, order, Ground.TRAVERSABILITY );
    }

    // ==== Interactions ==== :

    @Override
    public void interact( Interactive object ) throws UnsupportedInteractionException {
        if( object instanceof Entity ) {
            Entity entity = (Entity) object;

            if( this.contains( entity.getCenter().toPoint2D() ) && !entity.getImmunity( Abyss.class ) ) {
                entity.defeat();
            }
        }

        throw new UnsupportedInteractionException();
    }
}
