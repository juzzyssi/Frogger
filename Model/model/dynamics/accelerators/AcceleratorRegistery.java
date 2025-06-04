package Model.model.dynamics.accelerators;

import java.util.Set;

import Math.Vector;
import Model.model.dynamics.api.Accelerator;
import Util.StopWatch;

public class AcceleratorRegistery implements Accelerator{

    // ==== Fields ==== :

    private StopWatch stopWatch;
    private boolean updated;

    private Set<Accelerator> accelerators;

    // ==== Methods ==== :

    @Override
    public Vector getDisp() {
        Vector out = new Vector(0, 0);
        for( Accelerator acc : this.accelerators ) {
            out = Vector.add( out, acc.getDisp() );
        }

        return out;
    }

    // ==== Interfaces ==== :

    // Continuum integration:
    @Override
    public void checkIn( long time, Object... args ) {
        if( !this.updated ) {
            this.updated = true;
            this.stopWatch.checkIn( time, args );

            for( Accelerator acc : this.accelerators ) {
                acc.checkIn( time, args );
            }
        }
    }

    @Override 
    public void checkOut( long time, Object... args ) {
        if( this.updated ) {
            this.updated = false;
            this.stopWatch.checkOut( time, args );

            for( Accelerator acc : this.accelerators ) {
                acc.checkOut( time, args );
            }
        }
    }

    // ==== Constructors ==== :
    public AcceleratorRegistery( long time ) {
        this.updated = false;
        this.stopWatch = new StopWatch( time );
    }
}
