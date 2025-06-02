// ==== Package ==== :
package Model.model.dynamics;

import java.awt.Dimension;
// ==== General ==== :
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import Engine.user.User;
import Graphics.Camera;
import Math.Vector;
import Model.api.Action;
import Model.api.Associative;
import Model.api.Traversable;
import Model.api.components.Renderable;
import Model.api.engine.LoopIntegration;
import Model.model.World;
import Model.model.statics.Supercell;
import Model.model.statics.primitives.Cell;

import java.util.ArrayList;



public abstract class Entity extends Rectangle implements LoopIntegration, Renderable, Action, Associative{

    // ==== Fields ==== :

    // Instances:
    Image image;
    ArrayList<Object> family = new ArrayList<>(0);

    /* LoopIntegration-functionality fields */
    boolean updated = false;

    /* Anchorable-functionality fields */
    Traversable anchor;

    // Concretes:
    public static final int MOVE_LEFT = 0, MOVE_DOWN = 1, MOVE_RIGHT = 2, MOVE_UP = 3;

    public static final Vector
    UP = new Vector( 0, -Cell.WIDTH ),
    DOWN = new Vector( 0, Cell.WIDTH ),
    LEFT = new Vector( -Cell.WIDTH, 0),
    RIGHT = new Vector( Cell.WIDTH, 0);


    
    // ==== Interfaces ==== :

    // LoopIntegration:
    @Override
    public void checkIn( long time ){
        if( !this.updated ){
            this.updated = true;
            
            /* Calls the "Anchorable" to update in case it's dynamic (logs) */
            if( this.anchor instanceof LoopIntegration ){
                ((LoopIntegration)this.anchor).checkIn( time );
            }            

            Vector anchor = this.anchor.getAnchor();
            this.x = (int) anchor.getX();
            this.y = (int) anchor.getY();
        } 
    }
    @Override
    public void checkOut( long time ){
        if( this.updated ){
            this.updated = false;
            
            /* Calls the "Anchorable" to "check out" */
            if( this.anchor instanceof LoopIntegration ){
                ((LoopIntegration)this.anchor).checkOut( time );
            }
        } 
    }

    // Renderable:
    @Override
    public void render( Graphics g, Camera camera ){

        /* The reactangle is constantly updated: we just get to use its properties */
        int dispX = this.x - camera.getX();
        int dispY = this.y - camera.getY();

        g.drawImage( this.image, dispX, dispY, this.width, this.height, null);
    }

    // Associative:
    @Override
    public boolean isFamily( Object object ){
        return this.family.contains(object);
    }

    @Override
    public <T> boolean hasMember( Class<T> clazz ){

        for( Object i : this.family ){
            if( i.getClass().equals(clazz) ){
                return true;
            }
        }
        return false;
    }

    @Override
    public <T> T getFamilyMember( Class<T> clazz ){

        if( clazz.equals( this.getClass() )){
            throw new IllegalArgumentException( "Associative instances cannot have class twins" );
        }
        else{

            for( Object i : this.family ){
                if( clazz.equals( i.getClass() ) ){
                    return clazz.cast( i );
                }
            }
            throw new IllegalArgumentException( String.format("No %s was found in %s", clazz.getName(), this.toString() ));
        }
    }

    @Override
    public void adopt( Object object ){

        if( object instanceof World && this.hasMember( World.class )){
            throw new IllegalArgumentException("Worlds are immutable Associative instances");
        }
        else if( object.getClass().equals( this.getClass() ) ){
            throw new IllegalArgumentException("Associative groups cannot have class twins");
        }
        else if( !(this.isFamily(object)) ){
            boolean adopted = false;

            /* Replaces Instances with the same Class */
            for( Object i : this.family ){

                if( i.getClass().equals( object.getClass() )){
                    this.family.remove( this.getFamilyMember( i.getClass() ) );
                    this.family.add( object );
                    adopted = true;
                }
            }
            /* Otherwise the object is "adopted" */
            if( !(adopted) ){
                this.family.add( object );
            }
        }
    }

