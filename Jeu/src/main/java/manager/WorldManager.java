package manager;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.javatuples.Pair;

import engine.Cmd;
import engine.Command;
import prefab.entity.Character;
import prefab.entity.Enemy;
import prefab.entity.GameObject;
import prefab.entity.BatGoblin;
import prefab.entity.Player;
import prefab.entity.Robin;
import prefab.entity.Player.PlayerClasses;
import prefab.gui.*;
import prefab.information.Position;
import prefab.level.GameLevel;
import prefab.props.Teleportation;
import prefab.props.UsableObject;
import prefab.rendering.Animation;
import prefab.rendering.Animator;
import prefab.rendering.CharacterAnimation;
import prefab.rendering.Sprite;
import prefab.rendering.Visual;
import prefab.information.State;

/**
 * gère le monde dans lequel le joueur évolue
 */
public class WorldManager implements WorldPainter {

    public static final int KEY_TIME = 50;

    public static final int MOVE_TIME = 150;
    public static final int IMAGES_PER_MOVE = 10;

    // createurs
    private static LevelCreator levelCreator;

    // combats
    private static FightManager  fightManager;

    //class
    private static ClassManager  classManager;
    
    // niveaux
    public static HashMap<String, GameLevel> gameLevels;
    public static GameLevel currentLevel;

    // Huds
    private InventoryHud inventoryHud;

    private VitalResourcesHud vitalResourcesHud;

    private StatsHud statsHud;
    
    // joueur
    Player player;

    // mouvement du joueur autorisé ?
    private boolean isKeyLocked = false;



    /**
     * constructeur de la classe WorldManager
     * @throws Exception
     */
    public WorldManager() throws Exception {    
 


        // Initialisation de la classe du joueur
        classManager = ClassManager.getInstance();
        classManager.initClassManager();

        // Initialisation du joueur
        initPlayer();

        // Initialisation des Huds
        initHuds();
        // Initialisation des niveaux
        initLevels();    

        // Initialisation du gestionnaire de combats
        fightManager = FightManager.getInstance();
        fightManager.initFightManager();
        //testCombats();
    }
      
    /**
     * initialise le joueur
     * @throws Exception
     */
    public void initPlayer() throws Exception {     
        classManager.startClassSelection();
        player = Player.getInstance();
        player.initPlayer(PlayerClasses.WARRIOR);
        System.out.println("Player : "+ player.getPosition() + "\n");
    }

    /**
     * initialise les niveaux
     * @throws Exception
     */
    public void initLevels() throws Exception {
        levelCreator = new LevelCreator();
        gameLevels = levelCreator.getLevels();
        currentLevel = gameLevels.get("level_1");


    }

    /**
     * initialise les huds
     * @throws Exception
     */
    public void initHuds() throws Exception {

        ClassHud.getInstance();
        FightHud.getInstance();
        inventoryHud = InventoryHud.getInstance();
        vitalResourcesHud = VitalResourcesHud.getInstance();
        statsHud = StatsHud.getInstance();
        
      
        for (Hud hud : Hud.getHuds()) {
            hud.initHud();
        }

    }
    
    /**
     * permet de choisir à quelle fréquence les touches sont prises en 
     * compte indépendament du temps de rafraichissement de l'image ou du temps de calcul
     * 
     * EX : Si le joueur met 200 ms à se déplacer d'une case à une autre : 
     *   durant ce temps de déplacement, si l'utilisateur appuit sur une touche
     *   de déplacement (z, q, s ou d) elles ne sont pas prisent en compte
     */
    private void keyLocker(int lockTime) {
    	
    	isKeyLocked = true;    	
    	new Thread(() -> { 
    		try {
				Thread.sleep((long) lockTime);
				isKeyLocked = false;  
			} catch (InterruptedException e) {
				e.printStackTrace();
		} }).start();        
    }  
    
    /**
     * met à jour les données du monde
     * @param cmd commande du joueur
     * @throws Exception
     * @throws InterruptedException
     */
    public void updateWorld(Command command) throws Exception {

        playerTurn(command);
        enemiesTurn();               
    }

