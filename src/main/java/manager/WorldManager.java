package manager;

import java.util.HashMap;

import org.javatuples.Pair;

import engine.Cmd;
import engine.Command;
import prefab.entity.GameObject;
import prefab.entity.Player;
import prefab.gui.InventoryHud;
import prefab.information.PlayerClasses;
import prefab.information.Position;
import prefab.level.GameLevel;
import prefab.information.State;

/**
 * gère le monde dans lequel le joueur évolue
 */
public class WorldManager {

    private final static int moveTIME = 200;

    // createurs
    LevelCreator levelCreator;
    HudCreator hudCreator;
    
    // niveaux
    public HashMap<String, GameLevel> gameLevels;
    public GameLevel currentLevel;

    // huds
    InventoryHud inventoryHud;
    
    // joueur
    Player Player;

    // mouvement du joueur autorisé ?
    boolean isKeyLocked = false;

    /**
     * constructeur de la classe WorldManager
     */
    public WorldManager() {    
        // Initialisation du joueur
        initPlayer();
        // Initialisation des niveaux
        initLevels();
        // Initialisation des Huds
        initHuds();
    }
    
    /**
     * initialise le joueur
     */
    public void initPlayer() {
        //TEST
        Position p1 = new Position(10, 10);
        Player = new Player(p1, null, "player", 1, 1, PlayerClasses.CLERIC); 
        System.out.println("Player : "+ Player.getPosition() + "\n");
    }

    /**
     * initialise les niveaux
     */
    public void initLevels() {
        LevelCreator levelManager = new LevelCreator();
        gameLevels = levelManager.getLevels();
        currentLevel = gameLevels.get("default");
    }

    /**
     * initialise les huds
     */
    public void initHuds() {
        HudCreator hudManager = new HudCreator(this.Player);
        inventoryHud = hudManager.getInventory();
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
        // calcul du déplacement à effectuer
        int x = 0, y = 0;
        switch (cmd) {            
            case LEFT:
                Player.setState(State.IDLE_LEFT);
                x -= 1;
                break;
            case RIGHT:
                Player.setState(State.IDLE_RIGHT);
                x += 1;
                break;
            case UP:
                Player.setState(State.IDLE_UP);
                y += 1;
                break;
            case DOWN:
                Player.setState(State.IDLE_DOWN);
                y -= 1;
                break;
            default:
                return;
        }

        // si le mouvement amène en dehors de la fenêtre, on sort de la fonction 
        if (!Player.move(x, y)) {
            return;
        } 
        /** 
         * test s'il y a un obstacle qui empêche le joueur de se déplacer 
         * et si oui retourne l'object bloquant
         */
        Pair<Boolean, GameObject> check = currentLevel.checkMove(Player);
        // si il n'y a pas d'obstacle, on sort de la fonction
        if (check.getValue0()) {
            System.out.println("Player : "+ Player.getPosition() + "\n");
            return;
        }

        // sinon on remet le joueur à sa position avant le déplacement
        Player.move(-x, -y);
        System.out.println("MOVEMENT IMPOSSIBLE \nObjet de la collision : "+ check.getValue1() + "\n");
        System.out.println("Player : "+ Player.getPosition() + "\n");
    }


    /**
     * utilise l'objet en face du joueur
     * @param cmd commande du joueur
     */
    private void usePlayer(Cmd cmd) {
        int x = 0, y = 0;
        switch (Player.getState()) {            
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
        if (!Player.move(x, y)) {
            return;
        } 

        /** 
         * test s'il y a un objet à utiliser en face du joueur
         * et si oui retourne l'object on sort de la fonction sinon
         */
        Pair<Boolean, GameObject> check = currentLevel.checkMove(Player);
        if (check.getValue1() == null) {
            return;
        }
        Player.move(-x, -y);
        System.out.println("Player utilise : "+ check.getValue1() + "\n");
        // si l'object n'est pas utilisable, on sort de la fonction
        if (!check.getValue1().objectUse(Player)) {
            return;
        }
        // sinon on utilise l'objet
    }
}
