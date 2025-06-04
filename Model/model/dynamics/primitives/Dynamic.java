package Model.model.dynamics.primitives;

import Engine.api.components.ContinuumIntegration;
import Math.Vector;
import Model.model.dynamics.accelerators.AcceleratorRegistery;

public abstract class Dynamic implements ContinuumIntegration{

    // ==== Fields ==== :

    // *** Instances *** :
    private AcceleratorRegistery accelerators;
    private Vector disp;

    // Continuum integration:
    private boolean updated;

    // ==== Interfaces ==== :

    // Continuum integration:
    @Override
    public void checkIn( long time, Object ... args ) {
        if( !this.updated ) {
            this.updated = true;
            this.accelerators.checkIn( time, args );

            this.displace( this.accelerators.getDisp() );
        }
    }
    
    @Override
    public void checkOut( long time, Object ... args ) {
        if( this.updated ) {
            this.updated = false;
            this.accelerators.checkOut( time, args );
        }
    }


    // ==== Methods ==== :

    public Vector getDisplacement() {
        return new Vector( this.disp );
    }

    public void displace( Vector vector ) {
        this.disp = Vector.add( this.disp, vector );
    }

    // ==== Constructors ==== :
    public Dynamic( Vector disp ) {
        this.disp = disp;
    }

}
