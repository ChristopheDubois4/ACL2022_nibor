package prefab.level;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.javatuples.Pair;

import prefab.entity.GameObject;
import prefab.information.Visual;

/**
 * représente un niveau 
 * 
 * stock toutes les informations liées au niveau
 */
public class GameLevel {
    
    private List<GameObject> gameObjects;

    /**
     * constructeur de la classe GameObject
     */
    public GameLevel() {
        this.gameObjects = new ArrayList<GameObject>();
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

    /**
     * test si le déplacement d'un objet est possible dans le niveau en
     * regardant toutes les coordonées déjà occupées par les autres objets
     * 
     * @param objectToTest le GameObject que l'ont souhaite tester
     * @return un tuple qui contient :
     *  - un booléen qui vaut :
     *      -> true si l'objet à tester peut se déplacer
     *      -> false sinon
     *  - un GameObject : l'object qui bloc le passage (null sinon)
     */
    public Pair<Boolean, GameObject> checkMove(GameObject objectToTest) {
        // Récupérations de toutes le coordonées de l'objet à tester
        List<Pair<Integer, Integer>> coordinatesToTests = objectToTest.getOccupiedCoordinates();
        // Parcours de la liste des objets du niveau
        for (GameObject gameObject : this.gameObjects) {
            // Récupérations de toutes le coordonées de l'objet dans la liste
            List<Pair<Integer, Integer>> coordinates = gameObject.getOccupiedCoordinates();
            // Si les deux listes de coordonées ont au moins un élément en commun
            if ( !Collections.disjoint(coordinates, coordinatesToTests) ) {
                return new Pair<Boolean, GameObject>(false, gameObject);
            }
        }
        return new Pair<Boolean, GameObject>(true, null);
    }

    /**
     * dessine les gameObjects du niveau
     */
    public List<Visual> getVisuals() {
        sortGameObjects();

        List<Visual> visuals = new ArrayList<Visual>();

        for (GameObject gameObject : gameObjects) {
            visuals.add(gameObject.getVisual());
        }

        return visuals;
    }

    /**
     * trie les gameObject selon leurs ordre d'affichage
     */
    private void sortGameObjects() {
        Collections.sort(this.gameObjects);
    }

    @Override
    public String toString() {
        return Arrays.toString(this.gameObjects.toArray());
    }

}
