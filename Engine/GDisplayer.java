// ==== Package ==== :
package Engine;

// ==== General ==== :
import java.awt.Dimension;

import javax.swing.*;

import Model.model.statics.primitives.Tile;



/*
 */
public class GDisplayer extends JFrame{
    public GDisplayer( Dimension cameraD ) throws Exception{
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        GRunner gr = new GRunner( cameraD );
        this.add( gr );

        this.pack();
        this.setVisible( true );
    }

    public static void main(String[] args) throws Exception{
        Dimension cameraD = new Dimension( Tile.BLOCK.width*14, Tile.BLOCK.height*14 );
        new GDisplayer( cameraD );
    }
}
