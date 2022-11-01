package manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import prefab.entity.GameObject;
import prefab.information.Layer;
import prefab.information.Position;
import prefab.level.GameLevel;
import prefab.props.Ladder;

/**
 * créer les différents niveux du jeux
 */
public class LevelCreator {

    public HashMap<String, GameLevel> gameLevels;
    
    /**
     * constructeur de la classe LevelManager
     */
    public LevelCreator() {
        gameLevels = new HashMap<String, GameLevel>();
        initGameLevels();
        testSrpint1();
    }

    public HashMap<String, GameLevel> getLevels() {
        return gameLevels;
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
        Position p3 = new Position(8, 5);
        GameObject o1 = new GameObject(p1, null, "POMME", 1, 1);
        GameObject o2 = new GameObject(p2, null, "ACIDE", 1, 1);
        GameObject o3 = new Ladder(p3, null, 3);
        System.out.println("\n2 Obstacles de 1 case : ACIDE en (5,5) et en POMME en (20,8)\nLadder utilisable en (8,5)/(8,6)/(8,7) \n");

        level1.addGameObjects(new ArrayList<GameObject>(Arrays.asList(o1, o2, o3)));

        gameLevels.put("default",level1);
    }

}
