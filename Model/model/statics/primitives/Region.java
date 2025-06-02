// ==== Package ==== :
package Model.model.statics.primitives;

// ==== General ==== :
import java.awt.Rectangle;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import java.rmi.NoSuchObjectException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import Engine.api.management.exceptions.IllegalApiParameterException;
import Math.Vector;
import Model.exceptions.world.OutOfBoundsException;
import Model.model.dynamics.primitives.Interactive;
import Model.model.statics.Terrain;
import Util.Family;
import Util.TerrainAssociativeMutationException;
import Util.random.RandomSet;



/*  General Documentation:
 *
 *  Region instances are meant to be "holoistic" managers of collections of Supercell instances.
 *  They ease the designation of "individual traits" and (might eventually intorduce) other sorts of functionalities.
 *  Region instances ARE NOT in charge of "terrain" instantiation (at all); rather, all collections of "Supercells" are delegated and
 *  Transformed from a "parent" World object.
 */
public abstract class Region { // Maybe implement an "AmbienceEmitter" interface later on

    // ==== Fields ==== :

    // Instances:
    private Set<Cell> cells;
    private Family family;
    
    public Rectangle container;

    // Concretes:
    protected RandomSet< Class<? extends Cell> > subCellLiterals;
    protected RandomSet< Class<? extends Interactive> > interactives;

    // ==== Methods ==== :

    // Instances:                                                                                           // I.M.S. 0 ()

    public void updateContainer(){
        this.container.setBounds( Terrain.toRectangle( this.cells ) );
    }

    public Rectangle getContainer(){
        return this.container;
    }
    public Family getFamily(){
        return this.family;
    }

    public void remove( Cell cell ) {
        this.cells.remove( cell );
    }

    public void paint( Terrain terrain, Collection<Vector> vectors ) throws NoSuchMethodException, NoSuchObjectException, IllegalArgumentException, UnsupportedOperationException, OutOfBoundsException, IllegalApiParameterException, InstantiationException, IllegalAccessException, InvocationTargetException {
        /* Updating: */
        for( Vector vector : vectors ) {

            Constructor<? extends Cell> constructor = this.subCellLiterals.pickRandom( RandomSet.SET_TO_SPECIFIC_ODDS ).getDeclaredConstructor( Terrain.class, Vector.class );                
            
            Cell newSubCell = constructor.newInstance( terrain, vector );
            Cell oldCell = terrain.setAt( vector, newSubCell );
                
            if( oldCell != null ) {
                Region parent = oldCell.getFamily().getFamilyMember( Region.class );
                parent.remove( oldCell );

                if( parent.isEmpty() ){
                    terrain.singRegionOut( parent );
                }
            }
            this.cells.add( newSubCell );
        }
    }

    public boolean isEmpty() {
        return this.cells.isEmpty();
    }

    public static Collection<Vector> toVectors( Collection<Cell> cells ) {
        Collection<Vector> out = new ArrayList<>( cells.size() );

        for( Cell cell : cells ){
            out.add( cell.toVector() );
        }

        return out;
    }



    // ==== Constructors ==== :

    public Region( Set<Cell> cells, Terrain terrain, RandomSet< Class<? extends Cell> > subCellLiterals, RandomSet< Class<? extends Interactive> > interactives ) throws NoSuchObjectException, NoSuchMethodException, IllegalArgumentException, UnsupportedOperationException, InstantiationException, IllegalAccessException, InvocationTargetException, OutOfBoundsException, IllegalApiParameterException, TerrainAssociativeMutationException {
        this.cells = new HashSet<>();
        this.family = new Family( this );

        this.subCellLiterals = subCellLiterals;
        this.interactives = interactives;

        this.family.adopt( terrain );
        terrain.singRegionUp( this );

        this.paint( terrain, Region.toVectors( cells ) );
    }

    /* if( !cells.isEmpty() ){

            this.family = new Family( this );
            this.family.adopt( ((Cell) cells.get(0)).getFamily().getFamilyMember( World.class ) );
            
            this.traits = traits;

            RandomSet<Class<? extends Cell>> regionalCellClasses = this.traits.getCellClasses(modCount);
            Vector cellVector = new Vector( 0, 0);
            World parentWorld = this.family.getFamilyMember( World.class );
            for( Traversable cell : cells ){

                cellVector.set( ((Cell) cell).getAnchor() );
                parentWorld.setAt( cellVector, regionalCellClasses.pickRandom( RandomSet.SET_TO_SPECIFIC_ODDS ), this );
            }

            this.container = Region.findContainer( this );
        }
        else{
            throw new EmptyStackException();
        } */
}
