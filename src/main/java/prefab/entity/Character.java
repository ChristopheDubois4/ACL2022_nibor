package prefab.entity;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;

import prefab.competence.Attack;
import prefab.competence.Spell;
import prefab.equipment.Item;
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

    protected List<Item> inventory;
    protected List<Attack> attacks;
    protected List<Spell> spells;

    /**
     * constructeur de la classe Character heritant de GameObject
     */
    public Character(Position position, HashMap<State, BufferedImage> graphics, String objectName, int verticalHitBox, int horizontalHitBox) {
        super(position, graphics, objectName, verticalHitBox, horizontalHitBox);
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
    public Character(Position position, HashMap<State, BufferedImage> graphics, String objectName, int verticalHitBox, int horizontalHitBox,
            HashMap<Stats, Integer> stats, int money, int level, int xp, List<Attack> attacks,List<Spell> spells) {
        super(position, graphics, objectName, verticalHitBox, horizontalHitBox);
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
     * initailise les charactéristiques par défaut du personnage 
     */
    protected abstract void initCharacteristic();

    /**
     * gère la mort du personnage
     */
    public abstract void die();

}
