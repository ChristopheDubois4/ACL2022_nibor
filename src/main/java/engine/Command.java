package engine;

import org.javatuples.Pair;

/**
 * WORK IN PROGRESS
 * 
 * sert a stocker les commandes de l'utilisateur
 */
public class Command implements Cloneable{
    
    private Cmd keyCmd;

    private String mouseActionType;
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
     * met a jour le type de command (clavier)
     */
    public void setKeyCommand(Cmd c) {
        keyCmd = c;
        this.mouseActionType = "pressed";
    }

    /**
     * met a jour le type de command (souris)
     */
    public void setKeyCommand(Cmd c, String mouseActionType) {
        keyCmd = c;
        this.mouseActionType = mouseActionType;
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

    public String getMouseActionType() {
        return mouseActionType;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
