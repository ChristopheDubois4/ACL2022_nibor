package fight;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import prefab.gui.FightHud;

public class testFightHud {

    static FightHud f;
    
    @BeforeClass
    public static void setUp(){
        f = FightHud.getInstance();
        try {
            f.initHud();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetInstance() {
        assertEquals(f.INSTANCE, FightHud.getInstance());
    }

    @Test
    public void testVisuals() {
        assertFalse("visuels null", f.getFrontVisuals() == null);
    }

    @Test
    public void testHudIsDisplayed() {
        assertFalse(" PB : l'hud est affiché", f.hudIsDisplayed());
        f.changeDisplayState(0);
        assertTrue(" PB : l'hud n'est pas affiché", f.hudIsDisplayed());
    }
}