// ==== Package ==== :
package Model.model.interactives.api;

// ==== Exceptions ==== :
import Model.model.interactives.exceptions.UnsupportedInteractionException;



public interface Interactive {
    public void interact( Interactive object ) throws UnsupportedInteractionException;
}
