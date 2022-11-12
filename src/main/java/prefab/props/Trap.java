package prefab.props;

import java.util.HashMap;

import prefab.information.Image;
import prefab.information.Position;
import prefab.information.State;
import prefab.entity.GameObject;
import prefab.entity.Player;

/**
 * représente un piege s'actionnant par le passage du joueur
 */
public class Trap extends GameObject{
    
    private int dammage;

    /**
     * constructeur de la classe Character heritant de GameObject
     */
    public Trap(Position position, HashMap<State, Image> graphics, String objectName, int horizontalHitBox, int verticalHitBox) {
        super(position, graphics, objectName, horizontalHitBox, verticalHitBox);
    }

    public Trap(Position position, HashMap<State, Image> graphics,  int horizontalHitBox, int verticalHitBox, int dammage) {
        super(position, graphics, "Trap", horizontalHitBox, verticalHitBox);
        this.dammage=dammage;
    }

    /**
    * le joueur utilise un piège
    * @param user l'utilisateur du piège
    * @return true ça fait mal
    */
    @Override
    public boolean objectUse(Player user) {
        System.out.println("Utilisation trap\n");
        user.takeDammage(this.dammage);
        return true;
    }

}