    // Action:
    @Override
    public void act( int token ){

        if( token == MOVE_UP || token == MOVE_DOWN || token == MOVE_LEFT || token == MOVE_RIGHT ){
            this.move( token );
        }
    }
    @Override
    public int interact( User player ){
        /* Customizable */
        return Obstacle.UNDEFINED;
    }



    // ==== Methods ==== :

    // Instances:
    public void move( int token ){                                          // I.M.S. 0 (movement)
        
        // Gathering common resources:
        if( !this.hasMember( World.class ) ){
            throw new IllegalArgumentException(""+this.toString()+" has no World family instance");
        }
        World world = this.getFamilyMember( World.class );

        if( token == MOVE_UP ){
            Vector vectorUp = new Vector(this.anchor.getAnchor(), Entity.UP);

            /* Prevent out of bounds traveling */
            if( world.contains( vectorUp.toPoint() ) ){

                /* This, currently ONLY verifies for static instances: later you might want to look through dynamic instances as well
                 * (even before static ones, just like the log)
                */
                if( false ){ // Here would be where you implement it
                    System.out.println( "remember to check for dynamic instances first" ); 
                }
                else{
                    Supercell targetCell = world.getAt( vectorUp );

                    /* Anchors to the intended cell IF it's classified as "traversasble" */
                    if( targetCell.traits.getTraversability() ){
                        this.anchorTo( targetCell );
                    }
                }
            }
        }
        else if( token == MOVE_DOWN ){
            Vector vectorDown = new Vector(this.anchor.getAnchor(), Entity.DOWN);

            /* Prevent out of bounds traveling */
            if( world.contains( vectorDown.toPoint() ) ){

                /* This, currently ONLY verifies for static instances: later you might want to look through dynamic instances as well
                 * (even before static ones, just like the log)
                */
                if( false ){ // Here would be where you implement it
                    System.out.println( "remember to check for dynamic insatnces first" ); 
                }
                else{
                    Supercell targetCell = world.getAt( vectorDown );

                    /* Anchors to the intended cell IF it's classified as "traversasble" */
                    if( targetCell.traits.getTraversability() ){
                        this.anchorTo( targetCell );
                    }
                }
            }
        }
        else if( token == MOVE_LEFT ){
            Vector vectorLeft = new Vector(this.anchor.getAnchor(), Entity.LEFT);

            /* This, currently ONLY verifies for static instances: later you might want to look through dynamic instances as well
                 * (even before static ones, just like the log)
                */
                if( false ){ // Here would be where you implement it
                    System.out.println( "remember to check for dynamic insatnces first" ); 
                }
                else{
                    Supercell targetCell = world.getAt( vectorLeft );

                    /* Anchors to the intended cell IF it's classified as "traversasble" */
                    if( targetCell.traits.getTraversability() ){
                        this.anchorTo( targetCell );
                    }
                }
        }
        else if( token == MOVE_RIGHT ){
            Vector vectorRight = new Vector(this.anchor.getAnchor(), Entity.RIGHT);

            /* This, currently ONLY verifies for static instances: later you might want to look through dynamic instances as well
                 * (even before static ones, just like the log)
                */
                if( false ){ // Here would be where you implement it
                    System.out.println( "remember to check for dynamic insatnces first" ); 
                }
                else{
                    Supercell targetCell = world.getAt( vectorRight );

                    /* Anchors to the intended cell IF it's classified as "traversasble" */
                    if( targetCell.traits.getTraversability() ){
                        this.anchorTo( targetCell );
                    }
                }
        }
        else{
            throw new IllegalArgumentException("invalid token for "+this.toString() );
        }
    }

    public boolean getTraversability(){                                     // I.M.S. 1 (terrain traits)
        return this.anchor.getTraversability();
    }
    public String getIdentity(){
        return this.anchor.getIdentity();
    }
    public int getEffect(){
        return this.anchor.getEffect();
    }

    public void anchorTo( Traversable object ){
        this.anchor = object;
    }

    // ==== Constructors ==== :

    public Entity( Traversable anchor, Image image, Dimension hitbox ){
        super( (int) anchor.getAnchor().getX(), (int) anchor.getAnchor().getY(), hitbox.width, hitbox.height );
        this.image = image;
        this.anchor = anchor;
    }
}
