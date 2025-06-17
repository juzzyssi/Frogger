// ==== Package ==== : 
package Engine.api.management;

import Engine.api.management.exceptions.IllegalApiParameterException;
import Engine.api.management.ifaces.ApiBindable;
// ==== Interfaces ==== :
import Engine.api.management.primitives.ApiRegistery;
import Engine.api.components.Continuous;



public class ContinuumRegistery extends ApiRegistery<Continuous> implements Continuous{

    // ==== Fields ==== :

    private boolean updated;


    // ==== Interfaces ==== :

    @Override
    public void checkIn(long time, Object... args) {
        if( !this.updated ){
            for( Integer layer : this.keySet() ){
                for( Continuous object : this.get( layer ) ){
                    object.checkIn( time, args );
                }
            }
            this.updated = true;
        }
    }

    @Override
    public void checkOut(long time, Object... args) {
        if( this.updated ){
            for( Integer layer : this.keySet() ){
                for( Continuous object : this.get( layer ) ){
                    object.checkOut( time, args );
                }
            }
            this.updated = false;
        }
    }

    // ==== Interfaces ==== :

    @Override
    public boolean contains( ApiBindable object ) throws IllegalApiParameterException {
        return this.contains( object.toThreadElementOf( Continuous.class ) );
    }

    @Override
    public void add( ApiBindable object ) throws IllegalApiParameterException {
        this.add( object.toThreadElementOf(Continuous.class) );
    }

    @Override
    public void queueRemoval( ApiBindable object ) throws IllegalApiParameterException {
        this.queueRemoval( object.toThreadElementOf( Continuous.class ) ); 
    }

    // ==== Constructors ==== :

    public ContinuumRegistery(){
        super();
        this.updated = false;
    }
}
