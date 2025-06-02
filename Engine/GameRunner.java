// ==== Package ==== :
package Engine;

// ==== General ==== :
import java.awt.Dimension;

import javax.swing.*;

import Model.model.statics.primitives.Cell;



/*
 */
public class GameRunner extends JFrame{
    public GameRunner( int sizeX, int sizeY ) throws Exception{
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        GamePanel gamePane = new GamePanel( new Dimension( sizeX, sizeY ) );
        this.add( gamePane );

        this.pack();
        this.setVisible( true );
    }

    public static void main(String[] args) throws Exception{
        new GameRunner( Cell.WIDTH*14, Cell.WIDTH*14 );
    }
}
