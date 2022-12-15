package prefab.props;

import java.util.List;

import engine.Cmd;

import java.util.ArrayList;
import prefab.information.Position;
import prefab.information.State;
import prefab.rendering.Animation;
import prefab.rendering.Visual;
import prefab.entity.GameObject;
import prefab.entity.Player;
import prefab.equipment.Item;
import prefab.gui.InventoryHud;


/**
 * représente un coffre ouvrable par le joueur
 */
public class Chest extends GameObject implements UsableObject{
    

    private static final int chestFirstPosX = 10, chestFirstPosY = 11;
    private static final int inventoryLength = 15;
    private Item[] chestContents = new Item[inventoryLength];

    private InventoryHud inventoryHud;


    /**
     * constructeur de la classe Chest heritant de GameObject
     * @throws CloneNotSupportedException
     */
    public Chest(Position position, Animation animation, int horizontalHitBox, int verticalHitBox,InventoryHud inventoryHud) throws CloneNotSupportedException {
        super(position, animation, horizontalHitBox, verticalHitBox, State.CLOSE);
    }
    
    
    public Chest(Position position, Animation animation, int horizontalHitBox, int verticalHitBox, Item[] chestContents, InventoryHud inventoryHud) throws CloneNotSupportedException {
        this(position, animation, horizontalHitBox, verticalHitBox, inventoryHud);
        initChestContents(chestContents);
        this.inventoryHud=inventoryHud;
        
    }

    public void initChestContents(Item [] c){
        for(Item item : c){
            int k=0;
            while (chestContents[k] != null){
                k++;
            }
            chestContents[k]=item;
        }
    }

    /**
    * le joueur ouvre le coffre
    * @param user l'utilisateur du coffre
    * @return true le joueur a obtenu le contenu du coffre
    */
    @Override
    public void objectUse(Player user,Cmd cmd) {
        // il faut afficher inventaire
        // et le contenu du coffre puis échanger objet avec la souris
        if (getState() == State.OPEN ){
            setState(State.CLOSE);
            return;
        }
        inventoryHud.openWith(this);
        setState(State.OPEN);
    }

    public List<Visual> getVisuals() throws Exception{
        ArrayList<Visual> visuals = new ArrayList<Visual>();
        for ( int line=0; line<chestContents.length; line++ ) {
            int x = chestFirstPosX + line ;
            if(chestContents[line] != null)  visuals.add(Visual.createWithGameCoord(x, chestFirstPosY, chestContents[line].getImage()));
        }    
        return visuals;
    }

    public Item[] getChestContents() {
        return chestContents;
    }
}
