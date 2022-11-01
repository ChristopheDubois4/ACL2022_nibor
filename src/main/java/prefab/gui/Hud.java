package prefab.gui;

public class Hud {

    private boolean isDisplayed;

    public Hud() {

    }

    public boolean hudIsDisplayed() {
        return isDisplayed;
    }

    public void changeDisplayState() {
        isDisplayed = !isDisplayed;
    }
}
