package Math;

import java.awt.Point;

/*  Vectors... what else can I say? Designed as utility for dynamic-type objects?
 */
public class Vector {

    // ==== Fields ==== :

    // Instances:
    private double[] projections;

    // Concretes:
    public static final int X = 0, Y = 1;



    // ==== Methods ==== :

    // Concretes:
    public Vector addVectors( Vector vector1, Vector vector2 ){
        /* Auxiliary */
        return new Vector( vector1, vector2 );
    }

    // Instances:
    public double getTheta(){                       // I.M.S. 0 ( interpreting )
        return Math.atan2( this.projections[Y], this.projections[X] );
    }
    public double getMagnitude(){
        return Math.hypot(this.projections[X], this.projections[Y]);
    }
    public double getX(){
        return this.projections[X];
    }
    public double getY(){
        return this.projections[Y];
    }

    public void add( Vector vector ){               // I.M.S. 1 ( transforming )
        this.projections[X] += vector.projections[X];
        this.projections[Y] += vector.projections[Y];
    }
    public void subtract( Vector vector ){
        this.projections[X] -= vector.projections[X];
        this.projections[Y] -= vector.projections[Y];
    }
    public void scale( double scalar ){
        this.projections[X] *= scalar;
        this.projections[Y] *= scalar;
    }
    public void divide( double scalar ){
        this.projections[X] /= scalar;
        this.projections[Y] /= scalar;
    }

    public void flip(){
        this.projections[X] = -this.projections[X];
        this.projections[Y] = -this.projections[Y];
    }
    public void set( double x, double y ){
        this.projections[X] = x;
        this.projections[Y] = y;
    }

    public Vector copy(){
        return new Vector( this.projections[X], this.projections[Y] );
    }

    @Override
    public String toString(){
        return String.format( "x=%f, y=%f", this.projections[X], this.projections[Y] );
    }
    public Point toPoint(){
        return new Point( (int)(this.projections[X]), (int)(this.projections[Y]) );
    }



    // ==== Constructors ==== :

    public Vector( int projectionAtX, int projectionAtY ){
        this.projections[X] = (double)(projectionAtX);
        this.projections[Y] = (double)(projectionAtY);
    }
    public Vector( double projectionAtX, double projectionAtY ){
        this.projections[X] = projectionAtX;
        this.projections[Y] = projectionAtY;
    }
    public Vector( Vector vector1, Vector vector2 ){
        this.projections[X] = vector1.projections[X] + vector2.projections[X];
        this.projections[Y] = vector1.projections[Y] + vector2.projections[Y];
    }
}
