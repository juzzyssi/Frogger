// ==== Package ==== :
package Model.model.statics.primitives;

// ==== General ==== :
import java.awt.Rectangle;
import java.lang.reflect.Constructor;

import java.util.ArrayList;
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

    // *** Instances *** :
    private Set<Tile> tiles;
    
    private Family family;
    
    public Rectangle container;

    // *** Concretes *** :
    protected RandomSet< Class<? extends Tile> > subCellLiterals;
    protected RandomSet< Class<? extends Toy> > toys;

    // ==== Methods ==== :

    // *** Instances *** :

    // ( I.M.S. 0 : getters & setters )

    /*  Updates the region's container to contain all its current tiles: O( n )
     */
    public void updateContainer(){
        this.container.setBounds( Terrain.toRectangle( this.tiles ) );
    }

    // O( 1 )
    public Rectangle getContainer(){
        return this.container;
    }

    // O( 1 ) ( see: Util )
    public Family getFamily(){
        return this.family;
    }

    // ( I.M.S. 1 : mutators )

    // O( 1 )
    public void remove( Tile tile ) {
        this.tiles.remove( tile );
    }

    /*  Mutates all the tile instances present at the given collection of vectors to match its own "sub-cell" sets: O( n )
     */
    public void paint( Terrain terrain, Collection<Vector> vectors ) throws NoSuchMethodException, NoSuchObjectException, IllegalArgumentException, UnsupportedOperationException, OutOfBoundsException, IllegalApiParameterException, InstantiationException, IllegalAccessException, InvocationTargetException {
        /* Updating: */
        for( Vector vector : vectors ) {

            Constructor<? extends Tile> constructor = this.subCellLiterals.pickRandom( RandomSet.SET_TO_SPECIFIC_ODDS ).getDeclaredConstructor( Terrain.class, Vector.class );                
            
            Tile newSubCell = constructor.newInstance( terrain, vector );
            Tile oldCell = terrain.setAt( vector, newSubCell );
                
            if( oldCell != null ) {
                Region parent = oldCell.getFamily().getFamilyMember( Region.class );
                parent.remove( oldCell );

                if( parent.isEmpty() ){
                    terrain.singRegionOut( parent );
                }
            }
            this.tiles.add( newSubCell );
        }
    }

    // ( I.M.S. 2 : auxiliaries )

    // O( 1 )
    public boolean isEmpty() {
        return this.tiles.isEmpty();
    }

    // *** Concretes *** :

    // Returns a set of vectors that corresponds to the given set of tiles: O( n )
    public static Collection<Vector> toVectors( Collection<Tile> tiles ) {
        Collection<Vector> out = new ArrayList<>( tiles.size() );

        for( Tile tile : tiles ){
            out.add( tile.toVector() );
        }

        return out;
    }

    // ==== Constructors ==== :

    public Region( Set<Tile> tiles, Terrain terrain, RandomSet< Class<? extends Tile> > subCellLiterals, RandomSet< Class<? extends Toy> > toys ) throws NoSuchObjectException, NoSuchMethodException, IllegalArgumentException, UnsupportedOperationException, InstantiationException, IllegalAccessException, InvocationTargetException, OutOfBoundsException, IllegalApiParameterException, TerrainAssociativeMutationException {
        this.tiles = new HashSet<>();
        this.family = new Family( this );

        this.subCellLiterals = subCellLiterals;
        this.toys = toys;

        this.family.adopt( terrain );
        terrain.singRegionUp( this );

        this.paint( terrain, Region.toVectors( tiles ) );
    }
}
