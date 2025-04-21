// ==== Package ==== :
package World.model.templates;

// ==== General ==== :
import java.awt.Image;

// ==== Interfaces ==== :
import World.api.CellTemplateAccessibility;



/*  CellTemplate instances manage the visual and sonic properties of a Supercell instance. Each instance represents a single set of 
 *  "attributes" that are referrenced across the program, for efficiency reasons.
 */
public abstract class CellTemplate implements CellTemplateAccessibility{
    
    // ==== Fields ==== :

    // Instances:
    protected Image image;

    public int identity;
    public boolean traversability;
    public int effect;
    public boolean anchorability;

    // Concretes:
    public static final int VANAL  = 0, LETHAL = 1, WIN = 2;



    // ==== Interfaces ==== :

    // GraphicalTemplate:
    @Override
    public Image getImage(){
        return this.image;
    }

    public int getIdentity(){
        return this.identity;
    }

    public boolean getAnchorability(){
        return this.anchorability;
    }

    public boolean getTraversability(){
        return this.traversability;
    }

    public int getEffect(){
        return this.effect;
    }



    // ==== Constructors ==== :

    public CellTemplate( Image image ){                      // I.C.S. 0 ( even odds )
        this.image = image;
    }
}
