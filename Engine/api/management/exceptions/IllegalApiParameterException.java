package Engine.api.management.exceptions;

public class IllegalApiParameterException extends Exception{

    // ==== Constructors ==== :

    public IllegalApiParameterException(){
        super();
    }
    public IllegalApiParameterException( String string ){
        super( string );
    }
    public IllegalApiParameterException( Throwable cause ){
        super( cause );
    }
}