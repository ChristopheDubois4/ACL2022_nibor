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

    ArmorPieces armorPiece;

    public Armor(String name, String graphicsSelector, int price , ArmorPieces armorPiece) {
        super(name, graphicsSelector, price);
        this.armorPiece  = armorPiece;
    }
    public Armor(String name, String graphicsSelector, ArmorPieces armorPiece) {
        this(name, graphicsSelector, 0, armorPiece);
    }
	
    
}
