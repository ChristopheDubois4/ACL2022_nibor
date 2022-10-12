package game;

import java.util.HashMap;
import java.awt.image.BufferedImage;


public abstract class GameObject {
    
    Position position;
    HashMap<State,BufferedImage> graphics;
    String objectName;
    int verticalHitBox, horizontalHitBox;


    /**
     * constructeur de la classe GameObject
     * @param position position de l'objet
     * @param graphics composantes graphiques position de l'objet
     * @param objectName nom position de l'objet
     * @param verticalHitBox largeur de la hitbox position de l'objet
     * @param horizontalHitBox hauteur de la hitbox position de l'objet
     */
    public GameObject(Position position, HashMap<State,BufferedImage> graphics, String objectName, int verticalHitBox, int horizontalHitBox) {
        this.position = position;
        this.graphics = graphics;
        this.objectName = objectName;
        this.verticalHitBox = verticalHitBox;
        this.horizontalHitBox = horizontalHitBox;
    }

    public abstract void draw();

    public abstract void move();
}
