package manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.javatuples.Pair;

import prefab.entity.GameObject;
import prefab.information.Layer;
import prefab.information.Position;
import prefab.level.GameLevel;

/**
 * gère et crée les différents niveux du jeux
 */
public class LevelManager {

    public HashMap<String, GameLevel> gameLevels;
    public String currentLevel;
    
    /**
     * constructeur de la classe LevelManager
     */
    public LevelManager() {
        gameLevels = new HashMap<String, GameLevel>();
        initGameLevels();
        testSrpint1();
    }

    
    /**
     * methode temporaire
     * 
     * Méthode pour tester des fonctionnalitées liées au sprint 1
     */
    private void testSrpint1() {       

        //testTriage();
        testMovement();  
    }

    /**
     * créer les niveaux du jeux
     */
    private void initGameLevels() {

    }

    /**
     * test si le déplacement d'un object est valide 
     * 
     * @param objectToTest le GameObject que l'ont souhaite tester
     * @return un tuple qui contient :
     *  - un booléen qui vaut :
     *      -> true si l'objet à tester peut se déplacer
     *      -> false sinon
     *  - un GameObject : l'object qui bloc le passage (null sinon)
     */
    public Pair<Boolean, GameObject> checkMove(GameObject objectToTest) {
        return gameLevels.get(currentLevel).checkMove(objectToTest);
    }


    /**
     * methode temporaire
     * 
     * niveau pour tester le triage d'un liste de gameObject
     */
    private void testTriage() {     

        GameLevel level1 = new GameLevel();

        Position p1 = new Position(10, 10, Layer.BACKGROUND);
        Position p2 = new Position(0, 0, Layer.DEFAULT);
        Position p3 = new Position(14, 8, Layer.DEFAULT);
        Position p4 = new Position(14, 15, Layer.DEFAULT);
        Position p5 = new Position(14, 1, Layer.FOREGROUND);

        GameObject o1 = new GameObject(p1, null, "o1", 2, 2);
        GameObject o2 = new GameObject(p2, null, "o2", 1, 1);
        GameObject o3 = new GameObject(p3, null, "o3", 2, 6);
        GameObject o4 = new GameObject(p4, null, "o4", 2, 6);
        GameObject o5 = new GameObject(p5, null, "o5", 2, 6);

        level1.addGameObjects(new ArrayList<GameObject>(Arrays.asList(o4, o3, o5, o2, o1)));

        gameLevels.put("test",level1);
        System.out.println(gameLevels.get("test"));
        gameLevels.get("test").draw();
        System.out.println(gameLevels.get("test"));
    }

     /**
     * methode temporaire
     * 
     * niveau pour tester le déplacement d'un objet
     */
    private void testMovement() {     

        GameLevel level1 = new GameLevel();
        Position p1 = new Position(20, 8);
        Position p2 = new Position(5, 5);
        GameObject o1 = new GameObject(p1, null, "POMME", 1, 1);
        GameObject o2 = new GameObject(p2, null, "ACIDE", 1, 1);
        System.out.println("\n2 Obstacles de 1 case : ACIDE en (5,5) et en POMME en (20,8) \n");

        level1.addGameObjects(new ArrayList<GameObject>(Arrays.asList(o1, o2)));

        currentLevel = "test";
        gameLevels.put("test",level1);
    }

}
