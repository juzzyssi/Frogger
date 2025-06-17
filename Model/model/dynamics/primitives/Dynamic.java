// ==== Package ==== :
package Model.model.dynamics.primitives;

// ==== Generals ==== :
import Math.Vector;

// ==== Interfaces ==== :
import Engine.api.components.Continuous;
import Model.model.dynamics.api.Accelerator;



public abstract class Dynamic implements Continuous{

    // ==== Fields ==== :

    /* INSTANCES: */
    protected AcceleratorRegistery accelerators;
    private Vector disp;

    // Continuous:
    private boolean updated;

    // ==== Interfaces ==== :

    // Continuous:
    @Override
    public void checkIn( long time, Object ... args ) {
        if( !this.updated ) {
            this.updated = true;

            this.displace( this.accelerators.getAccDisp() );
        }
    }
    
    @Override
    public void checkOut( long time, Object ... args ) {
        if( this.updated ) {
            this.updated = false;
            this.accelerators.executeRemovalQueue();
        }
    }

    // ==== Methods ==== :

    /* INSTANCES: */
    public Vector getDisplacement() {
        return new Vector( this.disp );
    }

    public void setDisplacement( Vector vector ) {
        this.disp = vector;
    }

    public void displace( Vector vector ) {
        this.disp = Vector.add( this.disp, vector );
    }

    public void queueRemoval( Accelerator acc ) {
        this.accelerators.queueRemoval(acc);
    }

    // ======== Accelerators Managemenet ======== :

    public void addAccelerator( Accelerator acc ) {
        this.accelerators.add( acc );
    }

    public void queueAcceleratorRemoval( Accelerator acc ) {
        this.accelerators.queueRemoval(acc);
    }

    public boolean hasAccelerator( Accelerator acc ) {
        return this.accelerators.contains( acc );
    }

    // ==== Constructors ==== :
    public Dynamic( Vector disp ) {
        this.accelerators = new AcceleratorRegistery();
        this.disp = disp;
    }

}
