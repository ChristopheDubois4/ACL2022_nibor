package prefab.props;

import java.util.HashMap;

import prefab.information.Position;
import prefab.information.State;
import prefab.entity.GameObject;
import java.awt.image.BufferedImage;


/**
 * représente une porte ouvrable par le joueur
 */
public class Door extends GameObject{

    /**
     * constructeur de la classe Character heritant de GameObject
     */
    public Door(Position position, HashMap<State, BufferedImage> graphics, int verticalHitBox, int horizontalHitBox) {
        super(position, graphics, verticalHitBox, horizontalHitBox);
    }


}
