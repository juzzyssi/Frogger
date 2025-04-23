// ==== Package ==== :
package World.model.statics;

// ==== General ==== :
import java.awt.Graphics;
import java.awt.Point;

import World.model.World;

import Graphics.Camera;
import Math.Vector;

// ==== Interfaces ==== :
import World.api.engine.Renderable;
import World.api.template.CellTemplateAccessibility;



/*  General Documentation:
 *
 *  SuperCell instances introduce common fields of variance among Cell instances (such as traversability or visuals).
 *  They inherit their properties from a single source through an interface (CellTemplateAccessibility); this is to avoid
 *  unnecessary memory weight.
 */
public class Supercell extends Cell implements Renderable{

    // ==== Fields ==== :

    // Instances:
    public CellTemplateAccessibility traits;



    // ==== Interfaces ==== :

    // Renderable:
    @Override
    public void render( Graphics g, Camera camera ){
        /*  Supercell instances' rendering process simulate selective displayal by transformating their relative anchor position
        to the intended Camera instance's "frame". */

        /* Determines displacement */
        Vector cameraPosition = camera.getPosition();

        if( !this.hasMember( World.class ) ){
            throw new IllegalArgumentException(""+this.toString()+" has no World instance associative");
        }

        // world origin will always be (0, 0); this is just decorative
        World world = this.getFamilyMember( World.class );

        /* Temp fix */
        int displacedX = (int) ( this.displacement.getX() - (cameraPosition.getX() - world.x) );
        int displacedY = (int) ( this.displacement.getY() - (cameraPosition.getY() - world.y) );

        g.drawImage(
            this.traits.getImage(),
            displacedX, displacedY,
            this.width, this.height,
            null
        );
    }

    // Traversable:

    /* Traits used to determine "event's" outcomes */
    @Override
    public int getIdentity(){
        return this.traits.getIdentity();
    }
    @Override
    public boolean getTraversability(){
        return this.traits.getTraversability();
    }
    @Override
    public int getEffect(){
        return this.traits.getEffect();
    }



    // ==== Methods ==== :

    // Instances:
    @Override                                                       // I.M.S. 0 ( generic utility )
    public String toString(){
        return String.format("SuperCell[ pos=(x=%d, y=%d, width=%d, height=%d), identity=%d ]",
        this.x, this.y, this.width, this.height,
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