    public void playerTurn(Command command) throws Exception {

        
        Cmd cmd = command.getKeyCommand();    

        if (isKeyLocked) {
            return;
        }

        if (classManager.getIsChoosingClass()) {
            classManager.evolve(command);
            if (classManager.allowKeyLocker()) {
                keyLocker(3*KEY_TIME);
            }
            return;            
        }
        else if (!classManager.getIsChoosingClass() && classManager.getClassPlayed() != null){
            player.setClass(classManager.getClassPlayed());
            System.out.println("Vous jouez : "+classManager.getClassPlayed()); 
            classManager.classPlayedIsInit();
            vitalResourcesHud.changeDisplayState();
            statsHud.changeDisplayState();
        }

        if (fightManager.getIsInFight()) {
            /*
            if (cmd == Cmd.CLOSE) {
                fightManager.finishFight();
                return;
            }
            */
            fightManager.evolve(command);
            if (fightManager.allowKeyLocker()) {
                keyLocker(3*KEY_TIME);
            }
            return;            
        }


        if (cmd == Cmd.INVENTORY && command.getActionType() == "released"  && !inventoryHud.isChestDisplay()) {
            inventoryHud.changeDisplayState(); 
            keyLocker(KEY_TIME);
            return;         
        }

        if (cmd == Cmd.USE && command.getActionType() == "released" && inventoryHud.isChestDisplay()) {
            keyLocker(KEY_TIME);
            inventoryHud.changeDisplayState(); 
            usePlayer(cmd);
            return;
        }

        if (inventoryHud.hudIsDisplayed()) {
            if (cmd == Cmd.CLOSE && inventoryHud.isChestDisplay()) {
                keyLocker(KEY_TIME);
                inventoryHud.changeDisplayState(); 
                usePlayer(cmd);
                return;
            }
            if (cmd == Cmd.CLOSE && !inventoryHud.isChestDisplay()) { 
                inventoryHud.changeDisplayState(); 
                return;
            }
            inventoryHud.processClick(command);
            return;
        }

        if (cmd == Cmd.USE && command.getActionType() == "released") {
            System.out.println("Cas d'utilisation\n");
            keyLocker(KEY_TIME);
            usePlayer(cmd);
            return;
        }


        // Faire des trucs 

        // Si LEFT, DOWN, RIGHT ou UP (implicite à cause des "return")       
        if (!player.AnimationPlayMoving()) {
            moveCharacter(cmd, player);  
       }
        
    }

    public void enemiesTurn() throws Exception {

        if (fightManager.getIsInFight() || classManager.getIsChoosingClass()) {
            return;
        }
        
        List<Enemy> enemies = new ArrayList<Enemy>();
        List<GameObject> objects = currentLevel.getGameObjects();
        for (GameObject gameObject : objects) {
            if ((gameObject instanceof Enemy) && !(gameObject instanceof Robin)) {
                enemies.add((Enemy) gameObject);
            }
        }

        for (Enemy enemy : enemies) {
            // si obstacle cganer direction, compteur max (while cpt < 10)
            if (!enemy.AnimationPlayMoving()) {

                Cmd cmd;
                int iterMax = 5;
                int iter = -1;
                do {
                    cmd = enemy.getNextMove(++iter > 0);
                } 
                while (!moveCharacter(cmd, enemy) && iter < iterMax);
            }            
            
        }
    }

    /**
     * deplace le joueur
     * @param cmd commande du joueur
     * @throws Exception
     */
    private boolean moveCharacter(Cmd cmd, Character character) throws Exception {    

        //Si le joueur est mort il ne peut pas bouger
        if (character.getState()==State.DEAD) return true;

        // calcul du déplacement à effectuer
        int deltaX = 0, deltaY = 0;
        switch (cmd) {            
            case LEFT:
                character.setState(State.IDLE_LEFT);
                deltaX -= 1;
                break;
            case RIGHT:
                character.setState(State.IDLE_RIGHT);
                deltaX += 1;
                break;
            case UP:
                character.setState(State.IDLE_UP);
                deltaY += 1;
                break;
            case DOWN:
                character.setState(State.IDLE_DOWN);
                deltaY -= 1;
                break;
            default:
                return true;
        }
        
        /** 
         * si le joueur peut se déplacer retourne <true, ...>, sinon <false, ...>
         * si c'est un objet qui bloque le personnage retourne <..., l'objet en question >, sinon <..., null> 
         */
        Pair<Boolean, GameObject> check = currentLevel.checkMove(character, deltaX, deltaY);
        /**
         * s'il n'y a pas d'obstacle, déplace le joueur
         */
        if (check.getValue0()) {             
            character.move(deltaX, deltaY);  
            if (check.getValue1() instanceof Player) {
                keyLocker(MOVE_TIME);
            }
            System.out.println("character : "+ character.getPosition() + "\n");
            return true;
        }

        if (character instanceof Player) {
            if (check.getValue1() instanceof Teleportation ) {
                ((UsableObject) check.getValue1()).objectUse(player,cmd);
            }
            else if (check.getValue1() instanceof Enemy) {                 
                fightManager.startNewFight((Enemy) check.getValue1());                 
            }
        }       

        else if (check.getValue1() instanceof Player && character instanceof Enemy) {
            fightManager.startNewFight((Enemy) character);
            return true;
        }

        System.out.println("MOVEMENT IMPOSSIBLE \n"+character.toString()+" collision avec "+ check.getValue1() + "\n");
        System.out.println("character : "+ character.getPosition() + "\n");
      
        return false;
    }
    
    

