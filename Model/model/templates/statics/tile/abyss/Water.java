// ==== Package ==== :
package Model.model.templates.statics.tile.abyss;

import Model.model.primitives.statics.Terrain;
import Model.model.primitives.statics.Tile;
import Model.model.subprimitives.statics.tiles.Abyss;
import Util.random.RandomObject;
import Util.random.RandomSet;

import java.awt.Image;

import javax.swing.ImageIcon;

import Graphics.Camera;

import Math.Vector;

// ==== Exceptions ==== :
import Util.threads.IllegalOrderException;



public abstract class Water extends Abyss{
    
    // ==== Fields ==== :

    // Instances:
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

    public Water( Terrain parent, Vector vector, RandomSet<Image> images ) throws IllegalOrderException {
        super( parent, vector, Tile.DEAFULT_ORDER );

        this.image = images.pickRandom( RandomSet.SET_TO_SPECIFIC_ODDS );
    }

    // ==== Inner Classes ==== :

    public static class Current extends Water{

        // ==== Fields ==== :

        public static RandomSet<Image> IMAGES;
        static{
            Current.IMAGES = new RandomSet<>( );
            Current.IMAGES.add( new RandomObject<Image>( new ImageIcon( "Graphics/library/terrain/tiles/Water.png" ).getImage() , 0.7) );
        }

        // ==== Constructors ==== :

        public Current( Terrain terrain, Vector vector ) throws IllegalOrderException {
            super(terrain, vector, Current.IMAGES );
        }

    }
}
