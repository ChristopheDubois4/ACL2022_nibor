package prefab.gui;

import java.util.List;

import engine.Command;
import prefab.information.Visual;

public abstract class Hud {

    protected boolean isDisplayed = false;

    public Hud() {

    }

    public boolean hudIsDisplayed() {
        return isDisplayed;
    }

    public void changeDisplayState() {
        isDisplayed = !isDisplayed;
    }

    public abstract List<Visual> getVisual();

    public void processClick(Command command) {
    }
    
}
