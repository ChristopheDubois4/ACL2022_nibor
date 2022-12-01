package prefab.entity;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import prefab.competence.Attack;
import prefab.competence.Spell;
import prefab.equipment.Armor;
import prefab.equipment.Effect;
import prefab.equipment.Item;
import prefab.equipment.Weapon;
import prefab.equipment.Armor.ArmorPieces;
import prefab.information.Position;
import prefab.information.State;
import prefab.information.Stats;
import manager.WorldManager;

/**
 * représente les personnages du jeu de manière générale
 */
public abstract class Character extends GameObject {
    
    protected HashMap<Stats, Integer> stats;
    protected HashMap<Stats, Integer> currentStats;  

    protected int money;
    protected int level;    
    protected int xp;

    public static final int inventoryLengthX = 15;
    public static final int inventoryLengthY = 6;
    protected Item[][] inventory = new Item[inventoryLengthX][inventoryLengthY];

    protected HashMap<ArmorPieces,Armor> equippedArmor;

    protected Weapon weapon;

    protected List<Attack> attacks;
    protected List<Spell> spells;
    
    protected List<Effect> effects;
    
    private boolean isInMouvement = false;
        
    /**
     * constructeur de la classe Character heritant de GameObject
     */
    public Character(Position position, HashMap<State, BufferedImage> graphics, String objectName, int horizontalHitBox, int verticalHitBox) {
        super(position, graphics, horizontalHitBox, verticalHitBox);
        this.attacks = new ArrayList<Attack>();   
        this.spells = new ArrayList<Spell>();  
        this.effects=new ArrayList<>();
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
        this(position, graphics, objectName, horizontalHitBox, verticalHitBox);
        this.stats = stats;
        this.attacks = attacks;
        this.spells = spells;
        this.effects=new ArrayList<>();
        this.currentStats = new HashMap<Stats , Integer>();
        initDefaultEquipment();
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

    public HashMap<ArmorPieces, Armor> initDefaultEquipment(){
        equippedArmor.put(ArmorPieces.HELMET,null);
        equippedArmor.put(ArmorPieces.CHESTPLATE,null);
        equippedArmor.put(ArmorPieces.LEGGING,null);
        equippedArmor.put(ArmorPieces.BOOTS,null);
        return equippedArmor;
    }
    
    public Item[][] getInventory() {
        return inventory;
    }

    public List<Attack> getAttacks() {
        return attacks;
    }
    public List<Spell> getSpells() {
        return spells;
    }

    public void setInventory(List<Item> inventory) {
        //this.inventory = inventory;
    }

    public void displayInventoryConsole(){
        for ( int line=0; line<getInventory().length; line++ ) {
            for ( int column=0; column<getInventory()[line].length; column++ ) {
                if(getInventory()[line][column] == null) System.out.print("Empty ");
                else System.out.print(getInventory()[line][column].getName()+ " " );
            }
            System.out.println();
        }
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
        value = Math.min(stats.get(Stats.HP), currentStats.get(Stats.HP) + value);
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
    public Item addItem(Item item, int positionX,int positionY) {
        Item itemExchanged = inventory[positionX][positionY];
        inventory[positionX][positionY] = item;
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
     * déplace le personnage
     */
    public void move(int deltaX, int deltaY) {  
        super.move(deltaX, deltaY);
        animateMovement();       
    }
    
    /**
     * permet de mettre a jour la position et l'état du personnage lors d'un déplacement
     */
    private void animateMovement() {
    	isInMouvement = true;
    	visual.resetShift();
    	new Thread(() -> {
    		for (int i = 0; i<WorldManager.IMAGES_PER_MOVE; i++) {		     
    			try {    				
					visual.updateMoveShift();    	    		
    				Thread.sleep((long) (WorldManager.MOVE_TIME/WorldManager.IMAGES_PER_MOVE));
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
        	} isInMouvement = false;
    	}).start();    	
    }

    public void setEquippedArmor(HashMap<ArmorPieces, Armor> equippedArmor) {
        this.equippedArmor = equippedArmor;
    }
    
    public boolean getIsInMouvement( ) {
    	return isInMouvement;
    }


    public List<Effect> getEffects() {
        return  this.effects;
    }


    public void addEffects(List<Effect> effects2) {
        this.effects.addAll(effects2);
    }
}
