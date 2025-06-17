// ==== Package ==== :
package Model.model.templates.statics.region.stampedes;

import Model.model.primitives.interactives.SandBox;
import Model.model.primitives.interactives.Toy;
import Model.model.primitives.statics.Terrain;
import Model.model.primitives.statics.Tile;
import Model.model.subprimitives.interactives.Vehicle;
import Model.model.subprimitives.statics.region.Stampede;
import Model.model.templates.interactives.vehicles.Car;
import Model.model.templates.statics.tile.ground.Road;

import java.rmi.NoSuchObjectException;
import java.util.Collection;
import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.List;

import Util.random.RandomObject;
import Util.random.RandomSet;

// ==== Exceptions ==== :
import Engine.api.management.exceptions.IllegalApiParameterException;
import Math.Vector;
import Util.TerrainAssociativeMutationException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import Model.exceptions.world.OutOfBoundsException;

public abstract class Highway extends Stampede{

    // ==== Constructors ==== :
    public Highway( Collection<Vector> vectors, SandBox sandbox, Terrain terrain, RandomSet< Class<? extends Tile> > subCellLiterals, RandomSet< Class<? extends Vehicle> > vehicles ) throws NoSuchObjectException, NoSuchMethodException, IllegalArgumentException, UnsupportedOperationException, InstantiationException, IllegalAccessException, InvocationTargetException, OutOfBoundsException, IllegalApiParameterException, TerrainAssociativeMutationException{
        super( vectors, sandbox, terrain, subCellLiterals, vehicles );
    }

    // ==== Inner classes ==== :

    public static class Light extends Highway {
        
        // ==== Fields ==== :

        protected static RandomSet< Class<? extends Tile> > subCellLiterals;
        static {
            Light.subCellLiterals = new RandomSet<>();
            Light.subCellLiterals.add( new RandomObject<Class<? extends Tile>>( Road.Clean.class,  RandomSet.STANDARD_ODDS ) );
        }

        protected static RandomSet< Class<? extends Vehicle> > vehicles;
        static {
            Light.vehicles = new RandomSet<>();
            Light.vehicles.add( new RandomObject<Class<? extends Vehicle>>( Car.class, RandomSet.STANDARD_ODDS ) );
        }

        // ==== Interfaces ==== :

        @Override
        public void generateToys( SandBox sandbox ) throws NoSuchMethodException, SecurityException, IllegalArgumentException, EmptyStackException, InstantiationException, IllegalAccessException, InvocationTargetException {

            System.out.println( "a" );
            if( this.toysLiterals != null ) {
                Constructor<? extends Toy> cons = this.toysLiterals.pickRandom( RandomSet.SET_TO_SPECIFIC_ODDS ).getDeclaredConstructor( List.class );

                for( int y = this.container.y; y < this.container.y + this.container.height; y += Tile.BLOCK.height ) {
                    Vector in = new Vector( this.container.x - Car.LENGHT_RANGE[ 1 ].width, y );
                    Vector out = new Vector( this.container.x + this.container.width + Car.LENGHT_RANGE[ 1 ].width, y );

                    List<Vector> pVectors = new LinkedList<>();
                    pVectors.add( in );
                    pVectors.add( out );

                    Toy toy = cons.newInstance( pVectors );
                    sandbox.add(toy);
                    this.toys.add(toy);
                }
            }
        }

        // ==== Constructors ==== :

        public Light( Collection<Vector> vectors, SandBox sandbox, Terrain terrain ) throws NoSuchObjectException, NoSuchMethodException, IllegalArgumentException, UnsupportedOperationException, InstantiationException, IllegalAccessException, InvocationTargetException, OutOfBoundsException, IllegalApiParameterException, TerrainAssociativeMutationException {
            super(vectors, sandbox, terrain, Light.subCellLiterals, Light.vehicles);
        }
    }
}
