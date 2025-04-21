// ==== Package ==== :
package World.model;

// ==== General ==== :
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import Util.RandomSet;

import World.model.statics.Supercell;
import World.model.statics.Cell;
import World.model.statics.SuperRegion;

import Graphics.Camera;

// ==== Interfaces ==== :

import World.api.Associative;
import World.api.LoopIntegration;
import World.api.RegionTemplateAccessibility;
import World.api.Renderable;
import World.api.Anchorable;



/* Worlds are the parent class of all "static" & "dynamic" world generation (terrain, interactives, etc.).
 * Worlds are responsible for any interaction with other game elements such as:
 * "cameras", "dynamic" objects, "static" objects or interactive features overall.
 * 
 * Static and dynamic objects are managed separately.
 * 
 * The class could be worked to procedurally generate terrain that's not limited to uniquelyu expressable rectangles;
 * I don't think I'll have time for that
 */
public class World extends Rectangle implements Renderable, Associative, LoopIntegration, Anchorable{

    // ==== Fields ==== :

    // Instances:
    public ArrayList<Object> family = new ArrayList<>(0);
    public ArrayList<Supercell> terrain = new ArrayList<>(0);

    public ArrayList<SuperRegion> regions = new ArrayList<>(0);

    public Point anchor = new Point(0 ,0);
    public long time;



    // ==== Interfaces ==== : ( Find documentation here: World > api )

    // Renderable:
    @Override
    public void render( Graphics g, Camera camera ){
        /* Expect rendering mistakes as a moving camera is implemented due to the limit of drawing reference frame from which cells are called */

        if( this.contains( camera.getRectangle() )){

            /* Calls all SuperCells on a given rect to "be drawn" */
            Point cameraPosition = camera.getPosition();
            Dimension cameraDimension = camera.getDimensions();

            for( int iy=cameraPosition.y ; iy<cameraPosition.y+cameraDimension.height ; iy+=Cell.WIDTH ){
                for( int ix=cameraPosition.x ; ix<cameraPosition.x+cameraDimension.width ; ix+=Cell.WIDTH ){

                    this.getAt( ix, iy ).render(g, camera);
                }
            }
        }
        else{
            throw new IllegalArgumentException( String.format("camera[ x=%d, y=%d, width=%d, height=%d ] is out of World[ x=%d, y=%d, width=%d, height=%d]",
            camera.getPosition().x, camera.getPosition().y, camera.getDimensions().width, camera.getDimensions().height,
            this.x, this.y, this.width, this.height)
            );
        }
    }

    // Associative:
    @Override
    public boolean isFamily( Object object ){
        return this.family.contains(object);
    }

    @Override
    public <T> boolean hasMember( Class<T> clazz ){
        
        for( Object i : this.family ){
            if( i.getClass().equals(clazz) ){
                return true;
            }
        }
        return false;
    }

    @Override
    public <T> T getFamilyMember( Class<T> clazz ){
        if( clazz.equals(this.getClass())){
            return clazz.cast( this );
        }
        else{
            for( Object i : this.family ){
                if( clazz.equals( i.getClass() ) ){
                    return clazz.cast( i );
                }
            }
            throw new IllegalArgumentException("no" + clazz.getName() + "was found in" + this.toString() );
        }
    }

    @Override
    public void adopt( Object object ){
        if( this.getClass().equals( object.getClass() ) ){
            throw new IllegalArgumentException("Worlds cannot belong to a family with existing Worlds");
        }
        else if( !(this.isFamily(object)) ){
            boolean adopted = false;

            /* if any object in this family shares classes; the old one is replaced */
            for( Object i : this.family ){
                if( i.getClass().equals(object.getClass()) ){
                    this.family.remove( this.getFamilyMember( object.getClass() ) );
                    this.family.add( object );
                    adopted = true;
                }
            }
            /* Otherwise, the object is simly "adopted" */
            if( !(adopted) ){
                this.family.add( object );
            }
        }
    }

    // LoopIntegration
    @Override
    public void update( long time ){
        // W.I.P.
    }

    // Anchorable:
    @Override
    public Point getAnchor(){
        return this.anchor;
    }
    @Override
    public void anchorTo( Object object ){
        throw new IllegalArgumentException( "World instances cannot mutate anchors / origins" );
    }



    // ==== Methods ==== :

    // Instances:
    private int calculateIndex( double doubleX, double doubleY ){                        // I.M.S. 0 ()
        /* int casting leaves the index */

        int newX = (int)(doubleX);
        int newY = (int)(doubleY);
        /* Casting doubles to integers to avoid unforseen effects of double's off decimals */

        if( this.contains(newX, newY) ){
            int x_index = (newX + this.anchor.x - this.x) / Cell.WIDTH;
            x_index = x_index == this.getColumns() ? x_index - 1 : x_index;

            int y_index = (newY + this.anchor.y - this.y) / Cell.WIDTH;
            y_index = y_index == this.getRows() ? y_index - 1 : y_index;
        
            return y_index * this.getColumns() + x_index;
        }
        else{
            throw new IllegalArgumentException( String.format("point[x=%d, y=%d] is out of "+this.toString(),
            newX, newY,
            this.x, this.y, this.width, this.height)
            );
        }
    }
    public Supercell getAt( Point point ){
        return this.terrain.get( this.calculateIndex( point.getX(), point.getY()) );
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

    public void makeRegion( ArrayList<Supercell> supercells, RegionTemplateAccessibility traits ){     // I.M.S. 3 ()
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

        this.time = System.nanoTime();

        /* Creates a single ArrayList of SuperCells; each row and column's cell is accessed with index arithmetic operations */
        for( int newY=0 ; newY<worldInitHeight ; newY+=Cell.WIDTH ){
            for( int newX=0 ; newX<worldInitWidth ; newX+=Cell.WIDTH ){

                newSuperCell = new Supercell( anchor.x + newX, anchor.y + newY, traits.pickCellTemplateAccessibility( RandomSet.SET_TO_SPECIFIC_ODDS ) );
                newSuperCell.adopt( this );
                newSuperCell.anchorTo( this );
                this.terrain.add( newSuperCell );

            }
        }
    }
}
