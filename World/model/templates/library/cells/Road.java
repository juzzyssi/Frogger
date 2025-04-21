package World.model.templates.library.cells;

import java.awt.Image;

import javax.swing.ImageIcon;

import World.model.templates.CellTemplate;

public class Road extends CellTemplate{

    // ==== Fields ==== :

    public static final Image mainImage = new ImageIcon( "Graphics/library/world/Road.png" ).getImage();



    // ==== Constructor ==== :

    public Road(){
        super( Road.mainImage );

        this.traversability = true;
        this.identity = 2;
        this.effect = CellTemplate.VANAL;
        this.anchorability = true;
    }

}
