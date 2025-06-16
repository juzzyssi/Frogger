// ==== Package ==== :
package Math;

// ==== Generals ==== :
import java.util.List;



public class Curves{

    // ==== Fields ==== :

    // *** Concretes *** :
    public static final double ARC_LENGTH_DT = 0.05;

    // ==== Methods ==== :

    // Concretes:
    public static int factorial( int n ){
        return n > 0 ? factorial( n - 1 ) * n : 1;
    }
    public static int binomialCoefficient( int n, int k ){
        return factorial(n) / ( factorial(n) * factorial( n - k ) );
    }

    public static Vector bezierCurve( List<Vector> vectors, double time ) throws IllegalArgumentException{
        if( vectors.size() > 1 && Vector.areCongruent( vectors ) ) {
            time = time > 1.0 ? 1.0 : time < 0.0 ? 0.0 : time;

            if( vectors.size() == 2 ) {

                Vector dVector = Vector.add( vectors.get(1), vectors.get(0).flip() );
                return Vector.add( vectors.get(0), dVector.scale( time ) );
            
            } else {            
                int n = vectors.size() - 1;
                Vector temp = new Vector( vectors.get(0).size() ), out = new Vector( vectors.get(0).size() );

                for( int k = 0 ; k <= n ; k++ ){
                    for( int u = 0; u < n + 1; u++ ) {
                        temp.set( u, 
                            vectors.get( k ).get( u ) * (long) ( (double) binomialCoefficient(n, k) * ( Math.pow(time, (double) k) * Math.pow(1.0 - time, (double)(n - k) ) ) )
                        );                    
                    }
                    out = Vector.add( out, temp );
                }

                return out;
            }
        } else {
            throw new IllegalArgumentException( "The collection of vectors isn't congruent" );
        }
    }

    public static long getArcLengthEstimate( List<Vector> vectors, double dt ) throws IllegalArgumentException {
        if( 0.0 <= dt && dt <= 1.0 ) {
            long l = 0;

            for( double it = 0.0; it <= 1.0 - dt; it += dt ) {
                l += Vector.add( Curves.bezierCurve(vectors, it + dt), Curves.bezierCurve(vectors, it).flip() ).getAbs();
            }

            return l;
        } else {
            throw new IllegalArgumentException( "dt must exists within the interval [1, 0]");
        }
    }
}
