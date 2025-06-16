// ==== Package ==== :
package Model.model.interactives.primitives;

// ==== Generals ==== :
import Engine.api.management.ContinuumRegistery;
import Engine.api.management.RenderRegistery;
import Engine.api.management.primitives.ApiManager;
import Engine.api.management.primitives.ApiRegistery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import Model.exceptions.world.OutOfBoundsException;
import Model.model.interactives.exceptions.UnsupportedInteractionException;
import Model.model.statics.Terrain;
import Model.model.statics.primitives.Tile;
import Util.threads.ThreadElement;

// ==== Interfaces ==== :
import Engine.api.components.Continuous;
import Engine.api.management.ifaces.ApiBindable;

import java.util.List;
import java.util.Set;

// ==== Exceptions ==== :
import Engine.api.management.exceptions.IllegalApiParameterException;

import Util.threads.IllegalOrderException;



public class SandBox implements Continuous, ApiBindable{

    // ==== Fields ==== :

    /* CONCRETES: */
    protected static HashMap< Integer, List<Class<?>> > DEAFULT_ORDER;
    static{
        HashMap< Integer,  List<Class<?>> > temp = new HashMap<>(); 
        // ==== //
        ArrayList<Class<?>> order_1 = new ArrayList<>();
        order_1.add( Continuous.class );

        temp.put( 1, order_1 );
        // ==== //
        SandBox.DEAFULT_ORDER = temp;
    }

    /* INTERACTIVES: */
    private Set<Toy> toys;
    private Set<ApiRegistery<?>> apis;
    
    private ContinuumRegistery continuumApi; // Shallow reference.
    private RenderRegistery renderApi;  // Shallow reference.

    private boolean updated;

    private Set<Toy> removalQueue;
    private ApiManager<SandBox> apiManager;

    // ==== Interfaces ==== :
    
    // Continuous:
    @Override
    public void checkIn( long time, Object... args ) {
        if( !this.updated ) {
            this.updated = true;
            this.play( (Terrain) args[0] );
        }
    }

    @Override
    public void checkOut( long time, Object... args ) {
        if( this.updated ) {
            this.updated = false;
            this.executeLocalRemovalQueue();
        }
    }

    // Api bindable:
    @Override
    public <T> ThreadElement<T> toThreadElementOf( Class<T> clazz ) throws IllegalApiParameterException {
        return apiManager.getAs( clazz );
    }

    // ==== Methods ==== :

    /* INSTANCES: */
    public void add( Toy... toys ) {
        for( Toy toy : toys ) {
            this.toys.add( toy );
            for( ApiRegistery<?> registery : this.apis ) {
                try{
                    registery.add( toy );
                } catch( IllegalApiParameterException e ) {
                    // Nothing.
                }
            }
        }
    }

    public <T extends Toy> void queueToyRemoval( T toy ) {
        if( this.toys.contains(toy) ) {
            this.removalQueue.add( toy );

            for( ApiRegistery<?> registery : this.apis ) {
                try {
                    registery.queueRemoval(toy);
                } catch (IllegalApiParameterException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void executeLocalRemovalQueue() {
        this.toys.removeAll( this.removalQueue );
        this.removalQueue.clear();
    }

    public void play( Terrain terrain ) {
        for( Toy prot : this.toys ) {

            /* toys interactions: */
            for( Toy ext : this.toys ) {
                if( prot != ext ) {
                    try{
                        prot.interact( ext );
                    } catch( UnsupportedInteractionException e ) {
                        // Nothing.
                    }
                }
            }

            /* terrain interaction: */
            Tile tile;
            try{
                tile = terrain.getAt( prot.getDisplacement() );
                
                try{
                    tile.interact( prot );
                } catch( UnsupportedInteractionException e1 ) {
                    try{
                        prot.interact( tile );
                    } catch( UnsupportedInteractionException e2 ) {
                        // Nothing.
                    }
                }
            } catch( OutOfBoundsException e1 ) {
                // Nothing.
            }
        }
    }

    // ==== Constructors ==== :

    public SandBox( ContinuumRegistery continuumApi, RenderRegistery renderApi ) throws IllegalOrderException {
        this.apiManager = new ApiManager<SandBox>( SandBox.DEAFULT_ORDER, this);
        this.toys = new HashSet<>();

        this.continuumApi = continuumApi;
        this.updated = false;
        try{
            this.continuumApi.add( this );
        } catch( IllegalApiParameterException e ) {
            e.printStackTrace();
        }
        this.renderApi = renderApi;

        this.apis = new HashSet<>();
        this.apis.add( this.renderApi );
        this.apis.add( this.continuumApi );

        this.removalQueue = new HashSet<>();
    }
}
