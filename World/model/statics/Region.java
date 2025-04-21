// ==== Package ==== :
package World.model.statics;

// ==== General ==== :
import World.model.World;

import java.util.ArrayList;

import Util.RandomSet;

// ==== Interfaces ==== :
import World.api.Associative;
import World.api.RegionTemplateAccessibility;



/*  Region instances operate as managers of Supercell instances. They designate "individual traits" from "holoistic" operations.
 *  Region instances are essentially sub-collections of Supercells within the main World instance. (they inherit Supercells, not create)
 */
public abstract class Region extends ArrayList<Supercell> implements Associative{ // Maybe implement an "AmbienceEmitter" interface later on

    // ==== Fields ==== :

    // Instances:
    private ArrayList<Object> family = new ArrayList<>(0); /* Associative-functionality field */
    private RegionTemplateAccessibility traits;



    // ==== Interfaces ==== : ( Find documentation here: World > api )

    // Family:
    @Override
    public boolean isFamily( Object object ){
        return this.family.contains(object);
    }

    @Override
    public <T> boolean hasMember( Class<T> clazz ){
        for( Object i : this.family ){
            if( i.getClass().equals(clazz) ){
                return true;
            }
        }
        return false;
    }

    @Override
    public <T> T getFamilyMember( Class<T> clazz ){
        if( clazz.equals( this.getClass() )){
            return clazz.cast( this );
        }
        else if( clazz.equals( super.getClass() ) ){
            return clazz.cast(this);
        }
        else{
            for( Object i : this.family ){
                if( clazz.equals( i.getClass() ) ){
                    return clazz.cast( i );
                }
            }
            throw new IllegalArgumentException("No "+clazz.getName()+" was found in "+this.toString() );
        }
    }
    
    @Override
    public void adopt( Object object ){
        if( object instanceof World && this.hasMember( World.class )){
            throw new IllegalArgumentException( "Worlds are immutable Associative instances" );
        }
        else if( object.getClass().equals( this.getClass() )){
            throw new IllegalArgumentException("Associative groups cannot have class twins");
        }
        else if( !(this.isFamily(object)) ){
            boolean adopted = false;

            /* Substitues older class twin */
            for( Object i : this.family ){
                if( i.getClass().equals(object.getClass()) ){
                    this.family.remove( this.getFamilyMember( object.getClass() ) );
                    this.family.add( object );
                    adopted = true;
                }
            }
            /* Otherwise the object is "adopted" */
            if( !(adopted) ){
                this.family.add( object );
            }
        }
    }



    // ==== Methods ==== :

    // Instances:                                                                                           // I.M.S. 0 ()
    public String toString(){
        return String.format("Region[ size=%d, physicalSets=%s ]",
            this.size(),
            this.traits.toString()
        );
    }



    // ==== Constructors ==== :

    public Region( ArrayList<Supercell> superCells, RegionTemplateAccessibility traits ){
        super( superCells );

        if( superCells.size() > 0 ){
            this.adopt( superCells.get(0).getFamilyMember( World.class ) );
            this.traits = traits;

            for( Supercell i : this ){
                i.adopt(this);
                i.inheritPhysicalTraits( this.traits.pickCellTemplateAccessibility( RandomSet.SET_TO_SPECIFIC_ODDS ) );
            }
        }
        else{
            throw new IllegalArgumentException("cannot construct a region without superCells");
        }
    }
}
