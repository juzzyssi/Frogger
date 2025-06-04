// ==== Package ==== :
package Util.threads;

// ==== General ==== :
import java.util.HashSet;
import java.util.TreeMap;
import java.util.TreeSet;

// ==== Interfaces ==== :
import java.util.Set;
import java.util.Collection;



public class ThreadRegistery<T> extends TreeMap</**/Integer, HashSet<T>/**/>{

    // ==== Methods ==== :

    //  Instances:

    //  I.M.S 0:

    /*  Fills the instance's LList gaps up to (and including) the specified order;
     *  O(n) where n is the difference between the new & current order limit (hopefully, as I'm assuming it balances on its own).
     */
    private void addOrder( int order ){
        if( this.size() - 1 < order ){
            for( int i = this.size(); i <= order ; i++ ){
                this.put( i, new HashSet<>() );
            }
        }
    }

    /*  Provides boolean confirmation of the instance's possesion over the specified object;
     *  O( Lg(n) ) where n is object's "order" value.
     */
    public boolean contains( ThreadElement<T> object ){
        if( object.getOrder() <= this.size() ){
            return this.get( object.getOrder() ).contains( object.getObject() );
        }
        return false;
    }
    public boolean contains( int order ){
        return 0 <= order && order < this.size();
    }

    //  I.M.S. 1: Collection's transformation

    private void add( int order, T object ) {
        if( !this.contains(order) ) {
            this.addOrder( order );
        }

        this.get( order ).add(object);
    }

    /*  Adds an object to the instance's "collection". If such object's order isn't present; the method calls "addOrder";
     *  O( Lg(n) ) if the order already exists within this isntance; otherwise O( n ) where n is the object's order (in both cases).
     */
    public void add( ThreadElement<T> object ) {
        this.add( object.getOrder(), object.getObject() );
    }
    
    /*  Appends the collection of objects as internal data-elements of the instance;
     *  O( nLg(i) ) where n is the number of elements added to collection of size i.
     */
    public void add( Collection< ThreadElement<T> > objects ){
        for( ThreadElement<T> object : objects ){
            this.add( object );
        }
    }

    /*  Removes a single object (if contained) from the instance;
     *  O( Lg(n) ) or O( n ) (depending on the order).
     */
    public void remove( ThreadElement<T> object ){
        if( this.contains(object) ){
            this.get( object.getOrder() ).remove( object.getObject() );
        }
    }

    /*  Removes a collection of unsorted ThreadElement instances of type T;
     *  O( n )
     */
    public void remove( Collection<ThreadElement<T>> objects ){
        ThreadRegistery<T> queue = ThreadRegistery.toThreadRegistery( objects );
        this.remove( queue );
    }

    /*  Removes a sorted collection of ThreadElement insatnces of type T;
     *  O( n )
     */
    public void remove( ThreadRegistery<T> objects ){
        for( Integer order : objects.keySet() ){
            this.get( order ).removeAll( objects.get( order ) );
        }
        this.clean();
    }

    /*  Clears the current data from the instance;
     *  O( n )
     */
    public void clear(){
        Set<Integer> layers = new TreeSet<>( this.keySet() );

        for( Integer order : layers ){
            this.remove( order );
        }
    }

    /*  Cleans the instance's "layers" if they are found empty;
     *  O( n ) where n is the number of layers
     */
    public void clean(){
        Set<Integer> layers = new TreeSet<>( this.keySet() );

        for( Integer order : layers ){
            if( this.get(order).isEmpty() ){
                this.remove( order );
            }
        }
    }

    // Concretes:

    /*  Returns a ThreadRegistery instance from a Collection of ThreadElement objects.
     *  O( n )
     */
    private static <T> ThreadRegistery<T> toThreadRegistery( Collection<ThreadElement<T>> objects ){
        ThreadRegistery<T> out = new ThreadRegistery<>();
        for( ThreadElement<T> object : objects ){
            out.add(object);
        }
        return out;
    }

    // ==== Constructors ==== :

    public ThreadRegistery(){
        super();
    }
}