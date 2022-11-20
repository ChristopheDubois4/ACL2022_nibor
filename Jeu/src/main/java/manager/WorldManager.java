package manager;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.javatuples.Pair;

import engine.Cmd;
import engine.Command;
import prefab.entity.GameObject;
import prefab.entity.Player;
import prefab.gui.VitalResourcesHud;
import prefab.gui.Hud;
import prefab.gui.InventoryHud;
import prefab.gui.StatsHud;
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
    LevelCreator levelCreator;
    HudCreator hudCreator;
    
    // niveaux
    public HashMap<String, GameLevel> gameLevels;
    public GameLevel currentLevel;

    // huds
    List<Hud> huds;
    InventoryHud inventoryHud;
    VitalResourcesHud healthBar;
    StatsHud statsInfo;
    
    // joueur
    Player player;

    // mouvement du joueur autorisé ?
    private boolean isKeyLocked = false;

    /**
     * constructeur de la classe WorldManager
     */
    public WorldManager() {    
        // Initialisation des niveaux
        initLevels();
        // Initialisation du joueur
        initPlayer();        
        // Initialisation des Huds
        initHuds();
    }
    
    /**
     * initialise le joueur
     */
    public void initPlayer() {
        //TEST
        HashMap<State,BufferedImage> graphicsPLAYER = Utilities.getGraphicsFromJSON("player");
        Position p1 = new Position(10, 10);
        player = new Player(p1, graphicsPLAYER, "player", 1, 1, PlayerClasses.CLERIC); 
        player.setState(State.IDLE_DOWN);
        System.out.println("Player : "+ player.getPosition() + "\n");
    }

    /**
     * initialise les niveaux
     */
    public void initLevels() {
        levelCreator = new LevelCreator();
        gameLevels = levelCreator.getLevels();
        currentLevel = gameLevels.get("default");
    }

    /**
     * initialise les huds
     */
    public void initHuds() {
        huds = new ArrayList<Hud>();        
        HudCreator hudManager = new HudCreator(this.player);

        inventoryHud = hudManager.getInventory();
        healthBar = hudManager.getHealthBar();
        statsInfo = hudManager.getStatsInfo();
        
        huds.add(inventoryHud);
        huds.add(healthBar);
        huds.add(statsInfo);
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
        
        if (isKeyLocked || cmd == Cmd.IDLE) {
            return;
        }

        if (cmd == Cmd.INVENTORY && command.getActionType() == "released") {
            inventoryHud.changeDisplayState();   
            keyLocker(KEY_TIME);
            return;         
        }

        if (inventoryHud.hudIsDisplayed()) {
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
        //Si le jouer est mort il ne peut pas bouger
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
         * s'il n'y a pas d'obstacle, déplace le joueur
         */
        if (check.getValue1() == null) {                        
            return;
        } 

        System.out.println("Player utilise : "+ check.getValue1() + "\n");
        // si l'object n'est pas utilisable, on sort de la fonction
        if (!check.getValue1().objectUse(player)) {
            return;
        }
        // sinon on utilise l'objet
    }

    @Override
    /**
     * récupère les visuels que l'on souhaite afficher
     * @return les visuels à afficher
     */
    public List<Visual> getVisuals() {
        List<Visual> visuals = currentLevel.getVisuals();
        
        visuals.add(player.getVisual());
        for (Hud hud : huds) {
            if (hud.hudIsDisplayed()) {
                visuals.addAll(hud.getVisual());
            }
        }
        return visuals;
    }
    
    /**
     * dessine les visuels spécifiques des Huds 
     */
	@Override
	public void drawHuds(Graphics2D g) {
		healthBar.draw(g);
        statsInfo.draw(g);		
	}

}
