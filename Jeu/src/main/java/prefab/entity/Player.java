package prefab.entity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
                this.stats.put(Stats.DEFENSE, 50);
                inventory[0] = new Item("epeeDelaMort", "sword_1");
                inventory[52] = new Item("truc", "bitcoin");
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
    public Item[] getInventory() {
        return inventory;
    }

    @Override
    public HashMap<ArmorPieces, Armor> getEquipedArmor() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Weapon getWeapon() {
        // TODO Auto-generated method stub
        return null;
    }

	@Override
	public int getHealth() {
		return currentStats.get(Stats.HP);
	}
    
}
