package Util;

import Engine.api.components.ContinuumIntegration;

public class StopWatch implements ContinuumIntegration{
    
    // ==== Fields ==== :

    private long prevCall;
    private long newCall;

    private boolean updated;

    // ==== Interfaces ==== :

    public void checkIn( long time, Object ... args ) {
        if( !this.updated ) {
            this.updated = true;

            this.prevCall = this.newCall;
            this.newCall = time;
        }
    }
    
    public void checkOut( long time, Object ... args ) {
        if( this.updated ) {
            this.updated = false;
        }
    }

    // ==== Methods ==== :

    public long getCallDifference() {
        return this.newCall - this.prevCall;
    }

    // ==== Constructors ==== :

    public StopWatch( long time ) {
        this.prevCall = time;
        this.newCall = time;
        this.updated = false;
    }
}
