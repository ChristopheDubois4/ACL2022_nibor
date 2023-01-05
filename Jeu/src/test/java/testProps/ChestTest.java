package testProps;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import engine.Cmd;
import manager.Utilities;
import prefab.entity.Player;
import prefab.equipment.Consumable;
import prefab.equipment.Item;
import prefab.equipment.Weapon;
import prefab.information.Position;
import prefab.information.State;
import prefab.props.Chest;
import prefab.rendering.Animation;

public class ChestTest {
    
    private static final int chestFirstPosX = 10, chestFirstPosY = 11;
    private static final int inventoryLength = 15;
    private static final int horizontalHitBox = 1, verticalHitBox = 1;

    Position position;
    Animation animation;
    
    private Chest chest;
    private Player player;
    private Cmd cmd;
    
    @Before
    public void setUp() throws Exception {
        position = Position.create(chestFirstPosX, chestFirstPosY);
        animation = Animation.create(Utilities.getSpritesFromJSON("chest"));
        chest = new Chest(position, animation, horizontalHitBox, verticalHitBox);        
    }
    
    @Test
    public void testConstructor() throws CloneNotSupportedException {
        Chest chestTest = new Chest(position, animation, horizontalHitBox, verticalHitBox);
        assertEquals(State.CLOSE, chestTest.getState());
        assertEquals(position.getX(), chestTest.getPosition().getX());
        assertEquals(position.getY(), chestTest.getPosition().getY());
        assertEquals(horizontalHitBox, (int) chestTest.getHitBox().getValue0());
        assertEquals(verticalHitBox, (int) chestTest.getHitBox().getValue1());
    }
    
    @Test
    public void testObjectUseOpenClose() throws Exception {
        cmd = Cmd.USE;
        chest.objectUse(player, cmd);
        assertEquals(State.OPEN, chest.getState());
        
        chest.objectUse(player, cmd);
        assertEquals(State.CLOSE, chest.getState());
    }

    @Test
    public void testFillChestItems() {
        Item[] chestContents = Chest.fillChestItemTest();
        
        assertEquals(2, chestContents.length);
        assertTrue(chestContents[0] instanceof Weapon);
        assertTrue(chestContents[1] instanceof Consumable);
    }
}