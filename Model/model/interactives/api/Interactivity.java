package Model.model.interactives.api;

import java.lang.UnsupportedOperationException;

import Model.model.interactives.primitives.Toy;

public interface Interactivity {

    public String[][] interact( Toy object ) throws UnsupportedOperationException;

}
