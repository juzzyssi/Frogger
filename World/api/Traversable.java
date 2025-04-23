package World.api;

import Math.Vector;

public interface Traversable {

    // ==== Methods ==== :

    /* Returns the identity of an instance */
    public int getIdentity();

    /* Returns the traversability of an instance */
    public boolean getTraversability();

    /* Returns the effect of an instance */
    public int getEffect();

    /* "Anchors" an instance to another's object "anchor" */
    public void anchorTo( Traversable object );

    /* Returns a traversable instance (used for reference) */
    public Vector getAnchor();

}
