package Math;

import java.util.ArrayList;

/*  Best suited for dynamic scenarios: stores both Vector and ComposedVector (shallow) instance references to provide practical
 *  real-time results.
 */
public class ComposedVector {

    // ==== Fields ==== :

    private ArrayList<Object> components;

    public Vector vectorNET;



    // ==== Methods ==== :

    // Instances:
    public <T> void addComponent( T vector ){
        if( vector instanceof Vector || vector instanceof ComposedVector ){
            this.components.add( (Object) vector );
        }
        else{
            throw new IllegalArgumentException("Only Vector or ComposedVector instances are allowed in a ComposedVector instance");
        }
    }
    public <T> void removeComponent( T vector ){
        if( vector instanceof Vector || vector instanceof ComposedVector ){
            this.components.remove( (Object) vector );
        }
        else{
            throw new IllegalArgumentException("Only Vector or ComposedVector instances are allowed in a ComposedVector instance");
        }
    }

    public void updateVectorNET(){
        this.vectorNET.set(0, 0);
        for( Object i : this.components ){
            if( i instanceof Vector ){
                this.vectorNET.add( (Vector) i );
            }
            else{
                ( (ComposedVector) i ).updateVectorNET();
                this.vectorNET.add( ( (ComposedVector) i ).vectorNET );
            }
        }
    }


    // ==== Constructors ==== :

    public ComposedVector(){
        this.vectorNET = new Vector(0, 0);
        this.components = new ArrayList<>(0);
    }
    public <T> ComposedVector( T vector ){
        this.vectorNET = new Vector(0, 0);
        this.components = new ArrayList<>(0);

        if( vector instanceof Vector || vector instanceof ComposedVector ){
            this.components.add( (Object) vector );
        }
        else{
            throw new IllegalArgumentException("Only Vector or ComposedVector instances are allowed in a ComposedVector instance");
        }       
    }
    public ComposedVector( ArrayList<Object> vectors ){
        this.vectorNET = new Vector(0, 0);
        this.components = new ArrayList<>(0);

        for( Object v : vectors ){
            if( !(v instanceof Vector || v instanceof ComposedVector) ){
                throw new IllegalArgumentException("Only Vector or ComposedVector instances are allowed in a ComposedVector instance");
            }
        }
        this.components = vectors;
    }
}
