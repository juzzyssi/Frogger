package World.model.templates.terrain.library.cells;

import javax.swing.ImageIcon;

import World.model.templates.terrain.CellTemplate;

public class Road extends CellTemplate{

    // ==== Constructor ==== :

    public Road(){
        super( new ImageIcon( "Graphics/library/world/Road.png" ).getImage() );

        this.traversability = true;
        this.identity = 2;
        this.effect = CellTemplate.VANAL;
        this.anchorability = true;
    }

}
