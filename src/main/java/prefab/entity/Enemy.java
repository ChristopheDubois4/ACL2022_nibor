package prefab.entity;

import java.util.List;

import prefab.equipment.Item;

/**
 * permet à un personnage de donner ses objets et son expérience au joueur
 */
public interface Enemy {

    public List<Item> dropItems();
    public int dropXp();

}
