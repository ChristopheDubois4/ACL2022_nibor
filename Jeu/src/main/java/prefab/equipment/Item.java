package prefab.equipment;
import java.awt.image.BufferedImage;
import java.util.HashMap;


/**
 * Descriptions d un item
 */

public class  Item {
    protected int price;
    protected HashMap<String,BufferedImage> graphics;
    private String name;


    /**
     * constructeur de la classe Item
     * @param price position de l'objet
     * @param graphics composantes graphiques de l'objet
     * @param name nom de l'objet
     */
    public Item(int price, HashMap<String,BufferedImage> graphics, String name) {
        this.price=price;
        this.graphics=graphics;
        this.setName(name);
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }
    
}
