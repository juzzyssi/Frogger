package Graphics;

import javax.swing.JPanel;

import Math.Vector;
import World.model.World;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

/* Used to render a JPanel.
 * It references an external timer.
 */
public class Camera extends JPanel {

    // ==== Fields ==== :
    
    // Instances:
    private World world; /* Shallow reference */

    private Vector position;
    private Dimension dimensions;
    private Rectangle asRectangle;



    // ==== Methods ==== :
    
    // Instances:
    @Override
    protected void paintComponent( Graphics g ){
        super.paintComponent(g);
        this.world.render(g, this);
    }

    public Vector getPosition(){
        return this.position;
    }
    public Dimension getDimensions(){
        return this.dimensions;
    }
    public Rectangle getRectangle(){
        return this.asRectangle;
    }

    public void observeWorld( World world ){
        this.world = world;
    } 
    


    // ==== Constructor ==== :

    public Camera( Vector disp, Dimension dimensions, World world ){
        super();
        this.position = disp;
        this.dimensions = dimensions;
        this.world = world;
        this.asRectangle = new Rectangle( (int) disp.getX(), (int) disp.getY(), dimensions.width, dimensions.height);

        this.setPreferredSize( this.dimensions );
    }
}
