// ==== Package ==== :
package Model.model.templates.interactives.vehicles;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;

import Engine.api.components.Continuous;
import Engine.api.components.Renderable;
import Engine.api.management.exceptions.IllegalApiParameterException;
import Engine.api.management.primitives.ApiManager;
import Graphics.Camera;
import Math.Curves;
import Math.Vector;
import Model.model.dynamics.api.Accelerator;
import Model.model.interactives.api.Interactive;
import Model.model.interactives.exceptions.UnsupportedInteractionException;
import Model.model.statics.primitives.Tile;
import Model.model.templates.interactives.subcategories.Entity;
import Model.model.templates.interactives.subcategories.Vehicle;
import Util.random.RandomObject;
import Util.random.RandomSet;
import Util.threads.IllegalOrderException;
import Util.threads.ThreadElement;



public class Log extends Vehicle implements Accelerator{

    // ==== Fields ==== :

    // *** Concretes *** :
    
    private static final int LOW = 0, UP = 1;
    public static final double[] SPEED_RANGE = new double[]{ 80, 150 };
    public static final Dimension[] LENGHT_RANGE = new Dimension[]{
        new Dimension( 2*Tile.BLOCK.width, Tile.BLOCK.height ), new Dimension( 7*Tile.BLOCK.width, Tile.BLOCK.height )
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
        Log.DEAFULT_ORDER = temp;
    }

    /* Image selection: */
    private static RandomSet<Image> LEFT_TILE_IMGS, MID_TILE_IMGS, RIGHT_TILE_IMGS;
    static {
        Log.LEFT_TILE_IMGS = new RandomSet<>( new RandomObject<Image>( new ImageIcon( "Graphics/library/toys/vehicles/log/leftTile.png" ).getImage(), RandomSet.STANDARD_ODDS) );
        Log.MID_TILE_IMGS = new RandomSet<>( new RandomObject<Image>( new ImageIcon( "Graphics/library/toys/vehicles/log/midTile.png" ).getImage(), RandomSet.STANDARD_ODDS) );
        Log.RIGHT_TILE_IMGS = new RandomSet<>( new RandomObject<Image>( new ImageIcon( "Graphics/library/toys/vehicles/log/rightTile.png" ).getImage(), RandomSet.STANDARD_ODDS) );
    }

    // *** Instances *** :

    private ApiManager<Log> apiManager;
    private List<Image> images;

    /* Accelerator: */
    private Vector oldDisplacement;

    /* Continunous: */
    private boolean updated;

    // ==== Interfaces ==== :

    // Accelerator:
    @Override
    public Vector getAccDisp() {
        return Vector.add( this.getDisplacement(), this.oldDisplacement.flip() );
    }

    // Interactive:
    @Override
    public void interact( Interactive object ) throws UnsupportedInteractionException {

        if( object instanceof Entity ) {
            Entity entity = (Entity) object;

            /*  Either appends or removes itself as an accelerator of the entity */
            if( this.getHitbox().contains( entity.getCenter().toPoint2D() ) ) {
                if( !entity.hasAccelerator( this ) ) {
                    entity.addAccelerator( this );
                }
                
            } else if( entity.hasAccelerator( this ) ) {
                entity.queueRemoval( this );
            }
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
        Vector disp = this.getDisplacement();

        for( int n = 0; n < this.getBlocks(); n++ ) {
            g.drawImage(
                this.images.get(n), 
                (int) disp.get(0) + n*Tile.BLOCK.width,
                Tile.BLOCK.height,
                Tile.BLOCK.width,
                Tile.BLOCK.height,
                null);
        }
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
            this.oldDisplacement = this.getDisplacement();
        }
    }
    // ==== Methods ==== :

    // *** Concretes *** :
    public static Dimension pickRandomHitbox() {
        int nDown = Log.LENGHT_RANGE[ LOW ].width / Tile.BLOCK.width;
        int nUpp = Log.LENGHT_RANGE[ UP ].width / Tile.BLOCK.width;
        int dN = nUpp - nDown;

        Random rand = new Random();
        return new Dimension( (nDown + rand.nextInt( dN + 1 )) * Tile.BLOCK.width, Tile.BLOCK.height );
    }

    public int getBlocks() {
        return this.hitbox.width / Tile.BLOCK.width;
    }

    // ==== Constructors ==== :

    public Log( List<Vector> vectors ) throws IllegalOrderException {
        super( Log.pickRandomHitbox(), vectors, Log.SPEED_RANGE, Curves.ARC_LENGTH_DT );
        this.apiManager = new ApiManager<Log>( Log.DEAFULT_ORDER, this );

        int nWidth = this.getBlocks();
        this.images = new ArrayList<>( nWidth );

        // ===== //

        this.images.add( Log.LEFT_TILE_IMGS.pickRandom( RandomSet.SET_TO_SPECIFIC_ODDS ) );
        for( int n = 1; n < nWidth - 1; n++ ) {
            this.images.add( Log.MID_TILE_IMGS.pickRandom( RandomSet.SET_TO_SPECIFIC_ODDS ) );
        }
        this.images.add( Log.RIGHT_TILE_IMGS.pickRandom( RandomSet.SET_TO_SPECIFIC_ODDS) );

        // ===== //

        this.addAccelerator( this.path );

        this.oldDisplacement = this.getDisplacement();
        this.updated = false;
    }
}
