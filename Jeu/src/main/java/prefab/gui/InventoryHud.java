package prefab.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javatuples.Pair;

import engine.Cmd;
import engine.Command;
import manager.Utilities;
import manager.ItemManager;
import prefab.entity.Character;
import prefab.entity.Player;
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
 * <b>[SINGLETON]</b>
 * <p>gère l'HUD lié à l'inventaire
 */
public class InventoryHud extends Hud{

    private static final InventoryHud INSTANCE = new InventoryHud();

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
    private static final ArrayList<Pair<Integer,Integer>> posEquippedStuff = new ArrayList<Pair<Integer,Integer>>
    (Arrays.asList(new Pair<Integer,Integer>(6, 10),new Pair<Integer,Integer>(6, 8),new Pair<Integer,Integer>(6, 6), new Pair<Integer,Integer>(6, 4),new Pair<Integer,Integer>(4, 7))) ;
    //                                          posHelmet                                          posChestplate                                    posLegging                                     posBoots                                     posWeapon                    
    private static final ArrayList<ArmorPieces> listTempoArmorPieces = new ArrayList<ArmorPieces>(Arrays.asList(ArmorPieces.HELMET,ArmorPieces.CHESTPLATE,ArmorPieces.LEGGING,ArmorPieces.BOOTS));
    private static final Object[] strucEquippedStuff = listTempoArmorPieces.toArray();

    private PlayerInfosFofHud player;
    private Pair<Integer, Integer> pressedClick, releasedClick;
    private BufferedImage backgroundImage;

    private String hudPressed;
    private String hudReleased;
    
     
    private Visual visual;
    private Visual visual1;

    /**
     * constructeur de la classe InventoryHud heritant de Hud 
     */
    public InventoryHud() {
        super();
    }

    /**
     * initialise l'HUD de l'inventaire
     * @throws Exception
     */
    @Override
    public void initHud() throws Exception {
        this.player = Player.getInstance();
        chestVisual = Visual.createWithGameCoord(chestFirstPosX, chestFirstPosY, Utilities.getImage(chestPath));

        this.backgroundImage = Utilities.getImage(inventoryPath);
        this.visual = Visual.createWithGameCoord(inventoryFirstPosX, inventoryFirstPosY, 1, 29, backgroundImage);

        this.backgroundImage = Utilities.getImage(equippedStuffPath);
        this.visual1 = Visual.createWithGameCoord(equippedStuffFirstPosX, equippedStuffFirstPosY, backgroundImage);
    }

