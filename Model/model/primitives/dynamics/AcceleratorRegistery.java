// ==== Package ==== :
package Model.model.primitives.dynamics;

import java.util.Collection;
// ==== General ==== :
import java.util.HashSet;

import Math.Vector;



public class AcceleratorRegistery extends HashSet<Accelerator> implements Accelerator {

    // ==== Fields ==== :

    protected Collection<Accelerator> queue;
    
    // ==== Interfaces ==== :

    // Accelerator:
    @Override
    public Vector getAccDisp() {
        Vector out = new Vector(0, 0);

        for( Accelerator acc : this ) {
            out = Vector.add( out, acc.getAccDisp() );
        }

        return out;
    }

    // ==== Methods ==== :

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
        this.queue = new HashSet<>();
    }
}
