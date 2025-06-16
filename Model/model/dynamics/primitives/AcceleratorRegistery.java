// ==== Package ==== :
package Model.model.dynamics.primitives;

import java.util.Collection;
// ==== General ==== :
import java.util.HashSet;

import Math.Vector;

// ==== Interfaces ==== :
import Model.model.dynamics.api.Accelerator;



public class AcceleratorRegistery extends HashSet<Accelerator> implements Accelerator {

    // ==== Fields ==== :

    protected boolean reset;
    protected Collection<Accelerator> queue;
    
    // ==== Interfaces ==== :

    // Accelerator:
    @Override
    public Vector getAccDisp() {
        Vector out = new Vector(0, 0);

        for( Accelerator acc : this ) {
            out = Vector.add( out, acc.getAccDisp() );
        }

        if( this.reset ) {
            this.clear();
        }
        return out;
    }

    // ==== Methods ==== :

    public void setReset( boolean state ) {
        this.reset = state;
    }

    public void queueRemoval( Accelerator acc ) {
        this.queue.add(acc);
    }

    public void executeRemovalQueue( ) {
        this.removeAll( this.queue );
        this.queue.clear();
    }

    // ==== Constructors ==== :
    public AcceleratorRegistery() {
        super();
        this.reset = true;
        this.queue = new HashSet<>();
    }
}
