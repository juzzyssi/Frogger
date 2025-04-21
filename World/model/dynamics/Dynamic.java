// ==== Package ==== :
package World.model.dynamics;

// ==== General ==== :
import java.util.ArrayList;

import java.awt.Point;

import Math.Vector;

// ==== Interfaces ==== :
import World.api.LoopIntegration;



/*  Objects of the class Dynamic--or any extension--are suited with functionality & processes that are responsive to 
 *  internal time changes that are specified by a given "time" parameter, which must work as an independent stopwatch.
 * 
 *  The time must be in nano seconds
 * 
 *  Such features, for the sake of completeness, are:
 *  - (Vector) acceleration in pixels per second... squared?
 *  - (Vector) velocity in pixels per second.
 *  - (Vector) displaacement in pixels.
 */
public class Dynamic implements LoopIntegration{

    // ==== Fields ==== :

    // Instances:
    private ArrayList<Vector> accList = new ArrayList<>(0); // acceleration
    public Vector accNET = new Vector(0.0, 0.0);
    public boolean accSweeper = true;

    private ArrayList<Vector> velList = new ArrayList<>(0); // velocity
    public Vector velNET = new Vector(0.0, 0.0);
    public boolean velSweeper = false;

    private ArrayList<Vector> disList = new ArrayList<>(0); // displacement
    public Vector disNET = new Vector(0.0, 0.0);
    public boolean disSweeper = false;

    private long internalTime = System.nanoTime();  // time
    private double elapsedTime = 0;



    // ==== Interfaces ==== :  ( Find documentation here: World > api )

    // Dynamism:
    @Override
    public void update( long time ){
        /* Must be called previous to any renderization */
        this.elapsedTime = (double)(time - this.internalTime)*1e-9; // in seconds
        this.internalTime = time;

        this.calculate();
        this.sweep();
    }



    // ==== Methods ==== :

    // Instances:
    public void setSweepers( boolean acc, boolean vel, boolean dis ){
        this.accSweeper = acc;
        this.velSweeper = vel;
        this.disSweeper = dis;
    }
    public void sweep(){
        if( this.accSweeper ){
            this.accNET.set(0.0, 0.0);
        }
        if( this.velSweeper ){
            this.velNET.set(0.0, 0.0);
        }
        if( this.disSweeper ){
            this.disNET.set(0.0, 0.0);
        }
    }
    public void sweep( boolean acc, boolean vel, boolean dis ){
        if( acc ){
            this.accNET.set(0.0, 0.0);
        }
        if( vel ){
            this.velNET.set(0.0, 0.0);
        }
        if( dis ){
            this.disNET.set(0.0, 0.0);
        }
    }

    public void addAcceleration( Vector vector ){
        this.accList.add(vector);
    }
    public void addVelocity( Vector vector ){
        this.velList.add(vector);
    }
    public void addDisplacement( Vector vector ){
        this.disList.add(vector);
    }

    private void calculate(){

        // ==== Acceleration ==== :

        if( this.accList.size() > 0 ){
            for( Vector v : this.accList ){
                this.accNET.add( v );
            }
            this.accList.clear();
        }

        // append the integration to velocity
        this.accNET.scale(this.elapsedTime);
        this.velList.add( accNET.copy() );
        this.accNET.divide(this.elapsedTime);

        // ==== Velocity ==== :

        if( this.velList.size() > 0 ){
            for( Vector v : this.velList ){
                this.velNET.add( v );
            }
            this.velList.clear();
        }

        // append the integration to displacement
        this.velNET.scale(this.elapsedTime);
        this.disList.add( velNET.copy() );
        this.velNET.divide(this.elapsedTime);

        // ==== Displacement ==== :

        if( this.disList.size() > 0 ){
            for( Vector v : this.disList ){
                this.disNET.add( v );
            }
            this.disList.clear();
        }
    }

    // Concretes:
    public static Vector PointToVector( Point point ){
        return new Vector( point.getX(), point.getY() );
    }



    // ==== Constructors ==== :
    
    public Dynamic(){
    }
    public Dynamic( Vector accNET, Vector velNET, Vector disNET ){
        this.accNET = accNET;
        this.velNET = velNET;
        this.disNET = disNET;
    }
}
