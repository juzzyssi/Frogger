package Model.model.subprimitives.statics.region;

import java.lang.reflect.InvocationTargetException;
import java.rmi.NoSuchObjectException;
import java.util.Collection;

import Engine.api.management.exceptions.IllegalApiParameterException;
import Math.Vector;
import Model.exceptions.world.OutOfBoundsException;
import Model.model.primitives.interactives.SandBox;
import Model.model.primitives.interactives.Toy;
import Model.model.primitives.statics.Region;
import Model.model.primitives.statics.Terrain;
import Model.model.primitives.statics.Tile;
import Util.TerrainAssociativeMutationException;
import Util.random.RandomSet;

public abstract class Biome extends Region{

    // ==== Constructors ==== :

    public Biome( Collection<Vector> vectors, SandBox sandbox, Terrain terrain, RandomSet< Class<? extends Tile> > subCellLiterals, RandomSet< Class<? extends Toy> > toys) throws NoSuchObjectException, NoSuchMethodException, IllegalArgumentException, UnsupportedOperationException, InstantiationException, IllegalAccessException, InvocationTargetException, OutOfBoundsException, IllegalApiParameterException, TerrainAssociativeMutationException {
        super(vectors, sandbox, terrain, subCellLiterals, toys );
    }

}
