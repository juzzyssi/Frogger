// ==== Package ==== :
package Model.model.templates.statics.cell;

// ==== Generals ==== :
import Model.model.dynamics.primitives.Interactive;
import Model.model.statics.Terrain;
import Model.model.statics.primitives.Cell;

import Util.random.RandomObject;
import Util.random.RandomSet;

import java.awt.Image;

import Graphics.Camera;

import Math.Vector;

// ==== Exceptions ==== :
import Util.threads.IllegalOrderException;



public class CleanRoad extends Cell{

    // ==== Fields ==== :

    // Concretes:
    public final static boolean TRAVERSABILITY = true;
    public static RandomSet<Image> IMAGES;
    static{
        SlimyGrass.IMAGES = new RandomSet<>( );
        SlimyGrass.IMAGES.add( new RandomObject<Image>( Graphics.library.terrain.images.Atlas.ROAD1 , 0.7) );
    }

    // Instances:
    public Image image;

    // ==== Interfaces ==== :

    // Renderable:
    public void render( java.awt.Graphics g, Camera camera ) {
        Vector anchor = camera.getPosition();
        g.drawImage( this.image, (int) (this.x - anchor.get( Terrain.X )) , (int) (this.y - anchor.get( Terrain.Y )), null);
    }

    // Interactivity:
    public String[] interact( Interactive object ) throws UnsupportedOperationException{
        return null;
    }

    // ==== Constructors ==== :

    public CleanRoad( Terrain parent, Vector vector ) throws IllegalOrderException {
        super( parent, vector, Cell.DEAFULT_ORDER, CleanRoad.TRAVERSABILITY );

        this.image = CleanRoad.IMAGES.pickRandom( RandomSet.SET_TO_SPECIFIC_ODDS );
    }
}
