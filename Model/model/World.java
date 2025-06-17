// ==== Package ==== :
package Model.model;

import Model.model.primitives.interactives.SandBox;
import Model.model.primitives.statics.Region;
import Model.model.primitives.statics.Terrain;
import Model.model.primitives.statics.Tile;
import Model.model.templates.interactives.entities.PFrog;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import Util.threads.IllegalOrderException;
import Graphics.Camera;
import Math.Vector;

// ==== Interfaces ==== :
import Engine.api.management.*;
import Engine.user.User;

import java.util.List;
import java.util.Map;

// ==== Exceptions ==== :
import java.lang.reflect.InvocationTargetException;



public class World{

    // ==== Generic Fields ==== :

    /* INSTANCES: */
    private User user;

    public PFrog pFrog;

    // ==== Constructors ==== :

    public World( User user, Dimension dimension, Class<? extends Region> clazz ) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IllegalOrderException{

        /* API: */
        this.renderRg = new RenderRegistery();
        this.continuumRg = new ContinuumRegistery();


        /* TERRAIN: */
        int worldInitWidth = dimension.width - (dimension.width % Tile.BLOCK.width);
        int worldInitHeight = dimension.height - (dimension.height % Tile.BLOCK.height);
        
        Rectangle plate = new Rectangle(0, 0, worldInitWidth, worldInitHeight);
        this.terrain = new Terrain( this.renderRg, this.continuumRg, plate );
        System.out.format( "The World's terrain has been set to: width=%d, height=%d%nrows=%d, columns=%d%n", worldInitWidth, worldInitHeight, this.terrain.getRows(), this.terrain.getColumns() );

        this.getTerrain().paint( terrain.toVectors(), clazz, this.sandbox );

        // setting up the spawn:
        this.spawns = new LinkedList<>();
        Vector terrainVector = this.terrain.getDisplacement();
        Dimension terrainBlock = this.terrain.getBlock();
        this.spawns.add( Vector.add( this.terrain.getDisplacement(), this.terrain.snapToGrid( new Vector( (long) (this.terrain.getWidth() / 2) + terrainVector.get( 0 ), (long) (this.terrain.getHeight() - terrainBlock.getHeight()) + terrainVector.get( 1 ) ) ) ) );


        /* SANDBOX: */
        this.sandbox = new SandBox( this.continuumRg, this.renderRg );


        /* GENERALS: */
        this.user = user;
        this.pFrog = new PFrog( this.user, this.spawns.getFirst() );
        this.sandbox.add( this.pFrog );
    }



    // ======================== API Functionality ========================:

    // ==== Fields ==== :

    /* INSTANCES: */
    protected RenderRegistery renderRg;
    protected ContinuumRegistery continuumRg;

    // Continuum integration:
    private boolean updated = false;
    public long time = System.nanoTime();

    // ==== Interfaces ==== :

    /* INSTANCES: */

    // Continuum integration:
    public void checkIn( long time, Camera camera ) throws Exception{
        if( !this.updated ){
            this.updated = true;
            this.continuumRg.checkIn( time );
            
            this.terrain.checkIn( time, camera );
            this.sandbox.checkIn( time, this.terrain );
        }
    }
    
    public void checkOut( long time ) throws Exception{
        if( this.updated ){
            this.continuumRg.checkOut( time );
            
            this.terrain.checkOut( time, null );
            this.sandbox.checkOut( time, null );
            
            this.continuumRg.executeRemovalQueue();
            this.renderRg.executeRemovalQueue();

            this.updated = false;
        }
    }

    // Renderable:
    public void render( Graphics g, Camera camera ) {
        this.renderRg.render(g, camera);
    }

    // ==== Methods ===== :

    /* INSTANCES: */
    public RenderRegistery getRenderApi() {
        return this.renderRg;
    }

    public boolean isUpdated() {
        return !this.updated;
    }



    // ======================== Terrain ========================:

    // ==== Fields ==== :

    /* INSTANCES: */
    private LinkedList<Vector> spawns;

    protected Terrain terrain;

    public Map< Class<? extends Region>, List<? extends Region> > regions;

    // ==== Methods ==== :

    /* INSTANCES: */
    public Terrain getTerrain() {
        return this.terrain;
    }



    // ======================== Sandbox ========================:

    // ==== Fields ==== :

    /* INSTANCES: */
    private SandBox sandbox;

    // ==== Methods ==== :
    
    /* INSTANCES: */
    public SandBox getSandBox() {
        return this.sandbox;
    }
}
