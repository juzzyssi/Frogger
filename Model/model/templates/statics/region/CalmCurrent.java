// ==== Package ==== :
package Model.model.templates.statics.region;

import Model.model.interactives.primitives.Toy;
import Model.model.statics.Terrain;
import Model.model.statics.primitives.Tile;
import Model.model.statics.primitives.Region;
import Model.model.templates.statics.tile.WaterCurrent;

import java.rmi.NoSuchObjectException;
import java.util.Set;

import Util.random.RandomObject;
import Util.random.RandomSet;

// ==== Exceptions ==== :
import Engine.api.management.exceptions.IllegalApiParameterException;

import Util.TerrainAssociativeMutationException;

import java.lang.reflect.InvocationTargetException;

import Model.exceptions.world.OutOfBoundsException;

public class CalmCurrent extends Region {
    // ==== Fields ==== :

    protected static RandomSet< Class<? extends Tile> > subCellLiterals;
    protected static RandomSet< Class<? extends Toy> > interactives;
    static{
        /* Characteristic tiles / "terrain": */
        CalmCurrent.subCellLiterals = new RandomSet<>();
        CalmCurrent.subCellLiterals.add( new RandomObject<Class<? extends Tile>>( WaterCurrent.class,  0.5) );

        /* W.I.P. */
        CalmCurrent.interactives = null;
    }

    // ==== Constructors ==== :
    public CalmCurrent( Set<Tile> tiles, Terrain terrain ) throws NoSuchObjectException, NoSuchMethodException, IllegalArgumentException, UnsupportedOperationException, InstantiationException, IllegalAccessException, InvocationTargetException, OutOfBoundsException, IllegalApiParameterException, TerrainAssociativeMutationException{
        super( tiles, terrain, CalmCurrent.subCellLiterals, CalmCurrent.interactives );
    }
}
