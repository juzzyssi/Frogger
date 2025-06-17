// ==== Package ==== :
package Model.model.primitives.interactives;

// ==== Generals ==== :
import Engine.api.management.ContinuumRegistery;
import Engine.api.management.RenderRegistery;

import java.util.HashSet;

import Model.exceptions.world.OutOfBoundsException;
import Model.model.primitives.interactives.Interactive.UnsupportedInteractionException;
import Model.model.primitives.statics.Terrain;
import Model.model.primitives.statics.Tile;
// ==== Interfaces ==== :
import Engine.api.components.Continuous;

import java.util.Set;

// ==== Exceptions ==== :
import Engine.api.management.exceptions.IllegalApiParameterException;

import Util.threads.IllegalOrderException;



public class SandBox implements Continuous {

    // ==== Fields ==== :

    /* INTERACTIVES: */
    private Set<Toy> toys;
    private RenderRegistery renderRg;
    private ContinuumRegistery continuumRg;
    
    // private Set<ApiRegistery<?>> apis;

    private boolean updated;

    private Set<Toy> removalQueue;

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

    // ==== Methods ==== :

    /* INSTANCES: */
    public void add( Toy... toys ) {
        for( Toy toy : toys ) {
            this.toys.add( toy );

            try{
                this.renderRg.add( toy );
            } catch( IllegalApiParameterException e ) {
                // Nothing.
            }

            try{
                this.continuumRg.add( toy );
            } catch( IllegalApiParameterException e ) {
                // Nothing.
            }
        }
    }

    public <T extends Toy> void queueToyRemoval( T toy ) {
        if( this.toys.contains(toy) ) {
            this.removalQueue.add( toy );

            try{
                this.renderRg.queueRemoval( toy );
            } catch(IllegalApiParameterException e) {
                e.printStackTrace();                
            }

            try{
                this.continuumRg.queueRemoval( toy );
            } catch(IllegalApiParameterException e) {
                e.printStackTrace();                
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
        this.toys = new HashSet<>();

        this.updated = false;

        this.renderRg = renderApi;
        this.continuumRg = continuumApi;

        this.removalQueue = new HashSet<>();
    }
}
