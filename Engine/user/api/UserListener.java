package Engine.user.api;

public interface UserListener {
        
    // ==== Methods ==== :
    public void KeyboardActionPerformed( UserKeyboardEvent e );

    public void MouseActionPerformed( UserMouseEvent e );

    /* W.I.P. */
    public static class UserMouseEvent {
        
        // ==== Fields ==== :

        // ==== Constructors ==== :

        public UserMouseEvent() {

        }
        
    }

    public static class UserKeyboardEvent {

        // ==== Fields ==== :

        /* CONCRETES: */
        public final static String TRIGGERED = "triggered", RELEASED = "released";

        /* INSTANCES: */
        private int keyCode;
        private String label;

        // ==== Methods ==== :

        public String getLabel() {
            return new String( this.label );
        }

        public int getKeyCode() {
            return this.keyCode;
        }

        // ==== Concretes ==== :

        public UserKeyboardEvent( String label, int keyCode ) {
            this.label = label;
            this.keyCode = keyCode;
        }
    }
    
}
