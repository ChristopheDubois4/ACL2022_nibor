package prefab.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import prefab.competence.Attack;
import prefab.competence.Spell;
import prefab.equipment.Armor;
import prefab.equipment.Armor.ArmorPieces;
import prefab.equipment.Consumable;
import prefab.equipment.Effect;
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

                spells.add(new Spell("Boule de feux"));
                spells.add(new Spell("Lance de glace"));

                attacks.add(new Attack("Charge"));
                attacks.add(new Attack("Regard malaisant"));
                attacks.add(new Attack("Lancé de Cl?ment"));

                this.stats.put(Stats.HP, 100);
                this.stats.put(Stats.MANA, 100);
                this.stats.put(Stats.STAMINA, 100);

                this.stats.put(Stats.DEFENSE, 50);
                this.stats.put(Stats.SPEED, 100);
                this.stats.put(Stats.DAMAGE, 5);

                List<Effect> effectPopo = new ArrayList<Effect>();
                effectPopo.add(new Effect(TypeEffects.HEAL, 10));
                List<Effect> effectSword = new ArrayList<Effect>();
                effectSword.add(new Effect(TypeEffects.HIT, 20));

                inventory[0][0] = new Weapon("epeeDelaMort", "sword_2",effectSword);
                inventory[2][2] = new Armor("test_1", "bitcoin", ArmorPieces.CHESTPLATE);
                inventory[2][3] = new Armor("test_1", "bitcoin", ArmorPieces.LEGGING);
                inventory[2][4] = new Armor("test_1", "bitcoin", ArmorPieces.BOOTS);


                inventory[0][0] = new Weapon("epeeDelaMort", "sword_1",effectSword);
                inventory[13][5] = new Consumable("Potion de soin", "potion_heal",effectPopo);
                inventory[13][2] = new Consumable("Potion de soin", "potion_heal",effectPopo);
                inventory[13][4] = new Consumable("Potion de soin", "potion_heal",effectPopo);

                weapon = new Weapon("epeeDelaMort", "sword_2",effectSword);
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
    public HashMap<ArmorPieces, Armor> getEquippedArmor() {
        // TODO Auto-generated method stub
        return equippedArmor;
    }

    @Override
    public void setEquippedArmor( HashMap<ArmorPieces, Armor> newEquippedArmor) {
        this.equippedArmor=newEquippedArmor;
    }

	public int getHealth() {
		return currentStats.get(Stats.HP);
	}

    @Override
    public Weapon getWeapon() {
        return weapon;
    }

    
    @Override
    public void setWeapon(Weapon weapon) {
        if (weapon==null){
            weapon=new Weapon(null, null, null);
        }
        this.weapon=weapon;
    }



    
}
