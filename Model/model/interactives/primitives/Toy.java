package Model.model.interactives.primitives;

import Engine.api.management.ifaces.ApiBindable;
import Math.Vector;
import Model.model.dynamics.primitives.Dynamic;
import Model.model.interactives.api.Interactivity;

public abstract class Toy extends Dynamic implements Interactivity, ApiBindable {

    // ==== Fields ==== :

    // ==== Methods ==== :

    // ==== Constructors ==== :
    public Toy( Vector vector ) {
        super( vector );
    }
}
