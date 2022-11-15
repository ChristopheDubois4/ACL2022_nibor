package prefab.gui;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.javatuples.Pair;

import engine.Cmd;
import engine.Command;
import prefab.equipment.Item;
import prefab.information.Image;
import prefab.information.Visual;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * gère l'HUD lié à l'inventaire
 */
public class InventoryHud extends Hud{

    private DisplayingPlayerInventory player;
    private Pair<Integer, Integer> pressedClick, releaseClick;
    private Image backgroundImage;

    /**
     * constructeur de la classe InventoryHud heritant de Hud 
     * @param player joueur
     */
    public InventoryHud(DisplayingPlayerInventory player, Image backgroundImage) {
        super();
        this.player = player;
        this.backgroundImage = backgroundImage;
        
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

    /**
     * (W I P)
     * (Les items n'étants pas encore implémentés, les images envoyés des items sont temporaires)
     * retourne la liste des visuels à afficher de l'inventaire
     */
    @Override
    public List<Visual> getVisual()  {
        List<Visual> visuals = new ArrayList<Visual>();
        visuals.add(new Visual(0, 14, backgroundImage.getBufferedImage()));

        /*
        Item[] inventory = player.getInventory();
        int x = 6;
        int y = 11;
        for (int i = 0; i < inventory.length; i++) {
            if (++x > 24) {
                x = 6;
                y--;
            }
            if (inventory[i] != null) {
                visuals.add(new Visual(x, y, inventory[i].getBufferedImage());
            }
        }
        */

        visuals.add(new Visual(6, 11, testSprint2().get(0)));
        visuals.add(new Visual(12, 5, testSprint2().get(1)));
        visuals.add(new Visual(16, 7, testSprint2().get(2)));

        return visuals;
    }

    /**
     * (TEMPORAIRE)
     * méthode permettant de tester l'affichage des items sur l'inventaiure le temps que 
     * la classe Item soit implémentée
     * @return
     */
    private List<BufferedImage> testSprint2() {

        List<BufferedImage> images = new ArrayList<BufferedImage>();

        String pathStr = "src/main/ressources/images/items/potion_heal.png";
        Path path = Paths.get(pathStr);
        BufferedImage im = null;
        try {
            im = ImageIO.read(new File(path.toAbsolutePath().toString()) );
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        images.add(im);

        pathStr = "src/main/ressources/images/items/sword_1.png";
        path = Paths.get(pathStr);
        try {
            im = ImageIO.read(new File(path.toAbsolutePath().toString()) );
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        images.add(im);

        pathStr = "src/main/ressources/images/items/bitcoin.png";
        path = Paths.get(pathStr);
        try {
            im = ImageIO.read(new File(path.toAbsolutePath().toString()) );
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        images.add(im);


        return images;
    }


}
