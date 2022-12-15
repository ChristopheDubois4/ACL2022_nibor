package prefab.equipment;

import java.util.List;

/**
 * Statistique et description d'une arme
 */

public class Weapon extends Item {

    public Weapon(String name, String graphicsSelector, int price) {
        super(name, graphicsSelector);
    }


    public Weapon(String name, String graphicsSelector, List<Effect> effect) {
        super(name, graphicsSelector);
    }

    
}