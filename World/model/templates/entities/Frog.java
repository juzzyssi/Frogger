package World.model.templates.entities;

import java.awt.Dimension;

import javax.swing.ImageIcon;

import Engine.user.User;
import World.model.dynamics.Entity;
import World.model.statics.Supercell;
import java.awt.Image;

public class Frog extends Entity {

    // ==== Fields ==== :

    // Instances:
    public User player; /* Shallow referene */

    // Concretes:
    public static Image image = new ImageIcon( "Graphics/library/entities/frog/idle.png" ).getImage();



    // ==== Constructors ==== :

    public Frog( User player, Supercell spawn, Dimension hitbox ){
        super( spawn, Frog.image, hitbox );
        this.player = player;
    }
}
