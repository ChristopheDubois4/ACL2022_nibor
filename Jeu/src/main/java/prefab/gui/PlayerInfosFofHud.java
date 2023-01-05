package prefab.gui;

import java.util.HashMap;

import prefab.equipment.Armor;
import prefab.equipment.Armor.ArmorPieces;
import prefab.equipment.Item;
import prefab.equipment.Weapon;
import prefab.information.Stats;

public interface PlayerInfosFofHud {
    
    public int getMoney();
    public Item[][] getInventory();
    public HashMap<ArmorPieces, Armor> getEquipedArmor();
    public Weapon getWeapon();
    public HashMap<Stats, Integer> getCurrentStats();
    public HashMap<Stats, Integer> getStats();
    public void setWeapon(Weapon weapon);
    public void setEquippedArmor(HashMap<ArmorPieces, Armor> equippedArmor);
}
