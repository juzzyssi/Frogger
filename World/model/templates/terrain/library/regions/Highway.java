// ==== Package ==== :
package World.model.templates.terrain.library.regions;

import Util.RandomSet;
import World.model.dynamics.Obstacle;
import World.model.templates.obstacles.Car;
import World.model.templates.terrain.RegionTemplate;
import World.model.templates.terrain.library.cells.Road;



public class Highway extends RegionTemplate {

    // ==== Fields ==== :

    @Override
    public RandomSet<Class<? extends Obstacle>> getObstacleClasses() {
        return this.obstaclesClasses;
    }
    


    // ==== Constructors ==== :

    public Highway() {
        super(new Road());
        this.addObstacleClass(Car.class);
    }
}
