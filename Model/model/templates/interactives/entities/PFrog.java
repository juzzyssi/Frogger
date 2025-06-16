// ==== Package ==== :
package Model.model.templates.interactives.entities;

import Model.model.interactives.api.Interactive;
import Model.model.interactives.exceptions.UnsupportedInteractionException;

// ==== Generals ==== :
import Model.model.interactives.primitives.SandBox;
import Model.model.statics.primitives.Tile;
import Model.model.templates.dynamics.accelerators.PImpluse;
import Model.model.templates.interactives.subcategories.Entity;
import Util.threads.IllegalOrderException;
import Util.threads.ThreadElement;
import Engine.api.components.Continuous;
import Engine.api.components.Renderable;
import Engine.api.management.exceptions.IllegalApiParameterException;
import Engine.api.management.primitives.ApiManager;
import Engine.user.User;
import Graphics.Camera;
import Math.Vector;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;

// ==== Interfaces ==== :
import Model.model.dynamics.api.Accelerator;



public class PFrog extends Entity{

    // ==== Fields ==== :

    /* CONCRETES: */
    protected final static Image IMAGE = new ImageIcon( "Graphics/library/toys/entities/frog/idle.png" ).getImage();
    protected static HashMap< Integer, List<Class<?>> > DEAFULT_ORDER;
    static{
        HashMap< Integer, List<Class<?>> > temp = new HashMap<>();                
        // ======== //
        ArrayList<Class<?>> order_2 = new ArrayList<>();
        order_2.add( Renderable.class );
        order_2.add( Continuous.class );

        temp.put( 2, order_2 );
        // ======== //
        PFrog.DEAFULT_ORDER = temp;
    }

    /* INSTANCES: */
    private Image image;
    protected SandBox sandbox;
    private Accelerator playerImpulse;
    private ApiManager<PFrog> apiManager;

    // ==== Interfaces ==== :

    @Override
    public <T> ThreadElement<T> toThreadElementOf( Class<T> clazz ) throws IllegalApiParameterException {
        return this.apiManager.getAs( clazz );
    }

    @Override
    public void interact( Interactive object ) throws UnsupportedInteractionException {
        /* W.I.P. */
        throw new UnsupportedInteractionException();
    }

    @Override
    public void render( Graphics g, Camera camera ) {
        Vector vector = Camera.getModelRenderVector( this.getDisplacement(), camera.getPosition() );

        g.drawImage(
            this.image,
            (int) vector.get( 0 ),
            (int) vector.get( 1 ),
            this.hitbox.width,
            this.hitbox.height,
            null);
    }

    // ==== Constructor ==== :

    public PFrog( User user, Vector displacement ) throws IllegalOrderException {
        super( displacement, new Dimension(Tile.BLOCK) );
        
        this.playerImpulse = new PImpluse( user );
        this.accelerators.add( this.playerImpulse );
        
        this.apiManager = new ApiManager<PFrog>( PFrog.DEAFULT_ORDER, this);
        this.image = PFrog.IMAGE;
    }
}
