// ==== Package ==== :
package Model.model.templates.interactives.vehicles;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;

import Engine.api.components.Continuous;
import Engine.api.components.Renderable;
import Engine.api.management.exceptions.IllegalApiParameterException;
import Engine.api.management.primitives.ApiManager;
import Graphics.Camera;
import Math.Curves;
import Math.Vector;
import Model.model.primitives.interactives.Interactive;
import Model.model.primitives.statics.Tile;
import Model.model.subprimitives.interactives.Entity;
import Model.model.subprimitives.interactives.Vehicle;
import Util.random.RandomObject;
import Util.random.RandomSet;
import Util.threads.IllegalOrderException;
import Util.threads.ThreadElement;



public class Car extends Vehicle{

    // ==== Fields ==== :

    // *** Concretes *** :
    
    private static final int LOW = 0, UP = 1;
    public static final double[] SPEED_RANGE = new double[]{ 2*Tile.BLOCK.width, 3*Tile.BLOCK.width };
    public static final Dimension[] LENGHT_RANGE = new Dimension[]{
        new Dimension( 3*Tile.BLOCK.width/2, Tile.BLOCK.height ), new Dimension( 5*Tile.BLOCK.width/2, Tile.BLOCK.height )
    };

    /* Api compatibility: */
    protected static HashMap< Integer, List<Class<?>> > DEAFULT_ORDER;
    static{
        HashMap< Integer, List<Class<?>> > temp = new HashMap<>();                
        // ======== //
        ArrayList<Class<?>> order_0 = new ArrayList<>();
        order_0.add( Continuous.class );

        temp.put( 0, order_0 );
        // ======== //
        ArrayList<Class<?>> order_1 = new ArrayList<>();
        order_1.add( Renderable.class );

        temp.put( 1, order_1 );
        // ======== //
        Car.DEAFULT_ORDER = temp;
    }

    /* Image selection: */
    private static RandomSet<Image> IMAGES;
    static {
        Car.IMAGES = new RandomSet<>( new RandomObject<Image>( new ImageIcon( "Graphics/library/toys/vehicles/car/car1.png" ).getImage(), RandomSet.STANDARD_ODDS) );
    }

    /* INSTANCES: */
    private ApiManager<Car> apiManager;
    private Image image;

    /* CONTINUOUS: */
    private boolean updated;

    // ==== Interfaces ==== :

    // Interactive:
    @Override
    public void interact( Interactive object ) throws UnsupportedInteractionException {

        if( object instanceof Entity ) {
            Entity entity = (Entity) object;
            entity.damage();
        }
    }

    // Api bindable:
    @Override
    public <T> ThreadElement<T> toThreadElementOf( Class<T> clazz ) throws IllegalApiParameterException {
        return this.apiManager.getAs( clazz );
    }

    // Renderable:
    @Override
    public void render( Graphics g, Camera camera ) {
        Vector vect = Camera.getModelRenderVector( this.getDisplacement(), camera.getPosition() );

        g.drawImage(
            this.image,
            (int) vect.get(0),
            (int) vect.get(1),
            this.hitbox.width,
            this.hitbox.height,
            null
        );
    }

    // Continuous:
    @Override
    public void checkIn( long time, Object... args ) {
        if( !this.updated ) {
            this.updated = true;
            super.checkIn(time, args);
        }
    }
    
    @Override
    public void checkOut( long time, Object... args ) {
        if( this.updated ) {
            this.updated = false;
            super.checkOut(time, args);
        }
    }
    // ==== Methods ==== :

    // *** Concretes *** :
    public static Dimension pickRandomHitbox() {
        int dw = Car.LENGHT_RANGE[ UP ].width - Car.LENGHT_RANGE[ LOW ].width;
        int dh = Car.LENGHT_RANGE[ UP ].height - Car.LENGHT_RANGE[ LOW ].height;

        return new Dimension( Car.LENGHT_RANGE[ LOW ].width + (int) (dw*Math.random()), Car.LENGHT_RANGE[ LOW ].height + (int) (dh*Math.random()) );
    }

    public int getBlocks() {
        return this.hitbox.width / Tile.BLOCK.width;
    }

    // ==== Constructors ==== :

    public Car( List<Vector> vectors ) throws IllegalOrderException {
        super( Car.pickRandomHitbox(), vectors, Car.SPEED_RANGE, Curves.ARC_LENGTH_DT );
        this.apiManager = new ApiManager<>( Car.DEAFULT_ORDER, this );

        this.image = Car.IMAGES.pickRandom( RandomSet.SET_TO_SPECIFIC_ODDS );
        this.addAccelerator( this.path );

        this.updated = false;
    }
}

