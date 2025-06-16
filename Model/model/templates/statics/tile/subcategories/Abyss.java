// ==== Package ==== :
package Model.model.templates.statics.tile.subcategories;

// ==== Generals ==== :
import Model.model.statics.Terrain;
import Model.model.statics.primitives.Tile;
import Model.model.templates.interactives.subcategories.Entity;
import Math.Vector;

// ==== Interfaces ==== :
import java.util.List;
import java.util.Map;

import Model.model.interactives.api.Interactive;

// ==== Exceptions ==== :
import Util.threads.IllegalOrderException;

import Model.model.interactives.exceptions.UnsupportedInteractionException;



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
