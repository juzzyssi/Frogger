// ==== Package ==== :
package Engine;

// ==== General ==== :
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import Engine.user.User;
import Graphics.Camera;
import Math.Vector;
import Model.model.World;
import Model.model.statics.primitives.Tile;
import Model.model.templates.statics.region.biomes.Forest;
import Model.model.templates.statics.region.stampedes.River;



/*  GamePanel manages the raw input & output of the program; that meaning the core game; post-processess purpose is 
 *  to simplify concentrated complexity, but not much more.
 */ 
public class GRunner extends JPanel implements ActionListener {

    // ==== Fields ==== :

    /* INSTANCES: */
    private Timer timer;
    private Camera camera;
    private boolean start;
    private World model;
    protected User user;

    // ==== Interfaces ==== :
    @Override
    public void actionPerformed(ActionEvent e) {
        this.user.actionPerformed(e);

        /* Updates the world while preventing some runtime exceptions / errors */
        if( model.isUpdated() || this.start ) {
            this.start = false;

            try{
                /* Avoid start loading race */
                if( this.getGraphics() != null ) {
                    this.model.checkIn( System.nanoTime(), this.camera ) ;
                    this.model.checkOut( System.nanoTime() );

                    this.repaint();
                }
            } catch( Exception ex ) {
                ex.printStackTrace();
            }
        }
    }
    


    // ==== Methods ==== :
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent( g );
        this.model.render(g, camera);
    }



    // ==== Constructor ==== :
    public GRunner( Dimension cameraDimensions ) throws Exception{
        super();

        // ==== init. settings ==== :

        this.setPreferredSize( cameraDimensions ); // 14 x 14
        this.timer = new Timer(10, this);

        this.start = false;
        this.user = new User( this );
        
        // ==== Level 1 Set-up ==== :
        {
            this.model = new World( this.user, cameraDimensions, Forest.Light.class );
            
            Rectangle square = new Rectangle( 0, Tile.BLOCK.height, 14*Tile.BLOCK.width, 2*Tile.BLOCK.height );
            this.model.getTerrain().paint( this.model.getTerrain().getVectors( square ), River.Calm.class );
        }


        this.camera = new Camera( new Vector(0, 0), cameraDimensions, this.model.getRenderApi() );

        this.setLayout(new BorderLayout());
        this.add( this.camera, BorderLayout.CENTER );
        
        this.timer.start();

    }
}
