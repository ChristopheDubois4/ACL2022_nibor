package prefab.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import manager.Utilities;
import prefab.competence.Attack;
import prefab.competence.Spell;
import prefab.equipment.Armor;
import prefab.equipment.Armor.ArmorPieces;
import prefab.equipment.Consumable;
import prefab.equipment.Effect;
import prefab.equipment.Weapon;
import prefab.equipment.Effect.TypeEffects;
import prefab.gui.PlayerInfosFofHud;
import prefab.information.Position;
import prefab.information.Stats;
import prefab.rendering.CharacterAnimation;


/**
 * représente le joueur, un personnage particulié du jeu que l'on contrôle
 */

public class Player extends Character implements PlayerInfosFofHud{

    /**
     * les classes de joueur sélectionables en début de jeu
     */
    public enum PlayerClasses {
        MAGE,
        WARRIOR,
        ASSASSIN,
        CLERIC
    }

    private static Player INSTANCE;
    
    PlayerClasses classPlayed;
    
    private int xpToNextLevel = 100;
    
    /**
     * constructeur de la classe Player heritant de Character
     * @param classPlayed la classe de combattant du joueur
     * @throws CloneNotSupportedException
     * @throws Exception
     */
    private Player() throws CloneNotSupportedException, Exception {              
        super(
            Position.create(10, 10),
            CharacterAnimation.createForPlayer(Utilities.getSpritesFromJSON("player")),
            1,
            1,
            "Nibor"
        );        
    }

    public static Player getInstance() throws CloneNotSupportedException, Exception {
        if (INSTANCE == null) {
            INSTANCE = new Player();
        }
        return INSTANCE;
    }

    public void initPlayer(PlayerClasses classPlayed) {
        this.classPlayed = classPlayed;
        initCharacteristic();
        startAnimation();
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

                List<Effect> effectSpell = new ArrayList<Effect>(){{add(new Effect(TypeEffects.HEAL, 25));}};

                spells.add(new Spell("Boule de feux", 60, 40, new ArrayList<Effect>()));
                spells.add(new Spell("Lance de glace", 45, 30, new ArrayList<Effect>()));
                spells.add(new Spell("Soin léger", 0, 20, effectSpell));

                attacks.add(new Attack("Charge", 30 ,15));
                attacks.add(new Attack("Regard malaisant", 40, 20));
                attacks.add(new Attack("Lancé de Cl?ment", 60 , 30, 15));

                this.stats.put(Stats.HP, 100);
                this.stats.put(Stats.MANA, 100);
                this.stats.put(Stats.STAMINA, 100);

                this.stats.put(Stats.DEFENSE, 50);
                this.stats.put(Stats.SPEED, 100);
                this.stats.put(Stats.DAMAGE, 50);

                List<Effect> effectPopo = new ArrayList<Effect>();
                effectPopo.add(new Effect(TypeEffects.HEAL, 10));

                inventory[2][1] = new Armor("test_1", "bitcoin", ArmorPieces.HELMET);
                inventory[2][2] = new Armor("test_1", "bitcoin", ArmorPieces.CHESTPLATE);
                inventory[2][3] = new Armor("test_1", "bitcoin", ArmorPieces.LEGGING);
                inventory[2][4] = new Armor("test_1", "bitcoin", ArmorPieces.BOOTS);


                inventory[0][0] = new Weapon("epeeDelaMort", "sword_1", 50);
                weapon = new Weapon("epeeDelaMort", "sword_1", 50);
                inventory[13][5] = new Consumable("Potion de soin", "potion_heal",effectPopo);
                inventory[13][2] = new Consumable("Potion de soin", "potion_heal",effectPopo);
                inventory[13][4] = new Consumable("Potion de soin", "potion_heal",effectPopo);


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
    
    public void receiveXp(int newXp) {
    	xp += newXp;
    	if (xp >= xpToNextLevel) {
    		levelUp();
    		xp -= xpToNextLevel;
    		xpToNextLevel = (int) (1.2*xpToNextLevel);
    	}
    }
    
    private void levelUp() {
    	level++;
    	
    }

    /**
     * utilise un consommable
     * (méthode pour le joueur)
     * @param posItem la position de l'item dans l'inventaire
     * @return 
     *      - vraie si le consommable peut être utilisé
     *      - faux sinon
     */
    public boolean useConsumable(int[] posItem) {

        try {
            if (addEffects(((Consumable) inventory[posItem[0]][posItem[1]]).getEffects())) {
                deleteItem(posItem);
                return true;
            } 
        } catch (Exception e) {
            System.out.println("(1) La position de l'objet est invalide\n(2) L'objet n'est pas de type 'Consumable'");
        }
        return false;
    }

    @Override
    public int getMoney() {       
        return this.money;
    }
    
    @Override
    public HashMap<ArmorPieces, Armor> getEquipedArmor() {
        return equippedArmor;
    }

	public int getHealth() {
		return currentStats.get(Stats.HP);
	}

    public Weapon getWeapon() {
        return weapon;
    }
    
    @Override
    public void setWeapon(Weapon newWeapon) {
        this.weapon=newWeapon;
    }
    
}
