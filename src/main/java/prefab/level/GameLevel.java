package prefab.level;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import prefab.entity.GameObject;

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
     * dessine les gameObjects du niveau
     */
    public void draw() {
        sortGameObjects();
        // A completer
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
