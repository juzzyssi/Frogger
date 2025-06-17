package Model.model.subprimitives.statics.region;

import java.lang.reflect.InvocationTargetException;
import java.rmi.NoSuchObjectException;
import java.util.Collection;

import Engine.api.management.exceptions.IllegalApiParameterException;
import Math.Vector;
import Model.exceptions.world.OutOfBoundsException;
import Model.model.primitives.interactives.SandBox;
import Model.model.primitives.statics.Region;
import Model.model.primitives.statics.Terrain;
import Model.model.primitives.statics.Tile;
import Model.model.subprimitives.interactives.Vehicle;
import Util.TerrainAssociativeMutationException;
import Util.random.RandomSet;

public abstract class Stampede extends Region{

    // ==== Constructors ==== :

    public Stampede( Collection<Vector> vectors, SandBox sandbox, Terrain terrain, RandomSet< Class<? extends Tile> > subCellLiterals, RandomSet< Class<? extends Vehicle> > vehicles) throws NoSuchObjectException, NoSuchMethodException, IllegalArgumentException, UnsupportedOperationException, InstantiationException, IllegalAccessException, InvocationTargetException, OutOfBoundsException, IllegalApiParameterException, TerrainAssociativeMutationException {
        super(vectors, sandbox, terrain, subCellLiterals, vehicles != null ? new RandomSet<>( vehicles.toArrayList() ) : null );
    }

}
