package Util.threads;

public class IllegalOrderException extends Exception{

    // ==== Constructors ==== :

    public IllegalOrderException(){
        super();
    }
    public IllegalOrderException( String string ){
        super( string );
    }
    public IllegalOrderException( Throwable cause ){
        super( cause );
    }
}
