package prefab.gui;

import java.util.HashMap;
import java.util.List;

import prefab.equipment.Armor;
import prefab.equipment.ArmorPieces;
import prefab.equipment.Item;
import prefab.equipment.Weapon;

public interface DisplayingPlayerInventory {
    
    public int getMoney();
    public Item[] getInventory();
    public HashMap<ArmorPieces, Armor> getEquipedArmor();
    public Weapon getWeapon();
}
