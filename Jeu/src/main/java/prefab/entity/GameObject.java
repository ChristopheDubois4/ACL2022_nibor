package prefab.entity;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import prefab.information.Position;
import prefab.information.State;
import prefab.rendering.Animation;


/**
 * représente tous les objets physiques présents dans les niveaux.
 * 
 * cela comprend les peronngaes et les objets tel que par exemple 
 * les portes, les obstacles ou les échelles
 */

 public class GameObject {
    
    Position position;
    State state;
    Animation animation;
    Pair<Integer, Integer> HitBox;
   
    /**
     * constructeur de la classe GameObject
     * @param position position de l'objet
     * @param animation animation de l'objet
     * @param horizontalHitBox largeur de la hitbox de l'objet
     * @param verticalHitBox hauteur de la hitbox de l'objet
     * @param state l'état de l'objet
     * @throws CloneNotSupportedException
     */
    public GameObject(Position position, Animation animation, int horizontalHitBox, int verticalHitBox, State state) throws CloneNotSupportedException {
        this.position = position;
        this.animation = animation;
        this.HitBox = new Pair<Integer, Integer>(horizontalHitBox, verticalHitBox);
        this.state = state;
        animation.setPosition(position);
        animation.setState(state);
    }

    /**
     * creér un GameObject
     * @param position position de l'objet
     * @param animation animation de l'objet
     * @param horizontalHitBox largeur de la hitbox de l'objet
     * @param verticalHitBox hauteur de la hitbox de l'objet
     * @param state l'état de l'objet
     * @return le GameObject créé
     * @throws Exception
     */
    public static GameObject create(Position position, Animation animation, int horizontalHitBox, int verticalHitBox, State state) throws Exception {
        // on empêche de construire des objets imcomplets
        if (position == null) {
            throw new NullPointerException("la position est nulle");
        }
        if (animation == null) {
            throw new NullPointerException("l'animation est nulle");
        }
        if (state == null) {
            throw new NullPointerException("le state est nul");
        }
        return new GameObject(position, animation, horizontalHitBox, verticalHitBox, state);
    }

     /**
     * creér un GameObject avec <code>state = DEFAULT</code>
     * @param position position de l'objet
     * @param animation animation de l'objet
     * @param horizontalHitBox largeur de la hitbox de l'objet
     * @param verticalHitBox hauteur de la hitbox de l'objet
     * @return le GameObject créé
     * @throws Exception
     */
    public static GameObject createWithDefaultState(Position position, Animation animation, int horizontalHitBox, int verticalHitBox) throws Exception {
        return GameObject.create(position, animation, horizontalHitBox, verticalHitBox, State.DEFAULT);
    }

    /**
     * @return une copie de la position de l'objet
     * @throws CloneNotSupportedException
     */
    public Position getPosition() throws CloneNotSupportedException {
        return (Position) position.clone();
    }

    public void setPosition(Position position) throws CloneNotSupportedException {
        animation.setPosition(position);
        this.position = (Position) position.clone();
    }

    /**
     * met à jour l'état de l'objet
     * @param newState le nouvel état
     */
    public void setState(State newState){
        state = newState;
        animation.setState(newState);
    }

    /**
     * @return l'état actuel de l'objet
     */
    public State getState(){
        return state;
    }

    /**
     * @return l'état actuel de l'objet
     */
    public Pair<Integer, Integer> getHitBox(){
        return new Pair<Integer,Integer>(HitBox.getValue0(), HitBox.getValue1());
    }


    /**
     * recupère toutes les coordonées que l'objet occupe en prenant
     * compte des hitBox des objets
     * <p>Ex : si x = 0, y = 0, HitBox = (1, 2) => Liste = [(0,0), (0,1)]
     * @return liste des coordonées
     */
    public List<Pair<Integer, Integer>> getOccupiedCoordinates(int deltaX, int deltaY) {
        // Initialisation de la liste que l'on renverra
        List<Pair<Integer, Integer>> occupiedCoordinates = new ArrayList<Pair<Integer, Integer>>();
        // Parcours des coordonées qu'occupe la hitbox à l'horizontale
        for (int i = 0; i < this.HitBox.getValue0(); i++) {
            int x = this.position.getX() + deltaX + i;  
            // Parcours des coordonnées qu'occupe la hitbox à la verticale
            for (int j = 0; j < this.HitBox.getValue1(); j++) {
                int y = this.position.getY() + deltaY + j;     
                occupiedCoordinates.add(new Pair<Integer, Integer>(x, y) );               
            }
        }
        return occupiedCoordinates;
    }

    /**
     * récupère une copie de l'animation de l'objet
     * @return
     *      <code>Animation</code> si l'animation n'est pas déjà en train
     *      d'être jouée dans l'Animator
     *      <li><code>null</code> sinon
     * @throws CloneNotSupportedException
     */
    public Animation getAnimation() throws CloneNotSupportedException {      
        // si l'animation n'est pas en train d'être jouée dans l'Animator
        if (!animation.getIsPlaying()) {
            return (Animation) animation.clone();
        }  
        return null;
    }
    
    public void stopAnimation() {
        animation.stopAnimation();
    }

    @Override
    public String toString() {
        return  this.getClass().toString();
    }
        
}