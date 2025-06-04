// ==== Package ==== :
package Model.model;

// ==== Generals ==== :
import Model.model.statics.Terrain;
import Model.model.statics.primitives.Tile;
import Model.model.statics.primitives.Region;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import java.util.List;
import java.util.Map;

import Util.Family;

import Graphics.Camera;

// ==== Interfaces ==== :
import Engine.api.management.*;

// ==== Exceptions ==== :
import java.lang.reflect.InvocationTargetException;



public class World{

    // ==== Generic Fields ==== :

    // *** Instances *** :
    public Family family;

    // ==== Constructors ==== :

    public World( Dimension dimension, Class<? extends Region> clazz ) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{

        /* API: */
        this.renderRg = new RenderRegistery();
        this.continuumRg = new ContinuumRegistery();

        /* Dimensions are rounded to match a multiple of "Tile.WIDTH" */
        int worldInitWidth = dimension.width - (dimension.width % Tile.BLOCK.width);
        int worldInitHeight = dimension.height - (dimension.height % Tile.BLOCK.height);
        
        /* Terrain: */
        Rectangle plate = new Rectangle(0, 0, worldInitWidth, worldInitHeight);
        this.terrain = new Terrain( this.renderRg, this.continuumRg, plate );
        System.out.format( "The World's terrain has been set to: width=%d, height=%d%nrows=%d, columns=%d%n", worldInitWidth, worldInitHeight, this.terrain.getRows(), this.terrain.getColumns() );

        this.getTerrain().paint( terrain.toVectors(), clazz );

        /* Sandbox: W.I.P. */
    }



    // ======================== API Functionality ========================:

    // See: Engine > api > management > primitivess

    // ==== Fields ==== :

    // *** Instances *** :

    // Apis:
    protected RenderRegistery renderRg;
    protected ContinuumRegistery continuumRg;

    // Continuum integration:
    private boolean updated = false;
    public long time = System.nanoTime();

    // ==== Interfaces ==== :

    // *** Instances *** :

    // Continuum integration:
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
            this.continuumRg.checkIn( time );
            this.terrain.checkOut(time, null);
            
            this.continuumRg.executeRemovalQueue();
            this.renderRg.executeRemovalQueue();

            this.updated = false;
        }
    }

    // ==== Methods ===== :

    // ( I.M.S. 0 : generic auxiliaries )
    public RenderRegistery getRenderApi() {
        return this.renderRg;
    }

    public boolean isUpdated() {
        return !this.updated;
    }


    // ======================== Terrain Functionality ========================:

    // See: Model > model > statics

    // ==== Fields ==== :

    // *** Instances *** :
    protected Terrain terrain;
    public Map< Class<? extends Region>, List<? extends Region> > regions;

    // ==== Methods ==== :

    // *** Instances *** :
    public Terrain getTerrain() {
        return this.terrain;
    }
}
