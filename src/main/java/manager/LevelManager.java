package manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import prefab.entity.GameObject;
import prefab.information.Layer;
import prefab.information.Position;
import prefab.information.State;
import prefab.level.GameLevel;

/**
 * gère et crée les différents niveux du jeux
 */
public class LevelManager {

    HashMap<String, GameLevel> gameLevels;
    
    /**
     * constructeur de la classe LevelManager
     */
    public LevelManager() {
        gameLevels = new HashMap<String, GameLevel>();
        initGameLevels();
        testSrpint1();
    }

    /**
     * créer les niveaux du jeux
     */
    private void initGameLevels() {

        // EXEMPLE DE TEST

        GameLevel level1 = new GameLevel();

        Position p1 = new Position(10, 50, State.DEFAULT, Layer.BACKGROUND);
        Position p2 = new Position(14, 5, State.DEFAULT, Layer.DEFAULT);
        Position p3 = new Position(14, 8, State.DEFAULT, Layer.DEFAULT);
        Position p4 = new Position(14, 15, State.DEFAULT, Layer.DEFAULT);
        Position p5 = new Position(14, 1, State.DEFAULT, Layer.FOREGROUND);

        GameObject o1 = new GameObject(p1, null, "o1", 2, 4);
        GameObject o2 = new GameObject(p2, null, "o2", 2, 6);
        GameObject o3 = new GameObject(p3, null, "o3", 2, 6);
        GameObject o4 = new GameObject(p4, null, "o4", 2, 6);
        GameObject o5 = new GameObject(p5, null, "o5", 2, 6);

        level1.addGameObjects(new ArrayList<GameObject>(Arrays.asList(o5, o4, o3, o2, o1)));

        gameLevels.put("test",level1);
    }

    private void testSrpint1() {       

        System.out.println(gameLevels.get("test"));

        gameLevels.get("test").draw();

        System.out.println(gameLevels.get("test"));

        while (true);
    }
}
