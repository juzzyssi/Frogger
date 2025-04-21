package Math;

import java.awt.Point;
import java.util.ArrayList;

/*
 *  Cuvers (bezier curves): you could compose with a field a sub-instance list of the same class from which to store decomposed curves
 *  such that any call to eh overall time is only divided across this guys.
 */
public class Curves{

    // ==== Methods ==== :

    // Concretes:
    public static int factorial( int n ){
        return n > 0 ? factorial( n - 1 ) * n : 1;
    }
    public static int binomialCoefficient( int n, int k ){
        return factorial(n) / ( factorial(n) * factorial( n - k ) );
    }
    public static double bernsteinPolynomial( double[] doubles, double time ){
        if( 0 <= time && time <= 1 ){
            double out = 0;
            int n = doubles.length;

            for( int k = 0 ; k <= n ; k++ ){
                out += (double) binomialCoefficient(n, k) * ( Math.pow( time, (double) k )* Math.pow(1.0 - time, (double)(n - k) ) );
            }
            return out;
        }
        else{
            throw new IllegalArgumentException("time must be an element of [0, 1]");
        }
    }
    public static Point bezierCurve( ArrayList<Point> points, double time ){
        double[] x = new double[ points.size() ];
        double[] y = new double[ points.size() ];

        for( int i = 0 ; i < points.size() ; i++ ){
            x[i] = points.get(i).getX();
            y[i] = points.get(i).getY();
        }
        
        return new Point(
            (int) bernsteinPolynomial( x, time),
            (int) bernsteinPolynomial( y, time)
        );
    }
}
