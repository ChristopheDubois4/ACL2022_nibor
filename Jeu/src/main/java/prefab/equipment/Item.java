package prefab.equipment;
import java.awt.image.BufferedImage;
import manager.Utilities;


/**
 * Descriptions d un item
 */

public abstract class Item {
    protected int price = 0;
    protected BufferedImage graphics;
    private String name;

    private String path = "src/main/ressources/images/items/";

    
    /**
     * constructeur de la classe Item
     * @param price prix de l'objet
     * @param graphics composantes graphiques de l'objet
     * @param name nom de l'objet
     */
    
    public Item(String name, String graphicsSelector, int price) {
        this.price=price;
        graphics = Utilities.getImage(path+graphicsSelector+".png");
        System.out.println((graphics == null));
        this.setName(name);
    } 
    
    public Item(String name, String graphicsSelector) {
    	this(name, graphicsSelector, 0);
    }
    
    public BufferedImage getImage() {
    	return graphics;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
}
