// ==== Package ==== :
package World.model.templates.terrain;

// ==== General ==== :
import java.awt.Image;

import World.api.template.CellTemplateAccessibility;



/*  General Documentation:
 *  
 *  CellTemplate instances manage the visual and sonic properties of a Supercell instance.
 *  Each instance represents a SINGLE set of "attributes" that are referrenced / called across the program (for efficiency reasons).
 *  CellTemplate instances "share" the required fields through an interface (CellTemplateAccessibility) so that any extension of
 *  CellTemplate remains compatible with Superell instances.
 */
public abstract class CellTemplate implements CellTemplateAccessibility{
    
    // ==== Fields ==== :

    // Instances:
    protected Image image;

    /* Traversability required fields */
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