    public static InventoryHud getInstance() {
        return INSTANCE;
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
                getPressedHud();
                useItem();
            }
        }

        if (command.getKeyCommand() == Cmd.MOUSE_LEFT) {
            if (command.getActionType() == "pressed") {
                pressedClick = command.getNormalizedClick();
                getPressedHud();
                System.out.println("press :"+hudPressed);
                return;
            }
            if (command.getActionType() == "released") {
                releasedClick = command.getNormalizedClick();
                getReleasedHud();
                System.out.println("release :"+hudReleased);
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
        switch (hudPressed+" and "+hudReleased) {
            case "InventoryHUD and InventoryHUD":
                swapItemInventory();
                break;
            case "ChestHUD and ChestHUD":
                swapItemChest();
                break;
            case "ChestHUD and InventoryHUD":
            case "InventoryHUD and ChestHUD":
                swapItemChestInventory();
                break;
            case "EquippedStuffHUD and InventoryHUD":
            case "InventoryHUD and EquippedStuffHUD":
                swapItemEquippedStufInventory();
                break;
            case "EquippedStuffHUD and ChestHUD":
            case "ChestHUD and EquippedStuffHUD":
                swapItemEquippedStufChest();
                break;
            default:
                break;
        }


    }

    private void swapItemEquippedStufChest() {
        HashMap<ArmorPieces, Armor> playerEquippedArmor = player.getEquipedArmor();
        Weapon playerWeapon = player.getWeapon();
        Item itemTemp;
        switch (hudPressed) {
            case "EquippedStuffHUD":
                int posChestItemReleased = getPosItemChestFromClick(releasedClick);
                int posEquippedStuffItemPressed = getPosItemEquippedStuffFromClick(pressedClick);
                itemTemp = chest.getChestContents()[posChestItemReleased];
                if (posEquippedStuffItemPressed == 4 && (itemTemp instanceof Weapon || itemTemp == null)
                        && playerWeapon.getImage() != null) {
                    chest.getChestContents()[posChestItemReleased] = playerWeapon;
                    if (itemTemp == null) {
                        player.setWeapon(new Weapon(null, "", 0));
                    } else
                        player.setWeapon((Weapon) itemTemp);
                } else if (posEquippedStuffItemPressed < 4 && (itemTemp instanceof Armor || itemTemp == null)
                        && playerEquippedArmor.get(strucEquippedStuff[posEquippedStuffItemPressed])
                                .getImage() != null) {
                    chest.getChestContents()[posChestItemReleased] = playerEquippedArmor
                            .get(strucEquippedStuff[posEquippedStuffItemPressed]);
                    if (itemTemp == null) {
                        Armor armorNull = new Armor(null, "",
                                (ArmorPieces) strucEquippedStuff[posEquippedStuffItemPressed]);
                        playerEquippedArmor.replace((ArmorPieces) strucEquippedStuff[posEquippedStuffItemPressed],
                                armorNull);
                    } else
                        playerEquippedArmor.replace((ArmorPieces) strucEquippedStuff[posEquippedStuffItemPressed],
                                (Armor) itemTemp);
                    player.setEquippedArmor(playerEquippedArmor);
                    System.out.print(" STATE :" + ((Armor) itemTemp).getArmorPiece());
                }
                break;
            case "ChestHUD":
                int posChestItemPressed = getPosItemChestFromClick(pressedClick);
                int posEquippedStuffItemReleased = getPosItemEquippedStuffFromClick(releasedClick);

                itemTemp = chest.getChestContents()[posChestItemPressed];
                if (posEquippedStuffItemReleased == 4 && itemTemp instanceof Weapon) {
                    if (playerWeapon.getImage() != null)
                        chest.getChestContents()[posChestItemPressed] = playerWeapon;
                    else
                        chest.getChestContents()[posChestItemPressed] = null;
                    playerWeapon = (Weapon) itemTemp;
                    player.setWeapon(playerWeapon);
                } else if (posEquippedStuffItemReleased < 4 && itemTemp instanceof Armor) {
                    if (((Armor) itemTemp)
                            .getArmorPiece() != (ArmorPieces) strucEquippedStuff[posEquippedStuffItemReleased])
                        return;
                    else if (playerEquippedArmor.get(strucEquippedStuff[posEquippedStuffItemReleased])
                            .getImage() == null) {
                        chest.getChestContents()[posChestItemPressed] = null;
                    } else
                        chest.getChestContents()[posChestItemPressed] = playerEquippedArmor
                                .get(strucEquippedStuff[posEquippedStuffItemReleased]);
                    playerEquippedArmor.replace((ArmorPieces) strucEquippedStuff[posEquippedStuffItemReleased],
                            (Armor) itemTemp);
                    player.setEquippedArmor(playerEquippedArmor);
                }
                break;
            default:
                break;
        }
    }

    private void swapItemEquippedStufInventory() {
        Item[][] playerInventory = player.getInventory();
        HashMap<ArmorPieces, Armor> playerEquippedArmor = player.getEquipedArmor();
        Weapon playerWeapon = player.getWeapon();
        Item itemTemp;
        switch (hudPressed) {
            case "EquippedStuffHUD":
                int[] posItemReleased = getPosItemInventoryFromClick(releasedClick);
                int posEquippedStuffItemPressed = getPosItemEquippedStuffFromClick(pressedClick);

                itemTemp = playerInventory[posItemReleased[0]][posItemReleased[1]];
                if (posEquippedStuffItemPressed == 4 && (itemTemp instanceof Weapon || itemTemp == null)
                        && playerWeapon.getImage() != null) {
                    playerInventory[posItemReleased[0]][posItemReleased[1]] = playerWeapon;
                    if (itemTemp == null) {
                        player.setWeapon(new Weapon(null, "", 0));
                    } else
                        player.setWeapon((Weapon) itemTemp);
                } else if (posEquippedStuffItemPressed < 4 && (itemTemp instanceof Armor || itemTemp == null)
                        && playerEquippedArmor.get(strucEquippedStuff[posEquippedStuffItemPressed])
                                .getImage() != null) {
                    playerInventory[posItemReleased[0]][posItemReleased[1]] = playerEquippedArmor
                            .get(strucEquippedStuff[posEquippedStuffItemPressed]);
                    if (itemTemp == null) {
                        Armor armorNull = new Armor(null, "",
                                (ArmorPieces) strucEquippedStuff[posEquippedStuffItemPressed]);
                        playerEquippedArmor.replace((ArmorPieces) strucEquippedStuff[posEquippedStuffItemPressed],
                                armorNull);
                    } else
                        playerEquippedArmor.replace((ArmorPieces) strucEquippedStuff[posEquippedStuffItemPressed],
                                (Armor) itemTemp);
                    player.setEquippedArmor(playerEquippedArmor);
                }

                break;
            case "InventoryHUD":
                int[] posItemPressed = getPosItemInventoryFromClick(pressedClick);
                int posEquippedStuffItemReleased = getPosItemEquippedStuffFromClick(releasedClick);

                itemTemp = playerInventory[posItemPressed[0]][posItemPressed[1]];
                if (posEquippedStuffItemReleased == 4 && itemTemp instanceof Weapon) {
                    if (playerWeapon.getImage() != null)
                        playerInventory[posItemPressed[0]][posItemPressed[1]] = playerWeapon;
                    else
                        playerInventory[posItemPressed[0]][posItemPressed[1]] = null;
                    playerWeapon = (Weapon) itemTemp;
                    player.setWeapon(playerWeapon);
                } else if (posEquippedStuffItemReleased < 4 && itemTemp instanceof Armor) {
                    System.out.println(((Armor) itemTemp).getArmorPiece() + " "
                            + (ArmorPieces) strucEquippedStuff[posEquippedStuffItemReleased]);
                    if (((Armor) itemTemp)
                            .getArmorPiece() != (ArmorPieces) strucEquippedStuff[posEquippedStuffItemReleased])
                        return;
                    else if (playerEquippedArmor.get(strucEquippedStuff[posEquippedStuffItemReleased])
                            .getImage() == null) {
                        playerInventory[posItemPressed[0]][posItemPressed[1]] = null;
                    } else
                        playerInventory[posItemPressed[0]][posItemPressed[1]] = playerEquippedArmor
                                .get(strucEquippedStuff[posEquippedStuffItemReleased]);
                    playerEquippedArmor.replace((ArmorPieces) strucEquippedStuff[posEquippedStuffItemReleased],
                            (Armor) itemTemp);
                    player.setEquippedArmor(playerEquippedArmor);
                }
                break;
            default:
                break;
        }
    }

    private int getPosItemEquippedStuffFromClick(Pair<Integer, Integer> click) {
        return posEquippedStuff.indexOf(click);
    }

    private void swapItemChestInventory() {
        Item[][] playerInventory = player.getInventory();
        switch (hudPressed) {
            case "ChestHUD":
                int posChestItemPressed = getPosItemChestFromClick(pressedClick);
                int[] posItemReleased = getPosItemInventoryFromClick(releasedClick);

                try {
                    Item itemTemp = chest.getChestContents()[posChestItemPressed];
                    chest.getChestContents()[posChestItemPressed] = playerInventory[posItemReleased[0]][posItemReleased[1]];
                    playerInventory[posItemReleased[0]][posItemReleased[1]] = itemTemp;
                } catch (Exception e) {
                }    
                break;
            case "InventoryHUD":
                int posChestItemReleased = getPosItemChestFromClick(releasedClick);
                int[] posItemPressed = getPosItemInventoryFromClick(pressedClick);
                try {
                    Item itemTemp = playerInventory[posItemPressed[0]][posItemPressed[1]];
                    playerInventory[posItemPressed[0]][posItemPressed[1]] = chest.getChestContents()[posChestItemReleased];
                    chest.getChestContents()[posChestItemReleased] = itemTemp;
                } catch (Exception e) {
                }    
                break;
            default:
                break;
        }
    }

    private void swapItemChest() {
        int posChestItemPressed = getPosItemChestFromClick(pressedClick);
        int posChestItemReleased = getPosItemChestFromClick(releasedClick);

        try {
            Item itemTemp = chest.getChestContents()[posChestItemPressed];
            chest.getChestContents()[posChestItemPressed] = chest.getChestContents()[posChestItemReleased];
            chest.getChestContents()[posChestItemReleased] = itemTemp;
        } catch (Exception e) {
    	}    
    }

    public void swapItemInventory() {
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
    public List<Visual> getFrontVisuals() throws Exception  {   
        
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
            if (armor.getValue().getImage() != null){
            switch(armor.getKey()){
                case HELMET:
                    visuals.add(Visual.createWithGameCoord(posEquippedStuff.get(0).getValue0(), posEquippedStuff.get(0).getValue1(), armor.getValue().getImage()));
                    break;
                case CHESTPLATE:
                    visuals.add(Visual.createWithGameCoord(posEquippedStuff.get(1).getValue0(), posEquippedStuff.get(1).getValue1(), armor.getValue().getImage()));
                    break;
                case LEGGING:
                    visuals.add(Visual.createWithGameCoord(posEquippedStuff.get(2).getValue0(), posEquippedStuff.get(2).getValue1(), armor.getValue().getImage()));
                    break;
                case BOOTS:
                    visuals.add(Visual.createWithGameCoord(posEquippedStuff.get(3).getValue0(), posEquippedStuff.get(3).getValue1(), armor.getValue().getImage()));
                    break;
                }
            }
        }
        

        if(player.getWeapon().getImage() != null)  visuals.add(Visual.createWithGameCoord(posEquippedStuff.get(4).getValue0(), posEquippedStuff.get(4).getValue1(), player.getWeapon().getImage()));;
        
 
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

    public void getPressedHud() {
        System.out.println("Pressed CLICK :"+pressedClick.getValue0()+" "+pressedClick.getValue1());
        if (posEquippedStuff.contains(pressedClick)){
            hudPressed="EquippedStuffHUD";
        }
        else if (chestDisplay == true && pressedClick.getValue1() == 11){
            hudPressed="ChestHUD";
        }
        else if (pressedClick.getValue0() >= 10 && pressedClick.getValue1() >= 3  && pressedClick.getValue0() < 25  && pressedClick.getValue1() < 9){
            System.out.println("___________");
            hudPressed = "InventoryHUD";
        }
        else hudPressed = "";
    }

    public void getReleasedHud() {
        System.out.println("Released CLICK :"+releasedClick.getValue0()+" "+releasedClick.getValue1());
        if (posEquippedStuff.contains(releasedClick)){
            hudReleased="EquippedStuffHUD";
        }
        else if (chestDisplay == true && releasedClick.getValue1() == 11){
            hudReleased="ChestHUD";
        }
        else if (releasedClick.getValue0() >= 10 && releasedClick.getValue1() >= 3 && releasedClick.getValue0() < 25 && releasedClick.getValue1() < 9){
            hudReleased = "InventoryHUD";
        }
        else hudReleased = "";
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
    }

}
