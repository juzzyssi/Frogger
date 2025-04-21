// ==== Package ==== :
package World.model.statics;

// ==== General ==== :
import java.awt.Graphics;
import java.awt.Point;

import World.model.World;

import Graphics.Camera;

// ==== Interfaces ==== :
import World.api.Renderable;
import World.api.AudioEmissor;
import World.api.CellTemplateAccessibility;



/* SuperCell instances extend Cell instances fields to implement native "traits", such as the appearance, sound, and behavior of a Cell.
 */
public class Supercell extends Cell implements Renderable, AudioEmissor{

    // ==== Fields ==== :

    // Instances:
    private CellTemplateAccessibility traits;



    // ==== Interfaces ==== :

    // Renderable:
    @Override
    public void render( Graphics g, Camera camera ){
        /*  Supercell instances' rendering process simulate selective displayal by transformating their relative position from the family's
         *  World instance to the intended Camera instance "frame".
         */

        if( this.hasMember(World.class) ){

            /* Determines the dis */
            Point cameraPosition = camera.getPosition();
            World parentWorld = this.getFamilyMember( World.class );

            int displacedX = this.x - (cameraPosition.x - parentWorld.anchor.x);
            int displacedY = this.y - (cameraPosition.y - parentWorld.anchor.y);

            g.drawImage(
                this.traits.getImage(),
                displacedX, displacedY,
                this.width, this.height,
                null
            );
        }
        else{
            throw new IllegalArgumentException("Family of "+this.toString()+" has no \"World\" instance");
        }
    }

    // AudioEmissor:
    @Override
    public void play(){
        // W.I.P: plays an object's native sound.
    }



    // ==== Methods ==== :

    // Instances:
    @Override                                                       // I.M.S. 0 ( generic utility )
    public String toString(){
        return String.format("SuperCell[ pos=(x=%d, y=%d, width=%d, height=%d), parent=%s, identity=%d ]",
        this.x, this.y, this.width, this.height,
        this.hasMember( Region.class ) ? this.getFamilyMember( Region.class ) : this.getFamilyMember( World.class ),
        this.traits.getIdentity()
        );
    }

    public void inheritPhysicalTraits( CellTemplateAccessibility traits ){   // I.M.S. 2 ( traits modification )
        this.traits = traits;
    }



    // ==== Constructors ==== :

    public Supercell( Point pos, CellTemplateAccessibility traits ){
        super( pos );
        this.traits = traits;
    }
    public Supercell( int x, int y, CellTemplateAccessibility traits ){
        super( x, y );
        this.traits = traits;
    }
    public Supercell( double x, double y, CellTemplateAccessibility traits ){
        super( x, y );
        this.traits = traits;
    }
}