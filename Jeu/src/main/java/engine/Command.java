package engine;

import org.javatuples.Pair;

/**
 * WORK IN PROGRESS
 *
 * sert a stocker les commandes de l'utilisateur
 */
public class Command implements Cloneable{

    private Cmd keyCmd;
    private String actionType;
    int clickX;
    int clickY;

    /**
     * constructeur de la classe command
     */
    public Command() {
        keyCmd = Cmd.IDLE;
    }

    public Cmd getKeyCommand() {
        return keyCmd;
    }

    /**
     * met a jour le type de command
     */
    public void setKeyCommand(Cmd c) {
        keyCmd = c;
        this.actionType = "pressed";
    }

    /**
     * met a jour le type de command
     */
    public void setKeyCommand(Cmd c, String actionType) {
        keyCmd = c;
        this.actionType = actionType;
    }

    /**
     * met à jour les coordonnées du clic de la souris
     * @param x abscisse
     * @param y ordonnée
     */
    public void setClick(int x, int y){
        clickX = x;
        clickY = y;
    }

    /**
     * renvoie les coordonées du clic de la souris
     * @return un tuple (x, y)
     */
    public Pair<Integer, Integer> getClick() {
        return new Pair<Integer, Integer>(clickX, clickY);
    }

    /**
     * renvoie les coordonées sur la grille du clic de la souris
     * @return un tuple (x, y) pouvant aller de (0, 0) à (26, 14)
     */
    public Pair<Integer, Integer> getNormalizedClick() {
        return new Pair<Integer, Integer>(clickX/60, 14-clickY/60);
    }

    /**
     * renvoie le type d'action de la commande
     * @return "pressed" ou "released"
     */
    public String getActionType() {
        return actionType;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
