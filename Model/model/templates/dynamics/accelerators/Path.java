// ==== Package ==== ::
package Model.model.templates.dynamics.accelerators;

// ==== Generals ==== :
import Util.StopWatch;
import Util.threads.IllegalOrderException;

import Math.Curves;
import Math.Vector;


import java.util.ArrayList;
import java.util.HashMap;

// ==== Interfaces ==== :
import Engine.api.components.Continuous;

import Model.model.dynamics.api.Accelerator;

import java.util.List;



public class Path implements Accelerator, Continuous{

    // ==== Fields ==== :

    /* CONCRETES: */
    protected static HashMap< Integer, List<Class<?>> > DEAFULT_ORDER;
    static{
        HashMap< Integer,  List<Class<?>> > temp = new HashMap<>();        
        
        ArrayList<Class<?>> order_0 = new ArrayList<>();
        order_0.add( Continuous.class );

        temp.put( 0, order_0 );

        Path.DEAFULT_ORDER = temp;
    }
    

    /* INSTANCES: */
    private List<Vector> vectors;
    private StopWatch stopwatch;
    private long duration, offset;

    private boolean updated, looped;

    // ==== Interfaces ==== :

    // Continuous:

    @Override
    public void checkIn( long time, Object... args ) {
        if( !this.updated ) {
            this.updated = true;

            this.stopwatch.checkIn(time, args);
        }
    }

    @Override
    public void checkOut( long time, Object... args ) {
        if( this.updated ) {
            this.updated = false;

            this.stopwatch.checkOut( time, args );
        }
    }

    // Accelerator:

    @Override
    public Vector getAccDisp() {

        /* Progress: */
        double p;
        if( this.looped ) {
            p = (( this.stopwatch.getDurationSince( 0 ) + this.offset) % this.duration) / (double) this.duration;
        } else {
            p = this.stopwatch.getDurationSince( 0 ) + this.offset / (double) this.duration;
        }

        /* Special case: */
        if( this.vectors.size() == 2 ) {

            Vector v1 = this.vectors.get( 0 );
            Vector v2 = this.vectors.get( 1 );
            Vector v1to2 = Vector.add( v2, v1.flip() );

            long x = v1.get( 0 ) + (long)(p*v1to2.get(0));
            long y = v1.get( 1 ) + (long)(p*v1to2.get(1));

            return new Vector( x, y );
        
        /* Smooth curves: */
        } else {
            return Curves.bezierCurve( this.vectors, p );
        }
    }

    // ==== Methods ==== :

    /* INSTANCES: */
    public boolean isLooped() {
        return this.looped;
    }

    // ==== Constructors ==== :

    public Path( List<Vector> vectors, boolean looped, long initNanoTime, double duration, double offset ) throws IllegalOrderException {
        this.stopwatch = new StopWatch( initNanoTime );
        this.vectors = vectors;
        for( Vector vector : this.vectors ) {
            if( vector == null ) {
                this.vectors.remove( vector );
            }
        }
        this.updated = false;
        this.duration = StopWatch.toNanoSec( duration );
        this.offset = StopWatch.toNanoSec( offset );
        this.looped = looped;
    }

}
