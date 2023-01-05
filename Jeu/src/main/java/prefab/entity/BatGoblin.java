package prefab.entity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.parser.ParseException;

import prefab.competence.Attack;
import prefab.competence.Spell;
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
public class BatGoblin extends Enemy {

    public BatGoblin(Position position, Animation animation, int horizontalHitBox, int verticalHitBox, String name) throws FileNotFoundException, IOException, ParseException, Exception {
        super(position, animation, horizontalHitBox, verticalHitBox, name);
     
    }

    @Override
    protected void initCharacteristic() throws FileNotFoundException, IOException, ParseException, Exception {

        this.stats = new HashMap<Stats , Integer>();
        this.currentStats = new HashMap<Stats , Integer>();

        this.stats.put(Stats.HP, 60);
        this.stats.put(Stats.MANA, 80);
        this.stats.put(Stats.STAMINA, 80);

        this.stats.put(Stats.DEFENSE, 20);
        this.stats.put(Stats.SPEED, 100);
        this.stats.put(Stats.DAMAGE, 40);
        inventory[0][0] = new Weapon("epeeDelaMort", "sword_1",30);
        inventory[13][5] = new Weapon("truc", "bitcoin", 50);
        List<Effect> effectPopo = new ArrayList<Effect>();
        effectPopo.add(new Effect(TypeEffects.HEAL, 10));
        inventory[13][5] = new Consumable("Potion de soin", "potion_heal",effectPopo);

        attacks.add(new Attack("Morsure", 30 ,15, 0));
        attacks.add(new Attack("Lacération", 60 ,40, 55));

        resetCurrentStats();
      
        spells.add(new Spell("nyan nyan", 40, 30, new ArrayList<Effect>()));
        
        ATK_PROBA = 0.6;
        MAGIC_PROBA = 0.3; 
        ITEM_PROBA = 0.1;   
        
    }

    @Override
    public Item dropItem() {
        return null;
    }

    @Override
    public int dropXp() {
        return 0;
    }



   
}
