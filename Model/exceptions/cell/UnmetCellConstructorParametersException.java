package Model.exceptions.cell;

public class UnmetCellConstructorParametersException extends Exception{

    // ==== Methods ==== :

    public UnmetCellConstructorParametersException(){
        super( "Unmet constructor parameters ( Vector.class )" );
    }
    public UnmetCellConstructorParametersException( String string ){
        super( string );
    }
    public UnmetCellConstructorParametersException( Throwable cause ){
        super( cause );
    }
    
}
