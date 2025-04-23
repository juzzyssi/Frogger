// ==== Package ==== :
package World.model.templates.terrain.library.regions;

import Util.RandomSet;
import World.model.dynamics.Obstacle;
import World.model.templates.terrain.RegionTemplate;
import World.model.templates.terrain.library.cells.Grass;



public class Forest extends RegionTemplate {

    // ==== Interfaces ==== :

    @Override
    public RandomSet<Class<? extends Obstacle>> getObstacleClasses() {
        return this.obstaclesClasses;
    }



    // ==== Constructors ==== :

    public Forest(){
        super( new Grass() );
    }

}
