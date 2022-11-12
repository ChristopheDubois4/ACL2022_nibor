package prefab.entity;

import java.util.HashMap;
import java.util.List;

import prefab.equipment.Armor;
import prefab.equipment.ArmorPieces;
import prefab.equipment.Item;
import prefab.equipment.Weapon;
import prefab.gui.DisplayingPlayerInventory;
import prefab.information.Image;
import prefab.information.PlayerClasses;
import prefab.information.Position;
import prefab.information.State;


/**
 * représente le joueur, un personnage particulié du jeu que l'on contrôle
 */

public class Player extends Character implements DisplayingPlayerInventory{
    
    PlayerClasses classPlayed;    

    /**
     * constructeur de la classe Player heritant de Character
     * @param classPlayed la classe de combattant du joueur
     */
    public Player(Position position, HashMap<State, Image> graphics, String objectName, int horizontalHitBox, int verticalHitBox, PlayerClasses classPlayed) {
        super(position, graphics, objectName, horizontalHitBox, verticalHitBox);
        this.classPlayed = classPlayed;
        this.xp = 0;      
        initCharacteristic();
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
            
                break;
        
            default:
                break;
        }
    }   

    @Override
    public void die() {
        
    }

    @Override
    public int getMoney() {       
        return this.money;
    }

    @Override
    public List<Item> getInventory() {
        // Trie l'inventaire avant de l'afficher
        return null;
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
    
}
