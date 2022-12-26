package prefab.gui;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import manager.FightManager;
import prefab.rendering.Visual;

public abstract class Hud {

    static List<Hud> huds = new ArrayList<Hud>();

    boolean isDisplayed = false;

    public Hud() {
        huds.add(this);
    }

    public abstract void initHud() throws Exception;

    public static List<Hud> getHuds() {
        return huds;
    }

    public boolean hudIsDisplayed() {
        return isDisplayed && !FightManager.getInstance().getIsInFight();
    }

    public void changeDisplayState() {
        isDisplayed = !isDisplayed;
    }

    public abstract void draw(Graphics2D g);

    public List<Visual> getVisuals() throws Exception {
        return new ArrayList<Visual>();
    }

    public List<Visual> getFrontVisuals() throws Exception {
        return new ArrayList<Visual>();
    }

    
}
