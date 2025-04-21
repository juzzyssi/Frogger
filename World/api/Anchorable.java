// ==== Package ==== :
package World.api;

// ==== General ==== :
import java.awt.Point;



/*  Anchorable instances receive auxiliary support with coordinates system; they can handle "cell-based" operations with more ease.
 *  This is especially useful with dynamic "cell-based" logic. (such as "truncs")
 */
public interface Anchorable {

    // ==== Methods ==== :
    
    public void anchorTo( Object object );
    /* "Anchors" an instance to another's object "anchor" */

    public Point getAnchor();
    /* Returns the "anchor" Point of a given distance */

}