    /**
     * utilise l'objet en face du joueur
     * @param cmd commande du joueur
     * @throws Exception
     */
    private void usePlayer(Cmd cmd) throws Exception {
        int deltaX = 0, deltaY = 0;
        switch (player.getState()) {            
            case IDLE_LEFT:
                deltaX -= 1;
                break;
            case IDLE_RIGHT:
                deltaX += 1;
                break;
            case IDLE_UP:
                deltaY += 1;
                break;
            case IDLE_DOWN:
                deltaY -= 1;
                break;
            default:
                break;
        }

        /** 
         * si le joueur peut se déplacer retourne <true, ...>, sinon <false, ...>
         * si c'est un objet qui bloque le personnage retourne <..., l'objet en question >, sinon <..., null> 
         */
        Pair<Boolean, GameObject> check = currentLevel.checkMove(player, deltaX, deltaY);
        /**
         * s'il n'y a pas d'objet utilisable rien ne se passe, on sort de la fonction
         */
        System.out.println("player.getState() "+ player.getState());

        System.out.println("Cas d'SLTTT\n");

        if (check.getValue1() == null) {                        
            return;
        } 

        System.out.println("Player utilise : "+ check.getValue1() + "\n");
        //si l'objet est utilisable, on l'utilise
        //si un HUD est nécessaire return true
        if (check.getValue1() instanceof UsableObject) {
         
            ((UsableObject) check.getValue1()).objectUse(player,cmd);
          
        }
    }

    /**
     * récupère les visuels que l'on souhaite afficher
     * @return les visuels à afficher
     */
    @Override
    public List<Visual> getVisuals() throws Exception {
        // initialisation de la liste des visuels à afficher
        List<Visual> visuals = new ArrayList<Visual>();
        // Visuels animés
        visuals.addAll(Animator.getInstance().getVisuals());
        // Visuels du niveau
        if (!FightManager.getInstance().getIsInFight() && !classManager.getIsChoosingClass()) {
            visuals.addAll(currentLevel.getVisuals());
        }
         
        // Visuels des HUDS
        for (Hud hud : Hud.getHuds()) {
            if (hud.hudIsDisplayed()) {
                visuals.addAll(hud.getVisuals());
            }
        }
        // Tri des viseuls pour avoir la superposition souhaitée
        Collections.sort(visuals);
        return visuals;
    }

    public static void removeObject(GameObject gameObject) {
        currentLevel.removeGameObject(gameObject);
    }

    /**
     * récupère les visuels que l'on souhaite afficher par dessus les huds
     * @return les visuels à afficher
     * @throws Exception
     */
    public List<Visual> getFrontVisuals() throws Exception {
        
        List<Visual> visuals = new ArrayList<Visual>();

        for (Hud hud : Hud.getHuds()) {
            if (hud.hudIsDisplayed()) {
                visuals.addAll(hud.getFrontVisuals());
            }
        }
  
        return visuals;
    }     
    
    
    /**
     * dessine les visuels spécifiques des Huds 
     * @param g permet de dessiner sur la fenêtre
     */
	@Override
	public void drawHuds(Graphics2D g) {
        for (Hud hud : Hud.getHuds()) {
            if (hud.hudIsDisplayed()) {
                hud.draw(g);
            }
        }
        
	}

}
