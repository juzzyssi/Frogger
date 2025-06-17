// ==== Package ==== :
package Model.model.subprimitives.interactives;

// ==== Generals ==== :
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;

import Model.model.primitives.interactives.Toy;
import Model.model.templates.dynamics.accelerators.Path;

import Math.Curves;
import Math.Vector;

import Util.StopWatch;
import Util.threads.ThreadElement;

import Engine.api.management.primitives.ApiManager;

// ==== Interfaces ==== :
import Engine.api.components.Continuous;
import Engine.api.components.Renderable;

import java.util.List;

// ==== Exceptions ==== :
import Engine.api.management.exceptions.IllegalApiParameterException;
import Util.threads.IllegalOrderException;



public abstract class Vehicle extends Toy{

    // ==== Fields ==== :

    /* CONCRETES: */
    protected static HashMap< Integer, List<Class<?>> > DEAFULT_ORDER;
    static{
        HashMap< Integer,  List<Class<?>> > temp = new HashMap<>(); 
        // ==== //
        ArrayList<Class<?>> order_0 = new ArrayList<>();
        order_0.add( Continuous.class );

        temp.put( 0, order_0 );
        // ==== //        
        ArrayList<Class<?>> order_1 = new ArrayList<>();
        order_1.add( Renderable.class );

        temp.put( 1, order_1 );
        // ==== //
        Vehicle.DEAFULT_ORDER = temp;
    }
    protected static final double DEAFULT_DT = 1;

    /* INSTANCES: */
    public Path path;
    protected ApiManager<Vehicle> apiManager;

    private boolean updated;

    // ==== Interfaces ==== :

    // Api bindable:
    @Override
    public <T> ThreadElement<T> toThreadElementOf( Class<T> clazz ) throws IllegalApiParameterException {
        return this.apiManager.getAs( clazz );
    }

    // Continuous :
    @Override
    public void checkIn( long time, Object... args ) {
        if( !this.updated ) {
            this.updated = true;
            this.path.checkIn( time, args );

            this.displace( path.getAccDisp() );
        }
    }

    @Override
    public void checkOut( long time, Object... args ) {
        if( this.updated ) {
            this.updated = false;
            this.path.checkOut( time, args );
        }
    }


    // ==== Methods ==== :

    // *** Concretes *** :
    public static double pickRandomSpeed( double[] limits ) {
        return ( limits[1] - limits[0] ) * Math.random() + limits[0];
    }

    public boolean isLooped() {
        return this.path.isLooped();
    }

    // ==== Constructors ==== :

    public Vehicle( Dimension hitbox, List<Vector> vectors, double[] speedLimits, double dt ) throws IllegalOrderException {
        super( vectors.get(0), hitbox );
        long nanoDuration = StopWatch.toNanoSec( Curves.getArcLengthEstimate(vectors, Vehicle.DEAFULT_DT) / Vehicle.pickRandomSpeed( speedLimits ) );
        this.path = new Path( vectors, true, System.nanoTime(), nanoDuration, nanoDuration*Math.random() );
        this.apiManager = new ApiManager<Vehicle>( Vehicle.DEAFULT_ORDER, this );

        this.updated = false;
    }
}
