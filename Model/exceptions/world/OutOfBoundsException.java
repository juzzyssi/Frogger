package Model.exceptions.world;

public class OutOfBoundsException extends Exception{

    // ==== Constructors ==== :

    public OutOfBoundsException(){
        super();
    }
    public OutOfBoundsException( String string ){
        super( string );
    }
    public OutOfBoundsException( Throwable cause ){
        super( cause );
    }

}
