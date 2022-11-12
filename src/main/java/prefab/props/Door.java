package prefab.props;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import prefab.information.Position;
import prefab.information.State;
import prefab.entity.GameObject;

/**
 * représente une porte ouvrable par le joueur
 */
public class Door extends GameObject{

    /**
     * constructeur de la classe Character heritant de GameObject
     */
    public Door(Position position, HashMap<State, BufferedImage> graphics, String objectName, int verticalHitBox, int horizontalHitBox) {
        super(position, graphics, objectName, verticalHitBox, horizontalHitBox);
    }


}
