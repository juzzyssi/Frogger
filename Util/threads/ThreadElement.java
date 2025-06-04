// ==== Package ==== :
package Util.threads;



public class ThreadElement<T> { /* Auxiliary class */
   
    // ==== Fields ==== :

    // Instances:
    private T object;
    private int order;

    // ==== Methods ==== :

    // Intances:

    // I.M.S. 0 : "getters" & "setter".
    
    public int getOrder(){
        return this.order;
    }

    public T getObject(){
        return this.object;
    }

    public void setOrder( int order ){
        this.order = order;
    }

    public void setObject( T object ){
        this.object = object;
    }

    @Override
    public String toString() {
        return String.format( "ThreadElement[ order=%d, object=%s ]", this.order, this.object.toString() );
    }

    // ==== Constructors ==== : 
    
    public ThreadElement( T object, int order ) throws IllegalOrderException{
        this.object = object;
        if( 0 > order ) {
            throw new IllegalOrderException("Order cannot be lower than 0");
        }
        this.order = order;
    }
}
