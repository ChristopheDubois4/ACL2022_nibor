package prefab.entity;

import java.util.HashMap;
import java.util.List;

import prefab.equipment.Item;
import prefab.equipment.Weapon;
import prefab.information.Position;
import prefab.information.State;
import prefab.information.Stats;

import java.awt.image.BufferedImage;


/**
 * représente un personnage particulié qui est un ennemi du joueur
 * (Nom à changer)
 */
public class Mob1 extends Character implements Enemy{
	
	protected int pos;

    public Mob1(Position position, HashMap<State, BufferedImage> graphics, String objectName, int horizontalHitBox, int verticalHitBox, int pos) {
        super(position, graphics, objectName, horizontalHitBox, verticalHitBox);
        state = State.IDLE_LEFT;
        this.pos = pos;

        this.stats = new HashMap<Stats , Integer>();
        this.currentStats = new HashMap<Stats , Integer>();

        this.stats.put(Stats.HP, 100);
        this.stats.put(Stats.MANA, 100);
        this.stats.put(Stats.STAMINA, 100);

        this.stats.put(Stats.DEFENSE, 50);
        this.stats.put(Stats.SPEED, 100);
        this.stats.put(Stats.DAMAGE, 5);
        inventory[0][0] = new Weapon("epeeDelaMort", "sword_1");
        inventory[13][5] = new Weapon("truc", "bitcoin");
        resetCurrentStats();
    }

    @Override
    public List<Item> dropItems() {
        return null;
    }

    @Override
    public int dropXp() {
        return 0;
    }

    @Override
    protected void initCharacteristic() {
        
    }

    @Override
    public void die() {
        
    }
    
    //alterne entre une position gauche et une position droite
    public void change_pos() {
    	switch(this.pos) {
    	
    	case 1:
    		this.position.addToXY(0, 1);
    		this.pos = this.pos+1;
    		this.setState(State.IDLE_RIGHT);
    		break;
    	case 2:
    		this.position.addToXY(0,-1);
    		this.pos = this.pos-1;
    		this.setState(State.IDLE_LEFT);
    		break;
    	}
    }

}
