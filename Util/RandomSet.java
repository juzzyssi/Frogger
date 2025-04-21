package Util;

import java.util.ArrayList;
import java.util.Random;

/* Manages objects of the specified class as pawns of a system primarlily focused on random selection.
 */
public class RandomSet<T> extends java.util.ArrayList<RandomObject<T>>{
    
    // ==== Fields ==== :

    // Concretes:
    public static final int SET_TO_SPECIFIC_ODDS = 0, SET_TO_EVEN_ODDS = 1;
    public static final double STANDARD_ODDS = 0.35;



    // ==== Methods ==== :

    // Instances:
    public T getBaseObject(){                     // I.M.S. 0 ()
        if( !this.isEmpty() ){
            for( int i=0 ; i<this.size() ; i++ ){
                if( this.get(i).isBaseObject() ){
                    return this.get(i).getObject();
                }
            }
            throw new IllegalArgumentException(""+this.toString()+" has no \"baseObject\"");
        }
        else{
            throw new IllegalArgumentException(""+this.toString()+" is empty");
        }
    }
    public T pickRandom( int instructive ){
        if( !this.isEmpty()){
            if( instructive == RandomSet.SET_TO_EVEN_ODDS ){
                Random choiceIndex = new Random();
                return this.get( choiceIndex.nextInt( this.size() + 1 ) ).getObject();
            }
            else if( instructive == RandomSet.SET_TO_SPECIFIC_ODDS ){

                
                double choice = Math.random();
    
                for( int i=0 ; i<this.size() ; i++ ){
                    if( choice <= this.get(i).getOdds() ){
                        return this.get(i).getObject();
                    }
                }
                return this.getBaseObject();

            }
            else{
                throw new IllegalArgumentException(""+this.toString()+" did not recognize the instructive");
            }
        }
        else{
            throw new IllegalArgumentException(""+this.toString()+" is empty");
        }
    }
    @Override
    public String toString(){                                   // I.M.S. 1 ( generic utility )
        String out = "";
        for( int i = 0 ; i < this.size() ; i++ ){
            out += this.get(i).toString() + " ";
        }
        return out;
    }

    // Concretes:

    private static <T> void AssignRandomBaseObject( RandomSet<T> randomSet ){    // C.M.S. 0 ()
        double choice = Math.random();
        int randomIndex = (int) (choice * (double)(randomSet.size()));
        randomIndex = randomIndex == randomSet.size() ? randomIndex - 1 : randomIndex;

        randomSet.get( randomIndex ).setBaseObject( true );
    }



    // ==== Constructors ==== :
    
    @SuppressWarnings("unchecked")
    public RandomSet(ArrayList<?> list) {
        super(0);
        boolean hasRandomBaseObject = false;

        if (list.isEmpty()){
            throw new IllegalArgumentException( "RandomSet constructors must parametrize a non-empty ArrayList" );
        }
        else{

            Object sample = list.get(0);
            if (sample instanceof RandomObject){
                RandomObject<T> casted_i;
                for( Object i : list ){
                    casted_i = (RandomObject<T>)(i);
                    hasRandomBaseObject = !hasRandomBaseObject && casted_i.isBaseObject() ? true : false ; 
                    this.add( casted_i );
                }
            }
            else{
                for( int i=0 ; i<list.size() ; i++ ){
                    T object = (T) list.get(i);
                    this.set(i, new RandomObject<>(object, RandomSet.STANDARD_ODDS ));
                }
            }

            if( !hasRandomBaseObject ){
                /* If the RandomSet doesn't have any "baseObject", the constructor assigns one randomly" */
                RandomSet.AssignRandomBaseObject( this );
            }
        }
    }
    public RandomSet( RandomObject<T> base ){
        super(0);
        base.setBaseObject(true);
        this.add( base );
    }
    public RandomSet( T baseObject ){
        super(0);
        this.add( new RandomObject<>( baseObject, RandomSet.STANDARD_ODDS, true));
    }
    public RandomSet(){
    }

}
