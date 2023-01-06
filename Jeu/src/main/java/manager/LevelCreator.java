package manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.awt.image.BufferedImage;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import prefab.entity.GameObject;
import prefab.entity.Robin;
import prefab.entity.BatGoblin;
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
import prefab.rendering.Visual;

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

                //background
                String str =(String)level.get("background_graphics");
                BufferedImage backgroundImage = Utilities.getImage("src/main/ressources/images/items/"+ str + ".png");
                
                
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
                            BatGoblin mob = new BatGoblin(p, aM, 1, 1, "Je suis méchant");
                            TrappedBox trappedBox = new  TrappedBox(p,animation,horizontalHitBox,verticalHitBox,mob);//recup les parametres pour le constructeur
                            gameObjects.add(trappedBox);
                            break;
                        case "Door" :
                            String nextLevel = (String) gameObject.get("nextLevel");
                            //if (nextLevel=="maze")generateMaze(levels);
                            JSONObject nextPos = (JSONObject) gameObject.get("nextPosition");
                            int newX = (int) ((long) nextPos.get("newX"));
                            int newY = (int) ((long) nextPos.get("newY"));
                            Position nextPosition = Position.create(newX, newY-1);
                            Door door = new Door(p, animation, verticalHitBox, horizontalHitBox, nextLevel, nextPosition);//recup les parametres pour le constructeur
                            gameObjects.add(door);
                            break;    
                        case "Teleportation" :
                            nextLevel = (String) gameObject.get("nextLevel");
                            nextPos = (JSONObject) gameObject.get("nextPosition");
                            newX = (int) ((long) nextPos.get("newX"));
                            newY = (int) ((long) nextPos.get("newY"));
                            nextPosition = Position.create(newX, newY-1);
                            Teleportation tp = new Teleportation(p, animation, verticalHitBox, horizontalHitBox, nextLevel, nextPosition);//recup les parametres pour le constructeur
                            gameObjects.add(tp);
                            break;                      
                        default:
                            break;
                    }
                }

                if (levelName.equals("level_2")) { 
                    

                    HashMap<State,Sprite> sM =  Utilities.getSpritesFromJSON("mob");
                    Animation aM = CharacterAnimation.createForPNJ(sM);
                    Position p1M = Position.create(3, 9);
                    BatGoblin mob = new BatGoblin(p1M, aM, 1, 1, "Robinet le teigneux");
                    Position p2M = Position.create(13, 11);
                    Animation aM2 = CharacterAnimation.createForPNJ(sM);
                    BatGoblin mob2 = new BatGoblin(p2M, aM2, 1, 1, "Robinait le ténébreux");
                    Position p3M = Position.create(20, 7);
                    Animation aM3 = CharacterAnimation.createForPNJ(sM);
                    BatGoblin mob3 = new BatGoblin(p3M, aM3, 1, 1, "Robinson le téméraire");

                    gameObjects.add(mob);
                    gameObjects.add(mob2);
                    gameObjects.add(mob3);
                }
                if (levelName.equals("level_3")) { 
                    

                    HashMap<State,Sprite> sM =  Utilities.getSpritesFromJSON("mob");
                    Animation aM = CharacterAnimation.createForPNJ(sM);
                    Position p1M = Position.create(25, 5);
                    BatGoblin mob = new BatGoblin(p1M, aM, 1, 1, "Robinet le teigneux");
                    Position p2M = Position.create(18, 10);
                    Animation aM2 = CharacterAnimation.createForPNJ(sM);
                    BatGoblin mob2 = new BatGoblin(p2M, aM2, 1, 1, "Robinait le ténébreux");

                    gameObjects.add(mob);
                    gameObjects.add(mob2);
                }
                if (levelName.equals("level_4")) { 
                    
                    HashMap<State,Sprite> sM =  Utilities.getSpritesFromJSON("robin");
                    Animation aM = CharacterAnimation.createForPNJ(sM);
                    Position p1M = Position.create(11, 9);
                    Robin robin = new Robin(p1M, aM, 3, 1, "R.O.B.I.N");
                   

                    gameObjects.add(robin);
                }
                GameLevel gameLevel = new GameLevel(gameObjects,levelInitMapArray,backgroundImage);
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



    //https://medium.com/pragmatic-programmers/the-growing-tree-algorithm-844d67e115b8

    // void generateMaze(JSONArray levels){

    //     //initialisation de la liste vide contenant toutes les pos possible pour les murs(2 en moins sur y pour le hud)
    //     int[][] levelInitMapArray = new int[13][27];

    //     int[][] active =  new int[13][27];
    //     active[5][9]=1;
    //     //création du labyrinthe(1 case sur 2 est visitable)
    //     while (activeNotEmpty(active)){
    //         int[][] cell = selectCellFromActive(active);//surement la dernire lié puis on remonte en arriere
    //         if (UnvisitedNeighborg(cell)){//regarde autour de la celule si il ya des voisins dispo
    //             int[][] newCell = chooseRandomUnvisitedNeighborg(cell) ;//choisi un voisin de maniere aleatoire(uniforme)
    //             addNeigborgToActive(active,newCell);//
    //             createlink(levelInitMapArray,cell,newCell);//ajoute en case accessible la case entre les 2 cells
    //         }
    //         else{//impasse
    //             unactivateCell(cell);//on enleve la case de la liste des cases actives
    //             addCellToMap(cell);//on ajoute la case à la map
    //         }
    //     }
    //     //Toutes les cases qui ne sont pas accessibles sont donc des murs 

    //     //on recup le labyrinthe qui est stocké en dernier dans la liste de level
    //     JSONObject level = (JSONObject) levels.get(levels.size());

    //     //création d'un gameObject mur
    //     GameObject wall= ;
    //     //ajout de gameObject pour chaque pos de mur
    //     for (int i = 0 ; i < 13; i++){
    //         for (int j = 0 ; i < 27; i++){
    //             if (needWall(levelInitMapArray,i,j)){
    //                 addGameObjectToJsonLevel(level,wall);
    //             }
    //         }
    //     }
    // }
























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
