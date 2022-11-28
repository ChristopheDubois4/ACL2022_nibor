package prefab.equipment;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import prefab.information.State;

/**
 * Statistique et description d'une arme
 */

public class Weapon extends Item {

	public Weapon(int price, HashMap<State, BufferedImage> graphics, String name) {
		super(price, graphics, name);
	}
    
}
