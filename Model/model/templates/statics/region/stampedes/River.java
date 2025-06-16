// ==== Package ==== :
package Model.model.templates.statics.region.stampedes;

import Model.model.statics.Terrain;
import Model.model.statics.primitives.Tile;
import Model.model.templates.interactives.subcategories.Vehicle;
import Model.model.templates.statics.region.subcategories.Stampede;
import Model.model.templates.statics.tile.abyss.Water;

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

public abstract class River extends Stampede {

    // ==== Constructors ==== :
    public River( Collection<Vector> vectors, Terrain terrain, RandomSet< Class<? extends Tile> > subCellLiterals, RandomSet< Class<? extends Vehicle> > vehicles ) throws NoSuchObjectException, NoSuchMethodException, IllegalArgumentException, UnsupportedOperationException, InstantiationException, IllegalAccessException, InvocationTargetException, OutOfBoundsException, IllegalApiParameterException, TerrainAssociativeMutationException{
        super( vectors, terrain, subCellLiterals, vehicles );
    }

    // ==== Inner classes ==== :

    public static class Calm extends River {
        
        // ==== Fields ==== :

        /* CONCRETES: */
        protected static RandomSet< Class<? extends Tile> > subCellLiterals;
        static{
            Calm.subCellLiterals = new RandomSet<>();
            Calm.subCellLiterals.add( new RandomObject<Class<? extends Tile>>( Water.Current.class, RandomSet.STANDARD_ODDS) );
        }

        protected static RandomSet< Class<? extends Vehicle> > interactives;
        static{
            Calm.interactives = null;
        }

        // ==== Methods ==== :

        @Override
        /* Implements toy generation */
        public void paint( Terrain terrain, Collection<Vector> vectors ) throws NoSuchMethodException, NoSuchObjectException, IllegalArgumentException, UnsupportedOperationException, OutOfBoundsException, IllegalApiParameterException, InstantiationException, IllegalAccessException, InvocationTargetException {
            super.paint( terrain, vectors );

            if( this.toys != null ) {
                
            }
        }
        
        // ==== Constructors ==== :

        public Calm( Collection<Vector> vectors, Terrain terrain ) throws NoSuchObjectException, NoSuchMethodException, IllegalArgumentException, UnsupportedOperationException, InstantiationException, IllegalAccessException, InvocationTargetException, OutOfBoundsException, IllegalApiParameterException, TerrainAssociativeMutationException {
            super( vectors, terrain, Calm.subCellLiterals, Calm.interactives );
        }
    }
}
