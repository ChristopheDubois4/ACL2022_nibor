package prefab.props;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import prefab.information.Position;
import prefab.information.State;
import prefab.entity.GameObject;

/**
 * représente une échelle utilisable par le joueur
 */
public class Ladder extends GameObject{

    /**
     * constructeur de la classe Character heritant de GameObject
     */
    public Ladder(Position position, HashMap<State, BufferedImage> graphics, String objectName, int verticalHitBox, int horizontalHitBox) {
        super(position, graphics, objectName, verticalHitBox, horizontalHitBox);
    }

    @Override
    public void draw() {
        
    }

    @Override
    public void move() {
        
    }
    
}
