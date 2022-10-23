package prefab.entity;

import java.util.HashMap;

import prefab.information.Position;
import prefab.information.State;

import java.awt.image.BufferedImage;

/**
 * représente tous les objets physiques présents dans les niveaux.
 * 
 * cela comprend les peronngaes et les objets tel que par exemple 
 * les portes, les obstacles ou les échelles
 */

public class GameObject implements Comparable<GameObject> {
    
    protected Position position;
    protected HashMap<State,BufferedImage> graphics;
    protected String objectName;
    protected int verticalHitBox, horizontalHitBox;


    /**
     * constructeur de la classe GameObject
     * @param position position de l'objet
     * @param graphics composantes graphiques de l'objet
     * @param objectName nom position de l'objet
     * @param verticalHitBox largeur de la hitbox de l'objet
     * @param horizontalHitBox hauteur de la hitbox de l'objet
     */
    public GameObject(Position position, HashMap<State,BufferedImage> graphics, String objectName, int verticalHitBox, int horizontalHitBox) {
        this.position = position;
        this.graphics = graphics;
        this.objectName = objectName;
        this.verticalHitBox = verticalHitBox;
        this.horizontalHitBox = horizontalHitBox;
    }

    public Position getPositon() {
        return this.position;
    }

     /**
     * affiche l'object
     */
    public void draw() {

    }
    
    /**
     * déplace l'object
     */
    public void move() {

    }

    /**
     * compare 2 gameObject
     * @param o
     * @return le resultat de la comparaison
     */
    @Override
    public int compareTo(GameObject o) {
        return this.position.compareTo(o.getPositon());        
    }

    
    @Override
    public String toString() {
        return objectName;
    }
}
