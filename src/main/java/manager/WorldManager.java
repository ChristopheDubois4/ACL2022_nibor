package manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.javatuples.Pair;

import engine.Cmd;
import engine.Command;
import prefab.entity.GameObject;
import prefab.entity.Player;
import prefab.gui.Hud;
import prefab.gui.InventoryHud;
import prefab.information.Image;
import prefab.information.PlayerClasses;
import prefab.information.Position;
import prefab.level.GameLevel;
import prefab.information.State;
import prefab.information.Visual;

/**
 * gère le monde dans lequel le joueur évolue
 */
public class WorldManager implements WorldPainter{

    private final static int moveTIME = 50;

    // createurs
    LevelCreator levelCreator;
    HudCreator hudCreator;
    
    // niveaux
    public HashMap<String, GameLevel> gameLevels;
    public GameLevel currentLevel;

    // huds
    List<Hud> huds;
    InventoryHud inventoryHud;
    
    // joueur
    Player player;

    // mouvement du joueur autorisé ?
    boolean isKeyLocked = false;

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
        HashMap<State,Image> graphicsPLAYER = levelCreator.getGraphicsFromJSON("player");
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
    private void keyLocker() {
        Thread thread = new Thread();
        thread.start();
        isKeyLocked = true;
        try {
            Thread.sleep(moveTIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        isKeyLocked = false;
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

        if (cmd == Cmd.INVENTORY) {
            inventoryHud.changeDisplayState();   
            keyLocker();
            return;         
        }

        if (inventoryHud.hudIsDisplayed()) {
            inventoryHud.processClick(command);
            return;
        }        

        if (cmd == Cmd.USE) {
            System.out.println("Cas d'utilisation\n");
            keyLocker();
            usePlayer(cmd);
            return;
        }

        // Faire des trucs 

        // Si LEFT, DOWN, RIGHT ou UP (implicite à cause des "return")
        keyLocker();
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
        int x = 0, y = 0;
        switch (cmd) {            
            case LEFT:
                player.setState(State.IDLE_LEFT);
                x -= 1;
                break;
            case RIGHT:
                player.setState(State.IDLE_RIGHT);
                x += 1;
                break;
            case UP:
                player.setState(State.IDLE_UP);
                y += 1;
                break;
            case DOWN:
                player.setState(State.IDLE_DOWN);
                y -= 1;
                break;
            default:
                return;
        }

        // si le mouvement amène en dehors de la fenêtre, on sort de la fonction 
        if (!player.move(x, y)) {
            return;
        } 
        /** 
         * test s'il y a un obstacle qui empêche le joueur de se déplacer 
         * et si oui retourne l'object bloquant
         */
        Pair<Boolean, GameObject> check = currentLevel.checkMove(player);
        // si il n'y a pas d'obstacle, on sort de la fonction
        if (check.getValue0()) {
            System.out.println("Player : "+ player.getPosition() + "\n");
            return;
        }

        // sinon on remet le joueur à sa position avant le déplacement
        player.move(-x, -y);
        System.out.println("MOVEMENT IMPOSSIBLE \nObjet de la collision : "+ check.getValue1() + "\n");
        System.out.println("Player : "+ player.getPosition() + "\n");
    }


    /**
     * utilise l'objet en face du joueur
     * @param cmd commande du joueur
     */
    private void usePlayer(Cmd cmd) {
        int x = 0, y = 0;
        switch (player.getState()) {            
            case IDLE_LEFT:
                x -= 1;
                break;
            case IDLE_RIGHT:
                x += 1;
                break;
            case IDLE_UP:
                y += 1;
                break;
            case IDLE_DOWN:
                y -= 1;
                break;
            default:
                break;
        }

        // si la case utilisé est en dehors de la fenêtre, on sort de la fonction 
        if (!player.move(x, y)) {
            return;
        } 

        /** 
         * test s'il y a un objet à utiliser en face du joueur
         * et si oui retourne l'object on sort de la fonction sinon
         */
        Pair<Boolean, GameObject> check = currentLevel.checkMove(player);
        if (check.getValue1() == null) {
            player.move(-x, -y);
            return;
        }
        player.move(-x, -y);
        System.out.println("Player utilise : "+ check.getValue1() + "\n");
        // si l'object n'est pas utilisable, on sort de la fonction
        if (!check.getValue1().objectUse(player)) {
            return;
        }
        // sinon on utilise l'objet
    }

    @Override
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

}
