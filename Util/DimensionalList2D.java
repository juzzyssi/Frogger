// ==== Package ==== :
package Util;

// ==== Generals ==== :
import java.awt.Dimension;
import java.awt.Rectangle;

import java.util.ArrayList;

import Math.Vector;

// ==== Interfaces ==== :
import java.util.List;

// ==== Exceptions ==== :
import Model.exceptions.world.OutOfBoundsException;
import Model.model.statics.Terrain;



public class DimensionalList2D<T>{

    // ==== Fields ==== :

    // Concretes:

    public static final int X = 0, Y = 1;

    // Instances:
    private ArrayList<T> elements;

    private Rectangle plate;
    private Dimension block;

    // ==== Methods ==== :

    // Instances:

    // I.M.S. 0 : Simple auxiliaries.

    /*  A practical set of auxiliary methods, dedicated to superficial information provision of the List's "status"; 
     *  all of them with an order of efficiency of O( 1 ).
     */

    public boolean contains( Vector vector ){
        return this.plate.contains( this.project( vector ).toPoint2D() );
    }

    public boolean contains( Rectangle rectangle ) {
        return this.plate.contains(rectangle);
    }

    public Vector project( Vector vector ) {
        return new Vector( vector.get( DimensionalList2D.X ), vector.get( DimensionalList2D.Y ) );
    }

    public boolean contains( T object ) {
        return this.elements.contains( object );
    }

    /*  Returns a list representation of this instance;
     *  O( n ).
     */
    public List<T> toList() {
        return new ArrayList<>( this.elements );
    }

    // I.M.S. 1 : "Getters & setters".

    /*  Similar to above (self-explanatory); 
     *  O( 1 ).
     */
    public int getColumns(){
        return this.plate.width / this.block.width;
    }

    public int getRows(){
        return this.plate.height / this.block.height;
    }

    public T setAt( Vector vector, T object ) throws OutOfBoundsException {
        return this.elements.set( this.toIndex( vector ), object );
    }

    public T getAt( Vector vector ) throws OutOfBoundsException {
        return this.elements.get( this.toIndex( vector ) );
    }

    // I.M.S. 2 : "Translators".

    /*  
     * 
     */
    private int toIndex( Vector vector ) throws OutOfBoundsException{
        if( this.contains(vector) ){
            Vector rel = new Vector(
                /* x : */ vector.get( DimensionalList2D.X ) - (long) this.plate.getX(),
                /* y : */ vector.get( DimensionalList2D.Y ) - (long) this.plate.getY() );

            int unitsY =  rel.get( DimensionalList2D.Y ) == this.plate.height ?
            /* Prevents false indexes; caused by the vector meeting the bottom limit */
                this.getRows() - 1 // true
                :
                (int)(rel.get( DimensionalList2D.Y ) / this.block.getHeight()) // false
            ;
            int unitsX = rel.get( DimensionalList2D.X ) == this.plate.width ?
            /* Prevents false indexes; caused by the vector meeting the right limit */
                this.getColumns() - 1 // true
                :
                (int)(rel.get( DimensionalList2D.X ) / this.block.getWidth()) // false
            ;
            
            return unitsY * this.getColumns() + unitsX;

        } else {
            throw new OutOfBoundsException( String.format("%s is out of %s", this.project( vector ).toString(), this.toString()) );
        }
    }

    private Vector snapToBlock(Vector vector) {
        Vector p = project(vector);
        long x = p.get( Terrain.X );
        long y = p.get( Terrain.Y );

        if (x % block.width != 0) {
            long rx = x % block.width;
            x += (x >= 0 ? block.width - rx : -rx);
        }
        if (y % block.height != 0) {
            long ry = y % block.height;
            y += (y >= 0 ? block.height - ry : -ry);
        }
        return new Vector(x, y);
    }

    // I.M.S. 3 : Auxiliaries:

    /*  Returns a list representation of the specified sub-list;
     *  O( n ).
     */
    public DimensionalList2D<T> subDimList2d( Rectangle rectangle ) throws OutOfBoundsException{
        if( this.contains(rectangle) ) {
            DimensionalList2D<T> out = new DimensionalList2D<>( this.block, rectangle );

            Vector i = new Vector( rectangle.x, rectangle.y );
            for( long y = rectangle.y; y < rectangle.y + rectangle.height; y += this.block.height ) {
                for( long x = rectangle.x ; x < rectangle.x + rectangle.width; x+= this.block.width ) {
                    i.set( 0, x );
                    i.set( 1, y );

                    out.setAt( i, this.getAt(i) );
                }
            }

            return out;
        } else {
            throw new OutOfBoundsException( String.format("%s exists outside of %s", rectangle.toString(), this.toString()) );
        }
    }    

