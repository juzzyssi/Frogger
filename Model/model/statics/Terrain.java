// ==== Package ==== :
package Model.model.statics;

// ==== Generals ==== :
import java.awt.Graphics;
import java.awt.Rectangle;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import Graphics.Camera;

import Math.Vector;

import Util.DimensionalList2D;

// ==== Interfaces ==== :
import Engine.api.components.ContinuumIntegration;
import Engine.api.components.Renderable;
import Engine.api.management.ContinuumRegistery;
import Engine.api.management.RenderRegistery;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

// ==== Exceptions ==== :
import Model.exceptions.world.OutOfBoundsException;
import Model.model.statics.primitives.Tile;
import Model.model.statics.primitives.Region;
import Engine.api.management.exceptions.IllegalApiParameterException;
import Engine.api.management.primitives.ApiRegistery;



public class Terrain implements Renderable, ContinuumIntegration {

    // ==== Fields ==== :

    // Concretes:
    public static final int X = 0, Y = 1;

    // *** Instances *** :

    // Api components registery: 
    private RenderRegistery renderApi;
    private ContinuumRegistery continuumApi;

    private Set<ApiRegistery<?>> apis;

    // Data:
    private DimensionalList2D<Tile> tiles;
    private Set<Region> regions;

    // Rendering:
    private DimensionalList2D<Tile> oldFrame;

    // Continuum integration:
    private boolean updated;

    // ==== Interfaces ==== :

    // Continuum integration:
    @Override
    public void checkIn( long time, Object... args ){
        if( !this.updated ) {
            this.updated = true;

            /* Updating the frame: */
            Camera camera = (Camera) args[0];

            try{
                this.updateFrame( camera );
            } catch( Exception e ) {
                System.out.println( e.getMessage() );
            }
            
            /* Continuum updating: */
            this.continuumApi.checkIn( time, args );
        }
    }

    @Override
    public void checkOut( long time, Object... args ){
        if( this.updated ) {
            this.updated = false;            
            this.continuumApi.checkOut(time, args);
        }
    }

    // Renderable:
    @Override
    public void render( Graphics g, Camera camera ){
        this.renderApi.render(g, camera);
    }

    // ==== Methods ==== :

    // *** Instances *** : 

    // ( I.M.S. 0 : getters & setters )

    // Returns the tile present at such vector: O( 1 )
    public Tile getAt( Vector vector ) throws OutOfBoundsException{
        return this.tiles.getAt( vector );
    }

    // Sets the tile present at such vector: O( 1 )
    public Tile setAt( Vector vector, Tile tile ) throws OutOfBoundsException, IllegalArgumentException, UnsupportedOperationException, IllegalApiParameterException{
        if( Tile.class.isInstance( tile ) ){
            Object object = this.tiles.getAt( vector );

            if( object != null ){
                Tile old = this.tiles.getAt( vector );
                this.queueRemovalFromAllAPIs( old );
            }
            this.adoptToAllAPIs( tile );

            return this.tiles.setAt( vector, tile );
        } else {
            throw new IllegalArgumentException( String.format("%s is not an instance of any %s sub-class", tile.toString(), Tile.class.getName()) );
        }
    }

    // O( 1 )
    public int getColumns() {
        return this.tiles.getColumns();
    }
    
    // O( 1 )
    public int getRows() {
        return this.tiles.getRows();
    }

    // O( n ) (copy)
    public Collection<Tile> getCells( Rectangle rectangle ) throws OutOfBoundsException {
        return this.tiles.subDimList2d(rectangle).toList();
    }

    // ( I.M.S. 1 : api management )

    // Adds the tile to each valid api in the region: O( n ) / O( 1 )
    public void adoptToAllAPIs( Tile tile ) {
        for( ApiRegistery<?> i : this.apis ) {
            try{
                i.add( tile );
            } catch( IllegalApiParameterException e ) {
                System.out.println( e.getMessage() );
            }
        }
    }

    // "adoptToAllAPIs" opposite: O( n ) / O( 1 )
    public void queueRemovalFromAllAPIs( Tile tile ) {
        for( ApiRegistery<?> i : this.apis ) {
            try{
                i.queueRemoval( tile );
            } catch( IllegalApiParameterException e ) {
                System.out.println( e.getMessage() );
            }
        }
    }
    
    // Executes the queue: O( n ) ( see: Engine > api > management > primitves )
    public void executeAPIRemovalQueue(){
        this.renderApi.executeRemovalQueue();
        this.continuumApi.executeRemovalQueue();
    }

    // ( I.M.S. 2 : others )

    // Manages which cells are to be displayed by comparison with previous records: O( n )
    public void updateFrame( Camera camera ) throws OutOfBoundsException, IllegalApiParameterException {
        
        if( this.tiles.contains( camera.getRectangle() ) ){
            if( this.oldFrame != null ){
                DimensionalList2D<Tile> framedCells = this.tiles.subDimList2d( camera.getRectangle() );

                for( Tile tile : framedCells.toList() ) {

                    /* Recently visible: */
                    if( !framedCells.contains( tile ) && !this.oldFrame.contains( tile ) ) {
                        this.renderApi.add( tile );
                    /* Recently hidden: */
                    } else if( framedCells.contains( tile ) && !this.oldFrame.contains( tile ) ) {
                        this.renderApi.queueRemoval( tile );
                    }
                }
                
                this.oldFrame = framedCells;
                /* First frame: */
            } else {
                this.oldFrame = this.tiles.subDimList2d( camera.getRectangle() );
                for( Object tile : this.oldFrame.toList() ) {
                    this.renderApi.add( (Tile) tile );
                }
            }
        } else {
            throw new OutOfBoundsException();
        }
    }

    // O( 1 )
    public void singRegionUp( Region region ) {
        this.regions.add(region);
    }

    // O( 1 )
    public void singRegionOut( Region region ) {
        this.regions.remove(region);
    }

    // ( I.M.S. 3 : Mutation )

    // (Optional approach) mutates a set of tiles to match a given region: O( n )
    public void paint( Set<Tile> tiles, Class<? extends Region> region ) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {     // I.M.S. 3 ()
        Constructor<? extends Region> constructor = region.getDeclaredConstructor( Set.class, Terrain.class );
        constructor.newInstance( tiles, this );
    }

    // ( I.M.S. 4 : Auxiliaries )

    // O( n )
    public Set<Tile> toSet() {
        return new HashSet<Tile>( this.tiles.toList() );
    }

    // *** Concretes *** :

    // ( C.M.S. 0 : auxiliaries )

    /*  Returns the smallest Rectangle that contains each tile of the specified Set: O( n )
     */
    public static Rectangle toRectangle( Set<? extends Tile> tiles ){
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
    }

    // ==== Constructors ==== :

    public Terrain( RenderRegistery renderRg, ContinuumRegistery continuumRg ) {
        this.renderApi = renderRg;
        this.continuumApi = continuumRg;
        this.updated = false;

        this.apis = new HashSet<>();
        this.apis.add( this.renderApi );
        this.apis.add( this.continuumApi );

        this.tiles = new DimensionalList2D<>( Tile.BLOCK );
    }
    public Terrain( RenderRegistery renderRg, ContinuumRegistery continuumRg, Rectangle chunck ) {
        this.renderApi = renderRg;
        this.continuumApi = continuumRg;
        this.updated = false;

        this.apis = new HashSet<>();
        this.apis.add( this.renderApi );
        this.apis.add( this.continuumApi );

        this.tiles = new DimensionalList2D<>( Tile.BLOCK, chunck );
    }
}
