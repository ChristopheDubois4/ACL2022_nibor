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

/**
 * représente un coffre ouvrable par le joueur
 */
public class Chest extends GameObject{
    private List<Item> chestContents;
    /**
     * constructeur de la classe Chest heritant de GameObject
     */
    public Chest(Position position, HashMap<State, Image> graphics, String objectName, int horizontalHitBox, int verticalHitBox) {
        super(position, graphics, objectName, horizontalHitBox, verticalHitBox);
    }
    public Chest(Position position, HashMap<State, Image> graphics, int horizontalHitBox, int verticalHitBox, List<Item> chestContents) {
        super(position, graphics, "Chest", horizontalHitBox, verticalHitBox);
        this.chestContents=chestContents;
        
    }


    /**
    * le joueur ouvre le coffre
    * @param user l'utilisateur du coffre
    * @return true le joueur a obtenu le contenu du coffre
    */
    @Override
    public boolean objectUse(Player user) {
        //A COMPLETER (format console ici)
        boolean isOpen=true;
        ListIterator<Item> it = chestContents.listIterator() ;
        if (chestContents.isEmpty()){
            System.out.print("Coffre vide");
            return false;
        }
        System.out.print("Contenue du coffre :");
        while(it.hasNext()) {
            Item element = it.next() ;
            System.out.print(element.getName()+" ");
        }
        System.out.println();
        user.displayInventoryConsole();
        while (isOpen){
        }
        return true;
    }
}
