// ==== Package ==== :
package Engine.api.management.primitives;

// ==== General ==== :
import java.util.Collection;


import Engine.api.management.ifaces.RegisteryBinding;

import java.util.Arrays;

import Util.threads.ThreadElement;
import Util.threads.ThreadRegistery;



public abstract class ApiRegistery<T> extends ThreadRegistery<T> implements RegisteryBinding{

    // ==== Fields ==== :
    
    // Instances:
    private ThreadRegistery<T> removalQueue = new ThreadRegistery<>();
    
    // ==== Methods ==== :

    // I.M.S. 0 : Queue removals.

    /*  Queues an instance for later removal;
     *  O( Lg(n) ) where n is the size of the queue.
     */
    public void queueRemoval( ThreadElement<T> object ){
        if( this.contains(object) && !removalQueue.contains(object) ){
            this.removalQueue.add(object);
        }
    }

    /*  Queues a collection for later removal;
     *  O( nLg(n) ) where n is the size of the collecction.
     */
    public void queueRemoval( Collection<ThreadElement<T>> objects ){
        this.removalQueue.add( objects );
    }

    @SuppressWarnings("unchecked")
    public void queueRemoval( ThreadElement<T> ... objects ){
        this.queueRemoval( Arrays.asList(objects) );
    }

    /*  
     * 
     */
    public boolean queueContains( ThreadElement<T> object ) {
        return this.removalQueue.contains( object );
    }

    // I.M.S. 1 : Removing.

    /*  
     * 
     */
    public void executeRemovalQueue(){
        this.remove( this.removalQueue );

        this.clean();
        removalQueue.clear();
    }

    // ==== Constructors ==== :

    public ApiRegistery(){
        this.removalQueue = new ThreadRegistery<>();
    }
}