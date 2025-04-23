// ==== Package ==== :
package World.api.engine;



// In the engine, create something like an arraylist of LoopIntegration instances that get called each new loop; maybe make that within the world,
// and just call each in the engine.

/*  LoopIntegration instances handle functionality, integrated with the game's loop. The thread safety must be manually moderated. 
 */
public interface LoopIntegration {

    // ==== Methods ==== :

    /* Called to perform instance's updating */
    public void checkIn( long time );
    
    /* Called to set Instances ready for the next "check in" */
    public void checkOut( long time );

}
