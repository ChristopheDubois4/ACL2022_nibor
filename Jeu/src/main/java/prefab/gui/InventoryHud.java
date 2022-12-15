package prefab.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javatuples.Pair;

import engine.Cmd;
import engine.Command;
import manager.Utilities;
import model.NiborPainter;
import manager.ItemManager;
import prefab.entity.Character;
import prefab.equipment.Armor;
import prefab.equipment.Consumable;
import prefab.equipment.Item;
import prefab.equipment.Weapon;
import prefab.equipment.Armor.ArmorPieces;
import prefab.props.Chest;
import prefab.rendering.Visual;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * gère l'HUD lié à l'inventaire
 */
public class InventoryHud extends Hud{

    private String inventoryPath = "src/main/ressources/images/huds/inventory/inventory.png";
    // Rappel : On commence à (0,0) en bas à gauche
    private static final int inventoryFirstPosX = 10, inventoryFirstPosY = 8; 
    private static final int lengthInventoryX = 15, lengthInventoryY = 5;

    private String chestPath = "src/main/ressources/images/huds/inventory/chest.png";
    private static final int chestFirstPosX = 10, chestFirstPosY = 11;
    private Visual chestVisual;
    private boolean chestDisplay = false;

    private Chest chest;


    private String equippedStuffPath = "src/main/ressources/images/huds/inventory/equippedStuff.png";
    private static final int equippedStuffFirstPosX = 3, equippedStuffFirstPosY = 11; 
    private static final Pair<Integer,Integer> posHelmet = new Pair<Integer,Integer>(6, 10);
    private static final Pair<Integer,Integer> posChestplate = new Pair<Integer,Integer>(6, 8);
    private static final Pair<Integer,Integer> posLegging = new Pair<Integer,Integer>(6, 6);
    private static final Pair<Integer,Integer> posBoots = new Pair<Integer,Integer>(6, 4);
    private static final Pair<Integer,Integer> posWeapon = new Pair<Integer,Integer>(4, 7);

    private PlayerInfosFofHud player;
    private Pair<Integer, Integer> pressedClick, releasedClick;
    private BufferedImage backgroundImage;
    
     
    private Visual visual;
    private Visual visual1;

    /**
     * constructeur de la classe InventoryHud heritant de Hud 
     * @param player joueur
     * @throws Exception
     */
    public InventoryHud(PlayerInfosFofHud player) throws Exception {
        super();
        this.player = player;

        chestVisual = Visual.createWithGameCoord(chestFirstPosX, chestFirstPosY, Utilities.getImage(chestPath));

        this.backgroundImage = Utilities.getImage(inventoryPath);
        this.visual = Visual.createWithGameCoord(inventoryFirstPosX, inventoryFirstPosY, 1, 29, backgroundImage);

        this.backgroundImage = Utilities.getImage(equippedStuffPath);
        this.visual1 = Visual.createWithGameCoord(equippedStuffFirstPosX, equippedStuffFirstPosY, backgroundImage);

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

        int X = (Click.getValue0() - inventoryFirstPosX) % (lengthInventoryX + 2);
        int Y = -((Click.getValue1() - inventoryFirstPosY )) ;

        System.out.println("1er click : "+Click);
    	System.out.println("indice : "+X+" "+Y);

        int[] pos = {X,Y};
        return pos;

    }

         /**
     * renvoi la position de l'item cliqué
     */
    public int getPosItemChestFromClick(Pair<Integer, Integer> Click){

        int X = (Click.getValue0() - chestFirstPosX) % (lengthInventoryX + 2);

        System.out.println("1er click : "+Click);
    	System.out.println("indice : "+X);
        return X;

    }

    /**
     * echange la position de 2 items
     */
    public void swapItem() {
        if (chestDisplay && pressedClick.getValue1()==11 && releasedClick.getValue1()==11){
            System.out.println("OUI");
            int posChestItemPressed = getPosItemChestFromClick(pressedClick);
            int posChestItemReleased = getPosItemChestFromClick(releasedClick);
            try {
            Item itemTemp = chest.getChestContents()[posChestItemPressed];
            chest.getChestContents()[posChestItemPressed] = chest.getChestContents()[posChestItemReleased];
            chest.getChestContents()[posChestItemReleased] = itemTemp;
            } catch (Exception e) {
    	    }    
        }

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

        try {
            int[] posItem = getPosItemInventoryFromClick(pressedClick);

            Item[][] playerInventory = player.getInventory();
            Item item = playerInventory[posItem[0]][posItem[1]];

            //si c'est un consommable on eneleve l'item de l'inventaire
        
            if( item instanceof Consumable) {
                ItemManager.useConsumable(posItem, (Character) player);
                return;
            }
            
            if( item instanceof Weapon) {
                
                return;
            }            
            
            if( item instanceof Armor) {
                
                return;
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

   
    /**
     * retourne la liste des visuels à afficher de l'inventaire
     * @return la liste des visuels
     * @throws Exception
     */
    @Override
    public List<Visual> getVisuals() throws Exception  {   
        
        List<Visual> visuals = new ArrayList<Visual>();
        visuals.add(visual);
        visuals.add(visual1);
        Item[][] inventory = player.getInventory();

        for ( int line=0; line<inventory.length; line++ ) {
            int x = inventoryFirstPosX + line;
            for ( int column=0; column<inventory[line].length; column++ ) {
                int  y = inventoryFirstPosY - column;
                if(inventory[line][column] != null)  visuals.add(Visual.createWithGameCoord(x, y, inventory[line][column].getImage()));
            }
        } 


        for (Map.Entry<ArmorPieces,Armor> armor : player.getEquipedArmor().entrySet()){
            switch(armor.getKey()){
                case HELMET:
                    visuals.add(Visual.createWithGameCoord(posHelmet.getValue0(), posHelmet.getValue1(), armor.getValue().getImage()));
                    break;
                case CHESTPLATE:
                    visuals.add(Visual.createWithGameCoord(posChestplate.getValue0(), posChestplate.getValue1(), armor.getValue().getImage()));
                    break;
                case LEGGING:
                    visuals.add(Visual.createWithGameCoord(posLegging.getValue0(), posLegging.getValue1(), armor.getValue().getImage()));
                    break;
                case BOOTS:
                    visuals.add(Visual.createWithGameCoord(posBoots.getValue0(), posBoots.getValue1(), armor.getValue().getImage()));
                    break;
            }
        }
        
        visuals.add(Visual.createWithGameCoord(posWeapon.getValue0(), posWeapon.getValue1(), player.getWeapon().getImage()));
 
        if (chestDisplay) {    
            Visual chestVisualToDisplay = Visual.createWithExactCoord(chestVisual.getX() , chestVisual.getY() - 29,chestVisual.getBufferedImage());
            visuals.add(chestVisualToDisplay);
            visuals.addAll(chest.getVisuals()); 
            return visuals;
        }        
        return visuals;
    }

    public boolean isChestDisplay() {
        return chestDisplay;
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

    @Override
    public void draw(Graphics2D g) {
        // TODO Auto-generated method stub
        
    }
}
