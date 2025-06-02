package Graphics;

import javax.swing.JPanel;

import Engine.api.management.RenderRegistery;
import Math.Vector;
import Model.model.statics.Terrain;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

/* Used to render a JPanel.
 * It references an external timer.
 */
public class Camera extends JPanel {

    // ==== Fields ==== :
    
    // Instances:
    private RenderRegistery registery; /* Shallow reference */

    private Vector position;
    private Dimension dimensions;
    private Rectangle rectangle;



    // ==== Methods ==== :
    
    // Instances:
    @Override
    protected void paintComponent( Graphics g ){
        super.paintComponent(g);
        this.registery.render(g, this);
    }

    public Vector getPosition(){
        return this.position;
    }
    public Dimension getDimensions(){
        return this.dimensions;
    }
    public Rectangle getRectangle(){
        return this.rectangle;
    }

    public void observe( RenderRegistery registery ){
        this.registery = registery;
    }

    public boolean contains( Vector vector ) {
        return this.rectangle.contains( vector.toPoint2D() );
    }
    


    // ==== Constructor ==== :

    public Camera( Vector disp, Dimension dimensions, RenderRegistery registery ){
        super();
        this.position = disp;
        this.dimensions = dimensions;
        this.registery = registery;
        this.rectangle = new Rectangle( (int) disp.get( Terrain.X ), (int) disp.get( Terrain.Y ), dimensions.width, dimensions.height);

        this.setPreferredSize( this.dimensions );
    }
}
