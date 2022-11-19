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
import prefab.information.Visual;

/**
 * représente les personnages du jeu de manière générale
 */
public abstract class Character extends GameObject {
    
    protected HashMap<Stats, Integer> stats;
    protected HashMap<Stats, Integer> currentStats;  

    protected int money;
    protected int level;    
    protected int xp;

    private static final int inventoryLength = 75;
    protected Item[] inventory = new Item[inventoryLength];

    protected List<Attack> attacks;
    protected List<Spell> spells;
    
    private boolean isInMouvement = false;
        
    /**
     * constructeur de la classe Character heritant de GameObject
     */
    public Character(Position position, HashMap<State, BufferedImage> graphics, String objectName, int horizontalHitBox, int verticalHitBox) {
        super(position, graphics, objectName, horizontalHitBox, verticalHitBox, State.IDLE_DOWN);        
        initVisual();
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
    public Character(Position position, HashMap<State, BufferedImage> graphics, String objectName, int horizontalHitBox, int verticalHitBox,
            HashMap<Stats, Integer> stats, int money, int level, int xp, List<Attack> attacks,List<Spell> spells) {
        super(position, graphics, objectName, horizontalHitBox, verticalHitBox, State.IDLE_DOWN);
        this.stats = stats;
        this.currentStats = new HashMap<Stats , Integer>();
        initVisual();
        resetCurrentStats();
    }            
    
    public void resetCurrentStats(){
        currentStats.putAll(stats);
    }

    public HashMap<Stats, Integer> getStats(){
        return stats;
    }

    public HashMap<Stats, Integer> getCurrentStats(){
        return currentStats;
    }
    
    public Item[] getInventory() {
        return null;
    }

    public void setInventory(List<Item> inventory) {
        //this.inventory = inventory;
    }

    public void displayInventoryConsole(){
        System.out.print("Votre inventaire : ");
        for (Item item : getInventory()){
            if(item == null) System.out.print("Empty ");
            else System.out.print(item.getName()+" ");
        }
        System.out.print("\n");
    }

	/**
	 * inflige des dommages au joueur
	 * @param value Valeur des degats
	 */
    public void takeDammage(int value){        
        value = (int) ( currentStats.get(Stats.HP) - value*(100-currentStats.get(Stats.DEFENSE))/100 );
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
    
    
    /**
     * (W I P)
     * @param moveTime
     * @param nbUpdate
     */
    public void animateMovement(int moveTime, int nbUpdate) {
    	isInMouvement = true;
    	visual.resetShift();
    	new Thread(() -> {
    		for (int i = 0; i<nbUpdate; i++) {		     
    			try {    				
					visual.updateMoveShift();    	    		
    				Thread.sleep(moveTime/nbUpdate);
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
        	} isInMouvement = false;
    	}).start();    	
    }
    
    public boolean getIsInMouvement( ) {
    	return isInMouvement;
    }
}
