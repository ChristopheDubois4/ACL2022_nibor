package prefab.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.javatuples.Pair;

import prefab.information.Image;
import prefab.information.Position;
import prefab.information.State;
import prefab.information.Visual;

/**
 * représente tous les objets physiques présents dans les niveaux.
 * 
 * cela comprend les peronngaes et les objets tel que par exemple 
 * les portes, les obstacles ou les échelles
 */

public class GameObject implements Comparable<GameObject> {
    
    protected Position position;
    protected HashMap<State,Image> graphics;
    protected String objectName;
    protected Pair<Integer, Integer> HitBox;
    protected State state;


    /**
     * constructeur de la classe GameObject
     * @param position position de l'objet
     * @param graphics composantes graphiques de l'objet
     * @param objectName nom position de l'objet
     * @param HitBox (largeur, hauteur) de la hitbox de l'objet
     */
    public GameObject(Position position, HashMap<State,Image> graphics, String objectName, int horizontalHitBox, int verticalHitBox) {
        this.position = position;
        this.graphics = graphics;
        this.objectName = objectName;
        this.HitBox = new Pair<Integer, Integer>(horizontalHitBox, verticalHitBox);
        this.state = State.DEFAULT;
    }

    /**
     * constructeur surchargé de la classe GameObject
     * @param state l'état de l'objet
     */
    public GameObject(Position position, HashMap<State,Image> graphics, String objectName, int horizontalHitBox, int verticalHitBox, State state) {
        this.position = position;
        this.graphics = graphics;
        this.objectName = objectName;
        this.HitBox = new Pair<Integer, Integer>(horizontalHitBox, verticalHitBox);
        this.state = state;
    }

    public void setPosition(Position position) {
        this.position=position;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setState(State state){
        this.state=state;
    }

    public State getState(){
        return this.state;
    }

    /**
     * recupère toutes les coordonées que l'objet occupe en prenant
     * compte des hitBox des objets
     * Ex : si x = 0, y = 0, HitBox = (1, 2) => Liste = [(0,0), (0,1)]
     * @return liste des coordonées
     */
    public List<Pair<Integer, Integer>> getOccupiedCoordinates() {
        // Initialisation de la liste que l'on renverra
        List<Pair<Integer, Integer>> occupiedCoordinates = new ArrayList<Pair<Integer, Integer>>();
        // Parcours des coordonées qu'occupe la hitbox à l'horizontale
        for (int i = 0; i < this.HitBox.getValue0(); i++) {
            int x = this.position.getX() + i;  
            // Parcours des coordonées qu'occupe la hitbox à la verticale
            for (int j = 0; j < this.HitBox.getValue1(); j++) {
                int y = this.position.getY() + j;     
                occupiedCoordinates.add(new Pair<Integer, Integer>(x, y) );               
            }
        }
        return occupiedCoordinates;
    }


    /**
    * permet de donner un usage à un objet
    * @return un boolean qui vaut :
    *   -> false l'objet n'est pas utilisable
    *   -> true sinon
     */
    public boolean objectUse(Player user) {
        return false;
    }


     /**
     * affiche l'object
     */
    public Visual getVisual() {        
        Image image = graphics.get(state);
        int y = position.getY() + image.getLenghtY() -1;
        int x = position.getX();
        return new Visual(x, y, image.getBufferedImage());
    }
    
    /**
     * déplace l'object
     */
    public boolean move(int x, int y) {
        return this.position.addToXY(x, y);
    }

    /**
     * compare 2 gameObject
     * @param o le gmaeObject à comparer
     * @return le resultat de la comparaison
     */
    @Override
    public int compareTo(GameObject o) {
        return this.position.compareTo(o.getPosition());        
    }
    
    @Override
    public String toString() {
        return objectName;
    }
}
