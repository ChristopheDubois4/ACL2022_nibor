package prefab.props;

import java.util.HashMap;
import java.util.List;

import engine.Cmd;

import java.util.ArrayList;
import prefab.information.Position;
import prefab.information.State;
import prefab.information.Visual;
import prefab.entity.GameObject;
import prefab.entity.Player;
import prefab.equipment.Item;
import prefab.gui.Hud;
import prefab.gui.InventoryHud;
import manager.WorldManager;

import java.awt.image.BufferedImage;


/**
 * représente un coffre ouvrable par le joueur
 */
public class Chest extends GameObject{
    

    private static final int chestFirstPosX = 10, chestFirstPosY = 11;
    private static final int inventoryLength = 15;
    private Item[] chestContents = new Item[inventoryLength];

    private InventoryHud inventoryHud;


    /**
     * constructeur de la classe Chest heritant de GameObject
     */
    public Chest(Position position, HashMap<State, BufferedImage> graphics, String objectName, int horizontalHitBox, int verticalHitBox,InventoryHud inventoryHud) {
        super(position, graphics, objectName, horizontalHitBox, verticalHitBox, State.CLOSE);
    }
    
    
    public Chest(Position position, HashMap<State, BufferedImage> graphics, int horizontalHitBox, int verticalHitBox, Item[] chestContents, InventoryHud inventoryHud) {
        super(position, graphics, "Chest", horizontalHitBox, verticalHitBox, State.CLOSE);
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
        if (state == State.OPEN ){
            state = State.CLOSE;
            return;
        }
        inventoryHud.openWith(this);
        state = State.OPEN;
    }

    public List<Visual> getVisuals(){
        ArrayList<Visual> visuals = new ArrayList<Visual>();
        for ( int line=0; line<chestContents.length; line++ ) {
            int x = chestFirstPosX + line ;
            if(chestContents[line] != null)  visuals.add(new Visual(x,chestFirstPosY, chestContents[line].getImage(State.DEFAULT)));
        }    
    return visuals;
    }

    public Item[] getChestContents() {
        return chestContents;
    }
}
