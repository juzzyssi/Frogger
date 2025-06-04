package Model.model.interactives.api;

import java.lang.UnsupportedOperationException;

import Model.model.interactives.primitives.Token;
import Model.model.interactives.primitives.Toy;

public interface Interactivity {

    public Token interact( Toy object ) throws UnsupportedOperationException;

}
