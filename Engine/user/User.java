// ==== Package ==== :
package Engine.user;

// ==== Generals ==== :
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Point;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import Math.Vector;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
// ==== Interfaces ==== :

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import Engine.user.api.UserListener;
import Engine.user.api.UserListener.UserKeyboardEvent;
import Engine.user.api.UserListener.UserMouseEvent;



public class User implements ActionListener{

    // ==== Fields ==== :

    /* INSTANCES: */
    public User.Keyboard keyboard;
    public User.Mouse mouse;

    private JPanel panel;
    private Set<UserListener> keyboardListeners;
    private Set<UserListener> mouseListeners;

    // ==== Interfaces ==== :

    @Override
    public void actionPerformed( ActionEvent e ) {
        this.keyboard.actionPerformed(e);
        this.mouse.actionPerformed(e);
    }

    // ==== Methods ==== :

    /* INSTANCES: */
    public User.Keyboard getKeyboard() {
        return this.keyboard;
    }

    public User.Mouse getMouse() {
        return this.mouse;
    }

    public void addUserKeyboardListener( UserListener listener ) {
        this.keyboardListeners.add( listener );
    }

    public void removeUserListeners( UserListener... listeners ) {
        this.keyboardListeners.removeAll( Arrays.asList( listeners ) );
    }

    public void removeUserListener( UserListener listener ) {
        this.keyboardListeners.remove( listener );
    }

    public void notifyKeyboardListeners( UserKeyboardEvent e ) {
        for( UserListener listener : this.keyboardListeners ) {
            listener.KeyboardActionPerformed( e );
        }
    }

    public void notifyMouseListeners( UserMouseEvent e ) {
        for( UserListener listener : this.mouseListeners ) {
            listener.MouseActionPerformed( e );
        }
    }

    // ==== Constructors ==== :

    public User( JPanel focusedPanel ) {
        this.panel = focusedPanel;

        this.keyboard = this.new Keyboard( this );
        this.mouse = this.new Mouse( this );

        this.keyboardListeners = new HashSet<>();
        this.mouseListeners = new HashSet<>();
    }

    // ==== Inner classes ==== :

    public class Keyboard implements KeyListener, ActionListener{

        // ==== Fields ==== :

        /* INSTANCES: */
        private boolean[] prev, keys;
        protected Timer timer;

        private JPanel panel;
        private User user;

        // ==== Interfaces ==== :

        // Action listener:
        @Override
        public void actionPerformed( ActionEvent e ) {
            this.prev = Arrays.copyOf( this.keys, this.keys.length );
        }

        // Key listener:
        @Override
        public void keyPressed( KeyEvent e ){
            this.keys[e.getKeyCode()] = true;
            this.user.notifyKeyboardListeners( new UserKeyboardEvent( UserListener.UserKeyboardEvent.TRIGGERED, e.getKeyCode()) );
        }

        @Override
        public void	keyReleased( KeyEvent e ){
            this.keys[e.getKeyCode()] = false;
            this.user.notifyKeyboardListeners( new UserKeyboardEvent( UserListener.UserKeyboardEvent.RELEASED, e.getKeyCode()) );
        }
        
        @Override
        public void	keyTyped( KeyEvent e ){
        }

        // ==== Methods ==== :

        /* INSTANCES: */
        public boolean isPressed( int keyCode ) {
            return this.keys[ keyCode ];
        }

        public boolean keyTriggerTo( int keyCode, boolean state ) {
            if( state ) {
                return this.keys[ keyCode ] && !this.prev[ keyCode ] ;
            } else {
                return !this.keys[ keyCode ] && this.prev[ keyCode ];
            }
        }

        public void changePanelFocus(JPanel newPanel) {
            if (newPanel == panel || newPanel == null) return;

            if (panel != null) {
                panel.removeKeyListener(this);
            }

            panel = newPanel;
            panel.setFocusable(true);
            panel.addKeyListener(this);

            SwingUtilities.invokeLater(new Runnable() {
                @Override public void run() {
                    panel.requestFocusInWindow();
                }
            });
        }

        // ==== Constructors ==== :

        public Keyboard( User user ) {
            super();
            this.user = user;
            this.panel = this.user.panel;

            this.keys = new boolean[2000];
            this.prev = new boolean[2000];

            this.panel.setFocusable( true );
            this.panel.requestFocus();
            this.panel.addKeyListener( this );
        }
    }

    public class Mouse implements MouseListener, MouseMotionListener, ActionListener {

        // ==== Fields ==== :

        /* INSTANCES: */
        private int mx, my;
        private boolean[] mb;
        private JPanel panel;
        private User user;

        // ==== Interfaces ==== :

        @Override
        public void mousePressed( MouseEvent e ) {
            this.update(e);
            this.mb[ e.getButton() ] = true;
            this.user.notifyMouseListeners( new UserMouseEvent() );;
        }

        @Override
        public void mouseReleased( MouseEvent e ) { 
            this.update(e);
            this.mb[ e.getButton() ] = false;
            this.user.notifyMouseListeners( new UserMouseEvent() );;
        }

        @Override
        public void mouseDragged( MouseEvent e ) {
            this.update(e);
        }

        @Override
        public void mouseMoved( MouseEvent e ) {
            this.update(e);
        }

        @Override
        public void mouseClicked( MouseEvent e ){
            this.update(e);
            this.user.notifyMouseListeners( new UserMouseEvent() );;
        }

        @Override
        public void mouseEntered( MouseEvent e ) {
        }

        @Override
        public void mouseExited( MouseEvent e ) {
        }

        @Override
        public void actionPerformed( ActionEvent e ) {
        }

        // ==== Methods ==== :

        /* INSTANCES: */
        private void update(MouseEvent e){
            Point p = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), panel);
            mx = p.x;
            my = p.y;
        }

        public Vector getVector() {
            return new Vector( (long) this.mx, (long) this.my );
        }

        public boolean isPressed( int button ) {
            if( 0 <= button && button < this.mb.length ) {
                return this.mb[ button ];
            }
            return false;
        }

        public void changePanelFocus(JPanel newPanel) {
            if (this.panel != null) {
                this.panel.removeMouseListener(this);
                this.panel.removeMouseMotionListener(this);
            }
            this.panel = newPanel;
            this.panel.addMouseListener(this);
            this.panel.addMouseMotionListener(this);
        }


        // ==== Constructors ==== :

        public Mouse(User user) {
            this.user = user;
            this.panel = this.user.panel;

            panel.addMouseListener(this);
            panel.addMouseMotionListener(this);


            this.mb = new boolean[4];
            this.mx = 0;
            this.my = 0;
        }
    }
}