package prefab.props;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

import prefab.information.Image;
import prefab.information.Position;
import prefab.information.State;
import prefab.entity.GameObject;
import prefab.entity.Player;
import prefab.equipment.Item;
import prefab.gui.InventoryHud;

/**
 * représente un coffre ouvrable par le joueur
 */
public class Chest extends GameObject{
    
    InventoryHud inventoryHud;
    private Item[] chestContents;


    /**
     * constructeur de la classe Chest heritant de GameObject
     */
    public Chest(Position position, HashMap<State, Image> graphics, String objectName, int horizontalHitBox, int verticalHitBox) {
        super(position, graphics, objectName, horizontalHitBox, verticalHitBox);
    }
    public Chest(Position position, HashMap<State, Image> graphics, int horizontalHitBox, int verticalHitBox, Item[] chestContents) {
        super(position, graphics, "Chest", horizontalHitBox, verticalHitBox);
        this.state=State.CLOSE;
        this.chestContents=chestContents;
        
    }


    /**
    * le joueur ouvre le coffre
    * @param user l'utilisateur du coffre
    * @return true le joueur a obtenu le contenu du coffre
    */
    @Override
    public boolean objectUse(Player user) {
        // il faut afficher inventaire
        // et le contenu du coffre puis échanger objet avec la souris
        this.state=State.OPEN;


        return true;
    }
}
