// ==== Package ==== :
package Engine.api.components;

// ==== General ==== :
import Graphics.Camera;

import java.awt.Graphics;



/*  Renderable instances handle swing's drawing functionality to render visual elements.
 */
public interface Renderable{

    // ==== Methods ==== :
    
    /* Request to "draw" an object's visuals */
    public void render( Graphics g, Camera camera );
    
}
