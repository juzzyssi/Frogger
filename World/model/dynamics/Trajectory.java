// ==== Package ==== :
package World.model.dynamics;

// ==== General ==== :
import java.awt.Point;
import java.util.ArrayList;

import Math.Curves;
import Math.Vector;

// ==== Interfaces ==== :
import World.api.LoopIntegration;



/*  If I ever need to introduce extra utility. (I planned to, but some of it is currently irrelevant)
 */
public class Trajectory implements LoopIntegration{

    // Add something like intended velocity and based on that, make arrangements for "time" in order to reconciliate it 
    // with the standard bernsteinPol approach ( tE[0, 1] ) and the estimated duration.

    // ==== Fields ==== :

    // Instances:
    private ArrayList<Point> points;

    /* LoopIntegration-functionality fields */
    private long internalTime = System.nanoTime();
    private long previousInternalTime = 0;
    
    private double ellapsedTime = 0.0;
    private double previousEllapsedTime  = 0.0;

    public double duration;

    


    // ==== Interfaces ==== : ( Find documentation here: World > api )

    // LoopIntegration:
    @Override
    public void update( long time ){
        /* See the "differentiation" method for reference */
        this.previousEllapsedTime = this.ellapsedTime;
        this.ellapsedTime = time - this.internalTime;

        this.previousInternalTime = internalTime;
        this.internalTime = time;
    }



    // ==== Methods ==== :

    // Instances:
    public Vector getAt( double time ){ // returns a vector from 

        time /= this.duration;
        time = 0 > time ? 0 : (time > 1 ? 1 : time);

        double[] x = new double[ this.points.size() ];
        double[] y = new double[ this.points.size() ];

        for( int i = 0 ; i < this.points.size() ; i++ ){
            x[i] = this.points.get(i).getX();
            y[i] = this.points.get(i).getY();
        }

        return new Vector( Curves.bernsteinPolynomial(x, time), Curves.bernsteinPolynomial(y, time) );
    }

    public Vector differentiate( double time ){
        /*  The problem with calculating a non-limit derivate of a given curve, is that over longer periods of time
         *  the discrepances between the real value of the derivate AND the called vector become substantial;
         * 
         *  To reduce this, based on previous differences of time not only can we estimate when the next call will be and thus,
         *  the difference of vectors; but we can also compare it with previous "ellapsed time" (ellapsedTimeDifference) and add
         *  back the discrepancy as corrector vector.
         * 
         *  That way, over even longer periods of time, the only downfalls to the algorithm
         *  are those introduced by doubles decimal discrepancies.
         */
        Vector difference = this.getAt(time + this.ellapsedTime);
        difference.subtract( this.getAt(time) );

        Vector tweak = this.getAt( this.internalTime );
        tweak.subtract( this.getAt( this.previousInternalTime + this.previousEllapsedTime ) );

        difference.add(tweak);
        return difference;
    }


    // ==== Constructors ==== :

    public Trajectory( ArrayList<Point> points, double duration ){
        this.duration = duration;
        this.points = points;
    }
}
