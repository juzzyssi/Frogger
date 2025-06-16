// ==== Package ==== :
package Model.model.templates.statics.region.biomes;

import Model.model.interactives.primitives.Toy;
import Model.model.statics.Terrain;
import Model.model.statics.primitives.Tile;
import Model.model.templates.statics.region.subcategories.Biome;
import Model.model.templates.statics.tile.ground.Grass;

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



public abstract class Forest extends Biome{

    // ==== Constructors ==== :

    public Forest( Collection<Vector> vectors, Terrain terrain, RandomSet< Class<? extends Tile> > subCellLiterals, RandomSet< Class<? extends Toy> > toys ) throws NoSuchObjectException, NoSuchMethodException, IllegalArgumentException, UnsupportedOperationException, InstantiationException, IllegalAccessException, InvocationTargetException, OutOfBoundsException, IllegalApiParameterException, TerrainAssociativeMutationException{
        super( vectors, terrain, subCellLiterals, toys );
    }

    // ==== Inner classes ==== :

    public static class Light extends Forest { 
        
        // ==== Fields ==== :

        protected static RandomSet< Class<? extends Tile> > subCellLiterals;
        static {
            Light.subCellLiterals = new RandomSet<>();
            Light.subCellLiterals.add( new RandomObject<Class<? extends Tile>>( Grass.Slimy.class,  RandomSet.STANDARD_ODDS) );
        }

        protected static RandomSet< Class<? extends Toy> > interactives;
        static{
            Light.interactives = null;
        }

        // ==== Constructors ==== :
        
        public Light( Collection<Vector> vectors, Terrain terrain ) throws NoSuchObjectException, NoSuchMethodException, IllegalArgumentException, UnsupportedOperationException, InstantiationException, IllegalAccessException, InvocationTargetException, OutOfBoundsException, IllegalApiParameterException, TerrainAssociativeMutationException {
            super( vectors, terrain, Light.subCellLiterals, Light.interactives );
        }
    }
}