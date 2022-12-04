package manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import prefab.entity.GameObject;
import prefab.entity.Mob1;
import prefab.equipment.Item;
import prefab.gui.InventoryHud;
import prefab.information.Layer;
import prefab.information.Position;
import prefab.information.State;
import prefab.level.GameLevel;
import prefab.props.*;

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
        initGameLevels(); 
    }

    public HashMap<String, GameLevel> getLevels() {
        return gameLevels;
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
                    String type = (String) gameObject.get("type");
                    // position
                    JSONObject position = (JSONObject) gameObject.get("position");
                    int x = (int) ((long) position.get("x"));
                    int y = (int) ((long) position.get("y"));
                    Layer layer =  Layer.valueOf((String) position.get("layer"));
                    Position p = new Position(x, y, layer);
                    // graphics
                    HashMap<State,BufferedImage> graphics = Utilities.getGraphicsFromJSON((String) gameObject.get("graphics"));
                    
                    int horizontalHitBox = 0;
                    int verticalHitBox = 0;

                    try {
                        horizontalHitBox = (int) ((long) gameObject.get("horizontalHitBox"));
                        verticalHitBox = (int) ((long) gameObject.get("verticalHitBox"));
                    } catch (Exception e) {
                        System.out.println("DANS LE TRY HITBOX");
                    }{}


                    // traitement différent selon le type de l'objet
                    switch (type) {
                        case "GameObject" :
                            GameObject props = new GameObject(p, graphics, horizontalHitBox, verticalHitBox);
                            gameObjects.add(props);
                            break;
                        case "Chest" :
                            Item[] chestContents;
                            chestContents = Chest.fillChestItem();
                            Chest chest = new  Chest(p,graphics,chestContents,inventoryHud);//recup les parametres pour le constructeur
                            gameObjects.add(chest);
                            break;          
                        case "Ladder" :
                            Ladder ladder = new Ladder(p,graphics,verticalHitBox);//recup les parametres pour le constructeur
                            gameObjects.add(ladder);
                            break;      
                        case "Trap" :
                            int dammage = (int) ((long) gameObject.get("dammage"));
                            Trap trap = new Trap(p, graphics, horizontalHitBox, verticalHitBox, dammage);
                            gameObjects.add(trap);
                            break;  
                        case "TrappedBox" :
                            Mob1 mob = new Mob1(p, graphics, "Jean le Destructeur",1 , 1);
                            TrappedBox trappedBox = new  TrappedBox(p,graphics,horizontalHitBox,verticalHitBox,mob);//recup les parametres pour le constructeur
                            gameObjects.add(trappedBox);
                            break;
                        case "Door" :
                            String nextLevel = (String) gameObject.get("nextLevel");
                            Door door = new Door(p,graphics,verticalHitBox,horizontalHitBox,nextLevel);//recup les parametres pour le constructeur
                            gameObjects.add(door);
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
        System.out.println(gameLevels);

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


     /**
     * methode temporaire
     * 
     * niveau pour tester le déplacement d'un objet
     */
}
