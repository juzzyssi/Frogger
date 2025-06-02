// ==== Package ==== :
package Model.model.statics;

// ==== Generals ==== :
import java.awt.Graphics;
import java.awt.Rectangle;

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
import Model.model.statics.primitives.Cell;
import Model.model.statics.primitives.Region;
import Engine.api.management.exceptions.IllegalApiParameterException;
import Engine.api.management.primitives.ApiRegistery;



public class Terrain implements Renderable, ContinuumIntegration {

    // ==== Fields ==== :

    // Concretes:
    public static final int X = 0, Y = 1;

    // Instances:

    /* Api components registery: */
    private RenderRegistery renderApi;
    private ContinuumRegistery continuumApi;

    private Set<ApiRegistery<?>> apis;

    /* Data: */
    private DimensionalList2D<Cell> cells;
    private Set<Region> regions;

    /* Rendering: */
    private DimensionalList2D<Cell> oldFrame;

    /* Continuum integration */
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

    // Instances: 

    // I.M.S. 0 : "Getters & setters".

    /*  Returns the "sub-Cell" instance at the given vector;
     *  O( 1 ).
     */
    public Cell getAt( Vector vector ) throws OutOfBoundsException{
        return this.cells.getAt( vector );
    }

    /*  Set's the "sub-Cell" instance at the specified vector;
     *  O( 1 ).
     */
    public Cell setAt( Vector vector, Cell cell ) throws OutOfBoundsException, IllegalArgumentException, UnsupportedOperationException, IllegalApiParameterException{
        if( Cell.class.isInstance( cell ) ){
            Object object = this.cells.getAt( vector );

            if( object != null ){
                Cell old = this.cells.getAt( vector );
                this.queueRemovalFromAllAPIs( old );
            }
            this.adoptToAllAPIs( cell );

            return this.cells.setAt( vector, cell );
        } else {
            throw new IllegalArgumentException( String.format("%s is not an instance of any %s sub-class", cell.toString(), Cell.class.getName()) );
        }
    }

    public int getColumns() {
        return this.cells.getColumns();
    }
    
    public int getRows() {
        return this.cells.getRows();
    }

    public Collection<Cell> getCells( Rectangle rectangle ) throws OutOfBoundsException {
        return this.cells.subDimList2d(rectangle).toList();
    }

    // I.M.S. 1 : Api management.

    public void adoptToAllAPIs( Cell cell ) {
        for( ApiRegistery<?> i : this.apis ) {
            try{
                i.add( cell );
            } catch( IllegalApiParameterException e ) {
                System.out.println( e.getMessage() );
            }
        }
    }

    public void queueRemovalFromAllAPIs( Cell cell ) {
        for( ApiRegistery<?> i : this.apis ) {
            try{
                i.queueRemoval( cell );
            } catch( IllegalApiParameterException e ) {
                System.out.println( e.getMessage() );
            }
        }
    }
    
    public void executeAPIRemovalQueue(){
        this.renderApi.executeRemovalQueue();
        this.continuumApi.executeRemovalQueue();
    }

    // I.M.S. 2 : Process efficiency.

    public void updateFrame( Camera camera ) throws OutOfBoundsException, IllegalApiParameterException {
        
        if( this.cells.contains( camera.getRectangle() ) ){
            if( this.oldFrame != null ){
                DimensionalList2D<Cell> framedCells = this.cells.subDimList2d( camera.getRectangle() );

                for( Cell cell : framedCells.toList() ) {

                    /* Recently visible: */
                    if( !framedCells.contains( cell ) && !this.oldFrame.contains( cell ) ) {
                        this.renderApi.add( cell );
                    /* Recently hidden: */
                    } else if( framedCells.contains( cell ) && !this.oldFrame.contains( cell ) ) {
                        this.renderApi.queueRemoval( cell );
                    }
                }
                
                this.oldFrame = framedCells;
                /* First frame: */
            } else {
                this.oldFrame = this.cells.subDimList2d( camera.getRectangle() );
                for( Object cell : this.oldFrame.toList() ) {
                    this.renderApi.add( (Cell) cell );
                }
            }
        } else {
            throw new OutOfBoundsException();
        }
    }

    public void singRegionUp( Region region ) {
        this.regions.add(region);
    }
    public void singRegionOut( Region region ) {
        this.regions.remove(region);
    }

    // Concretes:

    // C.M.S. 0 : 

    /*  Returns the smallest Rectangle that contains each cell of the specified Set;
     *  O( n ) where n is the size of the Set.
     */
    public static Rectangle toRectangle( Set<? extends Cell> cells ){
        int lowX = Integer.MAX_VALUE, lowY = Integer.MAX_VALUE, upX = Integer.MIN_VALUE, upY = Integer.MIN_VALUE;
        Vector temp;

        for( Cell i : cells ){
            temp = i.toVector();

            lowX = temp.get( Terrain.X ) < lowX ? (int) temp.get( Terrain.X ) : lowX;
            lowY = temp.get( Terrain.Y ) < lowY ? (int) temp.get( Terrain.Y ) : lowY;

            upX = temp.get( Terrain.X ) > upX ? (int) temp.get( Terrain.X ) : upX;
            upY = temp.get( Terrain.Y ) > upY ? (int) temp.get( Terrain.Y ) : upY;
        }

        return new Rectangle( lowX, lowY, upX - lowX + Cell.BLOCK.width, upY - lowY + Cell.BLOCK.height);
    }

    // ==== Constructors ==== :

    public Terrain( RenderRegistery renderRg, ContinuumRegistery continuumRg ) {
        this.renderApi = renderRg;
        this.continuumApi = continuumRg;
        this.updated = false;

        this.apis = new HashSet<>();
        this.apis.add( this.renderApi );
        this.apis.add( this.continuumApi );

        this.cells = new DimensionalList2D<>( Cell.BLOCK );
    }
    public Terrain( RenderRegistery renderRg, ContinuumRegistery continuumRg, Rectangle chunck ) {
        this.renderApi = renderRg;
        this.continuumApi = continuumRg;
        this.updated = false;

        this.apis = new HashSet<>();
        this.apis.add( this.renderApi );
        this.apis.add( this.continuumApi );

        this.cells = new DimensionalList2D<>( Cell.BLOCK, chunck );
    }
}
