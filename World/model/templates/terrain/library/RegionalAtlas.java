// ==== Package ==== :
package World.model.templates.terrain.library;

import World.model.templates.terrain.library.regions.Forest;
import World.model.templates.terrain.library.regions.Highway;
import World.model.templates.terrain.library.regions.River;

/*  General Documentation:
 * 
 *  RegionalAtlas is an auxiliary class which's static fields can be referrenced to access a "RegionTemplate" extension, rather than
 *  having to create one youself; small, quality of life detail if you ask me.
 */
public abstract class RegionalAtlas {

    // ==== Fields ==== :

    // Concretes:
    public final static Forest FOREST = new Forest();

    public final static River RIVER = new River();

    public final static Highway HIGHWAY = new Highway();

}
