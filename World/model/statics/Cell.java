// ==== Package ==== :
package World.model.statics;

// ==== General ==== :
import java.awt.*;
import java.util.ArrayList;

import Math.Vector;
import World.model.World;

// ==== Interfaces ==== :
import World.api.Associative;
import World.api.Traversable;



/*  General Documentation:
 *
 *  Cell is the building class of a World's "terrain". Essentially, Region (or SuperRegion) instances are virtual managers of 
 *  Cell (or Supercell) instance collections. They inherit from java.awt.Rectangle in order to implememnt collision's and 
 *  space logic.
 */
public abstract class Cell extends Rectangle implements Associative, Traversable{

    // ==== Fields ==== :

    // Concrete:
    public final static int WIDTH = 56;

    // Instances:    
    private ArrayList<Object> family = new ArrayList<>(0); /* Associative-functionality field */
    public Vector displacement;



    // ==== Interfaces ==== : ( Find documentation here: World > api )

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

        if( clazz.equals( this.getClass() )){
            throw new IllegalArgumentException( "Associative instances cannot have class twins" );
        }
        else{

            for( Object i : this.family ){
                if( clazz.equals( i.getClass() ) ){
                    return clazz.cast( i );
                }
            }
            throw new IllegalArgumentException( String.format("No %s was found in %s", clazz.getName(), this.toString() ));
        }
    }

    @Override
    public void adopt( Object object ){

        if( object instanceof World && this.hasMember( World.class )){
            throw new IllegalArgumentException("Worlds are immutable Associative instances");
        }
        else if( object.getClass().equals( this.getClass() ) ){
            throw new IllegalArgumentException("Associative groups cannot have class twins");
        }
        else if( !(this.isFamily(object)) ){
            boolean adopted = false;

            /* Replaces Instances with the same Class */
            for( Object i : this.family ){

                if( i.getClass().equals( object.getClass() )){
                    this.family.remove( this.getFamilyMember( i.getClass() ) );
                    this.family.add( object );
                    adopted = true;
                }
            }
            /* Otherwise the object is "adopted" */
            if( !(adopted) ){
                this.family.add( object );
            }
        }
    }

    // Traversability:
    @Override
    public void anchorTo( Traversable object ){
        throw new IllegalAccessError("Cells anchor ( Vector=[0, 0] ) is immutable");
    }
    @Override
    public Vector getAnchor(){
        return this.displacement;
    }



    // ==== Methods ==== :

    // Instances:
    public Point toPoint(){                                     // I.M.S 0 ( points )
        return new Point( this.x, this.y);
    }

    @Override                                                   // I.M.S 1 ( string )
    public String toString(){
        return String.format("Cell[ pos=(x=%d, y=%d, width=%d, height=%d), family=%s ]",
        this.x, this.y, this.width, this.height,
        this.family.toString()
        );
    }

    public Point toCentre( double x, double y ){                // I.M.S 2 ( general utility )
        /* If the parameter is contained within the Cell; returns a point in the center of this Cell */
        if( this.contains(x,y) ){
            return new Point( this.x + Cell.WIDTH/2, this.y + Cell.WIDTH/2 );
        }
        else{
            throw new IllegalArgumentException( String.format("%s does not contain Point[ x=%.0f, y=%.0f ]",
            this.toString(), x, y)
            );
        }
    }
    public Point toCentre( Point point ){
        return this.toCentre( point.getX(), point.getY() );
    }
    public Point toCentre( Vector vector ){
        return this.toCentre( vector.getX(), vector.getY() );
    }
    public Point toCentre( int x, int y ){
        return this.toCentre( (double)(x), (double)(y) );
    }



    // ==== Constructors ==== :

    public Cell( Point pos ){
        super( pos.x, pos.y, Cell.WIDTH, Cell.WIDTH);
        this.displacement = new Vector( pos.getX(), pos.getY());
    }
    public Cell( int x, int y ){
        super( x, y, Cell.WIDTH, Cell.WIDTH);
        this.displacement = new Vector( x, y);
    }
    public Cell( double x, double y ){
        super( (int) (x), (int) (y), Cell.WIDTH, Cell.WIDTH);
        this.displacement = new Vector(x, y);
    }
}