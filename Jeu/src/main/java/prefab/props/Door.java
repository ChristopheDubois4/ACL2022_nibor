package prefab.props;

import prefab.information.Position;
import prefab.information.State;
import prefab.rendering.Animation;
import prefab.entity.GameObject;

/**
 * représente une porte ouvrable par le joueur
 */
public class Door extends GameObject{

    /**
     * constructeur de la classe Character heritant de GameObject
     * @throws CloneNotSupportedException
     */
    public Door(Position position, Animation animation, int verticalHitBox, int horizontalHitBox) throws CloneNotSupportedException {
        super(position, animation, verticalHitBox, horizontalHitBox, State.DEFAULT);
    }


}
