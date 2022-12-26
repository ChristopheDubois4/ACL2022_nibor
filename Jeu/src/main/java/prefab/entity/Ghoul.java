package prefab.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import prefab.equipment.Consumable;
import prefab.equipment.Effect;
import prefab.equipment.Item;
import prefab.equipment.Weapon;
import prefab.equipment.Effect.TypeEffects;
import prefab.information.Position;
import prefab.information.Stats;
import prefab.rendering.Animation;


/**
 * représente un personnage particulié qui est un ennemi du joueur
 * (Nom à changer)
 */
public class Ghoul extends Character implements Enemy{

    public Ghoul(Position position, Animation animation, int horizontalHitBox, int verticalHitBox, String name) throws CloneNotSupportedException {
        super(position, animation, horizontalHitBox, verticalHitBox, name);

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
        List<Effect> effectPopo = new ArrayList<Effect>();
        effectPopo.add(new Effect(TypeEffects.HEAL, 10));
        inventory[13][5] = new Consumable("Potion de soin", "potion_heal",effectPopo);
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
