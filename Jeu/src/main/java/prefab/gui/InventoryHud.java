package prefab.gui;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import engine.Cmd;
import engine.Command;
import manager.Utilities;
import manager.ItemManager;
import prefab.equipment.Item;
import prefab.information.State;
import prefab.information.Visual;
import prefab.props.Chest;

import java.awt.image.BufferedImage;

/**
 * gère l'HUD lié à l'inventaire
 */
public class InventoryHud extends Hud{

    private String inventoryPath = "src/main/ressources/images/huds/inventory/inventory.png";
    // Rappel : On commence à (0,0) en bas à gauche
    private static final int inventoryFirstPosX = 10, inventoryFirstPosY = 8; 
    private static final int lengthInventoryX = 15, lengthInventoryY = 1;

    private String chestPath = "src/main/ressources/images/huds/inventory/chest.png";
    private static final int chestFirstPosX = 10, chestFirstPosY = 10;
    private Visual chestVisual = new Visual(chestFirstPosX, chestFirstPosY, Utilities.getImage(chestPath));
    

    private boolean chestDisplay = false;
    public boolean isChestDisplay() {
        return chestDisplay;
    }

    private Chest chest;


    private String equippedStuffPath = "src/main/ressources/images/huds/inventory/equippedStuff.png";
    private static final int equippedStuffFirstPosX = 5, equippedStuffFirstPosY = 10; 

    private PlayerInfosFofHud player;
    private Pair<Integer, Integer> pressedClick, releasedClick;
    private BufferedImage backgroundImage;
    
     
    private Visual visual;
    private Visual visual1;

    /**
     * constructeur de la classe InventoryHud heritant de Hud 
     * @param player joueur
     */
    public InventoryHud(PlayerInfosFofHud player) {
        super();
        this.player = player;

        this.backgroundImage = Utilities.getImage(inventoryPath);
        this.visual = new Visual(inventoryFirstPosX, inventoryFirstPosY, backgroundImage);
        visual.setDeltaPos(0,30);

        this.backgroundImage = Utilities.getImage(equippedStuffPath);
        this.visual1 = new Visual(equippedStuffFirstPosX, equippedStuffFirstPosY, backgroundImage);
        visual1.setDeltaPos(0,0);


        
    }

    /**
     * gère les clics de souris quand l'inventaire est ouvert
     * @param command commande émise par le joueur
     */
    public void processClick(Command command) {
        //System.out.println("PROCESS CLICK");
        //System.out.println("TYPE : "+command.getActionType());
        if (command.getKeyCommand() == Cmd.MOUSE_RIGHT) {
            if (command.getActionType() == "pressed") {
                pressedClick = command.getNormalizedClick();
                useItem();
            }
        }

        if (command.getKeyCommand() == Cmd.MOUSE_LEFT) {
            if (command.getActionType() == "pressed") {
                pressedClick = command.getNormalizedClick();
                return;
            }
            if (command.getActionType() == "released") {
                releasedClick = command.getNormalizedClick();
                swapItem();
            }
        }
    }

     /**
     * renvoi la position de l'item cliqué
     */
    public int[] getPosItemInventoryFromClick(Pair<Integer, Integer> Click){

        int X = (Click.getValue0() - inventoryFirstPosX) % (lengthInventoryX + 1);
        int Y = -((Click.getValue1() - inventoryFirstPosY )) ;

        System.out.println("1er click : "+Click);
    	System.out.println("indice : "+X+" "+Y);

        int[] pos = {X,Y};
        return pos;

    }

    /**
     * echange la position de 2 items
     */
    public void swapItem() {

    	int[] posItemPressed = getPosItemInventoryFromClick(pressedClick);
        int[] posItemReleased = getPosItemInventoryFromClick(releasedClick);
        
        Item[][] playerInventory = player.getInventory();
    	try {
    		Item itemTemp = playerInventory[posItemPressed[0]][posItemPressed[1]];
        	playerInventory[posItemPressed[0]][posItemPressed[1]] = playerInventory[posItemReleased[0]][posItemReleased[1]];
        	playerInventory[posItemReleased[0]][posItemReleased[1]] = itemTemp;
    	} catch (Exception e) {
    	}    	    	
    }

    //on utlise l'item
    public void useItem(){
    	int[] posItem = getPosItemInventoryFromClick(pressedClick);

        Item[][] playerInventory = player.getInventory();
        Item item = playerInventory[posItem[0]][posItem[1]];

        //si c'est un consommable on eneleve l'item de l'inventaire
        if (ItemManager.consumeItem(item)){
            deleteItem();
        }
    }

    //on delete l'item
    public void deleteItem(){
        int[] posItem = getPosItemInventoryFromClick(pressedClick);
        Item[][] playerInventory = player.getInventory();

        playerInventory[posItem[0]][posItem[1]] = null;
    }

    /**
     * retourne la liste des visuels à afficher de l'inventaire
     * @return la liste des visuels
     */
    @Override
    public List<Visual> getVisual()  {      
        List<Visual> visuals = new ArrayList<Visual>();
        visuals.add(visual);
        visuals.add(visual1);
        Item[][] inventory = player.getInventory();

        for ( int line=0; line<inventory.length; line++ ) {
            int x = inventoryFirstPosX + line;
            for ( int column=0; column<inventory[line].length; column++ ) {
                int  y = inventoryFirstPosY - column;
                if(inventory[line][column] != null)  visuals.add(new Visual(x, y, inventory[line][column].getImage(State.DEFAULT)));
            }
        } 

        if (chestDisplay) {
            chestVisual.setDeltaPos(0,30);
            visuals.add(chestVisual);
            visuals.addAll(chest.getVisuals()); 
            return visuals;
        }
        
        
        return visuals;
    }

    public void openWith(Chest chest) {
        this.chest=chest;
        this.changeDisplayState();
        chestDisplay = !chestDisplay;
    }

    @Override
    public void changeDisplayState() {
        isDisplayed = !isDisplayed;
        if (chestDisplay){
            chestDisplay = !chestDisplay;
        }
    }
}
