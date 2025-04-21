// ==== Package ==== :
package World.model.templates;

// ==== General ==== :
import java.util.ArrayList;

import Util.RandomSet;
import World.api.CellTemplateAccessibility;
import World.api.RegionTemplateAccessibility;



/* Templates of Region instances */
public abstract class RegionTemplate extends RandomSet<CellTemplate> implements RegionTemplateAccessibility{

    // ==== Interfaces ===== :

    // TemplateAccessibilitty:
    @Override
    public CellTemplateAccessibility pickCellTemplateAccessibility( int instructive ){
        return this.pickRandom( instructive );
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
