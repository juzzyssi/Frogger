// ==== Package ==== :
package Model.model.statics.primitives;

// ==== General ==== :
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Util.Family;
import Util.threads.ThreadElement;

import Math.Vector;

// ==== Interfaces ==== :
import Engine.api.management.ifaces.ApiBindable;
import Engine.api.management.primitives.ApiManager;
import Engine.api.components.Renderable;

import Model.api.Traversable;
import Model.model.interactives.api.Interactivity;
import Model.model.statics.Terrain;

// ==== Exceptions ==== :
import Util.TerrainAssociativeMutationException;
import Util.threads.IllegalOrderException;

import Engine.api.management.exceptions.IllegalApiParameterException;



public abstract class Tile extends Rectangle implements Renderable, ApiBindable, Interactivity{

    // ==== Fields ==== :

    // *** Concretes *** :
    public final static Dimension BLOCK = new Dimension(56, 56);

    protected static HashMap< Integer, List<Class<?>> > DEAFULT_ORDER;
    static{
        HashMap< Integer,  List<Class<?>> > temp = new HashMap<>();        
        
        ArrayList<Class<?>> order_0 = new ArrayList<>();
        order_0.add( Renderable.class );
        order_0.add( Traversable.class );

        temp.put( 0, order_0 );

        Tile.DEAFULT_ORDER = temp;
    }

    // *** Instances *** :
    private Family family;

    // Api fields:
    protected ApiManager<Tile> manager;

    // Traversability fields:
    protected boolean traversable;

    // ==== Interfaces ==== :

    // Api bindable:
    @Override
    public <T> ThreadElement<T> toThreadElementOf( Class<T> clazz ) throws IllegalApiParameterException{
        return this.manager.getAs( clazz );
    }

    // ==== Methods ==== :

    // *** Instances *** :

    // ( I.M.S. 0 : Generic auxiliaries )

    //  All the following methods share a O( 1 ).
    public boolean isTraversable(){
        return this.traversable;
    }
    
    @Override
    public String toString(){
        return String.format("Tile[ x=%d, y=%d, width=%d, height=%d, family=%s ]",
        this.x, this.y, this.width, this.height,
        this.family.toString()
        );
    }

    // See: Math
    public Vector toVector(){
        return new Vector( (long) this.x, (long) this.y );
    }

    // See: Util 
    public Family getFamily(){
        return this.family;
    }

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