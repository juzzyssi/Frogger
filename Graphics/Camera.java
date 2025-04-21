package Graphics;

import javax.swing.JPanel;

import World.model.World;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

/* Used to render a JPanel.
 * It references an external timer.
 */
public class Camera extends JPanel {

    // ==== Fields ==== :
    
    // Instances:
    private World world; /* Shallow reference */

    private Point position;
    private Dimension dimensions;
    private Rectangle asRectangle;



    // ==== Methods ==== :
    
    // Instances:
    @Override
    protected void paintComponent( Graphics g ){
        super.paintComponent(g);
        this.world.render(g, null);
    }

    public Point getPosition(){
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

    public Camera( Point pos, Dimension dimensions, World world ){
        super();
        this.position = pos;
        this.dimensions = dimensions;
        this.world = world;
        this.asRectangle = new Rectangle( pos.x, pos.y, dimensions.width, dimensions.height);

        this.setPreferredSize( this.dimensions );
    }
}
