package World.model.templates.library.cells;

import java.awt.Image;

import javax.swing.ImageIcon;

import World.model.templates.CellTemplate;

public class Grass extends CellTemplate{

    // ==== Fields ==== :

    public static final Image mainImage = new ImageIcon( "Graphics/library/world/Grass.png" ).getImage();



    // ==== Constructor ==== :

    public Grass(  ){
        super( Grass.mainImage );

        this.traversability = true;
        this.identity = 0;
        this.effect = CellTemplate.VANAL;
        this.anchorability = true;
    }

}
