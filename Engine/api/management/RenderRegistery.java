// ==== Package ==== :
package Engine.api.management;

// ==== General ==== :
import java.awt.Graphics;

import Graphics.Camera;

// ==== Interfaces ==== :
import Engine.api.components.Renderable;
import Engine.api.management.exceptions.IllegalApiParameterException;
import Engine.api.management.ifaces.ApiBindable;
import Engine.api.management.primitives.ApiRegistery;



public class RenderRegistery extends ApiRegistery<Renderable> implements Renderable{
    
    // ==== Interfaces ==== :

    // Renderable:

    /*  Calls--in layer-based order--each instance to be rendered;
     *  O( n*i ) where n is the total size of the registery and i the algorithm of each element.
     */
    @Override
    public void render( Graphics g, Camera camera ) {
        for( Integer layer : this.keySet() ){
            for( Renderable object : this.get( layer ) ){
                object.render(g, camera);
            }            
        }
    }

    // ==== Interfaces ==== :

    @Override
    public boolean contains( ApiBindable object ) throws IllegalApiParameterException {
        return this.contains( object.toThreadElementOf( Renderable.class ) );
    }

    @Override
    public void add( ApiBindable object ) throws IllegalApiParameterException{
        this.add( object.toThreadElementOf( Renderable.class ) );
    }

    @Override
    public void queueRemoval( ApiBindable object ) throws UnsupportedOperationException, IllegalApiParameterException{
        this.queueRemoval( object.toThreadElementOf(Renderable.class ) );
    }

    // ==== Constructor ==== :

    public RenderRegistery(){
        super();
    }
}