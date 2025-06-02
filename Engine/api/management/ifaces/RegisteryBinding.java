package Engine.api.management.ifaces;

import Engine.api.management.exceptions.IllegalApiParameterException;

public interface RegisteryBinding {

    /*  Returns boolean confirmation of the possesive state over an element:
     */
    public boolean contains( ApiBindable object ) throws IllegalApiParameterException;

    /*  Queue's the specified object for removal:
     */
    public void queueRemoval( ApiBindable object ) throws IllegalApiParameterException;

    /*  "Appends" the specified object to the "collection":
     */
    public void add( ApiBindable object ) throws IllegalApiParameterException;
}
