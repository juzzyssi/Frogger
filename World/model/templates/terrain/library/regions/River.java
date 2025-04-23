// ==== Package ==== :
package World.model.templates.terrain.library.regions;

import Util.RandomSet;
import World.model.dynamics.Obstacle;
import World.model.templates.terrain.RegionTemplate;
import World.model.templates.terrain.library.cells.Water;



public class River extends RegionTemplate {
    
    // ==== Interfaces ==== :

    @Override
    public RandomSet<Class<? extends Obstacle>> getObstacleClasses() {
        return this.obstaclesClasses;
    }

    

    // ==== Constructors ==== :

    public River(){
        super( new Water() );
    }
}

