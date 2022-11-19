package prefab.entity;

import java.util.HashMap;
import java.util.List;

import prefab.equipment.Item;
import prefab.information.Position;
import prefab.information.State;
import java.awt.image.BufferedImage;


/**
 * représente un personnage particulié qui est un ennemi du joueur
 * (Nom à changer)
 */
public class Mob1 extends Character implements Enemy{

    public Mob1(Position position, HashMap<State, BufferedImage> graphics, String objectName, int horizontalHitBox, int verticalHitBox) {
        super(position, graphics, objectName, horizontalHitBox, verticalHitBox);
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

}
