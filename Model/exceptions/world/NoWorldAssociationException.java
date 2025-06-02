package Model.exceptions.world;

public class NoWorldAssociationException extends Exception{

    public NoWorldAssociationException(){
        super();
    }
    public NoWorldAssociationException( String string ){
        super( string );
    }
    public NoWorldAssociationException( Throwable cause ){
        super( cause );
    }

}
