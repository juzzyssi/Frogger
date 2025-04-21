package World.model.templates.library.cells;

import java.awt.Image;

import javax.swing.ImageIcon;

import World.model.templates.CellTemplate;

public class Water extends CellTemplate{

    // ==== Fields ==== :

    public static final Image mainImage = new ImageIcon( "Graphics/library/world/Water.png" ).getImage();



    // ==== Constructor ==== :

    public Water(){
        super( Water.mainImage );

        this.traversability = true;
        this.identity = 1;
        this.effect = CellTemplate.LETHAL;
        this.anchorability = true;
    }

}
