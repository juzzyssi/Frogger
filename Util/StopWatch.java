package Util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Engine.api.components.Continuous;

public class StopWatch implements Continuous{
    
    // ==== Fields ==== :

    private LinkedList<Long> lapCalls;

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

    public static long toNanoSec( double time ) {
        return (long) (time * Math.pow( 10, 9));
    }
    public static double toSec( long time ) {
        return time * Math.pow( 10, -9); 
    }

    public long getCallDifference() {
        return this.newCall - this.prevCall;
    }

    public void declareLap() {
        this.lapCalls.add( System.nanoTime() );
    }

    /*  Returns a "lap call" from earliest to oldest.
     * 
    */
    public long getLap( int index ) {
        return this.lapCalls.get( index ).longValue();
    }

    public List<Long> getLaps() {
        return new ArrayList<>( this.lapCalls );
    }

    public long getLastLap() {
        return this.lapCalls.getLast();
    }

    public long getDurationSince( int lap ) {
        return System.nanoTime() - this.getLap( lap );
    }

    // ==== Constructors ==== :

    public StopWatch( long time ) {
        this.prevCall = time;
        this.newCall = time;
        this.updated = false;

        this.lapCalls = new LinkedList<>();
        this.lapCalls.add( this.newCall );
    }
}
