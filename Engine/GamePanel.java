// ==== Package ==== :
package Engine;

// ==== General ==== :
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import World.model.templates.library.regions.*;
import World.model.World;
import World.model.statics.Cell;

import Graphics.Camera;



/*  GamePanel manages the raw input & output of the program; that meaning the core game; post-processess purpose is 
 *  to simplify concentrated complexity, but not much more.
 */ 
public class GamePanel extends JPanel implements ActionListener {

    // ==== Fields ==== :
    private Timer timer;
    
    private Camera camera;

    private World level_1;

    // "Region's" set up:
    private final static int FOREST = 0, RIVER = 1, HIGHWAY = 2;
    private static ArrayList<Object> regionsTemplates = new ArrayList<>(0);
    {
        // Forest: (0)
        regionsTemplates.add( new Forest() );

        // River: (1)
        regionsTemplates.add( new River() );

        // Highway: (2)
        regionsTemplates.add( new Highway() );
    }



    // ==== Interfaces ==== :
    @Override
    public void actionPerformed(ActionEvent e) {
        this.repaint();
    }
    


    // ==== Methods ==== :
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent( g );
    }



    // ==== Constructor ==== :
    public GamePanel( Dimension cameraDimensions ){

        // ==== init. settings ==== :

        this.setPreferredSize( cameraDimensions ); // 14 x 14
        this.timer = new Timer(10, this);
        
        // ==== Level 1 Set-up ==== :
        {
            this.level_1 = new World( cameraDimensions, (Forest) regionsTemplates.get( FOREST ) );

            Rectangle River_rect = new Rectangle( 0, Cell.WIDTH, this.level_1.getColumns()*Cell.WIDTH, 5*Cell.WIDTH );
            Rectangle Highway_rect = new Rectangle( 0, 2*Cell.WIDTH + River_rect.height, this.level_1.getColumns()*Cell.WIDTH, 5*Cell.WIDTH );

            Rectangle Forest1_rect = new Rectangle( 0, 0, this.level_1.getColumns()*Cell.WIDTH, 1*Cell.WIDTH );
            Rectangle Forest2_rect = new Rectangle( 0, River_rect.height + Forest1_rect.height, this.level_1.getColumns()*Cell.WIDTH, 1*Cell.WIDTH );
            Rectangle Forest3_rect = new Rectangle( 0, River_rect.height + Highway_rect.height + Forest1_rect.height + Forest2_rect.height, this.level_1.getColumns()*Cell.WIDTH, 1*Cell.WIDTH );

            level_1.makeRegion( level_1.getCellsInRectangle( River_rect ), (River) regionsTemplates.get( RIVER ) );
            level_1.makeRegion( level_1.getCellsInRectangle( Highway_rect ), (Highway) regionsTemplates.get( HIGHWAY ) );

            level_1.makeRegion( level_1.getCellsInRectangle( Forest1_rect ), (Forest) regionsTemplates.get( FOREST ) );
            level_1.makeRegion( level_1.getCellsInRectangle( Forest2_rect ), (Forest) regionsTemplates.get( FOREST ) );
            level_1.makeRegion( level_1.getCellsInRectangle( Forest3_rect ), (Forest) regionsTemplates.get( FOREST ) );
        }


        this.camera = new Camera( new Point(0, 0), cameraDimensions, this.level_1 );

        this.setLayout(new BorderLayout());
        this.add( this.camera, BorderLayout.CENTER);
        
        this.timer.start();

    }
}
