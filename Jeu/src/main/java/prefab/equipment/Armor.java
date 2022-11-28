package prefab.equipment;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import prefab.information.State;

/**
 * Statistique et description d'une armure
 */

public class Armor extends Item {
    public Armor(int price, HashMap<State, BufferedImage> graphics, String name) {
        super(price, graphics, name);
    }
    public Armor(String name, String graphicsSelector) {
        super(name, graphicsSelector);  	
    }
	public enum ArmorPieces {
		HELMET,
		CHESTPLATE,
		LEGGING,
		BOOTS,
	}
    
}
