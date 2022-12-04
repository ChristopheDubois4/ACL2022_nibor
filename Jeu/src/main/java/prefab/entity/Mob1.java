package prefab.entity;

import java.util.HashMap;

import prefab.equipment.Item;
import prefab.equipment.Weapon;
import prefab.information.Position;
import prefab.information.State;
import prefab.information.Stats;

import java.awt.image.BufferedImage;


/**
 * représente un personnage particulié qui est un ennemi du joueur
 * (Nom à changer)
 */
public class Mob1 extends Character implements Enemy{

    public Mob1(Position position, HashMap<State, BufferedImage> graphics, String objectName, int horizontalHitBox, int verticalHitBox) {
        super(position, graphics, objectName, horizontalHitBox, verticalHitBox);

        this.stats = new HashMap<Stats , Integer>();
        this.currentStats = new HashMap<Stats , Integer>();

        this.stats.put(Stats.HP, 100);
        this.stats.put(Stats.MANA, 100);
        this.stats.put(Stats.STAMINA, 100);

        this.stats.put(Stats.DEFENSE, 50);
        this.stats.put(Stats.SPEED, 100);
        this.stats.put(Stats.DAMAGE, 5);
        inventory[0][0] = new Weapon("epeeDelaMort", "sword_1",30);
        inventory[13][5] = new Weapon("truc", "bitcoin", 50);
        resetCurrentStats();


    }

    @Override
    public Item dropItem() {
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
