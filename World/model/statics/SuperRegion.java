// ==== Package ==== :
package World.model.statics;

// ==== General ==== :

import java.util.ArrayList;

// ==== Interfaces ==== :

import World.api.RegionTemplateAccessibility;


/*  Class in charge of creating regions with both static & dynamic objects.
 *  
 *  Border PhysicalCellSets that prevent players from leaving the world while enabling dynamic objects to go out of the
 *  camera without the program crashing. Dynamic objects, for the sake of initial simplicity, will have the following spawn-characteristics:
 * 
 *  - Spawn region.
 *  - Initial dynamic traits.
 *  - Perhaps, a path retributor using bezier curves since that what's common between an object undergoing a straight path, 
 *    and another undergoing a curvy trajectory.
 * 
 * Implement entities
 */
public class SuperRegion extends Region{

    // ==== Fields ==== :
    
    ArrayList<Supercell> trajectories;
    RegionTemplateAccessibility traits;



    // ==== Constructors ==== :

    public SuperRegion( ArrayList<Supercell> superCells, RegionTemplateAccessibility traits ){
        super( superCells, traits );
    }
}
