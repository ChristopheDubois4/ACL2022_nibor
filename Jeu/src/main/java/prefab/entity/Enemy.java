package prefab.entity;

import java.util.HashMap;
import java.util.List;

import prefab.competence.Attack;
import prefab.competence.Spell;
import prefab.equipment.Item;
import prefab.information.Stats;
import prefab.information.Visual;

/**
 * permet à un personnage de donner ses objets et son expérience au joueur
 */
public interface Enemy {

    public List<Item> dropItems();
    public int dropXp();
    public Visual getVisual();
    public HashMap<Stats, Integer> getStats();
    public HashMap<Stats, Integer> getCurrentStats();
    public void takeDammage(int value);
    public Item[][] getInventory();
    public List<Attack> getAttacks();
    public List<Spell> getSpells();
}
