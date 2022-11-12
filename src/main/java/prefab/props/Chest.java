package prefab.props;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;

import prefab.information.Position;
import prefab.information.State;
import prefab.entity.GameObject;
import prefab.entity.Player;
import prefab.equipment.Item;

/**
 * représente un coffre ouvrable par le joueur
 */
public class Chest extends GameObject{

    /**
     * constructeur de la classe Chest heritant de GameObject
     */
    public Chest(Position position, HashMap<State, BufferedImage> graphics, String objectName, int horizontalHitBox, int verticalHitBox) {
        super(position, graphics, objectName, horizontalHitBox, verticalHitBox);
    }
    public Chest(Position position, HashMap<State, BufferedImage> graphics, int horizontalHitBox, int verticalHitBox, List<Item> chestContents) {
        super(position, graphics, "Chest", horizontalHitBox, verticalHitBox);
    }


    /**
    * le joueur ouvre le coffre
    * @param user l'utilisateur du coffre
    * @return true le joueur a obtenu le contenu du coffre
    */
    @Override
    public boolean objectUse(Player user) {
        System.out.println("Utilisation chest\n");
        //A COMPLETER
        return true;
    }
}
