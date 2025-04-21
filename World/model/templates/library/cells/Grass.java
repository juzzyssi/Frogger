package World.model.templates.library.cells;

import javax.swing.ImageIcon;

import World.model.templates.CellTemplate;

public class Grass extends CellTemplate{

    // ==== Constructor ==== :

    public Grass(  ){
        super( new ImageIcon( "Graphics/library/world/Grass.png" ).getImage() );

        this.traversability = true;
        this.identity = 0;
        this.effect = CellTemplate.VANAL;
        this.anchorability = true;
    }

}
