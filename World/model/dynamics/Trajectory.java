// ==== Package ==== :
package World.model.dynamics;

// ==== General ==== :
import java.awt.Point;
import java.util.ArrayList;

import Math.Curves;
import Math.Vector;
import World.api.engine.LoopIntegration;



/* Time is in nano-seconds, points must be arranged from first to last */
public class Trajectory implements LoopIntegration{

    // ==== Fields ==== :

    // Instances:
    private ArrayList<Point> points; /* Path generation */

    private long internalTime, duration, offset; /* LoopIntegration fields */
    private boolean updated = false;

    public Vector position; /* Anchorable-functionality field */
    


    // ==== Interfaces ==== : ( Find documentation here: World > api )

    // LoopIntegration:
    @Override
    public void checkIn( long time ){
        if( !this.updated ){
            this.updated = true;
            
            this.internalTime = time;
            this.position.set( this.getAt(this.internalTime) );
        }
    }
    @Override
    public void checkOut( long time ){
        if( this.updated ){
            this.updated = false;
        }
    }



    // ==== Methods ==== :

    // Instances:
    public Vector getAt( long time ){                                     // I.M.S. 0 ()

        double dTime = ((time + this.offset) % this.duration) / (double) this.duration; /* Constraints time */

        double[] x = new double[ this.points.size() ];
        double[] y = new double[ this.points.size() ];

        for( int i = 0 ; i < this.points.size() ; i++ ){
            x[i] = this.points.get(i).getX();
            y[i] = this.points.get(i).getY();
        }

        return new Vector( Curves.bernsteinPolynomial(x, dTime), Curves.bernsteinPolynomial(y, dTime) );
    }



    // ==== Constructors ==== :

    public Trajectory( ArrayList<Point> points, double duration, double offset ){
        /* duration & offsegt are given in secs */
        this.duration = (long) (duration*Math.pow(10, 9));
        this.offset = (long) (offset*Math.pow(10, 9));
        this.internalTime = System.nanoTime();

        this.points = points;

        // Initial setting:

        Point temporal = Curves.bezierCurve( this.points, (double) ( ((this.internalTime + this.offset) % this.duration) / this.duration) );
        this.position = new Vector( temporal.getX(), temporal.getY() );
    }
    public Trajectory( Point point1, Point point2, double duration, double offset ){
        /* duration & offsegt are given in secs */
        this.duration = (long) (duration*Math.pow(10, 9));
        this.offset = (long) (offset*Math.pow(10, 9));
        this.internalTime = System.nanoTime();

        this.points = new ArrayList<>(2);
        this.points.set(0, point1);
        this.points.set(1, point2);

        // Initial setting:

        Point temporal = Curves.bezierCurve( this.points, (double) ( ((this.internalTime + this.offset) % this.duration) / this.duration) );
        this.position = new Vector( temporal.getX(), temporal.getY() );
    }
}