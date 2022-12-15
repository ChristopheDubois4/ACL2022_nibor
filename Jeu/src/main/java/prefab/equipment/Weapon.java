package prefab.equipment;

import java.util.List;

/**
 * Statistique et description d'une arme
 */

public class Weapon extends Item {
	
	private int power;

    public Weapon(String name, String graphicsSelector, int power, int price) {
        super(name, graphicsSelector);
        this.power = power;
    }

    public Weapon(String name, String graphicsSelector, int power) {
        this(name, graphicsSelector, power, 0);
    }
    
    public int getPower() {
    	return power;
    }
    
    
    
}