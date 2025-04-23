// ==== Pacjage ==== :
package World.model.templates.obstacles;

// ==== General ==== :
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;

import Engine.user.User;
import Util.RandomObject;
import Util.RandomSet;
import World.model.dynamics.Obstacle;
import World.model.dynamics.Trajectory;
import World.model.statics.Cell;



public class Car extends Obstacle{

    // ==== Fields ==== :

    // Concretes:
    public static final int FROG_COLLISION = 0;

    /* Car instances deal with random field assignation */
    public static final RandomSet<Image> images = new RandomSet<>( 
        new RandomObject<Image>( new ImageIcon( "Graphics/library/entities/car/car1.png" ).getImage(), RandomSet.STANDARD_ODDS )
    );

    public static final Dimension
    lowDimension = new Dimension( Cell.WIDTH, Cell.WIDTH ),
    upDimension = new Dimension( (2*Cell.WIDTH)/3, Cell.WIDTH );



    // ==== Methods ==== :

    // Concretes:
    public static Dimension randomHitbox(){
        double dWidth = Car.upDimension.getWidth() - Car.lowDimension.getWidth(); 
        double dHeight = Car.upDimension.getHeight() - Car.lowDimension.getHeight();

        int assignedW = (int) ( dWidth * Math.random() ) + Car.lowDimension.width;
        int assignedH = (int) ( dHeight * Math.random() ) + Car.lowDimension.height;

        return new Dimension( assignedW, assignedH );
    }



    // ==== Interfaces ==== :

    // Action
    public void act( int token ){
        /* Customizable */
    }

    @Override
    public int interact( User player ){
        /* W.I.P. */
        return -1;
    }



    // ==== Constructors ==== :

    public Car( Trajectory path ){
        super(path, Car.randomHitbox(), Car.images.pickRandom( RandomSet.SET_TO_SPECIFIC_ODDS ) );
    }
}
