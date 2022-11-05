package prefab.gui;

import org.javatuples.Pair;

import engine.Cmd;
import engine.Command;

/**
 * gère l'HUD lié à l'inventaire
 */
public class InventoryHud extends Hud{

    private DisplayingPlayerInventory player;
    private Pair<Integer, Integer> pressedClick, releaseClick;

    /**
     * constructeur de la classe InventoryHud heritant de Hud 
     * @param player joueur
     */
    public InventoryHud(DisplayingPlayerInventory player) {
        super();
        this.player = player;
    }

    /**
     * gère les clics de souris quand l'inventaire est ouvert
     * @param command commande émise par le joueur
     */
    public void processClick(Command command) {
        System.out.println("PROCESS CLICK");
        System.out.println("TYPE : "+command.getMouseActionType());

        if (command.getKeyCommand() == Cmd.MOUSE_LEFT) {
            if (command.getMouseActionType() == "pressed") {
                pressedClick = command.getNormalizedClick();
                return;
            }
            if (command.getMouseActionType() == "release") {
                releaseClick = command.getNormalizedClick();
                swapItem();
            }
        }
    }

    /**
     * echange la position de 2 items
     */
    public void swapItem() {
        // Si possible alors 
        // cf Collections.swap(list, 1, 2);
        System.out.println("1er click : "+pressedClick);
        System.out.println("2er click : "+releaseClick);
    }


}
