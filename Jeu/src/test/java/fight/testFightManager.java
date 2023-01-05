package fight;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import manager.FightManager;
import manager.Utilities;
import prefab.entity.Ghoul;
import prefab.entity.Player;
import prefab.entity.Player.PlayerClasses;
import prefab.equipment.Consumable;
import prefab.equipment.Item;
import prefab.equipment.Weapon;
import prefab.information.Position;
import prefab.information.State;
import prefab.rendering.Animation;
import prefab.rendering.CharacterAnimation;
import prefab.rendering.Sprite;

public class testFightManager {

    Player p;
    Ghoul mob;
    static FightManager f = FightManager.getInstance();
    Item i1, i2, i3;

    @AfterClass
    public static void clean(){
        f.fightHud.changeDisplayState(0);
    }

    @Before
    public void setUp(){

        try {
            p = Player.getInstance();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            p.initPlayer(PlayerClasses.WARRIOR);
        } catch (Exception e) {
            e.printStackTrace();
        }

        i1 = new Weapon("ITEM", "potion_heal", 0);
        i2 = new Consumable("CONSOMMABLE 1", "potion_heal", null); 
        i3 = new Consumable("CONSOMMABLE 2", "potion_heal", null);
        
        p.addItem(i1);
        p.addItem(i2);
        p.addItem(i3);

        try {
            f.initFightManager();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        };
        
        HashMap<State, Sprite> s = new HashMap<State, Sprite>();
        try {
            s = Utilities.getSpritesFromJSON("mob");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Animation a = CharacterAnimation.createForPNJ(s);

        Position pos1 = Position.create(10, 10);

        try {
            mob = new Ghoul(pos1, a, 1, 1, "Jean le Destructeur");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetInstance() {
        assertEquals(f.INSTANCE, FightManager.getInstance());
    }
    
    @Test
    public void testGetConsumables()
    {
        List<int[]> c = FightManager.getInstance().getConsumables(p.getInventory(), true);
 
        assertTrue(" PB : Nombre de consommables pas OK", c.size() == 2);
        assertEquals(p.getInventory()[c.get(0)[0]][c.get(0)[1]], i2);
        assertEquals(p.getInventory()[c.get(1)[0]][c.get(1)[1]], i3);
    }

    @Test
    public void testSstartNewFight()
    {       
        assertFalse(" PB : le jeu est en mode combat", f.getIsInFight());
        assertFalse(" PB : le joueur est en mode combat", p.getState() == State.FIGHT);
        assertFalse(" PB : l'ennemi est en mode combat", mob.getState() == State.FIGHT);

        f.startNewFight(mob);

        assertTrue(" PB : le jeu n'est pas en mode combat", f.getIsInFight());
        assertTrue(" PB : le joueur n'est pas en mode combat", p.getState() == State.FIGHT);
        assertTrue(" PB : l'ennemi n'est pas en mode combat", mob.getState() == State.FIGHT);
    }

}
