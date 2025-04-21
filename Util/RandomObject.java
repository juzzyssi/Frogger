package Util;

/* Auxiliary Class:
 * Children class of RandomSet that manages fields (T object, double odds, "base case" object) related to random selection.
 */
public class RandomObject<T>{
    
    // ==== Fields ==== :

    // Instance:
    private T object;
    private double odds;
    private boolean baseObject;
    


    // ==== Methods ==== :

    // Instance:
    
    public T getObject(){                                           // I.M.S. 0 ()
        return this.object;
    }
    public double getOdds(){
        return this.odds;
    }
    public boolean isBaseObject(){
        return this.baseObject;
    }
    public boolean setBaseObject( boolean base ){
        return this.baseObject = base;
    }

    @Override
    public String toString(){                                       // I.M.S. 1 ()
        return String.format("object: %s, odds: %f", object.toString(), this.odds*100 );
    }



    // ==== Constructors ==== :

    public RandomObject( T object, double odds, boolean isBaseObject ){
        if( 0.0 < odds && odds < 1.0005 ){
            this.object = object;
            this.odds = odds;
            this.baseObject = isBaseObject;
        }
        else{
            throw new IllegalArgumentException("the odds must be a double between 0.0 and 1.0");
        }
    }
    public RandomObject( T object, double odds ){
        if( 0.0 < odds && odds < 1.0005 ){
            this.object = object;
            this.odds = odds;
            this.baseObject = false;
        }
        else{
            throw new IllegalArgumentException("the odds must be a double between 0.0 and 1.0");
        }
    }
}
