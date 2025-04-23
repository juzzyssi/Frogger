package World.api.template;

import Util.RandomSet;
import World.model.dynamics.Obstacle;

public interface RegionTemplateAccessibility {

    /* See World > api > template > CellTemplateAccessibility */
    public CellTemplateAccessibility pickCellTemplateAccessibility( int instructive );

    /* Must return a set of Obstacle extension CLASSES native to a SuperRegion: this approach was taken because obstacles are
     * very different conceptually, but for a region to naturally generate them; instances of a number of classes should be
     * creatable.
     */
    public RandomSet<Class<? extends Obstacle>> getObstacleClasses();

}
