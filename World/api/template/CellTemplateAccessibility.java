package World.api.template;

import java.awt.Image;

public interface CellTemplateAccessibility {

    public Image getImage();

    public int getIdentity();

    public boolean getAnchorability();

    public boolean getTraversability();

    public int getEffect();

}
