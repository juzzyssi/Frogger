package Model.model.templates.statics.region.subcategories;

import java.lang.reflect.InvocationTargetException;
import java.rmi.NoSuchObjectException;
import java.util.Collection;

import Engine.api.management.exceptions.IllegalApiParameterException;
import Math.Vector;
import Model.exceptions.world.OutOfBoundsException;
import Model.model.statics.Terrain;
import Model.model.statics.primitives.Region;
import Model.model.statics.primitives.Tile;
import Model.model.templates.interactives.subcategories.Vehicle;
import Util.TerrainAssociativeMutationException;
import Util.random.RandomSet;

public abstract class Stampede extends Region{

    // ==== Constructors ==== :

    public Stampede( Collection<Vector> vectors, Terrain terrain, RandomSet< Class<? extends Tile> > subCellLiterals, RandomSet< Class<? extends Vehicle> > vehicles) throws NoSuchObjectException, NoSuchMethodException, IllegalArgumentException, UnsupportedOperationException, InstantiationException, IllegalAccessException, InvocationTargetException, OutOfBoundsException, IllegalApiParameterException, TerrainAssociativeMutationException {
        super(vectors, terrain, subCellLiterals, vehicles != null ? new RandomSet<>( vehicles.toArrayList() ) : null );
    }

}
