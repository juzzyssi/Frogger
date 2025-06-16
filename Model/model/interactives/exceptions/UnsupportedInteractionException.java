package Model.model.interactives.exceptions;

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
