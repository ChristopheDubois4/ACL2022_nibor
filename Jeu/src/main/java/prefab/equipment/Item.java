package prefab.equipment;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import manager.Utilities;
import prefab.information.State;


/**
 * Descriptions d un item
 */

public class  Item {
    protected int price;
    protected HashMap<State,BufferedImage> graphics;
    private String name;

    
    /**
     * constructeur de la classe Item
     * @param price prix de l'objet
     * @param graphics composantes graphiques de l'objet
     * @param name nom de l'objet
     */
    public Item(int price, HashMap<State,BufferedImage> graphics, String name) {
        this.price=price;
        this.graphics=graphics;
        this.setName(name);
    }
    
    public Item(String name, String graphicsSelector) {
    	graphics = Utilities.getGraphicsFromJSON(graphicsSelector);
    }
    
    public BufferedImage getImage(State state) {
    	return graphics.get(state);
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }
    
}
