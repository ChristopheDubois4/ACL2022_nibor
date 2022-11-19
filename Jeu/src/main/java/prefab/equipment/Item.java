package prefab.equipment;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import manager.JsonUtilities;
import prefab.information.Image;
import prefab.information.State;


/**
 * Descriptions d un item
 */

public class  Item {
    protected int price;
    protected HashMap<State,Image> graphics;
    private String name;

    
    /**
     * constructeur de la classe Item
     * @param price position de l'objet
     * @param graphics composantes graphiques de l'objet
     * @param name nom de l'objet
     */
    public Item(int price, HashMap<State,Image> graphics, String name) {
        this.price=price;
        this.graphics=graphics;
        this.setName(name);
    }
    
    public Item(String name, String graphicsSelector) {

    	graphics = JsonUtilities.getGraphicsFromJSON(graphicsSelector);
    	
    }
    
    public Image getImage(State state) {
    	return graphics.get(state);
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }
    
}
