// ==== Package ==== :
package Model.model;

// ==== Generals ==== :
import Model.model.statics.Terrain;
import Model.model.statics.primitives.Cell;
import Model.model.statics.primitives.Region;

import java.util.List;
import java.util.Map;
import java.util.Set;

import java.awt.Dimension;
import java.awt.Graphics;

import Util.Family;

import java.lang.reflect.Constructor;

import Graphics.Camera;

import Math.Vector;

// ==== Interfaces ==== :
import Engine.api.management.*;

// ==== Exceptions ==== :
import java.lang.reflect.InvocationTargetException;


/*  Worlds are "god" classes that operate as both orchestrators and an interface between the game's "model" instances and the engine.
 *  World classes--for the most part--delegate "actions" to its "sub-components"
 * 
 */
public class World{

    // ==== Generic Conjugates ==== :

    //                                                          I.F.S. 0 (Instances | Association managing)
    public Family family; /* Holds singular related instances: camera */

    // ==== Constructors ==== :

    public World( Dimension dimension ){

        /* API: */
        this.renderRg = new RenderRegistery();
        this.continuumRg = new ContinuumRegistery();

        /* Dimensions are rounded to match a multiple of "Cell.WIDTH" */
        int worldInitWidth = dimension.width - (dimension.width % Cell.BLOCK.width);
        int worldInitHeight = dimension.height - (dimension.height % Cell.BLOCK.height);
        System.out.format( "The World's terrain has been set to: width=%d, height=%d%nrows=%d, columns=%d%n", worldInitWidth, worldInitHeight, this.terrain.getRows(), this.terrain.getColumns() );
        
        /* Terrain: */
        this.terrain = new Terrain( this.renderRg, this.continuumRg );

        /* Sandbox: */
    }



    // ======================== API Functionality ========================:

    /*  Note:
     * 
     *  This section aims covers matters related to a World's instance "application interface program" (API).
     *  Particularly, adressing issues such as layers of "Renderable" instances threads, or how a "ContinuumIntegration" instance
     *  interplays with other interfaces. 
     */

    // ==== Fields ==== :

    //                                                          I.F.S. 0 (Instances | API Managing)
    protected RenderRegistery renderRg;
    protected ContinuumRegistery continuumRg;

    /* Integer keys are used as collections-processing-order specifiers to provide layering complexity */

    private boolean updated = false;
    public long time = System.nanoTime();

    // ==== Interfaces Execution ==== :

    //                                                          I.M.S. 0 (Instances | Continuum Integration)
    public void checkIn( long time, Graphics g, Camera camera ) throws Exception{
        if( !this.updated ){
            this.updated = true;

            this.continuumRg.checkIn( time );
            this.terrain.checkIn(time, camera);

            this.renderRg.render( g, camera );
        }
    }
    
    public void checkOut( long time ) throws Exception{
        if( this.updated ){
            this.updated = false;

            this.continuumRg.checkIn( time );
            this.terrain.checkOut(time, null);
            
            this.continuumRg.executeRemovalQueue();
            this.renderRg.executeRemovalQueue();
        }
    }

    // ==== Methods ===== :

    public RenderRegistery getRenderApi() {
        return this.renderRg;
    }


    // ======================== Terrain Functionality ========================:

    /*  Note:
     * 
     */

    // ==== Fields ==== :

    //                                                          I.F.S. 1 (Instances | Renderable Managing)
    protected Terrain terrain;

    public Map< Class<? extends Region>, List<? extends Region> > regions;

    public final Vector anchor = new Vector(0 ,0);



    // ==== Methods ==== :

    // Instances:

    public void paint( Set<Cell> cells, Class<? extends Region> region ) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {     // I.M.S. 3 ()
        Constructor<? extends Region> constructor = region.getDeclaredConstructor( Set.class, Terrain.class );
        constructor.newInstance( cells, this );
    }
}
