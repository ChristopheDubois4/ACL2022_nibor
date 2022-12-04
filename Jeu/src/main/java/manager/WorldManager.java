package manager;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.javatuples.Pair;

import engine.Cmd;
import engine.Command;
import prefab.entity.GameObject;
import prefab.entity.Mob1;
import prefab.entity.Player;
import prefab.gui.*;
import prefab.information.PlayerClasses;
import prefab.information.Position;
import prefab.level.GameLevel;
import prefab.information.State;
import prefab.information.Visual;
import java.awt.image.BufferedImage;


/**
 * gère le monde dans lequel le joueur évolue
 */
public class WorldManager implements WorldPainter {

    public static final int KEY_TIME = 50;

    public static final int MOVE_TIME = 150;
    public static final int IMAGES_PER_MOVE = 10;

    // createurs
    private LevelCreator levelCreator;

    // combats
    private static FightManager  fightManager;
    
    // niveaux
    public HashMap<String, GameLevel> gameLevels;
    public GameLevel currentLevel;

    // huds
    private List<Hud> huds;
    private InventoryHud inventoryHud;
    private VitalResourcesHud healthBar;
    private StatsHud statsInfo;
    private static FightHud fightHud;
    
    // joueur
    Player player;

    // mouvement du joueur autorisé ?
    private boolean isKeyLocked = false;

    /**
     * constructeur de la classe WorldManager
     */
    public WorldManager() {    
        // Initialisation du joueur
        initPlayer();        
        // Initialisation des Huds
        initHuds();
        // Initialisation des niveaux
        initLevels();
        // Initialisation du gestionnaire de combats
        fightHud = new FightHud(player);
        fightManager = new FightManager(player, fightHud);     
        //testCombats();
    }

    /**
     * methode temporaire
     */
    public static void testCombats() {

        Position p1 = new Position(20, 5);
        HashMap<State,BufferedImage> graphicsBOX = Utilities.getGraphicsFromJSON("player");
        Mob1 mob = new Mob1(p1, graphicsBOX, "Jean le Destructeur", 1, 1);
        fightManager.getIsInFight();
        fightHud .loadEnemy(mob);   

        fightManager.startNewFight(mob);     

    }
    
    /**
     * initialise le joueur
     */
    public void initPlayer() {
        //TEST
        HashMap<State,BufferedImage> graphicsPLAYER = Utilities.getGraphicsFromJSON("player");
        Position p1 = new Position(10, 10);
        player = new Player(p1, graphicsPLAYER, "II_ChRom3_II", 1, 1, PlayerClasses.CLERIC); 
        player.takeDammage(50);
        System.out.println("Player : "+ player.getPosition() + "\n");
    }

    /**
     * initialise les niveaux
     */
    public void initLevels() {
        levelCreator = new LevelCreator(inventoryHud);
        gameLevels = levelCreator.getLevels();
        currentLevel = gameLevels.get("default");
    }

    /**
     * initialise les huds
     */
    public void initHuds() {
        huds = new ArrayList<Hud>();       

        inventoryHud = new InventoryHud(player);
        healthBar =  new VitalResourcesHud(player);
        statsInfo = new StatsHud(player);
        
        huds.add(healthBar);
        huds.add(statsInfo);
        huds.add(inventoryHud);

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
     * @throws InterruptedException
     */
    public void updateWorld(Command command) {

        Cmd cmd = command.getKeyCommand();      
        
        if (isKeyLocked) {
            return;
        }

        if (fightManager.getIsInFight()) {
            /*
            if (cmd == Cmd.CLOSE) {
                fightManager.finishFight();
                return;
            } */
            fightManager.evolve(command);
            if (fightManager.allowKeyLocker()) {
                keyLocker(3*KEY_TIME);
            }
            return;            
        }

        if (cmd == Cmd.IDLE) {
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
       
       
        if (!player.getIsInMouvement())
        	movePlayer(cmd);  
    }

    /**
     * deplace le joueur
     * @param cmd commande du joueur
     */
    private void movePlayer(Cmd cmd) {    
        //Si le joueur est mort il ne peut pas bouger
        if (player.getState()==State.DEAD) return;

        // calcul du déplacement à effectuer
        int deltaX = 0, deltaY = 0;
        switch (cmd) {            
            case LEFT:
                player.setState(State.IDLE_LEFT);
                deltaX -= 1;
                break;
            case RIGHT:
                player.setState(State.IDLE_RIGHT);
                deltaX += 1;
                break;
            case UP:
                player.setState(State.IDLE_UP);
                deltaY += 1;
                break;
            case DOWN:
                player.setState(State.IDLE_DOWN);
                deltaY -= 1;
                break;
            default:
                return;
        }
        
        /** 
         * si le joueur peut se déplacer retourne <true, ...>, sinon <false, ...>
         * si c'est un objet qui bloque le personnage retourne <..., l'objet en question >, sinon <..., null> 
         */
        Pair<Boolean, GameObject> check = currentLevel.checkMove(player, deltaX, deltaY);
        /**
         * s'il n'y a pas d'obstacle, déplace le joueur
         */
        if (check.getValue0()) {             
            player.move(deltaX, deltaY);              
            return;
        }

        System.out.println("MOVEMENT IMPOSSIBLE \nObjet de la collision : "+ check.getValue1() + "\n");
        System.out.println("Player : "+ player.getPosition() + "\n");
    }
    
    

    /**
     * utilise l'objet en face du joueur
     * @param cmd commande du joueur
     */
    private void usePlayer(Cmd cmd) {
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
        if (check.getValue1() == null) {                        
            return;
        } 

        System.out.println("Player utilise : "+ check.getValue1() + "\n");
        //si l'objet est utilisable, on l'utilise
        //si un HUD est nécessaire return true
        check.getValue1().objectUse(player,cmd);
    }

    @Override
    /**
     * récupère les visuels que l'on souhaite afficher
     * @return les visuels à afficher
     */
    public List<Visual> getVisuals() {

        if (fightManager.getIsInFight()) {
            return fightHud.getVisuals();
        }
        List<Visual> visuals = currentLevel.getVisuals();        
        visuals.add(player.getVisual());
        for (Hud hud : huds) {
            if (hud.hudIsDisplayed()) {
                visuals.addAll(hud.getVisuals());
            }
        }
        return visuals;
    }

    /**
     * récupère les visuels que l'on souhaite afficher par dessus les huds
     * @return les visuels à afficher
     */
    public List<Visual> getFrontVisuals() {

        if (fightManager.getIsInFight()) {
            return fightHud.getFrontVisuals();
        }
        return new ArrayList<Visual>();
    }     
    
    
    /**
     * dessine les visuels spécifiques des Huds 
     */
	@Override
	public void drawHuds(Graphics2D g) {

        if (fightManager.getIsInFight()) {
            fightHud.draw(g);
        } else {
            for (Hud hud : huds) {
                hud.draw(g);
            }
        }
	}

}
