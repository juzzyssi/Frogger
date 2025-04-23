// ==== Package ==== :
package World.api;

import Engine.user.User;

/* W.I.P.
 */
public interface Action {

    // ==== Methods ==== :

    /* Performs an action based an int token */
    public void act( int token );

    /* Returns a token based on an interaction */
    public int interact( User player );
}
