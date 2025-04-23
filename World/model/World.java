// ==== Package ==== :
package World.model;

// ==== General ==== :
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import Util.RandomSet;

import World.model.statics.Supercell;
import World.model.statics.Cell;
import World.model.statics.SuperRegion;

import Graphics.Camera;
import Math.Vector;

// ==== Interfaces ==== :


import World.api.engine.LoopIntegration;
import World.api.engine.Renderable;
import World.api.template.RegionTemplateAccessibility;



/*  Worlds are "god" classes that operate as both orchestrators and an interface between the game's "model" instances and the engine.
 *  World classes--for the most part--delegate "actions" to its "sub-components"
 * 
 */
public class World extends Rectangle implements Renderable, LoopIntegration{

    // ==== Fields ==== :

    // Instances:
    public ArrayList<Supercell> terrain = new ArrayList<>(0);
    public ArrayList<SuperRegion> regions = new ArrayList<>(0);

    public final Vector anchor = new Vector(0 ,0);
    public long time = System.nanoTime();

    private boolean updated = false;

    // public Frog player;



    // ==== Interfaces ==== : ( Find documentation here: World > api )

    // Renderable:
    @Override
    public void render( Graphics g, Camera camera ){
        /* Expect rendering mistakes as a moving camera is implemented due to the limit of drawing reference frame from which cells are called */

        // ==== Static Instances ==== :

        // Calls all SuperCells on a given Camera instance's Rectangle to be "drawn"
        if( this.contains( camera.getRectangle() )){

            Vector cameraPosition = camera.getPosition();
            Dimension cameraDimension = camera.getDimensions();

            // Position iteration based on Cell.WIDTH symetry
            for( int iy=(int)(cameraPosition.getY()) ; iy<(int)(cameraPosition.getY())+cameraDimension.height ; iy+=Cell.WIDTH ){
                for( int ix=(int)(cameraPosition.getX()) ; ix<(int)(cameraPosition.getX())+cameraDimension.width ; ix+=Cell.WIDTH ){

                    this.getAt( ix, iy ).render(g, camera);
                }
            }
        }
        else{
            throw new IllegalArgumentException( String.format("camera[ x=%d, y=%d, width=%d, height=%d ] is out of World[ x=%.2f, y=%.2f, width=%d, height=%d]",
            camera.getPosition().getX(), camera.getPosition().getY(), camera.getDimensions().width, camera.getDimensions().height,
            this.x, this.y, this.width, this.height)
            );
        }

        // ==== LooIntegrated Instances ==== : W.I.P.
    }

    // LoopIntegration
    public void checkIn( long time ){
        if( !this.updated ){
            this.updated = true;

            /* So far, only superRegion instances require "loop'-updates" */
            for( LoopIntegration i : this.regions ){
                i.checkIn( time );
            }    
        }
    }
    
    public void checkOut( long time ){
        if( this.updated ){
            this.updated = false;

            /* So far, only superRegion instances require "loop'-updates" */
            for( LoopIntegration i : this.regions ){
                i.checkOut( time );
            }    
        }
    }


    // ==== Methods ==== :

    // Instances:

    /* KEY */
    private int calculateIndex( double doubleX, double doubleY ){                        // I.M.S. 0 ()
        /* Calculates the index of a given instance in it's collection of Supercell instances by taking advantage of 
        int casting and the order of instance addition to the collection */

        int newX = (int)(doubleX);
        int newY = (int)(doubleY);
        /* Casting doubles to integers to avoid unforseen effects of double's off decimals */

        if( this.contains(newX, newY) ){
            int x_index = (newX + (int)(this.anchor.getX()) - this.x) / Cell.WIDTH;
            x_index = x_index == this.getColumns() ? x_index - 1 : x_index;

            int y_index = (newY + (int)(this.anchor.getY()) - this.y) / Cell.WIDTH;
            y_index = y_index == this.getRows() ? y_index - 1 : y_index;
        
            return y_index * this.getColumns() + x_index;
        }
        else{
            throw new IllegalArgumentException( String.format("point[x=%d, y=%d] is out of "+this.toString(), newX, newY) );
        }
    }
    public Supercell getAt( Point point ){
        return this.terrain.get( this.calculateIndex( point.getX(), point.getY()) );
    }
    public Supercell getAt( Vector vector ){
        return this.terrain.get( this.calculateIndex( vector.getX(), vector.getY()) );
    }
    public Supercell getAt( double x, double y ){
        return this.terrain.get( this.calculateIndex( x, y ) );
    }
    public Supercell getAt( int x, int y ){
        return this.terrain.get( this.calculateIndex( (double)(x), (double)(y) ) );
    }


    public int getRows(){                                                   // I.M.S. 1 ()
        return this.height / Cell.WIDTH;
    }
    public int getColumns(){
        return this.width / Cell.WIDTH;
    }

    public ArrayList<Supercell> getCellsInRectangle( Rectangle rect ){         // I.M.S. 2 ()

        if( this.contains(rect) ){
            ArrayList<Supercell> out = new ArrayList<>(0);

            for( double ix = rect.getX() ; ix <= rect.getX() + rect.getWidth() - (double)(Cell.WIDTH) ; ix += (double)(Cell.WIDTH) ){
                for( double iy = rect.getY() ; iy <= rect.getY() + rect.getHeight() - (double) (Cell.WIDTH) ; iy += (double)(Cell.WIDTH) ){
                    out.add( this.getAt( ix, iy ) );
                }
            }
            return out;
        }
        else{
            throw new IllegalArgumentException(""+rect.toString()+" is out of "+this.toString());
        }
    }

    public void makeRegion( ArrayList<Supercell> supercells, RegionTemplateAccessibility traits ) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{     // I.M.S. 3 ()
        this.regions.add( new SuperRegion(supercells, traits) );
    }



    // ==== Constructors ==== :

    public World( Dimension dimension, RegionTemplateAccessibility traits ){

        /* Dimensions are rounded to match a multiple of "Cell.WIDTH" */
        super( 0, 0, dimension.width - (dimension.width % Cell.WIDTH), dimension.height - (dimension.height % Cell.WIDTH) );
        
        int worldInitWidth = dimension.width - (dimension.width % Cell.WIDTH);
        int worldInitHeight = dimension.height - (dimension.height % Cell.WIDTH);
        System.out.format( "The world has been set to:%nwidth=%d, height=%d%nrows=%d, columns=%d%n", worldInitWidth, worldInitHeight, worldInitWidth/Cell.WIDTH, worldInitHeight/Cell.WIDTH );
        Supercell newSuperCell;

        /* Creates a single ArrayList of SuperCells; each row and column's cell is accessed with index arithmetic operations */
        for( int newY=0 ; newY<worldInitHeight ; newY+=Cell.WIDTH ){
            for( int newX=0 ; newX<worldInitWidth ; newX+=Cell.WIDTH ){

                newSuperCell = new Supercell( (int) anchor.getX() + newX, (int) anchor.getY() + newY, traits.pickCellTemplateAccessibility( RandomSet.SET_TO_SPECIFIC_ODDS ) );
                newSuperCell.adopt( this );
                this.terrain.add( newSuperCell );

            }
        }
    }
}
