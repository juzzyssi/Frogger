// ==== Package ==== :
package Model.model.primitives.interactives;



public interface Interactive {
    public void interact( Interactive object ) throws UnsupportedInteractionException;

    // ==== Exceptions ==== :
    public class UnsupportedInteractionException extends Exception {

        public UnsupportedInteractionException(){
            super();
        }
        public UnsupportedInteractionException( String string ){
            super( string );
        }
        public UnsupportedInteractionException( Throwable cause ){
            super( cause );
        }

    }
}
