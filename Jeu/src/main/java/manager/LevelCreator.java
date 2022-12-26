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
import prefab.entity.Ghoul;
import prefab.equipment.Consumable;
import prefab.equipment.Effect;
import prefab.equipment.Item;
import prefab.equipment.Effect.TypeEffects;
import prefab.information.Layer;
import prefab.information.Position;
import prefab.information.State;
import prefab.level.GameLevel;
import prefab.props.*;
import prefab.props.Chest;
import prefab.props.TrappedBox;
import prefab.rendering.Animation;
import prefab.rendering.CharacterAnimation;
import prefab.rendering.Sprite;

/**
 * créer les différents niveux du jeux
 */
public class LevelCreator {

    public HashMap<String, GameLevel> gameLevels;
    String defaultfile = "src/main/ressources/levels/default.json";

    
    /**
     * constructeur de la classe LevelManager
     * @throws Exception
     */
    public LevelCreator() throws Exception {
        gameLevels = new HashMap<String, GameLevel>();
        initGameLevels(); 
    }

    public HashMap<String, GameLevel> getLevels() {
        return gameLevels;
    }

    
    /**
     * methode temporaire
     * 
     * Méthode pour tester des fonctionnalitées liées au sprint 1
     * @throws Exception
     */
    private void testSrpint1() throws Exception {       

        //testTriage();
        //testMovement(); 
        testReforge();
    }

    private void testReforge() throws Exception {
         
        /*
        HashMap<State,Sprite> s =  Utilities.getSpritesFromJSON("trap");

        Animation a = CharacterAnimation.createForPNJ(s);

        Position p1 = Position.create(20, 5);

        GameObject o1 = new TrappedBox(p1, a, 1, 1, null);
        System.out.println("TRAPPEDBOX");

        level1.addGameObjects(new ArrayList<GameObject>(Arrays.asList(o1)));
        */

        Position p5 = Position.create(5, 5);

        List<Effect> hit20 = new ArrayList<Effect>(){{add(new Effect(TypeEffects.HIT, 20));}};

        Item[] chestContents = new Item[]{
            new Consumable("epee sdaacre","sword_1",hit20),
            new Consumable("epee sdaacre","bitcoin", hit20),
            new Consumable("epee sdaacre","potion_heal",hit20)
        };

        HashMap<State,Sprite> s =  Utilities.getSpritesFromJSON("chest");

        Animation a = Animation.create(s);

        GameObject o5 = new Chest(p5, a, 1, 1, chestContents);
        System.out.println("CHEST");


        Position p1 = Position.create(15, 5);

        HashMap<State, Sprite> graphicsCHEST = Utilities.getSpritesFromJSON("box");
        Animation aTrappedBox = Animation.create(graphicsCHEST);


        GameObject o1 = new TrappedBox(p1, aTrappedBox, 1,1, null);
        System.out.println("TRAPPEDBOX");
       
        GameLevel level1 = new GameLevel(new ArrayList<GameObject>(Arrays.asList(o1, o5)));

        gameLevels.put("default",level1);

    }

    /**
     * créer les niveaux du jeux
     * @throws Exception
     */
    private void initGameLevels() throws Exception {
        createLevelsFromJSON(defaultfile);
    }  
    
