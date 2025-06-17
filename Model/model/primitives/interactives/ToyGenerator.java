package Model.model.primitives.interactives;

import java.lang.reflect.InvocationTargetException;
import java.util.EmptyStackException;

public interface ToyGenerator {
        
        // ==== Methods ====:

        public void generateToys( SandBox sandbox ) throws NoSuchMethodException, SecurityException, IllegalArgumentException, EmptyStackException, InstantiationException, IllegalAccessException, InvocationTargetException;
}
