// ==== Package ==== :
package Model.model.statics.primitives;

// ==== General ==== :
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import Util.Family;

import Math.Vector;

// ==== Interfaces ==== :
import Engine.api.management.ifaces.ApiBindable;
import Engine.api.management.primitives.ApiManager;
import Engine.api.components.Renderable;

import Model.model.interactives.api.Interactive;
import Model.model.statics.Terrain;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import Util.threads.ThreadElement;

// ==== Exceptions ==== :
import Util.TerrainAssociativeMutationException;
import Util.threads.IllegalOrderException;

import Engine.api.management.exceptions.IllegalApiParameterException;



public abstract class Tile extends Rectangle implements Renderable, ApiBindable, Interactive{

    // ======== General Functionality ======== :

    // ==== Fields ==== :

    /* CONCRETES: */
    public final static Dimension BLOCK = new Dimension(56, 56);

    protected static HashMap< Integer, List<Class<?>> > DEAFULT_ORDER;
    static{
        HashMap< Integer,  List<Class<?>> > temp = new HashMap<>();        
        
        ArrayList<Class<?>> order_0 = new ArrayList<>();
        order_0.add( Renderable.class );

        temp.put( 0, order_0 );

        Tile.DEAFULT_ORDER = temp;
    }

    /* INSTANCES: */
    private Family family;
    protected ApiManager<Tile> manager;
    protected boolean traversable;

    // ==== Interfaces ==== :

    @Override /* Api bindable */
    public <T> ThreadElement<T> toThreadElementOf( Class<T> clazz ) throws IllegalApiParameterException{
        return this.manager.getAs( clazz );
    }

    // ==== Methods ==== :

    /* INSTANCES: */
    public boolean isTraversable(){
        return this.traversable;
    }   // O( 1 )

    public Vector toVector(){
        return new Vector( (long) this.x, (long) this.y );
    }   // O( n )

    public Family getFamily(){
        return this.family;
    }   // O( 1 )

    @Override
    public String toString() {
        return String.format( "Tile[ x=%d, y=%d, width=%d, height=%d, class=%s ]", this.x, this.y, this.width, this.height,this.getClass().getName() );
    }   // O( 1 )

    /* INSTANCES: */

    /*  Returns a set of correspondent vectors to each tile in the provided collection. 
     */
    public static Collection<Vector> toVectors( Collection<Tile> tiles ) {
        Collection<Vector> out = new ArrayList<>( tiles.size() );

        for( Tile tile : tiles ){
            out.add( tile.toVector() );
        }

        return out;
    }   // O( n )

    // ==== Constructors ==== :

    public Tile( Terrain parent, Vector pos, Map<Integer, List<Class<?>>> order, boolean traversable ) throws IllegalOrderException{
        super( (int) pos.get( Terrain.X ), (int) pos.get( Terrain.Y ), Tile.BLOCK.width, Tile.BLOCK.height );
        this.family = new Family( this );
        this.manager = new ApiManager<>( order, this );
        
        try{
            this.family.adopt( parent );    
        } catch( TerrainAssociativeMutationException e ) {
            // Shouldn't happen.
        }
        this.traversable = traversable;
    }
}