package Util;

/*  General Documentation:
 * 
 *  WorldAssociativeMutationException is associated with scenarios where any object-association-mutation involved World instances. (not allowed)
 */
public class TerrainAssociativeMutationException extends Exception {

    // ==== Constructors ==== :

    public TerrainAssociativeMutationException(){
        super( "World instances associations are immutable" );
    }
    public TerrainAssociativeMutationException( String string ){
        super( string );
    }
    public TerrainAssociativeMutationException( Throwable cause ){
        super( cause );
    }

}
