package prefab.equipment;

import java.util.HashMap;

import prefab.information.Stats;

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
    private HashMap<Stats, Integer> bonusStats = new HashMap<Stats, Integer>();

    public ArmorPieces getArmorPiece() {
        return armorPiece;
    }
    public Armor(String name, String graphicsSelector, int price , ArmorPieces armorPiece , HashMap<Stats, Integer> bonusStats) {
        super(name, graphicsSelector, price);
        this.armorPiece  = armorPiece;
        this.bonusStats = bonusStats;
    }
    public Armor(String name, String graphicsSelector, ArmorPieces armorPiece, HashMap<Stats, Integer> bonusStats) {
        super(name, graphicsSelector, 0);
        this.armorPiece  = armorPiece;
        this.bonusStats = bonusStats;

    }
    public Armor(String name, String graphicsSelector, ArmorPieces armorPiece) {
        super(name, graphicsSelector, 0);
        this.armorPiece  = armorPiece;
        this.bonusStats = null;

    }

    public HashMap<Stats, Integer> getBonusStats() {
        return bonusStats;
    }
    
}
