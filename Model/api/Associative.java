// ==== Package ==== :
package Model.api;



/*  Associative instances formally associate groups of objects (families). They provide alternative object-access in
 *  cases that would normally demand of iteration. Associative instances are intended for processes that work with "static terrain."  
 * 
 *  "Families" must limit their "members" to a single instance per class.
 */
public interface Associative {

    // ==== Methods ==== :
    
    public boolean isFamily( Object object );
    /* returns a boolean confirmation of another object's association with itself */

    public <T> boolean hasMember( Class<T> clazz );
    /* returns a boolean confirmation of another object's class if it associates with itself */

    public <T> T getFamilyMember( Class<T> clazz );
    /* returns the object of a given class that is associated with itself */

    public void adopt( Object object );
    /* adopts another object into the "family". If a member of equal class is found; the object must be substituted */
}
