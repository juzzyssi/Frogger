// ==== Package ==== :
package Math;

// ==== Generals ==== :
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;



public class Vector {

    // ==== Fields ==== :

    // Instances:
    private long[] projections;

    // ==== Methods ==== :

    // Concretes:

    // C.M.S. 0 :

    public static Vector add( Vector... vectors ) {
        ArrayList<Long> projections = new ArrayList<>( vectors[0].size() );

        for( Vector vector : vectors ) {
            for( int dimension = 0; dimension < vector.size(); dimension++ ) {
                if( dimension < projections.size() ) {
                    projections.set(dimension, projections.get(dimension).longValue() + vector.get( dimension ));
                } else {
                    projections.add( vector.get( dimension ) );
                }
            }
        }

        Vector out = new Vector( projections.size() );
        for( int dimension = 0; dimension < projections.size(); dimension++ ) {
            out.set( dimension, projections.get(dimension) );
        }

        return out;
    }

    public static boolean areCongruent( Vector... vectors ) {
        return Vector.areCongruent( Arrays.asList(vectors) );
    }

    public static boolean areCongruent( Collection<Vector> vectors ) {
        int s = -1;

        for( Vector vector : vectors ) {
            if( s == -1 ) {
                s = vector.size();
            } else {
                if( s != vector.size() ) {
                    return false;
                }
            }
        }
        return true;
    }

    // Instances:

    // I.M.S. 0 : "getters & setters".

    public int size(){
        return this.projections.length;
    }

    public long getAbs(){
        long sum = 0;
        for( long comp : this.projections ) {
            sum += comp*comp;
        }
        return (long) Math.sqrt(sum);
    }

    public long get( int dimension ) throws NullPointerException{
        if( 0 <= dimension && dimension < this.size() ) {
            return this.projections[ dimension ];
        } else {
            String cause = dimension < 0 ? String.format("%d exceeds %s dimensions", dimension, this.toString()) : String.format("%d cannot fall below 0", dimension);
            throw new NullPointerException( cause );
        }
    }

    public void set( int dimension, long projection ) {
        if( 0 <= dimension && dimension < this.size() ) {
            this.projections[dimension] = projection;
        } else {
            String cause = dimension < 0 ? String.format("%d exceeds %s dimensions", dimension, this.toString()) : String.format("%d cannot fall below 0", dimension);
            throw new NullPointerException( cause );
        }
    }

    public Vector flip(){
        Vector out = new Vector( this.size() );

        for( int dimension = 0; dimension < this.size(); dimension ++ ) {
            out.set( dimension, -this.get(dimension) );
        }
        return out;
    }

    public Vector scale( double scalar ){
        Vector out = new Vector(projections);
        for( int dimension = 0; dimension < this.size(); dimension++ ) {
            out.set(dimension, (long)(scalar*this.get(dimension)));
        }

        return out;
    }

    public double getAngle( Vector vector ) {
        if( vector.getAbs() != 0 && this.getAbs() != 0 ){
            Vector v1, v2;
            /* Ensuring both have comparable dimensions */
            if( vector.size() == this.size() ) {
                v1 = this;
                v2 = vector;
            } else if( vector.size() > this.size() ) {
                v2 = vector;
                v1 = new Vector( vector.size() );
                
                for( int dimension = 0; dimension < vector.size(); dimension++ ) {
                    v1.set(dimension, dimension >= this.size() ? 0 : this.get(dimension) );
                }
            } else {
                v2 = new Vector( this.size() );
                v1 = this;
                
                for( int dimension = 0; dimension < this.size(); dimension++ ) {
                    v2.set(dimension, dimension >= vector.size() ? 0 : vector.get(dimension) );
                }
            }

            /* Dot product */
            long compProduct = 0;
            for( int dimension = 0; dimension < v1.size(); dimension++ ) {
                compProduct += v1.get(dimension) * v2.get(dimension);
            }

            return Math.acos( compProduct / (v1.getAbs()*v2.getAbs()) );
        } else if( this.getAbs() == 0 ) {            
            throw new IllegalArgumentException( String.format("\"%s\"'s magnitude cannot be equal to zero", this.toString()) );
        } else {
            throw new IllegalArgumentException( String.format("\"%s\"'s magnitude cannot be equal to zero", vector.toString()) );
        }
    }

    @Override
    public String toString(){
        String projections = "";
        if( this.size() > 0 ) {
            for( int dimension = 0; dimension < this.size(); dimension++ ) {
                projections += "" + this.get(dimension) + ", ";
            }
            projections.substring(0, projections.length() - 3);
        }
        return "Vector[" + projections + "]";
    }

    public Point toPoint2D(){
        if( this.size() >= 2 ){
            return new Point( (int)(this.projections[0]), (int)(this.projections[1]) );
        } else {
            String cause = "\"" + this.toString() + "\" cannot have less than two dimensions";
            throw new IllegalArgumentException( cause );
        }
    }

    public Vector getUnitVector() {
        Vector out = new Vector( this.size() );

        long abs = this.getAbs();
        if( abs != 0 ) {
            out = this.scale( 1 / abs );
        }

        return out;
    }

    // ==== Constructors ==== :

    public Vector( int dimensions ) {
        this.projections = new long[ dimensions ];
    }
    public Vector( long ... projections ) {
        this.projections = new long[ projections.length ];
        for( int dimension = 0; dimension < projections.length; dimension++ ) {
            this.projections[ dimension ] = projections[ dimension ];
        }
    }
    public Vector( Vector vector ) {
        this.projections = new long[ vector.size() ];

        for( int dimension = 0; dimension < this.size(); dimension ++ ) {
            this.set( dimension, vector.get(dimension) );
        }
    }
}
