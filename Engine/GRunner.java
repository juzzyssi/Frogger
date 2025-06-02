// ==== Package ==== :
package Engine;

// ==== General ==== :
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import Graphics.Camera;
import Math.Vector;
import Model.model.World;
import Model.model.templates.statics.region.LightForest;



/*  GamePanel manages the raw input & output of the program; that meaning the core game; post-processess purpose is 
 *  to simplify concentrated complexity, but not much more.
 */ 
public class GRunner extends JPanel implements ActionListener {

    // ==== Fields ==== :
    private Timer timer;
    
    private Camera camera;

    private boolean start;

    private World model;



    // ==== Interfaces ==== :
    @Override
    public void actionPerformed(ActionEvent e) {

        /* Updates the world while preventing some runtime exceptions / errors */
        if( model.isUpdated() || this.start ) {
            this.start = false;
            try{
                this.model.checkIn( System.nanoTime(), this.getGraphics(), this.camera ) ;
                this.repaint();

                /* Sets the world ready for the next update */
                this.model.checkOut( System.nanoTime() );
            } catch( Exception i ) {
                System.out.println( i.getMessage() );
            }
        }
    }
    


    // ==== Methods ==== :
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent( g );
    }



    // ==== Constructor ==== :
    public GRunner( Dimension cameraDimensions ) throws Exception{

        // ==== init. settings ==== :

        this.setPreferredSize( cameraDimensions ); // 14 x 14
        this.timer = new Timer(10, this);

        this.start = false;
        
        // ==== Level 1 Set-up ==== :
        {
            this.model = new World( cameraDimensions, LightForest.class );
        }


        this.camera = new Camera( new Vector(0, 0), cameraDimensions, this.model.getRenderApi() );

        this.setLayout(new BorderLayout());
        this.add( this.camera, BorderLayout.CENTER);
        
        this.timer.start();

    }
}
