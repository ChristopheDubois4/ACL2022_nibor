package game;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;

public abstract class Character extends GameObject {
    
    HashMap<Stats, Integer> stats;
    HashMap<Stats, Integer> currentStats;  

    int money;
    int level;    
    int xp;

    List<Item> inventory;
    List<Skill> skills;

    /**
     * constructeur de la classe Character heritant de GameObject
     * les parametres hérités sont commentés dans la classe GameObject
     */
    public Character(Position position, HashMap<State, BufferedImage> graphics, String objectName, int verticalHitBox, int horizontalHitBox) {
        super(position, graphics, objectName, verticalHitBox, horizontalHitBox);
    }

    /**
     * constructeur surchargé de la classe Character heritant de GameObject
     * les parametres hérités sont commentés dans la classe GameObject
     * @param stats les stats par defaut du personnage
     * @param money l'argent par defaut du personngae
     * @param level le niveau par defaut du personnage
     * @param xp l'experience par defaut du personnage
     * @param skills les competences par defaut du personnage
     */
    public Character(Position position, HashMap<State, BufferedImage> graphics, String objectName, int verticalHitBox, int horizontalHitBox,
            HashMap<Stats, Integer> stats, int money, int level, int xp, List<Skill> skills) {
        super(position, graphics, objectName, verticalHitBox, horizontalHitBox);
        this.stats = stats;
        this.currentStats = new HashMap<Stats , Integer>();
        resetCurrentStats();
    }        
    
    

    public void resetCurrentStats(){
        currentStats.putAll(stats);
    }

    
	/**
	 * methode qui inflige des dommages au joueur
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
	 * methode qui redonne de la vie au joueur
	 * @param value Valeur des points de vie rendus
	 */
    public void healCharacter(int value){
        value = Math.max(stats.get(Stats.HP), currentStats.get(Stats.HP) + value);
        currentStats.put(Stats.HP, value);
    }

  
    
    protected abstract void initCharacteristic();
    public abstract void die();

}
