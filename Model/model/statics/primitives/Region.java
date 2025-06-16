// ==== Package ==== :
package Model.model.statics.primitives;

// ==== General ==== :
import java.awt.Rectangle;
import java.lang.reflect.Constructor;

import java.util.HashSet;

import Math.Vector;
import Model.model.interactives.primitives.Toy;
import Model.model.statics.Terrain;
import Util.Family;
import Util.random.RandomSet;

// === Interfaces === :
import java.util.Set;
import java.util.Collection;

// === Exceptions === :
import java.lang.reflect.InvocationTargetException;
import java.rmi.NoSuchObjectException;

import Engine.api.management.exceptions.IllegalApiParameterException;

import Model.exceptions.world.OutOfBoundsException;

import Util.TerrainAssociativeMutationException;



public abstract class Region {

    // ==== Fields ==== :

    /* CONCRETES: */
    protected RandomSet< Class<? extends Tile> > subCellLiterals;
    protected RandomSet< Class<? extends Toy> > toys;

    /* INSTANCES: */
    private Set<Tile> tiles;
    private Family family;    
    public Rectangle container;

    // ==== Methods ==== :

    /* INSTANCES: */
    public Rectangle getContainer(){
        return this.container;
    }   // O( 1 )

    public Family getFamily(){
        return this.family;
    }   // O( 1 )

    public boolean isEmpty() {
        return this.tiles.isEmpty();
    }   // O( 1 )

    /*  Updates the region's container to contain all the current tiles.
     */
    public void updateContainer(){
        this.container.setBounds( Terrain.toRectangle( this.tiles ) );
    }   // O( n )

    public void remove( Tile tile ) {
        this.tiles.remove( tile );
    }   // O( 1 )

    /*  Mutates all the tile instances present at the given collection of vectors to match its own "sub-cell" sets. ()
     */
    public void paint( Terrain terrain, Collection<Vector> vectors ) throws NoSuchMethodException, NoSuchObjectException, IllegalArgumentException, UnsupportedOperationException, OutOfBoundsException, IllegalApiParameterException, InstantiationException, IllegalAccessException, InvocationTargetException {
        
        /* Terrain generation: */
        for( Vector vector : vectors ) {

            /* New-tile */
            Constructor<? extends Tile> constructor = this.subCellLiterals.pickRandom( RandomSet.SET_TO_SPECIFIC_ODDS ).getDeclaredConstructor( Terrain.class, Vector.class );                
            Tile newSubCell = constructor.newInstance( terrain, vector );

            Tile oldCell = terrain.set( newSubCell );

            try{
                newSubCell.getFamily().adopt( (Region) this );
            } catch( TerrainAssociativeMutationException e ) {
                // Nothing
            }
            
            // Clears the old Tile & erases the parent region if empty.
            if( oldCell != null ) {
                Region parent = oldCell.getFamily().getFamilyMember( Region.class );
                parent.remove( oldCell );

                if( parent.isEmpty() ){
                    terrain.singRegionOut( parent );
                }
            }

            // Congruency:
            if( !terrain.contains( this ) ) {
                terrain.singRegionUp( (Region) this );
            }

            try{
                this.getFamily().adopt( terrain );
            } catch( TerrainAssociativeMutationException e ) {
                // Nothing
            }

            this.tiles.add( newSubCell );
        }
    }

    // ==== Constructors ==== :

    public Region( Collection<Vector> vectors, Terrain terrain, RandomSet< Class<? extends Tile> > subCellLiterals, RandomSet< Class<? extends Toy> > toys ) throws NoSuchObjectException, NoSuchMethodException, IllegalArgumentException, UnsupportedOperationException, InstantiationException, IllegalAccessException, InvocationTargetException, OutOfBoundsException, IllegalApiParameterException, TerrainAssociativeMutationException {
        this.tiles = new HashSet<>();
        this.family = new Family( this );

        this.subCellLiterals = subCellLiterals;
        this.toys = toys;

        this.family.adopt( terrain );
        terrain.singRegionUp( this );

        this.paint( terrain, vectors );
    }
}
