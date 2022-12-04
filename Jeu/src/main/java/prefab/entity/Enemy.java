package prefab.entity;

import prefab.equipment.Item;


/**
 * permet à un personnage de donner ses objets et son expérience au joueur
 */
public interface Enemy{

    public Item dropItem();
    public int dropXp();
    
}
