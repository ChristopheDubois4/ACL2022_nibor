package prefab.gui;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import engine.Cmd;
import engine.Command;
import manager.Utilities;
import prefab.equipment.Item;
import prefab.information.State;
import prefab.information.Visual;

import java.awt.image.BufferedImage;

/**
 * gère l'HUD lié à l'inventaire
 */
public class InventoryHud extends Hud{

    private String inventoryPath = "src/main/ressources/images/huds/inventory/inventory.png";

    private PlayerInfosFofHud player;
    private Pair<Integer, Integer> pressedClick, releasedClick;
    private BufferedImage backgroundImage;
    
    // Rappel : On commence à (0,0) en bas à gauche
    private static final int firstPosX = 10, firstPosY = 8; 
    private static final int lengthInventoryX = 15, lengthInventoryY = 5; 

    /**
     * constructeur de la classe InventoryHud heritant de Hud 
     * @param player joueur
     */
    public InventoryHud(PlayerInfosFofHud player) {
        super();
        this.player = player;
        this.backgroundImage = Utilities.getImage(inventoryPath);
        
    }

    /**
     * gère les clics de souris quand l'inventaire est ouvert
     * @param command commande émise par le joueur
     */
    public void processClick(Command command) {
        //System.out.println("PROCESS CLICK");
        //System.out.println("TYPE : "+command.getActionType());

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
     * echange la position de 2 items
     */
    public void swapItem() {
        int xPressed = pressedClick.getValue0(), yPressed = pressedClick.getValue1();
        int xReleased = releasedClick.getValue0(), yReleased = releasedClick.getValue1();

        //if (xPressed >= 8)
    	
    	int indicePressed = xPressed - firstPosX  + (firstPosY - yPressed)*lengthInventoryX;
    	int indiceReleased = xReleased - firstPosX  + (firstPosY - yReleased)*lengthInventoryX;

        System.out.println("1er click : "+pressedClick);
    	System.out.println("indicePressed : "+indicePressed);
        System.out.println("2er click : "+releasedClick);
    	System.out.println("indiceReleased : "+indiceReleased);

    	Item[] playerInventory = player.getInventory();
    	
        
    	try {
    		Item itemTemp = playerInventory[indicePressed];
        	playerInventory[indicePressed] = playerInventory[indiceReleased];
        	playerInventory[indiceReleased] = itemTemp;
    	} catch (Exception e) {
    	}    	    	
    }

    /**
     * retourne la liste des visuels à afficher de l'inventaire
     * @return la liste des visuels
     */
    @Override
    public List<Visual> getVisual()  {
        List<Visual> visuals = new ArrayList<Visual>();
        visuals.add(new Visual(firstPosX, firstPosY, backgroundImage));
        
        Item[] inventory = player.getInventory();
        int x = firstPosX;
        int y = firstPosY;
        for (int i = 0; i < inventory.length; i++) {
            if (x+1 > firstPosX + lengthInventoryX) {
                x = firstPosX;
                y--;
            }
            if (inventory[i] != null) {
                visuals.add(new Visual(x, y, inventory[i].getImage(State.DEFAULT)));
            }
            x++;
        }    
        return visuals;
    }
}
