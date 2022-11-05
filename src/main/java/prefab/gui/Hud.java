package prefab.gui;

public class Hud {

    private boolean isDisplayed = false;

    public Hud() {

    }

    public boolean hudIsDisplayed() {
        return isDisplayed;
    }

    public void changeDisplayState() {
        isDisplayed = !isDisplayed;
    }
}
