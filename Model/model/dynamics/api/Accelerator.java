package Model.model.dynamics.api;

import Engine.api.components.ContinuumIntegration;
import Math.Vector;

public interface Accelerator extends ContinuumIntegration {

    public Vector getDisp();

}
