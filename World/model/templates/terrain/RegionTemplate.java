// ==== Package ==== :
package World.model.templates.terrain;

// ==== General ==== :
import java.util.ArrayList;

import Util.RandomObject;
import Util.RandomSet;
import World.model.dynamics.Obstacle;

// ==== Interfaces ==== :
import World.api.template.CellTemplateAccessibility;
import World.api.template.RegionTemplateAccessibility;



/*  General Documentation:
 * 
 *  RegionTemplate (extension) instances perform similarly to CellTemplate "instances": they provide ("native") obstacles classes AND
 *  CellTemplate instances, sort of. They too implement the accesibility logic AND the "singular source of truth" logic.
 */
public abstract class RegionTemplate extends RandomSet<CellTemplate> implements RegionTemplateAccessibility{

    // ==== Fields ==== :

    /* This ArrayList includes Obstacle extension CLASSES that are native to a SuperRegion */
    protected RandomSet<Class<? extends Obstacle>> obstaclesClasses = new RandomSet<>();



    // ==== Interfaces ===== :

    // RegionTemplateAccessibilitty:
    @Override
    public CellTemplateAccessibility pickCellTemplateAccessibility( int instructive ){
        return this.pickRandom( instructive );
    }



    // ==== Methods ==== :

    protected void addObstacleClass( RandomObject<Class<? extends Obstacle>> clazz ){                    // I.M.S. 0 (obstacle class apending)
        this.obstaclesClasses.add( clazz );
    }
    protected void addObstacleClass( Class<? extends Obstacle> clazz ){
        this.obstaclesClasses.add( new RandomObject<>(clazz, RandomSet.STANDARD_ODDS) );
    }



    // ==== Constructors ==== :

    public RegionTemplate( ArrayList<?> inputList ){
        super( inputList );
    }
    public RegionTemplate( CellTemplate physicalCellSet ){
        super( physicalCellSet );
    }
    public RegionTemplate(){
        super();
    }

}
