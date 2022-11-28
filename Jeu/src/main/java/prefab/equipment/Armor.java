package prefab.equipment;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import prefab.information.State;

/**
 * Statistique et description d'une armure
 */

public class Armor extends Item {
	
	public enum ArmorPieces {
		HELMET,
		CHESTPLATE,
		LEGGING,
		BOOTS,
	}

	public Armor(int price, HashMap<State, BufferedImage> graphics, String name) {
		super(price, graphics, name);
	}
    
}
