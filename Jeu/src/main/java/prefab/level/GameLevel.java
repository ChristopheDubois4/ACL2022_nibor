package prefab.level;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.javatuples.Pair;

import prefab.entity.GameObject;
import prefab.rendering.Animation;
import prefab.rendering.Visual;
import prefab.entity.Character;

/**
 * représente un niveau 
 * 
 * stock toutes les informations liées au niveau
 */
public class GameLevel {
    
    private List<GameObject> gameObjects;
    private List<Animation> backgroundAnimations;
    private int[][] forbiddenPosition;

    /**
     * constructeur de la classe GameObject
     * @param levelInitMap
     * @param gameObjects2
     */
    public GameLevel(List<GameObject> gameObjects, int[][] levelInitMap) {
        this.gameObjects = gameObjects;
        this.forbiddenPosition = levelInitMap;
    }

    /**
     * constructeur surchargé de la classe GameObject
     * @param gameObjects liste de gameObject
     */
    public GameLevel(List<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }

    /**
     * ajoute un gameObject au niveau
     * @param gameObject le gameObject à ajouter
     */
    public void addGameObject(GameObject gameObject) {
        this.gameObjects.add(gameObject);
    }

    /**
     * ajoute plusieurs gameObject au niveau
     * @param gameObjects la liste à ajouter
     */
    public void addGameObjects(List<GameObject> gameObjects) {
        this.gameObjects.addAll(gameObjects);
    }

    /**
     * enlève un gameObject au niveau
     * @param gameObject le gameObject à enlever
     */
    public void removeGameObject(GameObject gameObject) {
        this.gameObjects.remove(gameObject);
    }
    
    /**
     * enlève plusieurs gameObject au niveau
     * @param gameObjects la liste à enlever
     */
    public void removeGameObjects(List<GameObject> gameObjects) {
        this.gameObjects.removeAll(gameObjects);
    }

    public void unload() {
        for (GameObject gameObject : gameObjects) {
            gameObject.stopAnimation();
        }
    }

    public void load() {
        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof Character) {
                ((Character) gameObject).startAnimation();
            }            
        }
    }



    /**
     * test si le déplacement d'un objet est possible dans le niveau en
     * regardant toutes les coordonées déjà occupées par les autres objets
     * 
     * @param character le GameObject que l'ont souhaite tester
     * @return un tuple qui contient :
     *  - un booléen qui vaut :
     *      -> true si le personngae peut se déplacer
     *      -> false sinon
     *  - un GameObject : l'objet qui bloc le passage (null sinon)
     * @throws CloneNotSupportedException
     */
    public Pair<Boolean, GameObject> checkMove(Character character, int deltaX, int deltaY) throws CloneNotSupportedException {
        // On test si le personnage se retrouve en dehors de la map
        if (!character.getPosition().authorizedPosition(deltaX, deltaY)) {
            return new Pair<Boolean, GameObject>(false, null);
        }
        if (forbiddenPosition[character.getPosition().getY()+deltaY][character.getPosition().getX()+deltaX] == -1){
            return new Pair<Boolean, GameObject>(false, null);
        }
        // Récupérations de toutes le coordonées de l'objet à tester
        List<Pair<Integer, Integer>> coordinatesToTests = character.getOccupiedCoordinates(deltaX, deltaY);
        // Parcours de la liste des objets du niveau
        for (GameObject gameObject : this.gameObjects) {
            // Récupérations de toutes le coordonées de l'objet dans la liste
            List<Pair<Integer, Integer>> coordinates = gameObject.getOccupiedCoordinates(0, 0);
            // Si les deux listes de coordonées ont au moins un élément en commun
            if ( !Collections.disjoint(coordinates, coordinatesToTests) ) {
                return new Pair<Boolean, GameObject>(false, gameObject);
            }
        }
        return new Pair<Boolean, GameObject>(true, null);
    }

    public List<Visual> getVisuals() throws Exception {

        List<Visual> visuals = new ArrayList<Visual>();
        for (GameObject gameObject : gameObjects) {
            Animation a = gameObject.getAnimation();
            if (a != null) {
                visuals.add(a.getVisual());
            }
        }
        return visuals;    
    }
  
    @Override
    public String toString() {
        return Arrays.toString(this.gameObjects.toArray());
    }

}
