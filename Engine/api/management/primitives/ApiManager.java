// ==== Package ==== :
package Engine.api.management.primitives;

// ==== Generals ==== :
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// ==== Interfaces ==== :
import Engine.api.management.exceptions.IllegalApiParameterException;

// ==== Exceptions ===== :
import Util.threads.IllegalOrderException;
import Util.threads.ThreadElement;



public class ApiManager<T> {
    
    // ==== Fields ==== :

    private Map< Class<?>, ThreadElement<?> > storage;
    private T parent;

    // ==== Methods ==== :

    // Instances:

    // I.M.S. 0 :

    public <E> void put( E object, int order ) throws IllegalOrderException{
        ThreadElement<E> thread = new ThreadElement<>(object, order);
        this.storage.put( object.getClass(), thread );
    }

    @SuppressWarnings("unchecked")
    public <E> ThreadElement<E> getAs( Class<E> clazz ) throws IllegalApiParameterException{
        if( this.storage.keySet().contains(clazz) ){
            ThreadElement<E> thread = (ThreadElement<E>) this.storage.get( clazz );
            return thread;
        } else {
            throw new IllegalApiParameterException();
        }
    }

    public T get(){
        return this.parent;
    }

    // ==== Constructors ==== :

    public ApiManager( Map< Integer, List<Class<?>> > clazzes, T parent ) throws IllegalOrderException{
        this.storage = new HashMap<>();
        this.parent = parent;

        for( Integer order : clazzes.keySet() ) {
            for( Class<?> clazz : clazzes.get(order) ){
                ThreadElement<?> thread = new ThreadElement<>( clazz.cast(parent), order );
                this.storage.put( clazz, thread );
            }
        }
    }
}
