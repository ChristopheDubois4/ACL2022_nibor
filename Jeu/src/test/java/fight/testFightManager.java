package fight;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import manager.FightManager;
import prefab.entity.Player;
import prefab.equipment.Consumable;
import prefab.equipment.Item;
import prefab.equipment.Weapon;

public class testFightManager {

    Player p;
    Item i1, i2, i3;

    @Before
    public void setUp(){

        try {
            p = Player.getInstance();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        i1 = new Weapon("ITEM", null, 0);
        i2 = new Consumable("CONSOMMABLE 1", null, null)
        i3 = new Consumable("CONSOMMABLE 2", null, null);
        
        p.addItem(i1);
        p.addItem(i2);
        p.addItem(i3);
    }
    
    @Test
    public void testGetConsumables()
    {
        List<int[]> c = FightManager.getInstance().getConsumables(p.getInventory(), true);
    }

}
