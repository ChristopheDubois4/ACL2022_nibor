package prefab.entity;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;

import prefab.competence.Attack;
import prefab.competence.Spell;
import prefab.equipment.Item;
import prefab.information.Image;
import prefab.information.Position;
import prefab.information.State;
import prefab.information.Stats;

/**
 * représente les personnages du jeu de manière générale
 */
public abstract class Character extends GameObject {
    
    protected HashMap<Stats, Integer> stats;
    protected HashMap<Stats, Integer> currentStats;  

    protected int money;
    protected int level;    
    protected int xp;

    private static final int inventoryLength = 171;
    protected Item[] inventory = new Item[inventoryLength];

    protected List<Attack> attacks;
    protected List<Spell> spells;

    /**
     * constructeur de la classe Character heritant de GameObject
     */
    public Character(Position position, HashMap<State, Image> graphics, String objectName, int horizontalHitBox, int verticalHitBox) {
        super(position, graphics, objectName, horizontalHitBox, verticalHitBox);
    }

    /**
     * constructeur surchargé de la classe Character heritant de GameObject
     * @param stats les stats par defaut du personnage
     * @param money l'argent par defaut du personnage
     * @param level le niveau par defaut du personnage
     * @param xp l'experience par defaut du personnage
     * @param attacks les attaques par defaut du personnage
     * @param spells les sorts par defaut du personnage
     */
    public Character(Position position, HashMap<State, Image> graphics, String objectName, int horizontalHitBox, int verticalHitBox,
            HashMap<Stats, Integer> stats, int money, int level, int xp, List<Attack> attacks,List<Spell> spells) {
        super(position, graphics, objectName, horizontalHitBox, verticalHitBox);
        this.stats = stats;
        this.currentStats = new HashMap<Stats , Integer>();
        resetCurrentStats();
    }        
    
    
    public void resetCurrentStats(){
        currentStats.putAll(stats);
    }
    
	/**
	 * inflige des dommages au joueur
	 * @param value Valeur des degats
	 */
    public void takeDammage(int value){        
        value = (int) ( currentStats.get(Stats.HP) - value*(100-currentStats.get(Stats.DEFENSE)) );
        value =  Math.max(0, value);
        currentStats.put(Stats.HP, value);        
        if (value == 0)
            die();
    }

    /**
	 * redonne de la vie au joueur
	 * @param value Valeur des points de vie rendus
	 */
    public void healCharacter(int value){
        value = Math.max(stats.get(Stats.HP), currentStats.get(Stats.HP) + value);
        currentStats.put(Stats.HP, value);
    }

    /**
     * ajoute un item dans l'inventaire
     * @param item objet que l'on souhaite ajouté
     * @param position position à laquelle on veut ranger l'objet
     * @return 
     *      - null si l'emplacement est vide
     *      - l'objet à l'emplacement "position" 
     */
    public Item addItem(Item item, int position) {
        Item itemExchanged = inventory[position];
        inventory[position] = item;
        return itemExchanged;
    }

    /**
     * initailise les charactéristiques par défaut du personnage 
     */
    protected abstract void initCharacteristic();

    /**
     * gère la mort du personnage
     */
    public abstract void die();

}
