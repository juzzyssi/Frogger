// ==== Package ==== :
package World.model.statics;

// ==== General ==== :
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import World.model.World;
import World.model.dynamics.Obstacle;
import World.model.dynamics.Trajectory;

import java.util.ArrayList;
import java.util.Random;
import java.awt.Point;

// ==== Interfaces ==== :
import World.api.template.RegionTemplateAccessibility;
import World.api.engine.LoopIntegration;


/*  General Documentation:
 *
 *  SuperRegion instances are extensions of Region object managers of "Supercells". They intorduce procedural generation of obstacles
 *  and manage the "obstacle course" logic of a given "region". (Yes, the code enables them to do more, but I've ran out of time)
 *  SuperRegion instances are classified as "loop integrated" to satisfy all the managed instances that require of periodical updating logic.
 * 
 *  SuperRegion works as meta-Obstacle managers. (I believe it's called reflection)
 */
public class SuperRegion extends Region implements LoopIntegration{

    // ==== Fields ==== :
    
    // Instances:
    RegionTemplateAccessibility traits;
    ArrayList<LoopIntegration> updatables;
    
    /* LoopIntegration-functionality fields */
    boolean updated = false;



    // ==== Interfaces ==== :

    // LoopIntegration:
    @Override
    public void checkIn( long time ){
        if( !this.updated ){
            for( LoopIntegration i : this.updatables ){
                i.checkIn( time );
            }
            this.updated = true;   
        }
    }
    @Override
    public void checkOut( long time ){
        if( this.updated ){
            for( LoopIntegration i : this.updatables ){
                i.checkOut( time );
            }
            this.updated = false;
        }
    }



    // ==== Methods ==== :

    // Concretes:



    // ==== Constructors ==== :

    public SuperRegion( ArrayList<Supercell> superCells, RegionTemplateAccessibility traits ) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        /* See: World > model > statics > Region */
        super( superCells, traits );
        this.updatables = new ArrayList<>();

        World world = this.getFamilyMember( World.class );

        /*  Algorithm Documentation:
         *
         *  Currently, the algorithm works solely due to forced "rectangle-shaped" Region instances; it iterates through a (rectangle-shaped)
         *  Region instance's rows and creates a new Trajectory / path that is then assigned to a native instance of an "obstacle".
         * 
         *  Also using reflection: (neat concept ngl)
         */
        if( this.traits.getObstacleClasses().size() > 0 ){
            Class<? extends Obstacle> obsClazz;
            Constructor<? extends Obstacle> constructor;

            Point[] points = new Point[2];
            Trajectory newTrajectory;

            Random rand = new Random();
            int initPointIndex, lastPointIndex;

            LoopIntegration newObstacle;
        
            for( int y = this.container.y ; y < this.container.y + this.container.height ; y += Cell.WIDTH ){

                /*  Currently, all Obstacle extensions are MANUALLY forced to require a single parameter: Trajectory */
                obsClazz = this.traits.getObstacleClasses().pickRandom(modCount);
                constructor = obsClazz.getConstructor( Trajectory.class );

                /*  A Trajectory instance is allowed to be "off-screen"; so are obstacles.
                 *  Therefore, to avoid ugly looking displacement loops, the Trajectory instance is adjusted to compensate for
                 *  an "obstacle's" width.
                 */
                
                points[0] = new Point( world.x - 2*Cell.WIDTH, y ); /* Left-edge Point */
                points[1] = new Point( world.x + world.width, y ); /* Right-edge Point */

                initPointIndex = rand.nextInt(1);
                lastPointIndex = initPointIndex == 0 ? 1 : 0;
                
                newTrajectory = new Trajectory( points[initPointIndex], points[lastPointIndex], y, y);
                
                /*  This might work since Obstacle implements the "LoopIntegration" interface */
                newObstacle = constructor.newInstance( newTrajectory );
                this.updatables.add( newObstacle );
            }

        }
    }
}
