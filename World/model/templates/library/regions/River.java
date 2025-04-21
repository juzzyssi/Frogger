// ==== Package ==== :
package World.model.templates.library.regions;

// ==== General ==== :
import World.model.templates.RegionTemplate;
import World.model.templates.library.cells.Water;



public class River extends RegionTemplate {

    // ==== Constructors ==== :

    public River(){
        super( new Water() );
    }
}

