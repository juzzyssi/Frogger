// ==== Package ==== :
package Model.model.templates.interactives.subcategories;

// ==== Generals ==== :
import Math.Vector;

import Model.model.interactives.primitives.Toy;

import java.awt.Dimension;

// ==== Interfaces ==== :
import java.util.Map;



public abstract class Entity extends Toy{

    // ======== General Functionality ======== :

    // ==== Fields ==== :

    /* INSTANCES: */
    private int lives;
    private Map< Class<?>, Boolean > immunity;

    // ==== Methods ==== :

    public Vector getCenter() {
        return Vector.add( this.getDisplacement(), new Vector( (long) (this.hitbox.width / 2), (long) (this.hitbox.height / 2) ) );
    }

    public int getLives() {
        return this.lives;
    }

    public boolean getImmunity( Class<?> clazz ) {
        if( this.immunity.keySet().contains( clazz ) ) {
            return this.immunity.get( clazz ).booleanValue();
        }
        return false;
    }

    public void setImmunity( Class<?> clazz, boolean bool ) {
        this.immunity.put( clazz, bool);
    }

    public void damage() {
        this.lives -= 1;
        if( lives <= 0 ) {
            this.defeat();
        }
    }

    public void defeat() {
    }

    // ==== Constructors ==== :
    public Entity( Vector disp, Dimension hitbox ) {
        super( disp, hitbox );
        this.lives = 1;
    }
}
