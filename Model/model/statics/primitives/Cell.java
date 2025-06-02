// ==== Package ==== :
package Model.model.statics.primitives;

// ==== General ==== :
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Util.Family;
import Util.TerrainAssociativeMutationException;
import Util.threads.ThreadElement;

import Math.Vector;

// ==== Interfaces ==== :
import Engine.api.management.ifaces.ApiBindable;
import Engine.api.management.primitives.ApiManager;
import Engine.api.components.Renderable;

import Model.api.Traversable;
import Model.model.dynamics.api.Interactivity;
import Model.model.statics.Terrain;
// ==== Exceptions ==== :
import Engine.api.management.exceptions.IllegalApiParameterException;

import Util.threads.IllegalOrderException;



public abstract class Cell extends Rectangle implements Renderable, ApiBindable, Interactivity{

    // ==== Fields ==== :

    // Concrete:
    public final static Dimension BLOCK = new Dimension(56, 56);

    protected static HashMap< Integer, List<Class<?>> > DEAFULT_ORDER;
    static{
        HashMap< Integer,  List<Class<?>> > temp = new HashMap<>();        
        
        /* Order 0: */
        ArrayList<Class<?>> order_0 = new ArrayList<>();
        order_0.add( Renderable.class );
        order_0.add( Traversable.class );

        temp.put( 0, order_0 );

        Cell.DEAFULT_ORDER = temp;
    }

    // Instances:
    private Family family;

    /* Api fields */
    protected ApiManager<Cell> manager;

    /* Traversability fields */
    protected boolean traversable;

    // ==== Interfaces ==== :

    // Api bindable:

    @Override
    public <T> ThreadElement<T> toThreadElementOf( Class<T> clazz ) throws IllegalApiParameterException{
        return this.manager.getAs( clazz );
    }

    // ==== Methods ==== :

    // Instances:

    // I.M.S. 0 : Generic auxiliaries.

    public boolean isTraversable(){
        return this.traversable;
    }

    /*  Returns a String representation of this object;
     *  O( 1 ).
     */
    @Override
    public String toString(){
        return String.format("Cell[ x=%d, y=%d, width=%d, height=%d, family=%s ]",
        this.x, this.y, this.width, this.height,
        this.family.toString()
        );
    }

    /*  Returns this Cell's family;
     *  O( 1 ).
     */
    public Family getFamily(){
        return this.family;
    }

    public Vector toVector(){
        return new Vector( (long) this.x, (long) this.y );
    }

    // ==== Constructors ==== :

    public Cell( Terrain parent, Vector pos, Map<Integer, List<Class<?>>> order, boolean traversable ) throws IllegalOrderException{
        super( (int) pos.get( Terrain.X ), (int) pos.get( Terrain.Y ), Cell.BLOCK.width, Cell.BLOCK.height );
        this.family = new Family( this );
        this.manager = new ApiManager<>( order, this );
        
        try{
            this.family.adopt( parent );    
        } catch( TerrainAssociativeMutationException e ) {
            // Shouldn't happen
        }
        this.traversable = traversable;
    }
}