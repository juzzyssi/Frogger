// ==== Package ==== :
package World.model.templates.library.regions;

// ==== General ==== :
import World.model.templates.RegionTemplate;
import World.model.templates.library.cells.Road;



public class Highway extends RegionTemplate {

    // ==== Constructors ==== :

    public Highway(){
        super( new Road() );
    }
}
