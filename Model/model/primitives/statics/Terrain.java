// ==== Package ==== :
package Model.model.primitives.statics;

import java.awt.Dimension;

// ==== Generals ==== :
import java.awt.Rectangle;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import Engine.api.management.ContinuumRegistery;
import Engine.api.management.RenderRegistery;
import Graphics.Camera;

import Math.Vector;

import Util.DimensionalList2D;

import java.util.HashSet;

// ==== Interfaces ==== :
import java.util.Collection;
import java.util.Set;

import Engine.api.components.Continuous;

// ==== Exceptions ==== :
import Model.exceptions.world.OutOfBoundsException;
import Model.model.primitives.interactives.SandBox;
import Engine.api.management.exceptions.IllegalApiParameterException;



public class Terrain implements Continuous {

    // ==== Fields ==== :

    /* CONCRETES: */
    public static final int X = 0, Y = 1;

    /* INSTANCES: */
    private RenderRegistery renderRg;
    private ContinuumRegistery continuumRg;

    private DimensionalList2D<Tile> tiles;
    private Set<Region> regions;

    // Rendering:
    private DimensionalList2D<Tile> oldFrame;

    // Continuum integration:
    private boolean updated;

    // ==== Interfaces ==== :

    // Continuous:
    @Override
    public void checkIn( long time, Object... args ){
        if( !this.updated ) {
            this.updated = true;

            /* Updating the frame: */
            Camera camera = (Camera) args[0];

            try{
                this.updateFrame( camera );
            } catch( Exception e ) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void checkOut( long time, Object... args ){
        if( this.updated ) {
            this.updated = false;
        }
    }

    // ======== General Functionality ======== :

    // ==== Methods ==== :

    /* INSTANCES: */
    public Tile getAt( Vector vector ) throws OutOfBoundsException{
        return this.tiles.getAt( vector );
    }   // O( 1 )

    public Tile set( Tile newTile ) throws OutOfBoundsException, IllegalArgumentException {
        
        // Ensuring grid-snap:
        Vector vector = this.tiles.snapToBlock( newTile.toVector() );
        newTile.setLocation( (int) vector.get( Terrain.X ), (int) vector.get( Terrain.Y ) );

        Tile oldTile = this.tiles.setAt( vector, newTile );
        if( oldTile != null ){
            this.queueRemovalFromAllAPIs( oldTile );
        }
        
        return oldTile; // Returns the previous tile
    }   // O( 1 )

    public int getColumns() {
        return this.tiles.getColumns();
    }   // O( 1 )
    
    public int getRows() {
        return this.tiles.getRows();
    }   // O( 1 )

    public Collection<Tile> getTiles( Rectangle rectangle ) throws OutOfBoundsException {
        return this.tiles.subDimList2d(rectangle).toList();
    }   // O( n )

    public Collection<Vector> getVectors( Rectangle rectangle ) throws OutOfBoundsException{
        Collection<Vector> out = new HashSet<>();

        for( Tile tile : this.getTiles( rectangle ) ) {
            out.add( tile.toVector() );
        }

        return out;
    }   // O( n )

    public boolean contains( Region region ) {
        return this.regions.contains( region );
    }   // O( 1 )

    public void singRegionUp( Region region ) {
        this.regions.add(region);
    }   // O( 1 )

    public void singRegionOut( Region region ) {
        this.regions.remove(region);
    }   // O( 1 )

    public Set<Tile> toSet() {
        return new HashSet<Tile>( this.tiles.toList() );
    }   // O( n )

    public Collection<Vector> toVectors() {
        return this.tiles.toVectors();
    }   // O( n )

    // ======== Api Management ======== :

    /* INSTANCES: */
    public void adoptToAllAPIs( Tile tile ) {

        try{
            this.renderRg.add( tile );
        } catch( IllegalApiParameterException e ) {
            System.out.println( e.getMessage() );
        }

        try{
            this.continuumRg.add( tile );
        } catch( IllegalApiParameterException e ) {
            System.out.println( e.getMessage() );
        }
    }   // O( 1 )

    public void queueRemovalFromAllAPIs( Tile tile ) {

        try{
            this.renderRg.queueRemoval( tile );
        } catch( IllegalApiParameterException e ) {
            System.out.println( e.getMessage() );
        }

        try{
            this.continuumRg.queueRemoval( tile );
        } catch( IllegalApiParameterException e ) {
            System.out.println( e.getMessage() );
        }
    }   // O( 1 )
    
    public void executeAPIRemovalQueue(){
        this.renderRg.executeRemovalQueue();
        this.continuumRg.executeRemovalQueue();
    }   // O( n )

    // ======== Terrain Processes ======== :

    /* CONCRETES: */

    /*  Returns the smallest rectangle containing all the provided tiles. 
     */
    public static Rectangle toRectangle( Collection<? extends Tile> tiles ){
        int lowX = Integer.MAX_VALUE, lowY = Integer.MAX_VALUE, upX = Integer.MIN_VALUE, upY = Integer.MIN_VALUE;
        Vector temp;

        for( Tile i : tiles ){
            temp = i.toVector();

            lowX = temp.get( Terrain.X ) < lowX ? (int) temp.get( Terrain.X ) : lowX;
            lowY = temp.get( Terrain.Y ) < lowY ? (int) temp.get( Terrain.Y ) : lowY;

            upX = temp.get( Terrain.X ) > upX ? (int) temp.get( Terrain.X ) : upX;
            upY = temp.get( Terrain.Y ) > upY ? (int) temp.get( Terrain.Y ) : upY;
        }

        return new Rectangle( lowX, lowY, upX - lowX + Tile.BLOCK.width, upY - lowY + Tile.BLOCK.height);
    }   // O( n )

    /* INSTANCES: */
    
    /* Manages which cells are to be displayed by comparison with previous records.
     */
    public void updateFrame( Camera camera ) throws OutOfBoundsException, IllegalApiParameterException {
        DimensionalList2D<Tile> framedCells = tiles.subDimList2d( camera.getRectangle() );

        if( this.tiles.contains( camera.getRectangle() ) ) {
            if (this.oldFrame != null) {
                /* 1: recently framed tiles */
                for (Tile tile : framedCells.toList()) {
                    if (!this.oldFrame.contains( tile )) {
                        this.adoptToAllAPIs( tile );
                    }
                }
                /* 2: recently unframed tiles */
                for (Tile tile : this.oldFrame.toList()) {
                    if (!framedCells.contains( tile )) {
                        this.queueRemovalFromAllAPIs(tile);
                    }
                }
            } else {
                for (Tile tile : framedCells.toList()) {
                    this.adoptToAllAPIs( tile );
                }
            }
            this.oldFrame = framedCells;
        }
    }   // O( n )

    /*  Creates a new region of the provided sub-class.
     */
    public void paint( Collection<Vector> vectors, Class<? extends Region> region, SandBox sandbox ) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {     // I.M.S. 3 ()
        Constructor<? extends Region> constructor = region.getDeclaredConstructor( Collection.class, SandBox.class, Terrain.class );
        constructor.newInstance( vectors, sandbox, this );
    }   // O( n )

    public double getWidth() {
        return this.tiles.getWidth();
    }

    public double getHeight() {
        return this.tiles.getHeight();
    }

    public Vector getDisplacement() {
        return this.tiles.getDisplacement();
    }

    public Dimension getBlock() {
        return this.tiles.getBlock();
    }

    public Vector snapToGrid( Vector vector ) {
        return this.tiles.snapToBlock( vector );
    }

    // ==== Constructors ==== :
    
    public Terrain( RenderRegistery renderRg, ContinuumRegistery continuumRg, Rectangle chunck ) {
        this.updated = false;

        this.continuumRg = continuumRg;
        this.renderRg = renderRg;

        this.regions = new HashSet<>();
        this.tiles = new DimensionalList2D<>( Tile.BLOCK, chunck );
    }
}