    /**
     * ( W I P )
     * créer un niveau à partir d'un fichier JSON
     * @param file chemin du fichier JSON
     * @throws Exception
     */
    private void createLevelsFromJSON(String file) throws Exception {
        // récupération du fichier JSON a partir d'un chemin
        File directory = new File(file);
        JSONParser jsonParser = new JSONParser();
        // lecture du fichier JSON
        try (FileReader reader = new FileReader(directory))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            // tableau de niveau sous format JSON
            JSONArray levels = (JSONArray) obj;
            // parcours des niveaux
            for (int i = 0; i < levels.size() ; i++) {

                JSONObject level = (JSONObject) levels.get(i);

                String levelName = (String) level.get("name");
                JSONArray levelInitMap = ((JSONArray) level.get("initMap"));
                int[][] levelInitMapArray = jsonArrayTo2DInt(levelInitMap);
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
                    Position p = Position.createPosition(x, y, layer);
                    // graphics
                    HashMap<State, Sprite> graphics = Utilities.getSpritesFromJSON((String) gameObject.get("graphics"));
                    Animation animation = Animation.create(graphics);            
                   
                    int horizontalHitBox = 0;
                    int verticalHitBox = 0;

                    try {
                        horizontalHitBox = (int) ((long) gameObject.get("horizontalHitBox"));
                        verticalHitBox = (int) ((long) gameObject.get("verticalHitBox"));
                    } catch (Exception e) {
                        System.out.println("DANS LE TRY HITBOX");
                    }


                    // traitement différent selon le type de l'objet
                    switch (type) {
                        case "GameObject" :
                            GameObject props = GameObject.createWithDefaultState(p, animation, horizontalHitBox, verticalHitBox);
                            gameObjects.add(props);
                            break;
                        case "Chest" :
                            Item[] chestContents;
                            chestContents = Chest.fillChestItem();
                            Chest chest = new Chest(p, animation, horizontalHitBox, verticalHitBox, chestContents); //recup les parametres pour le constructeur
                            gameObjects.add(chest);
                            break;          
                        case "Ladder" :
                            Ladder ladder = new Ladder(p,animation,verticalHitBox);//recup les parametres pour le constructeur
                            gameObjects.add(ladder);
                            break;      
                        case "Trap" :
                            int dammage = (int) ((long) gameObject.get("dammage"));
                            Trap trap = new Trap(p, animation, horizontalHitBox, verticalHitBox, dammage);
                            gameObjects.add(trap);
                            break;  
                        case "TrappedBox" :
                            // TEMPORAIRE
                            HashMap<State,Sprite> sM =  Utilities.getSpritesFromJSON("mob");
                            Animation aM = CharacterAnimation.createForPNJ(sM);
                            // FIN
                            Ghoul mob = new Ghoul(p, aM, 1, 1, "Je suis méchant");
                            TrappedBox trappedBox = new  TrappedBox(p,animation,horizontalHitBox,verticalHitBox,mob);//recup les parametres pour le constructeur
                            gameObjects.add(trappedBox);
                            break;
                        case "Door" :
                            String nextLevel = (String) gameObject.get("nextLevel");
                            JSONObject nextPos = (JSONObject) gameObject.get("nextPosition");
                            int newX = (int) ((long) nextPos.get("newX"));
                            int newY = (int) ((long) nextPos.get("newY"));
                            Position nextPosition = Position.create(newX, newY-1);
                            Door door = new Door(p, animation, verticalHitBox, horizontalHitBox, nextLevel, nextPosition);//recup les parametres pour le constructeur
                            gameObjects.add(door);
                            break;                      
                        default:
                            break;
                    }
                }
                GameLevel gameLevel = new GameLevel(gameObjects,levelInitMapArray);
                this.gameLevels.put(levelName, gameLevel);
            }           
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
    }

    public int[][] jsonArrayTo2DInt(JSONArray jsonArray){
        int[][] int2DConvert = new int[15][27];
        Object[] js = (Object[]) jsonArray.toArray();
        for (int i = 0 ; i < 15; i++){
            JSONArray jsonRowI = (JSONArray) js[i];
            Object[] jsonListRowI = (Object[]) jsonRowI.toArray();
            for (int j = 0; j < 27; j++) {
                Long jsonLongRowIColumnJ = (Long) jsonListRowI[j];
                int jsonIntRowIColumnJ = jsonLongRowIColumnJ.intValue();
                int2DConvert[14-i][j] = jsonIntRowIColumnJ;
            }
        }
        return int2DConvert;
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
     * @throws Exception
     */
    private void testMovement() throws Exception {     
        /*
        HashMap<State,BufferedImage> graphicsBOX = Utilities.getGraphicsFromJSON("box");
        HashMap<State,BufferedImage> graphicsDOOR = Utilities.getGraphicsFromJSON("door");        
        HashMap<State,BufferedImage> graphicsLADDER = Utilities.getGraphicsFromJSON("ladder");
        HashMap<State,BufferedImage> graphicsTRAP = Utilities.getGraphicsFromJSON("trap");
        HashMap<State,BufferedImage> graphicsCHEST = Utilities.getGraphicsFromJSON("chest");

        GameLevel level1 = new GameLevel();
        Position p1 = Position.create(20, 5);
        Position p2 = Position.create(5, 5);; 
        Position p3 = Position.create(8, 5);
        Position p4 = Position.create(26, 14);
        Position p5 = Position.create(0, 0);

        List<Effect> hit20 = new ArrayList<Effect>(){{add(new Effect(TypeEffects.HIT, 20));}};

        Item[] chestContents = new Item[]{
            new Consumable("epee sdaacre","sword_1",hit20),
            new Consumable("epee sdaacre","bitcoin", hit20),
            new Consumable("epee sdaacre","potion_heal",hit20)
        };

        HashMap<State,BufferedImage> graphicsPLAYER = Utilities.getGraphicsFromJSON("player");
        Mob1 mob = new Mob1(p1, null, 1, 1, "Jean le Destructeur"); //new Mob1(p1, graphicsPLAYER, 1, 1, "Jean le Destructeur");
        GameObject o1 = new TrappedBox(p1, graphicsBOX, 1, 1,mob);
        System.out.println("TRAPPEDBOX");

        GameObject o2 = GameObject.createWithDefaultState(p5, null, 1, 1);// new GameObject(p2, graphicsDOOR, "DOOR", 1, 1);
        System.out.println("DOOR");

        GameObject o3 = new Ladder(p3, graphicsLADDER, 3);
        System.out.println("LADDER");

        GameObject o4 = new Trap(p4, graphicsTRAP, 1,1,30);
        System.out.println("TRAP");

        GameObject o5 = new Chest(p5, graphicsCHEST, 1, 1, chestContents, inventoryHud);
        System.out.println("CHEST");

        System.out.println("\n2 Obstacles de 1 case : ACIDE en (5,5) et en Trap en (20,8)\nLadder utilisable en (8,5)/(8,6)/(8,7)\nChest en (10,5)\n");

        level1.addGameObjects(new ArrayList<GameObject>(Arrays.asList(o1, o2, o3, o4 ,o5)));

        gameLevels.put("default",level1);
        */

    }

}
