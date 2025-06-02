// ==== Package ==== :
package Util.random;

// ==== General ==== :
import java.util.ArrayList;
import java.util.Random;

// ==== Exceptions ==== :
import java.rmi.NoSuchObjectException;
import java.util.EmptyStackException;



/*  General Documentation:
 *
 *  Manages objects of the specified class as pawns of a system primarlily focused on random selection.
 */
public class RandomSet<T> extends java.util.ArrayList<RandomObject<T>>{
    
    // ==== Fields ==== :

    // Concretes:
    public static final int SET_TO_SPECIFIC_ODDS = 0, SET_TO_EVEN_ODDS = 1;
    public static final double STANDARD_ODDS = 0.35;



    // ==== Methods ==== :

    // Instances:
    public T getBaseObject() throws NoSuchObjectException, EmptyStackException{                     // I.M.S. 0 ()
        if( !this.isEmpty() ){

            for( int i=0 ; i<this.size() ; i++ ){
                if( this.get(i).isBaseObject() ){
                    return this.get(i).getObject();
                }
            }
            
            /* Dead code (a base object is assigned even if there wasn't one) */
            throw new NoSuchObjectException( String.format("%s's \"baseObject\" doesn't exists", this.toString()) );
        }
        else{
            throw new EmptyStackException();
        }
    }

    public T pickRandom( int instructive ) throws IllegalArgumentException, EmptyStackException{
        if( !this.isEmpty() ){

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

                /* Returns the "base object" of the RandomSet instance */
                try{
                    return this.getBaseObject();
                }
                catch( NoSuchObjectException e ){
                    RandomSet.AssignRandomBaseObject( this );

                    T out = null;
                    for( RandomObject<T> i : this ){
                        if( i.isBaseObject() ){
                            out = i.getObject();
                            break;
                        }
                    }
                    return out;
                }

            }
            else{
                throw new IllegalArgumentException( String.format("%d is not a valid token", instructive) );
            }
        }
        else{
            throw new EmptyStackException();
        }
    }

    public boolean hasRandomBaseObject(){
        boolean out = false;
        for( RandomObject<T> i : this ){
            if( !out && i.isBaseObject() ){
                out = true;
            }
        }
        return out;
    }

    @Override
    public String toString(){                                   // I.M.S. 1 ( generic utility )
        String out = "RandomSet[ ";
        for( int i = 0 ; i < this.size() ; i++ ){
            out += this.get(i).toString() + " ";
        }
        out += "]";
        return out;
    }

    public ArrayList<T> toArrayList(){
        ArrayList<T> out = new ArrayList<>(0);
        
        for( RandomObject<T> i : this ){
            out.add( i.getObject() );
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
    public RandomSet(ArrayList<?> list) throws EmptyStackException{
        super(0);
        boolean hasRandomBaseObject = false;

        if (list.isEmpty()){
            throw new EmptyStackException();
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
