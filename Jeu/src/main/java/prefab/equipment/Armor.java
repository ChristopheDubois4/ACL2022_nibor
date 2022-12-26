package prefab.equipment;

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

    public ArmorPieces getArmorPiece() {
        return armorPiece;
    }
    public Armor(String name, String graphicsSelector, int price , ArmorPieces armorPiece) {
        super(name, graphicsSelector, price);
        this.armorPiece  = armorPiece;
    }
    public Armor(String name, String graphicsSelector, ArmorPieces armorPiece) {
        super(name, graphicsSelector, 0);
        this.armorPiece  = armorPiece;

    }
    
}
