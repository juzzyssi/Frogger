// ==== Package ==== :
package Model.model.templates.statics.tile.ground;

import Model.model.statics.Terrain;
import Model.model.statics.primitives.Tile;
import Model.model.templates.statics.tile.subcategories.Ground;
import Util.random.RandomObject;
import Util.random.RandomSet;

import java.awt.Image;

import javax.swing.ImageIcon;

import Graphics.Camera;

import Math.Vector;

// ==== Exceptions ==== :
import Util.threads.IllegalOrderException;



public abstract class Road extends Ground{

    // ==== Fields ==== :

    /* INSTANCES: */
    public Image image;

    // ==== Interfaces ==== :

    // Renderable:
    @Override
    public void render( java.awt.Graphics g, Camera camera ) {
        Vector vector = Camera.getModelRenderVector( this.toVector(), camera.getPosition() );
        g.drawImage(
            this.image,
            (int) vector.get( 0 ),
            (int) vector.get( 1 ),
            Tile.BLOCK.width,
            Tile.BLOCK.height,
            null);
    }

    // ==== Constructors ==== :

    public Road( Terrain parent, Vector vector, RandomSet<Image> images ) throws IllegalOrderException {
        super( parent, vector, Tile.DEAFULT_ORDER );

        this.image = images.pickRandom( RandomSet.SET_TO_SPECIFIC_ODDS );
    }

    // ==== Inner classes ==== :

    public static class Clean extends Road{

        // ==== Fields ==== :

        public static RandomSet<Image> IMAGES;
        static{
            // ==== Clean ==== :
            Road.Clean.IMAGES = new RandomSet<>(  );
            Road.Clean.IMAGES.add( new RandomObject<Image>(new ImageIcon( "Graphics/library/terrain/tiles/Road.png" ).getImage(), 0.7) );
        }
        
        // ==== Constructors ==== :
        public Clean( Terrain parent, Vector vector ) throws IllegalOrderException {
            super( parent, vector, Road.Clean.IMAGES );
        }
    }
}
