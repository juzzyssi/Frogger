// ==== Package ==== :
package Model.model.templates.statics.tile.subcategories;

// ==== Generals ==== :
import Model.model.statics.primitives.Tile;
import Model.model.interactives.api.Interactive;
import Model.model.interactives.exceptions.UnsupportedInteractionException;
import Model.model.statics.Terrain;

import Math.Vector;

// ==== Interfaces ==== :
import java.util.List;
import java.util.Map;

// ==== Exceptions ==== :
import Util.threads.IllegalOrderException;



public abstract class Ground extends Tile{

    // ==== Fields ==== :

    /* INSTANCES: */
    public final static boolean TRAVERSABILITY = true;

    // ==== Interfaces ==== :

    // Interactivity:
    @Override
    public void interact( Interactive object ) throws UnsupportedInteractionException {
        throw new UnsupportedInteractionException();
    }
    
    // ==== Constructors ==== :

    public Ground( Terrain parent, Vector pos, Map<Integer, List<Class<?>>> order ) throws IllegalOrderException {
        super(parent, pos, order, Ground.TRAVERSABILITY );
    }
}
