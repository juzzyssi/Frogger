package Model.model.dynamics.api;

import Model.model.dynamics.primitives.Interactive;

import java.lang.UnsupportedOperationException;

public interface Interactivity {

    public String[] interact( Interactive object ) throws UnsupportedOperationException;

}
