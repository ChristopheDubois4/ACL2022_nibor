package prefab.props;

import java.util.HashMap;
import prefab.information.Position;
import prefab.information.State;
import prefab.entity.GameObject;
import prefab.entity.Player;
import prefab.equipment.Item;
import prefab.gui.InventoryHud;
import java.awt.image.BufferedImage;


/**
 * représente un coffre ouvrable par le joueur
 */
public class Chest extends GameObject{
    
    InventoryHud inventoryHud;
    private Item[] chestContents;


    /**
     * constructeur de la classe Chest heritant de GameObject
     */
    public Chest(Position position, HashMap<State, BufferedImage> graphics, String objectName, int horizontalHitBox, int verticalHitBox) {
        super(position, graphics, objectName, horizontalHitBox, verticalHitBox, State.CLOSE);
    }
    
    
    public Chest(Position position, HashMap<State, BufferedImage> graphics, int horizontalHitBox, int verticalHitBox, Item[] chestContents) {
        super(position, graphics, "Chest", horizontalHitBox, verticalHitBox, State.CLOSE);
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
