package prefab.entity;

import java.util.HashMap;
import prefab.equipment.Armor;
import prefab.equipment.ArmorPieces;
import prefab.equipment.Item;
import prefab.equipment.Weapon;
import prefab.gui.PlayerInfosFofHud;
import prefab.information.PlayerClasses;
import prefab.information.Position;
import prefab.information.State;
import prefab.information.Stats;
import java.awt.image.BufferedImage;



/**
 * représente le joueur, un personnage particulié du jeu que l'on contrôle
 */

public class Player extends Character implements PlayerInfosFofHud{
    
    PlayerClasses classPlayed;
    
    /**
     * constructeur de la classe Player heritant de Character
     * @param classPlayed la classe de combattant du joueur
     */
    public Player(Position position, HashMap<State, BufferedImage> graphics, String objectName, int horizontalHitBox, int verticalHitBox, PlayerClasses classPlayed) {
        super(position, graphics, objectName, horizontalHitBox, verticalHitBox);
        this.classPlayed = classPlayed;
        this.xp = 0;
        initCharacteristic();

        //tempo
        //this.inventory.set(0, new Item(0,null,"Item_1"));
        //this.inventory.set(1, new Item(0,null,"Item_2"));
        //this.inventory.set(2, new Item(0,null,"Item_3"));
        
    }

    /**
     * méthode qui initailise les charactéristiques par défaut du joueur 
     * selon la classe qu'il à choisi
     */
    protected void initCharacteristic(){     
        
        switch (classPlayed) {

            case MAGE:
                
                break;

            case WARRIOR:
            
                break;

            case ASSASSIN:
            
                break;

            case CLERIC:
                //tempo
                this.stats = new HashMap<Stats , Integer>();
                this.currentStats = new HashMap<Stats , Integer>();

                this.stats.put(Stats.HP, 100);
                this.stats.put(Stats.MANA, 100);
                this.stats.put(Stats.STAMINA, 100);

                this.stats.put(Stats.DEFENSE, 50);
                this.stats.put(Stats.SPEED, 100);
                this.stats.put(Stats.DAMAGE, 5);
                inventory[0][0] = new Item("epeeDelaMort", "sword_1");
                inventory[13][5] = new Item("truc", "bitcoin");

                weapon=new Weapon("epeeDelaMort", "sword_1");
                HashMap<ArmorPieces,Armor> equippedArmorTemp = new HashMap<ArmorPieces,Armor>();
                equippedArmorTemp.put(ArmorPieces.HELMET,new Armor("Helmet", "helmet_1"));
                equippedArmorTemp.put(ArmorPieces.CHESTPLATE,new Armor("ChestPlate", "chestplate_1"));
                equippedArmorTemp.put(ArmorPieces.LEGGING,new Armor("Legging", "legging_1"));
                equippedArmorTemp.put(ArmorPieces.BOOTS,new Armor("Boots", "boots_1"));
                setEquippedArmor(equippedArmorTemp);

                resetCurrentStats();

                break;
        
            default:
                break;
        }
    }   
    
    @Override
    public void die() {
        this.state=State.DEAD;
    }

    @Override
    public int getMoney() {       
        return this.money;
    }

    @Override
    public Item[][] getInventory() {
        return inventory;
    }

    @Override
    public HashMap<ArmorPieces, Armor> getEquipedArmor() {
        // TODO Auto-generated method stub
        return equippedArmor;
    }

    @Override
    public Weapon getWeapon() {
        // TODO Auto-generated method stub
        return weapon;
    }

	public int getHealth() {
		return currentStats.get(Stats.HP);
	}
    
}
