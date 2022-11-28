package manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import prefab.entity.GameObject;
import prefab.equipment.Consumable;
import prefab.equipment.Effect;
import prefab.equipment.Item;
import prefab.equipment.Effect.TypeEffects;
import prefab.gui.InventoryHud;
import prefab.information.Layer;
import prefab.information.Position;
import prefab.information.State;
import prefab.level.GameLevel;
import prefab.props.Chest;
import prefab.props.Ladder;
import prefab.props.Trap;

import java.awt.image.BufferedImage;

/**
 * créer les différents niveux du jeux
 */
public class LevelCreator {

    public HashMap<String, GameLevel> gameLevels;
    String defaultfile = "src/main/ressources/levels/default.json";
    private InventoryHud inventoryHud;

    
    /**
     * constructeur de la classe LevelManager
     */
    public LevelCreator(InventoryHud inventoryHud) {
        this.inventoryHud=inventoryHud;
        gameLevels = new HashMap<String, GameLevel>();
        // Quand les tests seront fini dé-commenter : 
        // initGameLevels(); 
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
        createLevelsFromJSON(defaultfile);
    }  
    
    /**
     * ( W I P )
     * créer un niveau à partir d'un fichier JSON
     * @param file chemin du fichier JSON
     */
    private void createLevelsFromJSON(String file) {
        // récupération du fichier JSON a partir d'un chemin
        File directory = new File(file);
        JSONParser jsonParser = new JSONParser();
        System.out.println("AVANT TRY");
        // lecture du fichier JSON
        try (FileReader reader = new FileReader(directory))
        {
            System.out.println("DANS LE TRY");
            
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            // tableau de niveau sous format JSON
            JSONArray levels = (JSONArray) obj;
            System.out.println(levels);
            // parcours des niveaux
            for (int i = 0; i < levels.size() ; i++) {

                JSONObject level = (JSONObject) levels.get(i);
                String levelName = (String) level.get("name");

                JSONArray levelObjects = (JSONArray) level.get("gameObjects");

                List<GameObject> gameObjects = new ArrayList<GameObject>();
                // parcours des objets du niveau
                for (int k = 0; k < levelObjects.size() ; k++) {
                    // Kième objet du ième niveau 
                    JSONObject gameObject = (JSONObject) levelObjects.get(k);
                    // nom de l'objet
                    String objectName = (String) gameObject.get("objectName");
                    // position
                    JSONObject position = (JSONObject) gameObject.get("position");
                    int x = (int) ((long) position.get("x"));
                    int y = (int) ((long) position.get("y"));
                    Layer layer =  Layer.valueOf((String) position.get("layer"));
                    Position p = new Position(x, y, layer);
                    // graphics
                    HashMap<State,BufferedImage> graphics = Utilities.getGraphicsFromJSON((String) gameObject.get("graphics"));
                    // hitbox
                    int horizontalHitBox = (int) ((long) gameObject.get("horizontalHitBox"));
                    int verticalHitBox = (int) ((long) gameObject.get("verticalHitBox"));
                    // type
                    String type = (String) gameObject.get("type");
                    // informations qui dépendent du type de l'objet
                    JSONObject typeInfos = (JSONObject) gameObject.get("typeInfos");
                    // traitement différent selon le type de l'objet
                    switch (type) {
                        case "GameObject" :
                            gameObjects.add(new GameObject(p, graphics, objectName, horizontalHitBox, verticalHitBox));
                            break;
                        case "Ghost" :
                            createGhost(gameObjects, p, graphics, objectName, horizontalHitBox, verticalHitBox, typeInfos);
                        break;                    
                        default:
                            break;
                    }
                }
                GameLevel gameLevel = new GameLevel(gameObjects);
                this.gameLevels.put(levelName, gameLevel);
            }           
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }

        // TESTS
        /*
        System.out.println("DANS LE WHILE");
        while (true);
        */
    }

    /**
     * ( W I P )
     * (les noms des mobs ne sont pas encore définis, "Ghost" est prit a titre d'exemple )
     * créer un object de type Ghost
     * @param gameObjects liste des gameObjects di niveau
     * @param p position
     * @param graphics composantes graphiques
     * @param objectName nom de l'objet
     * @param horizontalHitBox hitbox horizontale
     * @param verticalHitBox hitbox verticale
     * @param typeInfos JSONObject contenent les informations spécifique d'un objet de type Ghost
     */
    private void createGhost(List<GameObject> gameObjects, Position p, HashMap<State, BufferedImage> graphics,
            String objectName, int horizontalHitBox, int verticalHitBox, JSONObject typeInfos) {
        // traitement de typeInfos
        // constructeur Ghost
        // gameobjects.add(ghost)                
    }

     /**
     * methode temporaire
     * 
     * niveau pour tester le déplacement d'un objet
     */
    private void testMovement() {     

        HashMap<State,BufferedImage> graphicsBOX = Utilities.getGraphicsFromJSON("box");
        HashMap<State,BufferedImage> graphicsDOOR = Utilities.getGraphicsFromJSON("door");        
        HashMap<State,BufferedImage> graphicsLADDER = Utilities.getGraphicsFromJSON("ladder");
        HashMap<State,BufferedImage> graphicsTRAP = Utilities.getGraphicsFromJSON("trap");
        HashMap<State,BufferedImage> graphicsCHEST = Utilities.getGraphicsFromJSON("chest");
        HashMap<State,BufferedImage> graphicsITEM = Utilities.getGraphicsFromJSON("sword_1");
        HashMap<State,BufferedImage> graphicsbitcoin = Utilities.getGraphicsFromJSON("bitcoin");


        GameLevel level1 = new GameLevel();
        Position p1 = new Position(20, 5);
        Position p2 = new Position(5, 5);
        Position p3 = new Position(8, 5);
        Position p4 = new Position(26, 14);
        Position p5 = new Position(0, 0);

        Item[] chestContents = new Item[]{new Consumable("epee sdaacre","sword_1", new Effect(TypeEffects.HIT, 20)),new Consumable("epee sdaacre","bitcoin", new Effect(TypeEffects.HIT, 20))};

        System.out.println("BOX");

        GameObject o1 = new GameObject(p1, graphicsBOX, "BOX", 1, 1);
        System.out.println("VISUAL");

        GameObject o2 = new GameObject(p2, graphicsDOOR, "DOOR", 1, 1);
        System.out.println("DOOR");

        GameObject o3 = new Ladder(p3, graphicsLADDER, 3);
        System.out.println("TRAP");

        GameObject o4 = new Trap(p4, graphicsTRAP, 1,1,30);
        System.out.println("CHEST");

        GameObject o5 = new Chest(p5, graphicsCHEST, 1, 1, chestContents, inventoryHud);
        System.out.println("APRES CHEST");

        System.out.println("\n2 Obstacles de 1 case : ACIDE en (5,5) et en Trap en (20,8)\nLadder utilisable en (8,5)/(8,6)/(8,7)\nChest en (10,5)\n");

        level1.addGameObjects(new ArrayList<GameObject>(Arrays.asList(o1, o2, o3, o4 ,o5)));

        gameLevels.put("default",level1);
    }

}
