// ==== Package ==== :
package Model.model.templates.statics.region;

import Model.model.interactives.primitives.Toy;
import Model.model.statics.Terrain;
import Model.model.statics.primitives.Tile;
import Model.model.statics.primitives.Region;
import Model.model.templates.statics.tile.WaterCurrent;

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

public class CalmRiver extends Region {
    // ==== Fields ==== :

    protected static RandomSet< Class<? extends Tile> > subCellLiterals;
    protected static RandomSet< Class<? extends Toy> > interactives;
    static{
        /* Characteristic tiles / "terrain": */
        CalmRiver.subCellLiterals = new RandomSet<>();
        CalmRiver.subCellLiterals.add( new RandomObject<Class<? extends Tile>>( WaterCurrent.class,  0.5) );

        /* W.I.P. */
        CalmRiver.interactives = null;
    }

    // ==== Constructors ==== :
    public CalmRiver( Collection<Vector> vectors, Terrain terrain ) throws NoSuchObjectException, NoSuchMethodException, IllegalArgumentException, UnsupportedOperationException, InstantiationException, IllegalAccessException, InvocationTargetException, OutOfBoundsException, IllegalApiParameterException, TerrainAssociativeMutationException{
        super( vectors, terrain, CalmRiver.subCellLiterals, CalmRiver.interactives );
    }
}
