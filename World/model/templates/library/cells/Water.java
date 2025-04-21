package World.model.templates.library.cells;

import javax.swing.ImageIcon;

import World.model.templates.CellTemplate;

public class Water extends CellTemplate{

    // ==== Constructor ==== :

    public Water(){
        super( new ImageIcon( "Graphics/library/world/Water.png" ).getImage() );

        this.traversability = true;
        this.identity = 1;
        this.effect = CellTemplate.LETHAL;
        this.anchorability = true;
    }

}
