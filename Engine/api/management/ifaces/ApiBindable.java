// ==== Package ==== :
package Engine.api.management.ifaces;

import Engine.api.management.exceptions.IllegalApiParameterException;
// ==== General ==== :
import Util.threads.ThreadElement;



public interface ApiBindable {

    // ==== Methods ==== :

    public <T> ThreadElement<T> toThreadElementOf( Class<T> clazz ) throws IllegalApiParameterException;

}
