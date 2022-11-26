package prefab.entity;

import java.util.HashMap;

import prefab.competence.Attack;
import prefab.competence.Spell;
import prefab.equipment.Armor;
import prefab.equipment.Armor.ArmorPieces;
import prefab.equipment.Consumable;
import prefab.equipment.Effect;
import prefab.equipment.Item;
import prefab.equipment.Weapon;
import prefab.equipment.Effect.TypeEffects;
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

                spells.add(new Spell("boule de feux"));
                spells.add(new Spell("lance de glace"));

                attacks.add(new Attack("charge"));
                attacks.add(new Attack("regard malaisant"));
                attacks.add(new Attack("lancé de Clément"));

                this.stats.put(Stats.HP, 100);
                this.stats.put(Stats.MANA, 100);
                this.stats.put(Stats.STAMINA, 100);

                this.stats.put(Stats.DEFENSE, 50);
                this.stats.put(Stats.SPEED, 100);
                this.stats.put(Stats.DAMAGE, 5);

                Effect effectPopo = new Effect(TypeEffects.HEAL, 10);

                inventory[0][0] = new Weapon("epeeDelaMort", "sword_1");
                inventory[13][5] = new Consumable("truc", "bitcoin",effectPopo);
                inventory[13][2] = new Consumable("truc", "bitcoin",effectPopo);
                inventory[13][4] = new Consumable("truc", "bitcoin",effectPopo);


                weapon=new Weapon("epeeDelaMort", "sword_1");
                HashMap<ArmorPieces,Armor> equippedArmorTemp = new HashMap<ArmorPieces,Armor>();

                equippedArmorTemp.put(ArmorPieces.HELMET,new Armor("Helmet", "helmet_1", ArmorPieces.HELMET));
                equippedArmorTemp.put(ArmorPieces.CHESTPLATE,new Armor("ChestPlate", "chestplate_1", ArmorPieces.CHESTPLATE));
                equippedArmorTemp.put(ArmorPieces.LEGGING,new Armor("Legging", "legging_1", ArmorPieces.LEGGING));
                equippedArmorTemp.put(ArmorPieces.BOOTS,new Armor("Boots", "boots_1", ArmorPieces.BOOTS));
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
    public HashMap<ArmorPieces, Armor> getEquipedArmor() {
        // TODO Auto-generated method stub
        return equippedArmor;
    }

	public int getHealth() {
		return currentStats.get(Stats.HP);
	}

    @Override
    public Weapon getWeapon() {
        // TODO Auto-generated method stub
        return weapon;
    }



    
}