    // I.M.S. 4 : Transformation.

    private void horizontalExpansion(int blocks, int dir) {
        int rows     = getRows();
        int colsOld  = getColumns();
        int colsNew  = colsOld + blocks;
        this.elements.ensureCapacity(rows * colsNew);

        if (dir < 0) {                 // expand left
            for (int r = 0; r < rows; r++) {
                int base = r * (colsOld + blocks);
                for (int n = 0; n < blocks; n++) elements.add(base, null);
            }
            plate.setBounds(plate.x - blocks * block.width, plate.y,
                            plate.width + blocks * block.width, plate.height);
        } else {                       // expand right
            for (int r = rows - 1; r >= 0; r--) {
                int insertPos = (r + 1) * colsOld + r * blocks;
                for (int n = 0; n < blocks; n++) elements.add(insertPos, null);
            }
            plate.setBounds(plate.x, plate.y,
                            plate.width + blocks * block.width, plate.height);
        }
    }


    // re-work (reason; O( n*n ) which could be resolved if one could specifiy copy-positions ( O(n) ) )
    private void verticalExpansion( int blocks, int direction ) {
        if( direction >= 0 ) {
            for( int n = 0; n < blocks*this.getColumns(); n++ ) {
                this.elements.add(null);
            }
            this.plate.setBounds( this.plate.x, this.plate.y, this.plate.width, this.plate.height + blocks*this.block.height);
        } else {
            for( int n = 0; n < blocks*this.getColumns(); n++ ) {
                this.elements.add(0, null);
            }
            this.plate.setBounds( this.plate.x, this.plate.y - blocks*this.block.height, this.plate.width, this.plate.height + blocks*this.block.height);
        } 
    }

    public void extend( Vector vector ) {
        if( !this.plate.contains( vector.toPoint2D() ) ){
            Vector rounded = this.snapToBlock( vector );

            // Horizontal expansion:
            int dirX = rounded.get( DimensionalList2D.X ) < 0 ? -1 : 1;
            if( dirX > 0 ){
                rounded = Vector.add( rounded, new Vector(-this.plate.width, 0) );
            }
            this.horizontalExpansion( Math.abs((int) (rounded.get( DimensionalList2D.X ) / this.block.getWidth())) , dirX );

            // Vertical expansion:
            int dirY = rounded.get( DimensionalList2D.Y ) < 0 ? -1 : 1;
            if( dirY > 0 ){
                rounded = Vector.add( rounded, new Vector( 0, -this.plate.height ));
            }
            this.verticalExpansion( Math.abs((int) (rounded.get( DimensionalList2D.Y ) / this.block.getHeight())) , dirY );
        }
    }

    // ==== Constructors ==== :

    public DimensionalList2D( Dimension block ) throws IllegalArgumentException{
        if( block.width != 0 && block.height != 0 ) {
            this.block = block;

        this.plate = new Rectangle(0, 0, this.block.width, this.block.height);
        this.elements = new ArrayList<>();
        } else {
            throw new IllegalArgumentException( "Blocks' area must be greater than 0" );
        }
    }

    public DimensionalList2D( Dimension block, Rectangle plate ) throws IllegalArgumentException{
        if( block.width*block.height != 0 && plate.width*plate.height != 0 ) {
            this.block = block;

            if( plate.width >= block.width && plate.height >= block.height ) {
                this.plate = new Rectangle(plate);
                this.plate.width -= this.plate.width % this.block.width;
                this.plate.height -= this.plate.height % this.block.height;
            } else {
                throw new IllegalArgumentException();
            }

            this.elements = new ArrayList<>( (this.plate.width / this.block.width)*(this.plate.height / this.block.height));
            for( int n = 0; n < (this.plate.width / this.block.width)*(this.plate.height / this.block.height); n++ ) {
                this.elements.add(null);
            }

        } else {
            throw new IllegalArgumentException( "Blocks & plates' areas must be greater than 0" );
        }
    }
}