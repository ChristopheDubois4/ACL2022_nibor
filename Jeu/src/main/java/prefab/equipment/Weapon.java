package prefab.equipment;

/**
 * Statistique et description d'une arme
 */

public class Weapon extends Item {

    public Weapon(String name, String graphicsSelector, int price) {
        super(name, graphicsSelector);
    }

    public Weapon(String name, String graphicsSelector) {
        super(name, graphicsSelector);
    }

    
}