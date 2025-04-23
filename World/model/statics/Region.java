// ==== Package ==== :
package World.model.statics;

// ==== General ==== :
import World.model.World;

import java.awt.Rectangle;
import java.util.ArrayList;

import Util.RandomSet;

// ==== Interfaces ==== :
import World.api.Associative;
import World.api.template.RegionTemplateAccessibility;



/*  General Documentation:
 *
 *  Region instances are meant to be "holoistic" managers of collections of Supercell instances.
 *  They ease the designation of "individual traits" and (might eventually intorduce) other sorts of functionalities.
 *  Region instances ARE NOT in charge of "terrain" instantiation (at all); rather, all collections of "Supercells" are delegated and
 *  Transformed from a "parent" World object.
 */
public abstract class Region extends ArrayList<Supercell> implements Associative{ // Maybe implement an "AmbienceEmitter" interface later on

    // ==== Fields ==== :

    // Instances:
    private ArrayList<Object> family = new ArrayList<>(0); /* Associative-functionality field */
    private RegionTemplateAccessibility traits;

    public Rectangle container;



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

    public Rectangle findContainer(){
        /* Returns the smallest rectangle that contains this region: this shoudln't even be used but whatever */
        int lowX = Integer.MAX_VALUE, lowY = Integer.MAX_VALUE, upX = Integer.MIN_VALUE, upY = Integer.MIN_VALUE;

        for( Supercell i : this ){
            lowX = i.x < lowX ? i.x : lowX;
            lowY = i.y < lowY ? i.y : lowY;

            upX = i.x > upX ? i.x : upX;
            upY = i.y > upY ? i.y : upY;
        }

        return new Rectangle( lowX, lowY, upX + Cell.WIDTH, upY + Cell.WIDTH);
    }

    public Rectangle getContainer(){
        return this.container;
    }

    // Concretes:

    public static Rectangle findContainer( ArrayList<Supercell> supercells ){
        /* Returns the smallest rectangle that contains this region */

        int lowX = Integer.MAX_VALUE, lowY = Integer.MAX_VALUE, upX = Integer.MIN_VALUE, upY = Integer.MIN_VALUE;

        for( Supercell i : supercells ){
            lowX = i.x < lowX ? i.x : lowX;
            lowY = i.y < lowY ? i.y : lowY;

            upX = i.x > upX ? i.x : upX;
            upY = i.y > upY ? i.y : upY;
        }

        return new Rectangle( lowX, lowY, upX + Cell.WIDTH, upY + Cell.WIDTH);
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

        this.container = Region.findContainer(superCells);
    }
}
