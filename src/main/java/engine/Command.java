package engine;

import org.javatuples.Pair;

/**
 * WORK IN PROGRESS
 * 
 * sert a stocker les commandes de l'utilisateur
 */
public class Command {
    
    private Cmd keyCmd;

    private String mouseActionType;
    int clickX;
    int clickY;
    
    public Command() {
        keyCmd = Cmd.IDLE;
    }

    public Cmd getKeyCommand() {
        return keyCmd;
    }

    public void setKeyCommand(Cmd c) {
        keyCmd = c;
    }

    public void setKeyCommand(Cmd c, String mouseActionType) {
        keyCmd = c;
    }

    public void setClick(int x, int y){
        clickX = x;
        clickY = y;
    }

    public Pair<Integer, Integer> getClick() {
        return new Pair<Integer, Integer>(clickX, clickY);
    }

    public Pair<Integer, Integer> getNormalizedClick() {
        return new Pair<Integer, Integer>(clickX/60, 14-clickY/60);
    }

    public String getMouseActionType() {
        return mouseActionType;
    }
}
