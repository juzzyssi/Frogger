// ==== Package ==== :
package World.model.templates.library.regions;

// ==== General ==== :
import World.model.templates.RegionTemplate;
import World.model.templates.library.cells.Grass;



public class Forest extends RegionTemplate {

    // ==== Constructors ==== :

    public Forest(){
        super( new Grass() );
    }
}
