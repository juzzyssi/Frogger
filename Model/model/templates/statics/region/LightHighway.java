// ==== Package ==== :
package Model.model.templates.statics.region;

// ==== Generals ==== :
import Model.model.dynamics.primitives.Interactive;
import Model.model.statics.Terrain;
import Model.model.statics.primitives.Cell;
import Model.model.statics.primitives.Region;
import Model.model.templates.statics.cell.CleanRoad;

import java.rmi.NoSuchObjectException;
import java.util.Set;

import Util.random.RandomObject;
import Util.random.RandomSet;

// ==== Exceptions ==== :
import Engine.api.management.exceptions.IllegalApiParameterException;

import Util.TerrainAssociativeMutationException;

import java.lang.reflect.InvocationTargetException;

import Model.exceptions.world.OutOfBoundsException;

public class LightHighway extends Region{

    // ==== Fields ==== :

    protected static RandomSet< Class<? extends Cell> > subCellLiterals;
    protected static RandomSet< Class<? extends Interactive> > interactives;
    static{
        /* Characteristic cells / "terrain": */
        LightHighway.subCellLiterals = new RandomSet<>();
        LightHighway.subCellLiterals.add( new RandomObject<Class<? extends Cell>>( CleanRoad.class,  0.5) );

        /* W.I.P. */
        LightHighway.interactives = null;
    }

    // ==== Constructors ==== :
    public LightHighway( Set<Cell> cells, Terrain terrain ) throws NoSuchObjectException, NoSuchMethodException, IllegalArgumentException, UnsupportedOperationException, InstantiationException, IllegalAccessException, InvocationTargetException, OutOfBoundsException, IllegalApiParameterException, TerrainAssociativeMutationException{
        super( cells, terrain, LightHighway.subCellLiterals, LightHighway.interactives );
    }
}
