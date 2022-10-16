package prefab.props;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import prefab.information.Position;
import prefab.information.State;
import prefab.entity.GameObject;

/**
 * représente un piege s'actionnant par le passage du joueur
 */
public class Trap extends GameObject{
    
    /**
     * constructeur de la classe Character heritant de GameObject
     */
    public Trap(Position position, HashMap<State, BufferedImage> graphics, String objectName, int verticalHitBox, int horizontalHitBox) {
        super(position, graphics, objectName, verticalHitBox, horizontalHitBox);
    }

    @Override
    public void draw() {
        
    }

    @Override
    public void move() {
        
    }
    
}

