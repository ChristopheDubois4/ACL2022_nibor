package prefab.props;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import prefab.information.Position;
import prefab.information.State;
import prefab.level.GameObject;

/**
 * représente un coffre ouvrable par le joueur
 */
public class Chest extends GameObject{

    /**
     * constructeur de la classe Chest heritant de GameObject
     */
    public Chest(Position position, HashMap<State, BufferedImage> graphics, String objectName, int verticalHitBox, int horizontalHitBox) {
        super(position, graphics, objectName, verticalHitBox, horizontalHitBox);
    }

    @Override
    public void draw() {
        
    }

    @Override
    public void move() {
        
    }

}
