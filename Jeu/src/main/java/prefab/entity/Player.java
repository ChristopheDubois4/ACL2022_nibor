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
import prefab.information.Position;
import prefab.information.Stats;
import prefab.rendering.Animation;


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
    
    PlayerClasses classPlayed;
    
    private int xpToNextLevel = 100;
    
    /**
     * constructeur de la classe Player heritant de Character
     * @param classPlayed la classe de combattant du joueur
     * @throws CloneNotSupportedException
     */
    public Player(Position position, Animation animation, int horizontalHitBox, int verticalHitBox, String name, PlayerClasses classPlayed) throws CloneNotSupportedException {
        super(position, animation, horizontalHitBox, verticalHitBox, name);
        this.classPlayed = classPlayed;
        this.xp = 0;
        initCharacteristic();
        startAnimation();

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

                List<Effect> effectPopo = new ArrayList<Effect>(){{add(new Effect(TypeEffects.HEAL, 10));}};

                weapon = new Weapon("epeeDelaMort", "sword_1", 50);

                inventory[0][0] = new Weapon("epeeDelaMort", "sword_1", 50);
                inventory[13][5] = new Consumable("Potion de soin", "potion_heal",effectPopo);
                inventory[13][2] = new Consumable("Potion de soin", "potion_heal",effectPopo);
                inventory[13][4] = new Consumable("Potion de soin", "potion_heal",effectPopo);


                //weapon=new Weapon("epeeDelaMort", "sword_1");
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
