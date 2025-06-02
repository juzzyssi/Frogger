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
    public boolean isFamily( Object object ){
        return this.family.containsValue( object );
    }
    public <T> boolean hasMember( Class<T> clazz ){
        return this.family.containsKey( clazz );
    }
    public <T> T getFamilyMember( Class<T> clazz ) throws NoSuchObjectException{
        if( this.hasMember(clazz) ){
            return clazz.cast( this.family.get(clazz) );
        }
        else{
            throw new NoSuchObjectException( String.format("No object of class %s was found in %s's family",  clazz.getName(), this.directParent.toString()));
        }
    }
    public void adopt( Object object ) throws TerrainAssociativeMutationException{
        if( this.isFamily(object) ){

            if( object.getClass() == World.class && this.family.containsKey( World.class )){
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