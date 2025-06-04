// ==== Package ==== :
package Util;

// ==== General ==== :
import java.util.HashMap;
import java.util.Map;

import Model.model.World;

import java.rmi.NoSuchObjectException;



/*  General Documentation:
 * 
 *  Family provides auxiliary object-class maping support.
 */
public class Family {

    // ==== Fields ==== :

    // Instances:
    private Map<Class<?>, Object> family;
    private Object directParent;



    // ==== Methods ==== :

    // Instances:
    @SuppressWarnings("unchecked")
    public <T> T getFamilyMember( Class<T> clazz ) throws NoSuchObjectException {
        if( this.hasMember(clazz) ) {
            for( Class<?> cl : this.family.keySet() ) {
                if( clazz.isAssignableFrom( cl ) ) {
                    return (T) this.family.get( cl );
                }
            }
            throw new NoSuchObjectException( String.format("No object of class %s was found in %s's family",  clazz.getName(), this.directParent.toString()));
        } else {
            throw new NoSuchObjectException( String.format("No object of class %s was found in %s's family",  clazz.getName(), this.directParent.toString()));
        }    
    }

    public boolean hasMember( Class<?> clazz ) {
        for( Class<?> cl : this.family.keySet() ) {
            if( clazz.isAssignableFrom( cl ) ) {
                return true;
            }
        }
        return false;
    }

    public boolean isFamily( Object object ){
        return this.family.containsValue( object );
    }

    /* Adoption must happen through Classes that are intended as the root */
    public void adopt( Object object ) throws TerrainAssociativeMutationException{
        if( this.isFamily(object) ){

            if( object.getClass() == World.class && this.family.containsKey( World.class ) ){
                throw new TerrainAssociativeMutationException();
            }
            else{
                this.family.replace( object.getClass(), object);
            }
        }
        else{
            this.family.put( object.getClass(), object );
        }
    }



    // ==== Constructors ==== :

    public Family( Object directParent ){
        this.directParent = directParent;
        this.family = new HashMap<>();
    }
}