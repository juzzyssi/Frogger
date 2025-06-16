// ==== Package ==== :
package Model.model.templates.statics.region.stampedes;

import Model.model.statics.Terrain;
import Model.model.statics.primitives.Tile;
import Model.model.templates.interactives.subcategories.Vehicle;
import Model.model.templates.statics.region.subcategories.Stampede;
import Model.model.templates.statics.tile.ground.Road;

import java.rmi.NoSuchObjectException;
import java.util.Collection;

import Util.random.RandomObject;
import Util.random.RandomSet;

// ==== Exceptions ==== :
import Engine.api.management.exceptions.IllegalApiParameterException;
import Math.Vector;
import Util.TerrainAssociativeMutationException;

import java.lang.reflect.InvocationTargetException;

import Model.exceptions.world.OutOfBoundsException;

public abstract class Highway extends Stampede{

    // ==== Constructors ==== :
    public Highway( Collection<Vector> vectors, Terrain terrain, RandomSet< Class<? extends Tile> > subCellLiterals, RandomSet< Class<? extends Vehicle> > vehicles ) throws NoSuchObjectException, NoSuchMethodException, IllegalArgumentException, UnsupportedOperationException, InstantiationException, IllegalAccessException, InvocationTargetException, OutOfBoundsException, IllegalApiParameterException, TerrainAssociativeMutationException{
        super( vectors, terrain, subCellLiterals, vehicles );
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
            Light.vehicles = null;
        }

        // ==== Constructors ==== :

        public Light( Collection<Vector> vectors, Terrain terrain ) throws NoSuchObjectException, NoSuchMethodException, IllegalArgumentException, UnsupportedOperationException, InstantiationException, IllegalAccessException, InvocationTargetException, OutOfBoundsException, IllegalApiParameterException, TerrainAssociativeMutationException {
            super(vectors, terrain, Light.subCellLiterals, Light.vehicles);
        }
    }
}
