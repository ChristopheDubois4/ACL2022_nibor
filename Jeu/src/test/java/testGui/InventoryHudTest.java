package testGui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.javatuples.Pair;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import manager.Utilities;
import prefab.entity.Player;
import prefab.entity.Player.PlayerClasses;
import prefab.equipment.Weapon;
import prefab.gui.InventoryHud;
import prefab.information.Position;
import prefab.props.Chest;
import prefab.rendering.Animation;

public class InventoryHudTest {
    Pair<Integer, Integer> clickInventory;
    Pair<Integer, Integer> clickChest;
    Pair<Integer, Integer> clickEquipedStuff;     
    Pair<Integer, Integer> clickNoHud;
    Pair<Integer, Integer> clickPressed;
    Pair<Integer, Integer> clickReleased;
    
    @Before
    public void setUp() throws Exception{
        InventoryHud.INSTANCE.initHud();
        clickInventory = new Pair<Integer,Integer>(10, 3);
        clickChest = new Pair<Integer,Integer>(10, 11);
        clickEquipedStuff = new Pair<Integer,Integer>(6, 10);
        clickNoHud = new Pair<Integer,Integer>(0, 0);
        clickPressed = new Pair<Integer,Integer>(10, 8);
        clickReleased = new Pair<Integer,Integer>(10, 7);
        Player.getInstance().initPlayer(PlayerClasses.WARRIOR);
        Player.getInstance().addItem(new Weapon("test", "sword_1", 0));
    }

    @Test
    public void testGetInstance() {
        assertEquals(InventoryHud.INSTANCE, InventoryHud.getInstance());
    }

    @Test
    public void testIsDisplay() {
        assertEquals(false, InventoryHud.getInstance().hudIsDisplayed());
        InventoryHud.getInstance().changeDisplayState();
        assertEquals(true, InventoryHud.getInstance().hudIsDisplayed());
    }

    @Test
    public void testOpenWith() throws CloneNotSupportedException,Exception {
        Position position = Position.create(1, 1);
        Animation animation = Animation.create(Utilities.getSpritesFromJSON("chest"));
        Chest chest = new Chest(position, animation, 1, 1); 
        InventoryHud.getInstance().openWith(chest);
        assertEquals(true, InventoryHud.getInstance().isChestDisplay());
    }
    
    @Test
    public void testHudInventoryClicked() throws Exception {
        InventoryHud.getInstance().setPressedClick(clickInventory);
        InventoryHud.getInstance().setReleasedClick(clickInventory);
        InventoryHud.getInstance().getPressedHud();
        InventoryHud.getInstance().getReleasedHud();
        assertTrue(InventoryHud.getInstance().getHudPressed().equals("InventoryHUD"));
        assertTrue(InventoryHud.getInstance().getHudReleased().equals("InventoryHUD"));
    }

    @Test
    public void testHudChestClicked() throws CloneNotSupportedException,Exception {
        Position position = Position.create(1, 1);
        Animation animation = Animation.create(Utilities.getSpritesFromJSON("chest"));
        Chest chest = new Chest(position, animation, 1, 1); 
        InventoryHud.getInstance().openWith(chest);
        InventoryHud.getInstance().setPressedClick(clickChest);
        InventoryHud.getInstance().setReleasedClick(clickChest);
        InventoryHud.getInstance().getPressedHud();
        InventoryHud.getInstance().getReleasedHud();
        assertTrue(InventoryHud.getInstance().getHudPressed().equals("ChestHUD"));
        assertTrue(InventoryHud.getInstance().getHudReleased().equals("ChestHUD"));
    }

    @Test
    public void testHudEquipedStuffClicked(){
        InventoryHud.getInstance().setPressedClick(clickEquipedStuff);
        InventoryHud.getInstance().setReleasedClick(clickEquipedStuff);
        InventoryHud.getInstance().getPressedHud();
        InventoryHud.getInstance().getReleasedHud();
        assertTrue(InventoryHud.getInstance().getHudPressed().equals("EquippedStuffHUD"));
        assertTrue(InventoryHud.getInstance().getHudReleased().equals("EquippedStuffHUD"));
    }

    @Test
    public void testNoHudCliked(){
        InventoryHud.getInstance().setPressedClick(clickNoHud);
        InventoryHud.getInstance().setReleasedClick(clickNoHud);
        InventoryHud.getInstance().getPressedHud();
        InventoryHud.getInstance().getReleasedHud();
        assertTrue(InventoryHud.getInstance().getHudPressed().equals(""));
        assertTrue(InventoryHud.getInstance().getHudReleased().equals(""));
    }

    @Test
    public void testSwapItemInventory() throws CloneNotSupportedException, Exception{
        assertTrue(Player.getInstance().getInventory()[0][0].getName().equals("test"));
        assertTrue(Player.getInstance().getInventory()[0][1] == null);

        InventoryHud.getInstance().setPressedClick(clickPressed);
        InventoryHud.getInstance().setReleasedClick(clickReleased);
        InventoryHud.getInstance().swapItemInventory();
        assertTrue(Player.getInstance().getInventory()[0][1].getName().equals("test"));
        assertTrue(Player.getInstance().getInventory()[0][0] == null);
    }
}